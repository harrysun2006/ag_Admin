package com.test.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.test.kernel.DeployUtil;
import com.test.kernel.Host;

public class InitServlet extends HttpServlet {

	private static final String COMPANY_ID = "company_id";

	private static Log _log = LogFactory.getLog(InitServlet.class);

	public void init() throws ServletException {
		synchronized (InitServlet.class) {
			super.init();
			if(_log.isDebugEnabled()) {
				_log.debug("Initialize " + InitServlet.class.getName() + "... ...");
			}
			ServletContext ctx = getServletContext();
			Host host = new Host();
			host.setId(ctx.getInitParameter(COMPANY_ID));
			host.setName(ctx.getRealPath(""));
			DeployUtil.registerHost(host);
		}
	}

}
