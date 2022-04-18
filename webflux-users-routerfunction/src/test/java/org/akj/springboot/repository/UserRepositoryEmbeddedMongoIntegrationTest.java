//package org.akj.springboot.repository;
//
//import org.akj.springboot.domain.*;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.reactive.server.WebTestClient;
//import org.springframework.transaction.annotation.Transactional;
//import reactor.core.publisher.Flux;
//import reactor.test.StepVerifier;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@TestConfiguration("classpath:application-integration-test.yml")
//@ActiveProfiles("integration-test")
//class UserRepositoryEmbeddedMongoIntegrationTest {
//    @Autowired
//    UserRepository userRepository;
//
//    @BeforeEach
//    public void setup() {
//        userRepository.saveAll(List.of(createUser("james"), createUser("gary")));
//    }
//
//    @Test
//    void findAllUserWithPagination() {
//    }
//
//    @Test
//    void findByUserName() {
//        String userName = "gary";
//        Flux<User> userFlux = this.userRepository.findByUserName(userName);
//
//        StepVerifier.create(userFlux)
//                .expectNextMatches(u -> u.getId() != null && u.getUserName().equals(userName))
//                .expectComplete()
//                .verify();
//    }
//
//    @AfterEach
//    public void teardown() {
//        userRepository.deleteAll();
//    }
//
//    private User createUser(String userName) {
//        return User.builder()
//                .userInfo(UserInfo.builder().age(18).firstName("xxx").lastName("xxx")
//                        .gender(Gender.MALE).phone("NA").build())
//                .authType(AuthType.FACEBOOK).userStatus(UserStatus.INACTIVE).userName(userName).build();
//    }
//
//}