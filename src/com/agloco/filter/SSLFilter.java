/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.agloco.filter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.liferay.portal.util.PropsUtil;
import com.liferay.util.CollectionFactory;
import com.liferay.util.GetterUtil;
import com.liferay.util.Http;
import com.liferay.util.StringPool;
import com.liferay.util.Validator;

/**
 * <a href="SecureFilter.java.html"><b><i>View Source</i></b></a>
 * 
 * @author Brian Wing Shun Chan
 * 
 */
public class SSLFilter implements Filter
{

	public void init(FilterConfig config)
	{
		_httpsRequired = GetterUtil.getBoolean(PropsUtil.get("httpsRequired"));

		if (_httpsRequired)
		{
			String[] secureURLArray = PropsUtil.getArray("secureURL");

			for (int i = 0; i < secureURLArray.length; i++)
			{
				secureURL.add(secureURLArray[i]);
			}
		}
	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException
	{
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		StringBuffer servletPath = new StringBuffer();
		servletPath.append(request.getServletPath() == null ? "" : request.getServletPath());
		servletPath.append(request.getPathInfo() == null ? "" : request.getPathInfo());
		request.getSession().setAttribute("servletPath", servletPath.toString());

		String completeURL = Http.getCompleteURL(request);
		String serverName = request.getServerName();
		String queryString = request.getQueryString()!=null?request.getQueryString():"";

		String tmpURL = completeURL.substring(completeURL.indexOf("://") + 3);
		String requestURL = tmpURL.substring(tmpURL.indexOf("/"));
		if (requestURL.indexOf("?") >= 0)
			requestURL = requestURL.substring(0, requestURL.indexOf("?"));

		if (requestURL.startsWith("/web/guest/images"))
		{
			chain.doFilter(req, res);
			return;
		}
		if (_httpsRequired)
		{
			if (needSSL(requestURL))
			{
				if (completeURL.startsWith(Http.HTTPS_WITH_SLASH, 0))
				{
					chain.doFilter(req, res);
					return;
				}

				StringBuffer redirectURL = new StringBuffer();

				redirectURL.append(Http.HTTPS_WITH_SLASH);
				redirectURL.append(serverName);
				if (request.getServerPort() == 8080)
				{
					redirectURL.append(StringPool.COLON);
					redirectURL.append("8443");
				}
				redirectURL.append(requestURL);

				if (Validator.isNotNull(queryString))
				{
					redirectURL.append(StringPool.QUESTION);
					redirectURL.append(queryString);
				}

				res.setContentType("text/html");
				OutputStream out = res.getOutputStream();
				StringBuffer sb = new StringBuffer();
				sb
					.append("<script type=\"text/javascript\">\n")
					.append(" self.location.replace(\"").append(redirectURL.toString()).append("\");") // two lines add by terry at 2007-03-12
					.append("</script>");

				out.write(sb.toString().getBytes());
				out.flush();
				out.close();
			}
			else
			{
				if (completeURL.startsWith(Http.HTTP_WITH_SLASH, 0))
				{
					chain.doFilter(req, res);
					return;
				}

				StringBuffer redirectURL = new StringBuffer();

				redirectURL.append(Http.HTTP_WITH_SLASH);
				redirectURL.append(serverName);
				if (request.getServerPort() == 8443)
				{
					redirectURL.append(StringPool.COLON);
					redirectURL.append("8080");
				}
				redirectURL.append(requestURL);

				if (Validator.isNotNull(queryString))
				{
					redirectURL.append(StringPool.QUESTION);
					redirectURL.append(queryString);
				}

				res.setContentType("text/html");
				OutputStream out = res.getOutputStream();
				StringBuffer sb = new StringBuffer();
				sb
					.append("<script type=\"text/javascript\">\n")
					.append(" self.location.replace(\"").append(redirectURL.toString()).append("\");") // two lines add by terry at 2007-03-12
					.append("</script>");

				out.write(sb.toString().getBytes());
				out.flush();
				out.close();
			}
		}

	}

	public void destroy()
	{
	}

	protected boolean needSSL(String url)
	{
		if ((secureURL.size() > 0) && (secureURL.contains(url)))
			return true;
		else
			return false;
	}

	private static Log _log = LogFactory.getLog(SSLFilter.class);

	private Set secureURL = CollectionFactory.getHashSet();

	private static HashMap sessionMap = new HashMap();

	private boolean _httpsRequired;

	protected static HashMap getSessionMap()
	{
		return sessionMap;
	}

}