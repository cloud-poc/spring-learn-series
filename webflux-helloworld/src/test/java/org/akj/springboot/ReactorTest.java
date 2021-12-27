package org.akj.springboot;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

import java.util.Random;
import java.util.function.IntSupplier;

@Slf4j
public class ReactorTest {
    @Test
    public void test() {
        IntSupplier supplier = () -> new Random().nextInt(20);
        String[] arrays = {"1", "2", "3"};

        Subscriber<Integer> subscriber = new Subscriber<Integer>() {
            private Subscription subscription;

            @Override
            public void onSubscribe(Subscription subscription) {
                this.subscription = subscription;

                this.subscription.request(1);
            }

            @Override
            public void onNext(Integer item) {
                log.debug("data processed: {}", item);

                this.subscription.request(2);
            }

            @Override
            public void onError(Throwable throwable) {
                log.error(throwable.getMessage(), throwable);
                this.subscription.cancel();
            }

            @Override
            public void onComplete() {
                log.debug("all data processed finished..");
            }
        };

        Flux.fromArray(arrays).map(s -> Integer.parseInt(s)).subscribe(subscriber);
    }
}
