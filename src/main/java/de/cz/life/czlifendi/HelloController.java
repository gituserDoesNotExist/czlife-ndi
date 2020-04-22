package de.cz.life.czlifendi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.cz.life.czlifendi.ndi.SignalFinder;

@RestController
public class HelloController {

	@Autowired
	private SignalFinder signalFinder;
	
	
	@RequestMapping("/")
	public String index() {
		signalFinder.findSignal();
		
		return "Greetings from Spring Boot!";
	}

}