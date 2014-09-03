package com.agloco.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.agloco.model.ThreadSession;
import com.agloco.util.ThreadSessionUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.events.ServicePreAction;
import com.liferay.portal.model.User;
import com.liferay.portal.struts.ActionException;
import com.liferay.portal.util.PortalUtil;

/**
 * 
 * @author terry_zhao
 *
 */
public class AdminServicePreAction extends ServicePreAction {

	private final static Log log = LogFactory.getLog(AdminServicePreAction.class);
	
	public void run(HttpServletRequest req, HttpServletResponse res)
	throws ActionException {
		
		ThreadSession ts = new ThreadSession();
		String userId = PortalUtil.getUserId(req);
		if(StringUtils.isBlank(userId)){
			User user = null;
			try {
				user = PortalUtil.getUser(req);
			} catch (PortalException e) {
				if(log.isErrorEnabled()){
					log.error(" PortalUtil.getUser() error", e);
				}
			} catch (SystemException e) {
				if(log.isErrorEnabled()){
					log.error(" PortalUtil.getUser() error", e);
				}
			}
			if(user != null){
				userId = user.getUserId();
			}
			
		}
		ts.setUserId(userId);
		ts.setIp(req.getRemoteAddr());
		
		ThreadSessionUtil.set(ts);
	}

}
