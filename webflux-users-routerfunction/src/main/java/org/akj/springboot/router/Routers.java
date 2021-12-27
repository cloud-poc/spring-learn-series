package org.akj.springboot.router;

import org.akj.springboot.handler.UserHandler;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class Routers {

    @Bean
    @RouterOperations({@RouterOperation(path = "/v1/users", method = RequestMethod.GET, beanClass = UserHandler.class, beanMethod = "getAllUsers"),
            @RouterOperation(path = "/v1/users", method = RequestMethod.POST, beanClass = UserHandler.class, beanMethod = "addUser"),
            @RouterOperation(path = "/v1/users/{uid}", method = RequestMethod.GET, beanClass = UserHandler.class, beanMethod = "getUser"),
            @RouterOperation(path = "/v1/users/{uid}", method = RequestMethod.DELETE, beanClass = UserHandler.class, beanMethod = "deleteUser"),
            @RouterOperation(path = "/v1/users/{uid}", method = RequestMethod.PUT, beanClass = UserHandler.class, beanMethod = "updateUser",produces = "application/json")})
    RouterFunction<ServerResponse> userRouter(UserHandler handler) {
        return RouterFunctions.nest(path("/v1/users"),
                RouterFunctions.route(GET("").and(queryParam("pageNo", t -> Integer.valueOf(t) >= 0)
                                .and(queryParam("pageSize", t -> Integer.valueOf(t) >= 0))), handler::getAllUsers)
                        .andRoute(POST("").and(accept(MediaType.APPLICATION_JSON)), handler::addUser)
                        .andRoute(GET("/{uid}"), handler::getUser)
                        .andRoute(DELETE("/{uid}"), handler::deleteUser)
                        .andRoute(PUT("/{uid}"), handler::updateUser));

    }

}
