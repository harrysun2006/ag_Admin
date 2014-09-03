package com.agloco.exception;

import com.liferay.portal.PortalException;

public class LoginIsNullException extends PortalException
{
	public LoginIsNullException() {
		super();
	}

	public LoginIsNullException(String msg) {
		super(msg);
	}

	public LoginIsNullException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public LoginIsNullException(Throwable cause) {
		super(cause);
	}

}
