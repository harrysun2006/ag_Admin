package com.agloco.exception;

import com.liferay.portal.PortalException;

public class MemberCodeNullException extends PortalException
{
	public MemberCodeNullException() {
		super();
	}

	public MemberCodeNullException(String msg) {
		super(msg);
	}

	public MemberCodeNullException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public MemberCodeNullException(Throwable cause) {
		super(cause);
	}

}
