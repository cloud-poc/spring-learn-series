package org.akj.springboot.rest.delegate.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.akj.springboot.rest.delegate.DelegateProxy;
import org.akj.springboot.rest.delegate.annotation.ReactiveWebClient;
import org.akj.springboot.rest.delegate.annotation.WebClient;
import org.akj.springboot.rest.delegate.bean.MethodMetaData;
import org.akj.springboot.rest.delegate.bean.ReactiveMethodMetaData;
import org.akj.springboot.rest.delegate.bean.ServerInfo;
import org.akj.springboot.rest.delegate.handler.HttpClientHttpHandler;
import org.akj.springboot.rest.delegate.handler.HttpHandler;
import org.akj.springboot.rest.delegate.handler.WebClientHttpHandler;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.*;
import java.util.LinkedHashMap;
import java.util.stream.Stream;

import static java.lang.reflect.Proxy.newProxyInstance;
import static org.springframework.core.annotation.AnnotatedElementUtils.findMergedAnnotation;

//@Component
@Slf4j
@Data
public class WebClientFactoryBean implements FactoryBean<Object> {
    @Autowired
    private ObjectMapper mapper;

    private Class proxyType;

    public WebClientFactoryBean() {
    }

    @Override
    public Object getObject() throws Exception {
        log.info("start WebClientFactoryBean#getObject().");

        Assert.isTrue(this.isSupported(), "unsupported user interface.");
        log.info("create HttpHandler.");
        final HttpHandler httpHandler = isReactive() ? new WebClientHttpHandler(getServerInfo()) :
                new HttpClientHttpHandler(getServerInfo(), mapper);
        log.info("Http handler created, {}", httpHandler);

        DelegateProxy proxy = t -> {
            return newProxyInstance(this.getClass().getClassLoader(), new Class[]{getObjectType()},
                    new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            String identifier = getMethodIdentifier(method);
                            log.info("method invoked: {}", identifier);

                            MethodMetaData methodMetaData = extractMethodInfo(method, args);
                            log.info("method metadata: {}", methodMetaData);

                            Object retValue = httpHandler.invoke(methodMetaData);
                            log.info("request process finished.");

                            return retValue;
                        }
                    });
        };


        return proxy.newInstance(proxyType);
    }

    @Override
    public Class<?> getObjectType() {
        return proxyType;
    }

    private boolean isSupported() {
        return getObjectType().isAnnotationPresent(WebClient.class) ||
                getObjectType().isAnnotationPresent(ReactiveWebClient.class);
    }

    private boolean isReactive() {
        return getObjectType().isAnnotationPresent(ReactiveWebClient.class);
    }

    private ServerInfo getServerInfo() {
        String url = null;
        if (isReactive()) {
            ReactiveWebClient annotation = getObjectType().getAnnotation(ReactiveWebClient.class);
            url = annotation.url();
        } else {
            WebClient annotation = getObjectType().getAnnotation(WebClient.class);
            url = annotation.url();
        }
        Assert.notNull(url, "url must not be empty.");

        return ServerInfo.builder().url(url).build();
    }

    private MethodMetaData extractMethodInfo(Method method, Object[] args) {
        boolean reactive = isReactive();
        log.info("extract method info for {}, isReactive: ", method, reactive);
        MethodMetaData methodMetaData = null;

        if (reactive) {
            ReactiveMethodMetaData.ReactiveMethodMetaDataBuilder builder = ReactiveMethodMetaData
                    .builder().isReactive(true);
            extractMethodInfoFromAnnotation(method, args, builder);
            ReactiveMethodMetaData reactiveMethodMetaData = builder.build();
            extractAdditionalInfo(method, reactiveMethodMetaData);
            return reactiveMethodMetaData;
        } else {
            MethodMetaData.MethodMetaDataBuilder builder = MethodMetaData.builder().isReactive(false);
            extractMethodInfoFromAnnotation(method, args, builder);
            methodMetaData = builder.build();
        }

        return methodMetaData;
    }

    private void extractAdditionalInfo(Method method, ReactiveMethodMetaData methodMetaData) {
        // is Mono or Flux
        methodMetaData.setFlux(methodMetaData.getReturnType().isAssignableFrom(Flux.class));

        //adjust the request Body
        methodMetaData.setReactiveBody((Mono) methodMetaData.getBody());

        Type genericReturnType = method.getGenericReturnType();
        if (genericReturnType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericReturnType;
            Type[] types = parameterizedType.getActualTypeArguments();
            methodMetaData.setReactiveBodyType((Class<?>) types[0]);
        }
    }

    private void extractMethodInfoFromAnnotation(Method method, Object[] args,
                                                 MethodMetaData.MethodMetaDataBuilder builder) {
        RequestMapping methodMapping = findMergedAnnotation(method, RequestMapping.class);
        if (methodMapping != null) {
            //RequestMapping annotation = method.getAnnotation(RequestMapping.class);
            builder.method(methodMapping.method()[0]);

            String[] value = methodMapping.value();
            Assert.isTrue(null != value && value.length == 1, "value should not " +
                    "be empty and only one value is allowed.");
            String url = value[0];
            builder.url(url);
        }

        // get path variables
        Parameter[] parameters = method.getParameters();
        LinkedHashMap<String, Object> paramMap = new LinkedHashMap<>();
        builder.params(paramMap);

        LinkedHashMap<String, Object> pathVarsMap = new LinkedHashMap<>();
        builder.pathVariables(pathVarsMap);
        for (int i = 0; i < parameters.length; i++) {
            if (args[i] == null) continue;

            Parameter p = parameters[i];
            if (p.isAnnotationPresent(PathVariable.class)) {
                PathVariable pAnnotation = p.getAnnotation(PathVariable.class);
                String value = pAnnotation.value();
                if (Strings.isEmpty(value)) {
                    pathVarsMap.put(p.getName(), args[i]);
                    continue;
                }
                pathVarsMap.put(value, args[i]);
            }

            if (p.isAnnotationPresent(RequestBody.class)) {
                builder.body(args[i]);
            }

            if (p.isAnnotationPresent(RequestParam.class)) {
                RequestParam pAnnotation = p.getAnnotation(RequestParam.class);
                paramMap.put(pAnnotation.value(), args[i]);
            }

        }

        //return type
        builder.returnType(method.getReturnType());
        Type genericReturnType = method.getGenericReturnType();
        if (genericReturnType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericReturnType;
            Type[] types = parameterizedType.getActualTypeArguments();
            builder.actualReturnType((Class<?>) types[0]);
        }
    }

    private String getMethodIdentifier(Method method) {
        StringBuilder builder = new StringBuilder();
        String prefix = getObjectType().getName();
        builder.append(prefix)
                .append("#")
                .append(method.getName())
                .append("(");

        Stream.of(method.getParameters()).forEach(p -> builder.append(p.getType().getName()).append(","));
        String string = builder.toString();
        string = string.endsWith(",") ? string.substring(0, string.length() - 1) : string;

        return string + ")";
    }
}
