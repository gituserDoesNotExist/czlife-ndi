package de.cz.life.czlifendi;

public abstract class CzLifeBaseException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public CzLifeBaseException(String msg, Throwable e) {
		super(msg, e);
	}
	
	public CzLifeBaseException(String msg) {
		super(msg);
	}
	
}
