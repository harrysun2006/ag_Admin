package com.agloco.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.agloco.Constants;
import com.agloco.log4j.LogUtil;
import com.agloco.model.MessageObject;
import com.agloco.util.MiscUtil;

public class RedirectServlet extends HttpServlet {

	private static Log _log = LogFactory.getLog(RedirectServlet.class);
	
	public void init() {

	}

	public void service(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {

		try {
			HttpSession session = req.getSession();
			String url = req.getRequestURL().toString();
			if (url.indexOf("/r/") < 0) return;

			int begin = url.indexOf("/r/");
			int end = url.indexOf("/", begin + 3);
			if(end < 0) end = url.length();
			String referralCode = url.substring(begin + 3, end);
			if(referralCode != null) {
				referralCode = referralCode.toUpperCase().replaceAll("-", "");
				if(_log.isInfoEnabled()){
					MessageObject mo = new MessageObject();
					mo.setOperate(Constants.OPERATE_REFERRAL);
					mo.setReferralCode(referralCode);
					mo.setIp(req.getRemoteAddr());
					mo.setSessionId(session.getId());
					mo.setUserAgent(req.getHeader("User-Agent"));
					mo.setServerIp(MiscUtil.getLocalIp());
					try {
						_log.info(mo);
					}	catch (Exception e1) {
						LogUtil.error(mo.toString(), e1);
						// TODO: handle exception
					}
				}
				//remove at 2007-3-13
//				referralCode = Base64.encode(CryptUtil.AESEncrypt(referralCode, Constants.COMMON_AESKEY, Constants.DATABASE_CHARSET));
			}
			StringBuffer redirectURL = new StringBuffer();
			redirectURL.append("/redirect.jsp?referralCode=").append(referralCode);
			String queryString = req.getQueryString();
			if(queryString != null && queryString.length() > 0) {
				redirectURL.append("&").append(queryString);
			}
			res.setContentType("text/html");
			try{
				OutputStream out = res.getOutputStream();
				StringBuffer sb = new StringBuffer();
				// marked by Harry on 2007-03-21
//				sb.append("<script type=\"text/javascript\">\n"
//						+ " self.location.replace(\"" + redirectURL.toString() + "\");" 
//						+ " event.returnValue=false;"
//						+ " self.location.href=\"" + redirectURL.toString() + "\";"
//						+	"</script>");
				sb
					.append("<html>\n")
					.append("<body>\n")
					.append("<script type=\"text/javascript\">\n")
					.append("self.location.replace(\"").append(redirectURL.toString()).append("\");\n")
					.append("</script>\n")
					.append("</body>\n")
					.append("</html>\n");

				out.write(sb.toString().getBytes());
				out.flush();
				out.close();
			} catch(Exception se) {
				if(_log.isErrorEnabled()){
					_log.error(se.getClass().getName());	
				}	
			}
		} catch (Exception e) {
			if(_log.isErrorEnabled()){
				_log.error(e.getMessage(), e);	
			}
		}
	}

	public void destroy() {
		super.destroy();
	}

}
