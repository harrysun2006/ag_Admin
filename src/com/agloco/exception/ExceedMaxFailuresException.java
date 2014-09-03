package com.agloco.exception;

import com.liferay.portal.PortalException;

public class ExceedMaxFailuresException extends PortalException
{
	public ExceedMaxFailuresException() {
		super();
	}

	public ExceedMaxFailuresException(String msg) {
		super(msg);
	}

	public ExceedMaxFailuresException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public ExceedMaxFailuresException(Throwable cause) {
		super(cause);
	}

}
