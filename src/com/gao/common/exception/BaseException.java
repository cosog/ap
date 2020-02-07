package com.gao.common.exception;

public class BaseException extends RuntimeException{
	private static final long serialVersionUID = 828976064126144450L;

	public BaseException() {
		super();
	}

	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public BaseException(String message) {
		super(message);
	}

	public BaseException(Throwable cause) {
		super(cause);
	}
}
