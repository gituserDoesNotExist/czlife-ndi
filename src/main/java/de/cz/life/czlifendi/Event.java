package de.cz.life.czlifendi;

public class Event {

	private long id;
	private String videoFrame;

	public Event(long id, String videoFrame) {
		this.id = id;
		this.videoFrame = videoFrame;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return the date
	 */
	public String getVideoFrame() {
		return videoFrame;
	}

}