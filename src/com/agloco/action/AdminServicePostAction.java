package com.agloco.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.agloco.util.ThreadSessionUtil;
import com.liferay.portal.struts.ActionException;
/**
 * 
 * @author terry_zhao
 *
 */
public class AdminServicePostAction extends
		com.liferay.portal.events.ServicePostAction {

	public void run(HttpServletRequest req, HttpServletResponse res)
	throws ActionException {
		super.run(req, res);
		ThreadSessionUtil.close();
	}
}
