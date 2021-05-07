package me.astropvp.astrotournaments.exceptions;

public class ActiveMatchException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public ActiveMatchException(String errorMessage) {
		super(errorMessage);
	}
}
