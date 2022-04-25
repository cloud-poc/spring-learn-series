package org.akj.springboot.repository

import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import reactor.test.StepVerifier
import spock.lang.Specification
import spock.lang.Unroll

import java.time.Duration

@Testcontainers
@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
//@TestConfiguration("classpath:application-integration-test.yaml")
@ActiveProfiles("integration-test")
@Slf4j
class UserRepositoryIntegrationTest extends Specification {
    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:5.0.5")

    @Autowired
    UserRepository userRepository

    @DynamicPropertySource
    static def setProperties(DynamicPropertyRegistry registry) {
        mongoDBContainer.start()

        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl)
    }

    @Unroll
    def "validate create user: #userName"() {
        when:
        def flux = userRepository.save(user);

        then:
        StepVerifier.create(flux)
                .expectNextMatches(u -> u.getId() != null && u.getUserName().equals(userName))
                .expectComplete()
                .verify()

        where:
        userName | user
        "gary"   | createUser("gary")
        "james"  | createUser("james")
    }

    @Unroll
    def "validate retrieve users by pagination, pageSize: #pageRequest.getPageSize(), pageNumber: #pageRequest.getPageNumber()"() {
        given:
        def users = createUsers()
        def flux = userRepository.saveAll(users);

        when:
        def userFluxPageOne = userRepository.findAllUserWithPagination(pageRequest)

        then:
        StepVerifier.create(userFluxPageOne).expectNextCount(1).expectComplete().verifyThenAssertThat(Duration.ofSeconds(30))

        where:
        pageRequest << [PageRequest.of(0, 1).withSort(Sort.Direction.ASC, "userName"),
                        PageRequest.of(1, 1).withSort(Sort.Direction.ASC, "userName")]

    }

    @Unroll
    def "validate find user by userName, userName=#userName"() {
        given:
        def users = createUsers()
        userRepository.saveAll(users);

        when:
        def userMono = userRepository.findByUserName(userName)

        then:
        StepVerifier.create(userMono)
                .expectNextMatches(u -> u.getId() != null && u.getUserName().equals(userName))
                .expectComplete()
                .verify()

        where:
        userName << ["gary", "james"]
    }

    @Unroll
    def "validate user deletion, user name:#userName"() {
        given: "create user:" + userName
        def user = createUser(userName)
        def u = userRepository.save(user).block()

        when: "deleted user " + userName + " - permanently deletion"
        println(u.getId())
        userRepository.delete(u).block()

        then: "verify no such user " + userName
        StepVerifier.create(this.userRepository.findByUserName(userName))
                .expectComplete()
                .verifyThenAssertThat(Duration.ofSeconds(15))
        where:
        userName << ["jerry", "king", "Qiwadi"]
    }

    def createUsers() {
        return List.of(createUser("gary"), createUser("james"));
    }

    def createUser(userName) {
        return User.builder()
                .userInfo(UserInfo.builder().age(18).firstName("xxx").lastName("xxx")
                        .gender(Gender.MALE).phone("NA").build())
                .authType(AuthType.FACEBOOK).userStatus(UserStatus.INACTIVE).userName(userName).build();
    }

    def cleanup() {
        userRepository.deleteAll()
    }
}
