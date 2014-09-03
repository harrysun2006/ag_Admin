package com.agloco.exception;

import com.liferay.portal.PortalException;

public class PostCodeInvalidException extends PortalException
{
	public PostCodeInvalidException() {
		super();
	}

	public PostCodeInvalidException(String msg) {
		super(msg);
	}

	public PostCodeInvalidException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public PostCodeInvalidException(Throwable cause) {
		super(cause);
	}

}
