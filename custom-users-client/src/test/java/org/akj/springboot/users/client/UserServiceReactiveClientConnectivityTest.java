package org.akj.springboot.users.client;

import lombok.SneakyThrows;
import org.akj.springboot.users.domain.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

@SpringBootTest
public class UserServiceReactiveClientConnectivityTest {
    @Autowired
    private UserServiceReactiveClient userServiceClient;

    @BeforeEach
    void setUp() {
    }

    @SneakyThrows
    @Test
    void getAllUsers() {
        Flux<User> allUsers = userServiceClient.getAllUsers(0, 10, null);
        allUsers.subscribe(System.out::println, e -> {
            System.out.println(e);
        });

        allUsers = userServiceClient.getAllUsers(null, null, "gary");
        allUsers.subscribe(System.out::println, e -> {
            System.out.println(e);
        });

        Thread.sleep(30000);
    }

    @SneakyThrows
    @Test
    void addUser() {
        String userName = "gary-6";
        Flux<User> users = this.userServiceClient.getAllUsers(null, null, userName);
        users.filter(u -> u.getUserName().equals(userName)).count().subscribe(i -> {
            if (i > 0) return;
        });

        User user = User.builder()
                .userInfo(UserInfo.builder().age(18).firstName("Gary").lastName("Zhang").gender(Gender.MALE).phone("NA").build())
                .authType(AuthType.FACEBOOK).userStatus(UserStatus.INACTIVE).userName(userName).build();
        this.userServiceClient.addUser(Mono.just(user)).subscribe(u -> {
            Assertions.assertNotNull(user.getId());
        }, e -> {
            System.out.println(e.getMessage());
        });


        Thread.sleep(30000);
    }
//
//    @Test
//    void updateUser() {
//        String userName = "gary-5";
//        User user = null;
//        List<User> users = this.userServiceClient.getAllUsers(null, null, userName);
//        if (users.size() > 0) user = users.get(0);
//
//        String uid = user.getId();
//        String phone = "+8155346789";
//        user.getUserInfo().setPhone(phone);
//        user = this.userServiceClient.updateUser(uid, user);
//
//        Assertions.assertEquals(phone, user.getUserInfo().getPhone());
//    }
//
//    @Test
//    void getUserDetails() {
//        String uid = "61c99264aaa4496c51c5d19a";
//        User user = this.userServiceClient.getUserDetails(uid);
//        Assertions.assertNotNull(user, "user not found");
//    }
//
//    @Test
//    void delete() {
//        String uid = "61c99264aaa4496c51c5d19a";
//        this.userServiceClient.deleteUser(uid);
//    }
}