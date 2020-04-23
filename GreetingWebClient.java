package hello;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

public class GreetingWebClient {
  private WebClient client = WebClient.create("http://localhost:8180");
  
  public void getResult() {
    Flux<Object> employeeFlux = client.get().uri("/events").accept(MediaType.TEXT_EVENT_STREAM).retrieve().bodyToFlux(Object.class);
    
    employeeFlux.subscribe(System.out::println);
  }
}