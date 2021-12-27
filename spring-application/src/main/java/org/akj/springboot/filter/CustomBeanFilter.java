package org.akj.springboot.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

@Slf4j
public class CustomBeanFilter implements TypeFilter {
    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        String clazz = metadataReader.getClassMetadata().getClassName();
        log.info(clazz);
        return clazz.contains("HelloWorldController");
    }
}
