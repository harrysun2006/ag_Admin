package com.agloco.exception;

import com.liferay.portal.PortalException;

public class OmniadminCannotLogin extends PortalException
{
	public OmniadminCannotLogin() {
		super();
	}

	public OmniadminCannotLogin(String msg) {
		super(msg);
	}

	public OmniadminCannotLogin(String msg, Throwable cause) {
		super(msg, cause);
	}

	public OmniadminCannotLogin(Throwable cause) {
		super(cause);
	}

}
