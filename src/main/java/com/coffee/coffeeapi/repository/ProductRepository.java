package com.coffee.coffeeapi.repository;

import com.coffee.coffeeapi.model.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ProductRepository extends ReactiveCrudRepository<Product, String> {

  Flux<Product> findByNameOrderByPrice(String name);
}
