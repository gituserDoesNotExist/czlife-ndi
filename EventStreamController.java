package hello;

import java.net.MalformedURLException;
import java.time.Duration;
import java.util.Date;
import java.util.stream.Stream;

import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

@RestController
public class EventStreamController {
  
  @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, path = "/event/stream")
  public Flux<Event> events() {
    Stream<Event> stream = Stream.generate(() -> new Event(System.currentTimeMillis(), new Date()));
    Flux<Event> eventFlux = Flux.fromStream(stream);
    Flux<Long> durationFlux = Flux.interval(Duration.ofSeconds(1));
    return Flux.zip(eventFlux, durationFlux).map(Tuple2::getT1);
  }
  
  @GetMapping("/videos/testvideo")
  public ResponseEntity<UrlResource> videos() {
    try {
      UrlResource video = new UrlResource("file:C:\\eclipse\\other-projects\\webflux\\testvideo.mp4");
      return ResponseEntity//
          .status(HttpStatus.PARTIAL_CONTENT)//
          .contentType(MediaTypeFactory.getMediaType(video).orElse(MediaType.APPLICATION_OCTET_STREAM))//
          .body(video);
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }
  
}
