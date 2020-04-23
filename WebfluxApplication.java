package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class WebfluxApplication {
  
  public static void main(String[] args) {
    SpringApplication.run(WebfluxApplication.class, args);
    
//    GreetingWebClient gwc = new GreetingWebClient();
//    gwc.getResult();
  }
}