package com.agloco.exception;

import com.liferay.portal.PortalException;

public class MemberIsSuspendException extends PortalException
{
	public MemberIsSuspendException() {
		super();
	}

	public MemberIsSuspendException(String msg) {
		super(msg);
	}

	public MemberIsSuspendException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public MemberIsSuspendException(Throwable cause) {
		super(cause);
	}

}
