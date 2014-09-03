package com.agloco.exception;

import com.liferay.portal.PortalException;

public class CountryInvalidException extends PortalException
{
	public CountryInvalidException() {
		super();
	}

	public CountryInvalidException(String msg) {
		super(msg);
	}

	public CountryInvalidException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public CountryInvalidException(Throwable cause) {
		super(cause);
	}

}
