package org.akj.springboot.repository;

import org.akj.springboot.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {
    @Query(value = "{}")
    Flux<User> findAllUserWithPagination(Pageable page);

    Flux<User> findByUserName(String userName);
}
