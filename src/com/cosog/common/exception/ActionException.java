package com.cosog.common.exception;

public class ActionException extends BaseException{
	private static final long serialVersionUID = -25753078724267215L;

	public ActionException() {
		super();
	}

	public ActionException(String message, Throwable cause) {
		super(message, cause);
	}

	public ActionException(String message) {
		super(message);
	}

	public ActionException(Throwable cause) {
		super(cause);
	}
	
}
