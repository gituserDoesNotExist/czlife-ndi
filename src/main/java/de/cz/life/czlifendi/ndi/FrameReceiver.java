package de.cz.life.czlifendi.ndi;

import java.nio.ByteBuffer;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.walker.devolay.Devolay;
import com.walker.devolay.DevolayFinder;
import com.walker.devolay.DevolayFrameSync;
import com.walker.devolay.DevolayReceiver;
import com.walker.devolay.DevolaySource;
import com.walker.devolay.DevolayVideoFrame;

import de.cz.life.czlifendi.Event;

@Component
public class FrameReceiver {

	private static final Logger LOGGER = Logger.getLogger(FrameReceiver.class);

	public Stream<Event> generateVideoFrameStream() {
		Devolay.loadLibraries();
		DevolayReceiver receiver = createReceiver();
		DevolayVideoFrame videoFrame = new DevolayVideoFrame();
		// Attach the frame-synchronizer to ensure that audio is dynamically resampled based on request frequency.
		DevolayFrameSync frameSync = new DevolayFrameSync(receiver);
//		videoFrame.close();
//		frameSync.close();
//		receiver.close();

		return Stream.generate(() -> new Event(System.currentTimeMillis(), captureVideoFrame(videoFrame, frameSync)));

	}

	private String captureVideoFrame(DevolayVideoFrame videoFrame, DevolayFrameSync frameSync) {
		if (frameSync.captureVideo(videoFrame)) { // Only returns true if a video frame was returned
			// Handle video data here
			LOGGER.info("Received video data: " + videoFrame.getData());
		}
		return Optional.ofNullable(videoFrame.getData()).map(ByteBuffer::array).map(Object::toString).orElse("nothing...");
	}

	private DevolayReceiver createReceiver() {
		DevolayReceiver receiver = new DevolayReceiver();
		try (DevolayFinder finder = new DevolayFinder()) {
			DevolaySource[] sources;
			while ((sources = finder.getCurrentSources()).length == 0) {
				// If none found, wait until the list changes
				LOGGER.info("Waiting for sources...");
				finder.waitForSources(5000);
			}

			// Connect to the first source found
			LOGGER.info("Connecting to source: " + sources[0].getSourceName());
			receiver.connect(sources[0]);
		}
		return receiver;
	}

}