package com.agloco.ajax;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import uk.ltd.getahead.dwr.WebContextFactory;

import com.agloco.Constants;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.service.spring.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

/**
 * 
 * @author terry_zhao
 *
 */
public class BaseAjax {
	
	private AjaxTemplate template;
	
	protected AjaxTemplate getAjaxTemplate(){
		if(template == null){
			template = new AjaxTemplate();
		}
		return template;
	}
	
	public HttpServletRequest getRequest(){
		return WebContextFactory.get().getHttpServletRequest();
	}
	
	protected User getLoginUser() throws PortalException, SystemException{
		return PortalUtil.getUser(getRequest());
	}
	
	protected String getLoginUserId(){
		return PortalUtil.getUserId(getRequest());
	}
	
	protected boolean hasAdminPermission() throws PortalException, SystemException{
		boolean b = hasAdminPermissionByUserId();
		if(b){
			return b;
		}
		return hasAdminPermissionByUser();
	}
	
	private boolean hasAdminPermissionByUser() throws PortalException, SystemException{
		return hasAdminPermission(getLoginUser());
	}
	
	private boolean hasAdminPermissionByUserId() throws PortalException, SystemException{
		return hasAdminPermission(getLoginUserId());
	}
	
	private boolean hasAdminPermission(User user) throws PortalException, SystemException{
		if(user == null){
			return false;
		}
		return hasAdminPermission(user.getUserId());
	}
	
	private boolean hasAdminPermission(String userId) throws PortalException, SystemException{
		return hasPermission(userId,Constants.ADMIN_ROLE_ID);
	}
	
	protected boolean hasPermission(String userId,String roleId) throws PortalException, SystemException{
		
		if(StringUtils.isBlank(userId)){
			return false;
		}	
		if(StringUtils.isBlank(roleId)){
			return true;
		}
		
		List list = UserLocalServiceUtil.getRoleUsers(roleId);
		if(list == null || list.size() < 1){
			return false;
		}
		
		for(Iterator it = list.iterator(); it.hasNext();){
			User user = (User) it.next();
			if(userId.equals(user.getUserId())){
				return true;
			}
		}
		
		return false;
	}
	
}
