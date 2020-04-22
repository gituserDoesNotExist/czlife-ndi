package de.cz.life.czlifendi;

public class TimerException extends CzLifeBaseException {

	private static final long serialVersionUID = 1L;

	public TimerException(String msg, InterruptedException e) {
		super(msg);
	}


}
