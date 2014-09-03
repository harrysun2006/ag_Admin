package com.agloco.exception;

import com.liferay.portal.PortalException;

public class MemberHasBeenLoginException extends PortalException
{
	public MemberHasBeenLoginException() {
		super();
	}

	public MemberHasBeenLoginException(String msg) {
		super(msg);
	}

	public MemberHasBeenLoginException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public MemberHasBeenLoginException(Throwable cause) {
		super(cause);
	}

}
