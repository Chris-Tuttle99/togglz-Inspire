package com.inspire.togglz2.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspire.togglz2.entity.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CircuitBreakImpl implements CircuitBreakerInt{

    private static final Logger LOG = LoggerFactory.getLogger(CircuitBreakImpl.class);

    private final WebClient webClient;
    private final ReactiveCircuitBreaker discountCircuitBreaker;

    public CircuitBreakImpl(ReactiveCircuitBreakerFactory circuitBreakerFactory){
        this.webClient= WebClient.builder().baseUrl("http://localhost:8081").build();
        this.discountCircuitBreaker=circuitBreakerFactory.create("discounts");
    }

    @Override
    public Mono<Object[]> getDiscounted(List<Product> products) {

        return discountCircuitBreaker.run(webClient.post().uri("/discounts").header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(products),Product.class).retrieve().bodyToMono(Object[].class),
                throwable -> {
            return Mono.just(new Object[]{new Product(0,"FallbackProduct",0.00)});
        });

    }
}
