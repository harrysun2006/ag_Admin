package com.agloco.exception;

import com.liferay.portal.PortalException;

public class CountryIsNullExcetion extends PortalException
{
	public CountryIsNullExcetion() {
		super();
	}

	public CountryIsNullExcetion(String msg) {
		super(msg);
	}

	public CountryIsNullExcetion(String msg, Throwable cause) {
		super(msg, cause);
	}

	public CountryIsNullExcetion(Throwable cause) {
		super(cause);
	}

}
