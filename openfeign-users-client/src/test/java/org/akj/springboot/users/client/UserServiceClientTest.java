package org.akj.springboot.users.client;

import org.akj.springboot.users.domain.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class UserServiceClientTest {
    @Autowired
    private UserServiceClient userServiceClient;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getAllUsers() {
        List<User> allUsers = userServiceClient.getAllUsers(0, 10, null);
        Assertions.assertTrue(allUsers.size() >= 0);

//        allUsers = userServiceClient.getAllUsers(null, null, "gary");
//        Assertions.assertTrue(allUsers.size() >= 1);
    }

    @Test
    void addUser() {
        User user = User.builder()
                .userInfo(UserInfo.builder().age(18).firstName("Gary").lastName("Zhang").gender(Gender.MALE).phone("NA").build())
                .authType(AuthType.FACEBOOK).userStatus(UserStatus.INACTIVE).userName("gary-03").build();
        user = this.userServiceClient.addUser(user);

        Assertions.assertNotNull(user.getId());
    }

    @Test
    void updateUser() {
        String uid = "61c99264aaa4496c51c5d19a";
        String phone = "+8155346789";
        User user = User.builder()
                .userInfo(UserInfo.builder().age(18).firstName("Gary").lastName("Zhang").gender(Gender.MALE).phone(phone).build())
                .authType(AuthType.FACEBOOK).userStatus(UserStatus.INACTIVE).userName("gary-03").id(uid).build();
        user = this.userServiceClient.updateUser(uid, user);

        Assertions.assertEquals(phone, user.getUserInfo().getPhone());
    }

    @Test
    void getUserDetails() {
        String uid = "61c99264aaa4496c51c5d19a";
        User user = this.userServiceClient.getUserDetails(uid);
        Assertions.assertNotNull(user, "user not found");
    }

    @Test
    void delete() {
        String uid = "61c99264aaa4496c51c5d19a";
        this.userServiceClient.deleteUser(uid);
    }

}