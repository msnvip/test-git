package org.you.core.dao;

public class DbException extends RuntimeException{

	/**  */
	private static final long serialVersionUID = 189564843492872282L;
	
	
	public DbException(String message, Throwable  e){
		super(message,e);
	}
}
