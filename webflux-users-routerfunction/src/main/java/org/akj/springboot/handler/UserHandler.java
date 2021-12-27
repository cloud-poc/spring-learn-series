package org.akj.springboot.handler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;
import ma.glasnost.orika.MapperFacade;
import org.akj.springboot.validator.GenericValidator;
import org.akj.springboot.domain.User;
import org.akj.springboot.domain.UserStatus;
import org.akj.springboot.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class UserHandler {

    private UserRepository userRepository;
    private GenericValidator validator;
    private MapperFacade mapperFacade;

    public UserHandler(UserRepository userRepository, GenericValidator validator, MapperFacade mapperFacade) {
        this.userRepository = userRepository;
        this.validator = validator;
        this.mapperFacade = mapperFacade;
    }

    @Operation(summary = "retrieve all users")
    public Mono<ServerResponse> getAllUsers(ServerRequest request) {
        Flux<User> users = null;

        Optional<String> userName = request.queryParam("userName");
        if (userName.isPresent()) {
            users = this.userRepository.findByUserName(userName.get());
        } else {
            Integer pageNo = null;
            Integer pageSize = null;
            Optional<String> pageNoOptional = request.queryParam("pageNo");
            Optional<String> pageSizeOptional = request.queryParam("pageSize");
            pageNo = pageNoOptional.isPresent() ? Integer.valueOf(pageNoOptional.get()) : null;
            pageSize = pageSizeOptional.isPresent() ? Integer.valueOf(pageSizeOptional.get()) : null;
            this.validate(pageNo, pageSize);

            Pageable pageRequest = PageRequest.of(pageNo, pageSize).withSort(Sort.Direction.ASC, "userName");
            users = this.userRepository.findAllUserWithPagination(pageRequest);
        }

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(users, User.class);
    }

    @Operation(summary = "register new user profile")
    public Mono<ServerResponse> addUser(ServerRequest request) {
        Mono<User> userMono = request.bodyToMono(User.class).doOnNext(validator::validate);

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(this.userRepository.saveAll(userMono), User.class);
    }

    @Operation(summary = "retrieve user details")
    public Mono<ServerResponse> getUser(ServerRequest request) {
        String id = request.pathVariable("uid");

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(this.userRepository.findById(id), User.class);
    }

    @Operation(summary = "delete user profile")
    public Mono<ServerResponse> deleteUser(ServerRequest request) {
        String id = request.pathVariable("uid");

        return this.userRepository.findById(id).flatMap(u -> {
            u.setUserStatus(UserStatus.INACTIVE);
            return this.userRepository.save(u).then(ServerResponse.ok().build());
        }).switchIfEmpty(ServerResponse.notFound().build());
    }

    @Operation(summary = "update a user")
    public Mono<ServerResponse> updateUser(ServerRequest request) {
        String id = request.pathVariable("uid");
        Mono<User> userMono = request.bodyToMono(User.class).doOnNext(validator::validate);

        return this.userRepository.findById(id).flatMap(u -> {
            Mono<User> newMono = userMono.flatMap(user -> {
                user.setId(id);
//                mapperFacade.map();
                user.setCreateBy(u.getCreateBy());
                user.setCreateDate(u.getCreateDate());
                user.setLastModifiedBy(u.getLastModifiedBy());
                user.setLastModifiedDate(u.getLastModifiedDate());
                return Mono.just(user);
            });
            return ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON).body(this.userRepository.saveAll(newMono), User.class);
        }).switchIfEmpty(ServerResponse.notFound().build());
    }

    private void validate(Integer pageNo, Integer pageSize) {
        Assert.notNull(pageNo, "pageNo must not be empty.");
        Assert.notNull(pageSize, "pageSize must not be empty.");

        Assert.isTrue(pageNo >= 0, "the range of pageNo is [0,]");
        Assert.isTrue(pageSize >= 0 && pageSize <= 1000, "the range of pageNo is [0, 1000]");
    }
}
