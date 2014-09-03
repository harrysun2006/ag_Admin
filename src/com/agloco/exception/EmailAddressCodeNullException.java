package com.agloco.exception;

import com.liferay.portal.PortalException;

public class EmailAddressCodeNullException extends PortalException
{
	public EmailAddressCodeNullException() {
		super();
	}

	public EmailAddressCodeNullException(String msg) {
		super(msg);
	}

	public EmailAddressCodeNullException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public EmailAddressCodeNullException(Throwable cause) {
		super(cause);
	}

}
