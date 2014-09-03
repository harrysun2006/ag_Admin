package com.agloco.exception;

import com.liferay.portal.PortalException;

public class NoPostCodeException extends PortalException {
	public NoPostCodeException() {
		super();
	}

	public NoPostCodeException(String msg) {
		super(msg);
	}

	public NoPostCodeException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public NoPostCodeException(Throwable cause) {
		super(cause);
	}


}
