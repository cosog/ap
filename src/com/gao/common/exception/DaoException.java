package com.gao.common.exception;

public class DaoException extends BaseException{
	private static final long serialVersionUID = 4768736439996729600L;

	public DaoException() {
		super();
	}

	public DaoException(String message, Throwable cause) {
		super(message, cause);
	}

	public DaoException(String message) {
		super(message);
	}

	public DaoException(Throwable cause) {
		super(cause);
	}
	
	
}
