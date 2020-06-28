package com.coffee.coffeeapi;

import com.coffee.coffeeapi.model.Product;
import com.coffee.coffeeapi.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class CoffeeApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoffeeApiApplication.class, args);
	}


	/*@Bean
	CommandLineRunner init(ProductRepository repository) {

		return args -> {
			Flux<Product> productFlux = Flux.just(
					Product.builder()
							.name("Big Latte")
							.price(2.99)
							.build(),
					Product.builder()
							.name("Big Decaf")
							.price(2.49)
							.build(),
					Product.builder()
							.name("Green Tea")
							.price(1.99)
							.build()
					).flatMap(p -> repository.save(p));

			productFlux.thenMany(repository.findAll())
				.subscribe(System.out::println);
		};
	}*/

}
