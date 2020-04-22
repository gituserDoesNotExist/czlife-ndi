package de.cz.life.czlifendi.ndi;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.walker.devolay.Devolay;
import com.walker.devolay.DevolayFinder;
import com.walker.devolay.DevolaySource;

@Component
public class SourceFinder {

	private static final Logger LOGGER = Logger.getLogger(SourceFinder.class);

	public List<DevolaySource> findSources() {
		Devolay.loadLibraries();

		try (DevolayFinder finder = new DevolayFinder()) {
			waitForSources(finder);
			List<DevolaySource> sources = Arrays.asList(finder.getCurrentSources());
			logSources(sources);
			return sources;
		} catch (Exception e) {
			throw e;
		}
	}

	private void waitForSources(DevolayFinder finder) {
		if (!finder.waitForSources(10 * 1000)) {
			throw new NoSourceFoundException("No Network source found!");
		}
	}

	private void logSources(List<DevolaySource> sources) {
		LOGGER.info("Found the following sources:");
		sources.stream()//
				.map(DevolaySource::getSourceName)//
				.forEach(LOGGER::info);
	}


}
