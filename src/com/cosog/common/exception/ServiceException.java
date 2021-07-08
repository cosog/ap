package com.cosog.common.exception;

public class ServiceException extends BaseException{
	private static final long serialVersionUID = 5188896188335873006L;

	public ServiceException() {
		super();
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

}
