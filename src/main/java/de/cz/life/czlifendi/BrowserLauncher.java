package de.cz.life.czlifendi;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class BrowserLauncher {

	@Value("${url.welcome.page}")
	private String startupUrl;

//	@EventListener({ ApplicationReadyEvent.class })
	void applicationReadyEvent() throws URISyntaxException, IOException {
		System.setProperty("java.awt.headless", "false");
		Desktop.getDesktop().browse(new URI(startupUrl));
	}

}
