package com.apps.Ecomwallet.validation;


public class TransactionException extends Exception  {
	
	private static final long serialVersionUID = 6678123456992334561L;
	
	public TransactionException(String message) {
		super(message);
	}
}
