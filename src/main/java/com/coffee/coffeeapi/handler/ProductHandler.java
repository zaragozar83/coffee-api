package com.coffee.coffeeapi.handler;

import com.coffee.coffeeapi.model.Product;
import com.coffee.coffeeapi.model.ProductEvent;
import com.coffee.coffeeapi.repository.ProductRepository;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class ProductHandler {

  private final ProductRepository productRepository;

  public Mono<ServerResponse> getAllProducts(ServerRequest request) {

    Flux<Product> products = productRepository.findAll();

    return ServerResponse.ok()
                          .contentType(MediaType.APPLICATION_JSON)
                          .body(products, Product.class);
  }

  public Mono<ServerResponse> getProduct(ServerRequest request) {

    String id = request.pathVariable("id");

    Mono<Product> productMono = productRepository.findById(id);
    Mono<ServerResponse> notfound = ServerResponse.notFound().build();

    return productMono
              .flatMap(product ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(BodyInserters.fromValue(product)))
              .switchIfEmpty(notfound);
  }
  
  public Mono<ServerResponse> saveProduct(ServerRequest request) {

    Mono<Product> productMono = request.bodyToMono(Product.class);

    return productMono.flatMap(product ->
              ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(productRepository.save(product), Product.class));
  }

  public Mono<ServerResponse> updateProduct(ServerRequest request) {

    String id = request.pathVariable("id");

    Mono<Product> existingProductMono = productRepository.findById(id);
    Mono<Product> productMonoRequest = request.bodyToMono(Product.class);

    Mono<ServerResponse> notFoundMono = ServerResponse.notFound().build();

    return productMonoRequest.zipWith(existingProductMono,
            (product, existingProduct) -> Product.builder()
                                                  .id(existingProduct.getId())
                                                  .name(product.getName())
                                                  .price(product.getPrice())
                                                .build())
            .flatMap(product ->
                      ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(productRepository.save(product), Product.class)
            ).switchIfEmpty(notFoundMono);

  }

  public Mono<ServerResponse> deleteProduct(ServerRequest request) {

    String id = request.pathVariable("id");

    Mono<Product> existingProductMono = productRepository.findById(id);

    Mono<ServerResponse> notFoundMono = ServerResponse.notFound().build();

    return existingProductMono
              .flatMap(product ->
                  ServerResponse.ok()
                        .build(productRepository.delete(product))
              ).switchIfEmpty(notFoundMono);

  }

  public Mono<ServerResponse> deleteAllProducts(ServerRequest request) {

    return ServerResponse.ok()
              .build(productRepository.deleteAll());
  }

  public Mono<ServerResponse> getProductEvents(ServerRequest request) {
    Flux<ProductEvent> eventsFlux = Flux.interval(Duration.ofSeconds(1))
                                          .map(v -> ProductEvent.builder()
                                                                .eventId(v)
                                                                .eventType("Product Event")
                                                                .build());

    return ServerResponse.ok()
                          .contentType(MediaType.TEXT_EVENT_STREAM)
                          .body(eventsFlux, Product.class);

  }

}
