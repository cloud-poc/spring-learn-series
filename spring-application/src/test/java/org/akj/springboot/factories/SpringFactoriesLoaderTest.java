package org.akj.springboot.factories;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.net.URL;
import java.util.*;

@Slf4j
public class SpringFactoriesLoaderTest {

    @Test
    public void test() throws IOException {
        ClassLoader classLoader = SpringApplication.class.getClassLoader();
        Enumeration<URL> resources = classLoader.getResources("META-INF/spring.factories");
        var map = new HashMap<String, Map<String, List<String>>>();

        while (resources.hasMoreElements()) {
            HashMap<String, List<String>> resourceMap = new HashMap<>();
            URL url = resources.nextElement();
            log.debug("loaded spring factories: {}", url.getPath());
            UrlResource resource = new UrlResource(url);
            Properties properties = PropertiesLoaderUtils.loadProperties(resource);
            properties.entrySet().stream().forEach((item) -> {
                resourceMap.computeIfAbsent(((String) item.getKey()).trim(), key -> new ArrayList<>()).addAll(Arrays.asList(((String) item.getValue()).split(",")));
            });
            map.put(url.getPath(), resourceMap);
        }

        log.debug("file count: {}", map.size());
        var cache = new HashMap<String, List<String>>();
        map.entrySet().forEach(item -> {
            item.getValue().entrySet().forEach(i -> {
                cache.computeIfAbsent(i.getKey(), k-> new ArrayList<>()).addAll(i.getValue());
            });
        });

        log.debug("result of cache.get(\"org.springframework.boot.BootstrapRegistryInitializer\"): {}", cache.get("org.springframework.boot.BootstrapRegistryInitializer"));
        log.debug("result of cache.get(\"org.springframework.context.ApplicationContextInitializer\"): {}", cache.get("org.springframework.context.ApplicationContextInitializer"));
        log.debug("result of cache.get(\"org.springframework.context.ApplicationListener\"): {}", cache.get("org.springframework.context.ApplicationListener"));

    }
}
