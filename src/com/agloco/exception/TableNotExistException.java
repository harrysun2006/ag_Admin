package com.agloco.exception;

import com.liferay.portal.PortalException;

public class TableNotExistException extends PortalException
{
	public TableNotExistException() {
		super();
	}

	public TableNotExistException(String msg) {
		super(msg);
	}

	public TableNotExistException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public TableNotExistException(Throwable cause) {
		super(cause);
	}

}
