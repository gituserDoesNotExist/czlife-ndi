package de.cz.life.czlifendi.ndi;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.walker.devolay.Devolay;
import com.walker.devolay.DevolayAudioFrame;
import com.walker.devolay.DevolayFinder;
import com.walker.devolay.DevolayFrameSync;
import com.walker.devolay.DevolayReceiver;
import com.walker.devolay.DevolaySource;
import com.walker.devolay.DevolayVideoFrame;

import de.cz.life.czlifendi.Timer;

@Component
public class FrameReceiver {

	private static final Logger LOGGER = Logger.getLogger(FrameReceiver.class);

	private static final int CLOCK_SPEED = 30;

	@Autowired
	private SourceFinder sourceFinder;

	public void test() {
		Devolay.loadLibraries();
		DevolayReceiver receiver = new DevolayReceiver();
		receiver.connect(sourceFinder.findSources().get(0));

		DevolayVideoFrame videoFrame = new DevolayVideoFrame();
		DevolayAudioFrame audioFrame = new DevolayAudioFrame();
		// Attach the frame-synchronizer to ensure that audio is dynamically resampled
		// based on request frequency.
		DevolayFrameSync frameSync = new DevolayFrameSync(receiver);

		Timer.runForXSeconds(5, () -> {
			captureVideoFrame2(videoFrame, frameSync);
			captureAudioFrame(audioFrame, frameSync);
			Timer.sleepForXMilliseconds(1000 / CLOCK_SPEED);
		});

		videoFrame.close();
		audioFrame.close();
		frameSync.close();
		receiver.close();

	}

	private void captureVideoFrame2(DevolayVideoFrame videoFrame, DevolayFrameSync frameSync) {
		if (frameSync.captureVideo(videoFrame)) { // Only returns true if a video frame was returned
			LOGGER.info("Received video data: " + videoFrame.getFourCCType().name());
		}
	}

	private void captureAudioFrame(DevolayAudioFrame audioFrame, DevolayFrameSync frameSync) {
		frameSync.captureAudio(audioFrame, 48000, 2, (int) (48000 / CLOCK_SPEED));
		LOGGER.info("Received audio data: " + audioFrame.getSamples());
	}

}