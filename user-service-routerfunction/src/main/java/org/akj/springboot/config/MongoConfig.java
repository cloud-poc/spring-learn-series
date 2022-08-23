package org.akj.springboot.config;

import com.mongodb.reactivestreams.client.MongoClient;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;

@Configuration
@Profile("integration-test")
public class MongoConfig {

    @Bean
    @DependsOn("mongoServer")
    public SimpleReactiveMongoDatabaseFactory reactiveMongoDatabaseFactory(MongoProperties properties,
                                                                           MongoClient mongo) {
        String database = properties.getMongoClientDatabase();
        return new SimpleReactiveMongoDatabaseFactory(mongo, database);
    }
}
