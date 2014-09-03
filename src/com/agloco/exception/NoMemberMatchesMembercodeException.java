package com.agloco.exception;

import com.liferay.portal.PortalException;

public class NoMemberMatchesMembercodeException extends PortalException
{
	public NoMemberMatchesMembercodeException() {
		super();
	}

	public NoMemberMatchesMembercodeException(String msg) {
		super(msg);
	}

	public NoMemberMatchesMembercodeException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public NoMemberMatchesMembercodeException(Throwable cause) {
		super(cause);
	}

}
