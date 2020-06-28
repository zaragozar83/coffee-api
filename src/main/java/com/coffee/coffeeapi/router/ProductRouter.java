package com.coffee.coffeeapi.router;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;
import static org.springframework.web.reactive.function.server.RequestPredicates.method;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import com.coffee.coffeeapi.handler.ProductHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ProductRouter {

  @Bean
  RouterFunction<ServerResponse> routes(ProductHandler handler) {

//    return RouterFunctions.route(RequestPredicates.GET("/products")
//                                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
//                                  handler::getAllProducts)
//                          .andRoute(RequestPredicates.POST("/products")
//                                        .and(RequestPredicates.contentType(MediaType.APPLICATION_JSON)),
//                                  handler::saveProduct)
//                          .andRoute(RequestPredicates.DELETE("/products")
//                                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
//                                  handler::deleteAllProducts)
//                          .andRoute(RequestPredicates.GET("/products/events")
//                                        .and(RequestPredicates.accept(MediaType.TEXT_EVENT_STREAM)),
//                                  handler::getProductEvents)
//                          .andRoute(RequestPredicates.GET("/products/{id}")
//                                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
//                                  handler::getProduct)
//                          .andRoute(RequestPredicates.PUT("/products/{id}")
//                                        .and(RequestPredicates.contentType(MediaType.APPLICATION_JSON)),
//                                  handler::updateProduct)
//                          .andRoute(RequestPredicates.DELETE("/product/{id}")
//                                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
//                                  handler::deleteProduct);

    return nest(path("/products"),
              nest(accept(APPLICATION_JSON).or(contentType(APPLICATION_JSON)).or(accept(TEXT_EVENT_STREAM)),
                      route(GET("/"), handler::getAllProducts)
                              .andRoute(method(HttpMethod.POST), handler::saveProduct)
                              .andRoute(DELETE("/"), handler::deleteAllProducts)
                              .andRoute(GET("/events"), handler::getProductEvents)
                              .andNest(path("/{id}"),
                                  route(method(HttpMethod.GET), handler::getProduct)
                                                    .andRoute(method(HttpMethod.PUT), handler::updateProduct)
                                                    .andRoute(method(HttpMethod.DELETE), handler::deleteProduct)
                              )
              )
    );
  }

}
