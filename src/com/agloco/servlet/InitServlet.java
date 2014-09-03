package com.agloco.servlet;

import java.util.TimeZone;

import javax.servlet.http.HttpServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.agloco.util.ConvertUtil;
import com.agloco.util.HibernateUtil;
import com.liferay.portal.spring.util.SpringUtil;

/**
 * @author hysun
 * 
 */
public class InitServlet extends HttpServlet {

	private static Log _log = LogFactory.getLog(InitServlet.class);

	public InitServlet() {
		TimeZone UTC = TimeZone.getTimeZone("UTC");
		TimeZone.setDefault(UTC);
		System.out.println("TimeZone has been set to " + UTC.getID());
		_log.info("TimeZone has been set to " + UTC.getID());
		ConvertUtil.noop();
		
		//add at 2007-04-25
		SpringUtil.getContext();
		HibernateUtil.getAglocoDBName();
		HibernateUtil.getViewbarDBName();
		
		// socket listener
//		Socketlistener socketLisnter = new Socketlistener();
//		socketLisnter.start();
	}

	public void init() {
	}

	public void destroy() {
		super.destroy();
	}

}
