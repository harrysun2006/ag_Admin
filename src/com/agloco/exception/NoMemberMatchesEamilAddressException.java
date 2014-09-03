package com.agloco.exception;

import com.liferay.portal.PortalException;

public class NoMemberMatchesEamilAddressException extends PortalException
{
	public NoMemberMatchesEamilAddressException() {
		super();
	}

	public NoMemberMatchesEamilAddressException(String msg) {
		super(msg);
	}

	public NoMemberMatchesEamilAddressException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public NoMemberMatchesEamilAddressException(Throwable cause) {
		super(cause);
	}

}
