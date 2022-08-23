package org.akj.autoconfigure;

import org.junit.jupiter.api.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.testcontainers.containers.MongoDBContainer;

@Configuration
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE - 10)
@Profile("integration-test")
public class MongoTestAutoConfiguration {

    static Logger log = LoggerFactory.getLogger(MongoTestAutoConfiguration.class);

    @Bean
    public MongoServer mongoServer() {
        MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:5.0.5");
        mongoDBContainer.start();
        String url = mongoDBContainer.getReplicaSetUrl();
        System.setProperty("spring.data.mongodb.uri", url);
        log.info("Mongo configuration for testing has been completed, url: {}", url);

        return new MongoServer(mongoDBContainer);
    }

    class MongoServer {
        private MongoDBContainer mongoDBContainer;

        public MongoServer(MongoDBContainer mongoDBContainer) {
            this.mongoDBContainer = mongoDBContainer;
        }

        public void close() {
            if (mongoDBContainer != null) {
                mongoDBContainer.close();
            }
            log.info("Mongo(for testing) is closed");
        }
    }
}
