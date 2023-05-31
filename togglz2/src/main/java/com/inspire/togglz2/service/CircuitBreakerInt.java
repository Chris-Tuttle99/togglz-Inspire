package com.inspire.togglz2.service;

import com.inspire.togglz2.entity.Product;
import reactor.core.publisher.Mono;

import java.util.List;

public interface CircuitBreakerInt {

    public Mono<Object[]> getDiscounted(List<Product> prod);
}
