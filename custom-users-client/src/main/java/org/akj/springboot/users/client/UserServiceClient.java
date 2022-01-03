package org.akj.springboot.users.client;

import org.akj.springboot.rest.delegate.annotation.WebClient;
import org.akj.springboot.users.domain.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@WebClient(url = "http://localhost:9000")
public interface UserServiceClient {

    @GetMapping("/v1/users")
    List<User> getAllUsers(@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize,
                           @RequestParam("userName") String userName);

    @PostMapping("/v1/users")
    User addUser(@RequestBody User user);

    @PutMapping("/v1/users/{uid}")
    User updateUser(@PathVariable String uid, @RequestBody User user);

    @GetMapping("/v1/users/{uid}")
    User getUserDetails(@PathVariable String uid);

    @DeleteMapping("/v1/users/{uid}")
    void deleteUser(@PathVariable String uid);
}
