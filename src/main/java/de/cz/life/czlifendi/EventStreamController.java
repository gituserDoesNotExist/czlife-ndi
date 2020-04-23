package de.cz.life.czlifendi;

import java.nio.ByteBuffer;
import java.time.Duration;
import java.util.Date;
import java.util.stream.Stream;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import de.cz.life.czlifendi.ndi.FrameReceiver;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

@RestController
public class EventStreamController {

	private static final Logger LOGGER = Logger.getLogger(EventStreamController.class);

	@Autowired
	private FrameReceiver receiver;

	@GetMapping("test")
	public String test() {
		receiver.generateVideoFrameStream();
		return "test";
	}

	@GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, path = "/video/stream")
	public Flux<Event> videostream() {
		// and allocating size capacity
		ByteBuffer bb = ByteBuffer.allocateDirect(4);

		// putting the int to byte typecast value
		// in ByteBuffer using putInt() method
		bb.put((byte) 20);
		bb.put((byte) 30);
		bb.put((byte) 40);
		bb.put((byte) 50);
		bb.array();
		Flux<Event> eventFlux = Flux.fromStream(receiver.generateVideoFrameStream());
		Flux<Long> durationFlux = Flux.interval(Duration.ofMillis(25));

		return Flux.zip(eventFlux, durationFlux).map(Tuple2::getT1);
	}

	@GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, path = "/event/stream")
	public Flux<Event> events() {
		Stream<Event> stream = Stream.generate(() -> new Event(System.currentTimeMillis(), new Date().toString()));
		Flux<Event> eventFlux = Flux.fromStream(stream);
		Flux<Long> durationFlux = Flux.interval(Duration.ofSeconds(1));
		return Flux.zip(eventFlux, durationFlux).map(Tuple2::getT1);
	}

	@GetMapping("/videos/testvideo")
	public ResponseEntity<UrlResource> videos() {
		UrlResource video = new UrlResource(getClass().getResource("/testvideo.mp4"));
		return ResponseEntity//
				.status(HttpStatus.PARTIAL_CONTENT)//
				.contentType(MediaTypeFactory.getMediaType(video).orElse(MediaType.APPLICATION_OCTET_STREAM))//
				.body(video);
	}

}
