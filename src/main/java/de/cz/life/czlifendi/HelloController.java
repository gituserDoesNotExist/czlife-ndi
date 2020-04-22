package de.cz.life.czlifendi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.cz.life.czlifendi.ndi.FrameReceiver;
import de.cz.life.czlifendi.ndi.SourceFinder;

@RestController
public class HelloController {

	@Autowired
	private FrameReceiver frameReceiver;
	
	@RequestMapping("/")
	public String index() {
		frameReceiver.test();
		
		return "Greetings from Spring Boot!";
	}

}