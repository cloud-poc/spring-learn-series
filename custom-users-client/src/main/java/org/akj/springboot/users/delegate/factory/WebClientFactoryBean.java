package org.akj.springboot.users.delegate.factory;

import lombok.extern.slf4j.Slf4j;
import org.akj.springboot.users.client.UserServiceClient;
import org.akj.springboot.users.delegate.DelegateProxy;
import org.akj.springboot.users.delegate.annotation.WebClient;
import org.akj.springboot.users.delegate.bean.MethodMetaData;
import org.akj.springboot.users.delegate.bean.ServerInfo;
import org.akj.springboot.users.delegate.handler.GenericHttpHandler;
import org.akj.springboot.users.delegate.handler.HttpHandler;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.*;
import java.util.LinkedHashMap;
import java.util.stream.Stream;

import static java.lang.reflect.Proxy.newProxyInstance;
import static org.springframework.core.annotation.AnnotatedElementUtils.findMergedAnnotation;

@Component
@Slf4j
public class WebClientFactoryBean implements FactoryBean<UserServiceClient> {


    @Override
    public UserServiceClient getObject() throws Exception {
        log.info("start WebClientFactoryBean#getObject().");
        final HttpHandler httpHandler = new GenericHttpHandler(getServerInfo());

        DelegateProxy proxy = t -> {
            return newProxyInstance(this.getClass().getClassLoader(), new Class[]{getObjectType()},
                    new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            String identifier = getMethodIdentifier(method);
                            log.info("method invoked: {}", identifier);

                            MethodMetaData methodMetaData = extractMethodInfo(method, args);
                            log.info("method metadata: {}", methodMetaData);

                            return httpHandler.invoke(methodMetaData);
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

                        private MethodMetaData extractMethodInfo(Method method, Object[] args) {
                            MethodMetaData.MethodMetaDataBuilder builder = MethodMetaData.builder();
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
                            for (int i = 0; i < parameters.length; i++) {
                                Parameter p = parameters[i];
                                if (p.isAnnotationPresent(PathVariable.class)) {
                                    PathVariable pAnnotation = p.getAnnotation(PathVariable.class);
                                    String value = pAnnotation.value();
                                    if (Strings.isEmpty(value)) {
                                        paramMap.put(p.getName(), args[i]);
                                        continue;
                                    }
                                    paramMap.put(value, args[i]);
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

                            return builder.build();
                        }

                    });
        };


        return (UserServiceClient) proxy.newInstance(UserServiceClient.class);
    }

    @Override
    public Class<?> getObjectType() {
        return UserServiceClient.class;
    }

    private ServerInfo getServerInfo() {
        WebClient annotation = getObjectType().getAnnotation(WebClient.class);
        String url = annotation.url();
        Assert.notNull(url, "url must not be empty.");

        return ServerInfo.builder().url(url).build();
    }
}
