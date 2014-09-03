
package com.agloco.action;

import java.util.List;
import java.util.StringTokenizer;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.agloco.exception.CannotCatchedException;
import com.agloco.model.AGMember;
import com.agloco.service.util.MemberServiceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.util.ParamUtil;
import com.liferay.util.servlet.SessionErrors;

/**
 * 
 * @author terry_zhao
 *
 */
public class ListAdminUserAction extends PortletAction{

	private final static String FORWARD_LIST   = "portlet.addmin.user.list";
	private final static String FORWARD_ADD    = "portlet.addmin.user.add";
	private final static String FORWARD_EDIT   = "portlet.addmin.user.edit";
	
	private final static String ACTION_LIST    = "list";
	private final static String ACTION_SUSPEND = "suspend";
	private final static String ACTION_ACTIVE  = "active";
	private final static String ACTION_ADD     = "add";
	private final static String ACTION_EDIT    = "edit";
	private final static Log log = LogFactory.getLog(ListAdminUserAction.class);
	

	public void processAction(ActionMapping mapping, ActionForm form,
			PortletConfig config, ActionRequest req, ActionResponse res)
			throws Exception{
		
		//send request to render for processing
		
	}

	public ActionForward render(ActionMapping mapping, ActionForm form,
			PortletConfig config, RenderRequest req, RenderResponse res)
			throws Exception{
		
		String actionName = ParamUtil.getString(req, "actionName");
		
		if(ACTION_LIST.equals(actionName)){
			return list(mapping, form, config, req, res);
		}
		
		if(ACTION_SUSPEND.equals(actionName)){
			return suspend(mapping, form, config, req, res);
		}
		
		if(ACTION_ACTIVE.equals(actionName)){
			return active(mapping, form, config, req, res);
		}
		
		if(ACTION_ADD.equals(actionName)){
			return add(mapping, form, config, req, res);
		}
		
		if(ACTION_EDIT.equals(actionName)){
			return edit(mapping, form, config, req, res);
		}
		
		return list(mapping, form, config, req, res);

	}
	
	public ActionForward list(ActionMapping mapping, ActionForm form,
			PortletConfig config, RenderRequest req, RenderResponse res)
			throws Exception{
		
		int totalCount = 0; 
		int pageNumber = 1;
		int pageSize = 10;
		int maxPage = 0;
		List list = null;
		
		try {
			
			totalCount = MemberServiceUtil.getAdminUserCount();
			
			req.setAttribute("totalCount", new Integer(totalCount));
			if(totalCount < 1){
				req.setAttribute("currentPage", new Integer(pageNumber));
				req.setAttribute("maxPage", new Integer(maxPage));
				req.setAttribute("list", null);	
				return mapping.findForward(FORWARD_LIST);	
			}
			
			maxPage = totalCount%pageSize == 0 ? totalCount/pageSize : totalCount/pageSize + 1; 
			if(StringUtils.isNotBlank(ParamUtil.getString(req, "pageNumber"))){
				pageNumber = Integer.parseInt(ParamUtil.getString(req, "pageNumber"));
			}
			if(pageNumber < 1){
				pageNumber = 1;
			}
			if(pageNumber > maxPage){
				pageNumber = maxPage;
			}
			
			list = MemberServiceUtil.listAdminUser(pageNumber,pageSize);

			req.setAttribute("currentPage", new Integer(pageNumber));
			req.setAttribute("maxPage", new Integer(maxPage));
			req.setAttribute("list", list);
			
			return mapping.findForward(FORWARD_LIST);
			
		}
		catch (Exception e) {
			if(log.isErrorEnabled()){
				log.error("list admin user error!", e);
			}
			SessionErrors.add(req, CannotCatchedException.class.getName(), new CannotCatchedException());
			return mapping.findForward(FORWARD_LIST);
		}
	}
	
	private ActionForward add(ActionMapping mapping, ActionForm form,
			PortletConfig config, RenderRequest req, RenderResponse res)
			throws Exception{
		return mapping.findForward(FORWARD_ADD);
	}
	
	private ActionForward edit(ActionMapping mapping, ActionForm form,
			PortletConfig config, RenderRequest req, RenderResponse res)
			throws Exception{
		return mapping.findForward(FORWARD_EDIT);
	}
	
	private ActionForward suspend(ActionMapping mapping, ActionForm form,
			PortletConfig config, RenderRequest req, RenderResponse res)
			throws Exception{
		return updateStatus(mapping, form, config, req, res, AGMember.MEMBER_STATUS_INACTIVE);
	}
	
	private ActionForward active(ActionMapping mapping, ActionForm form,
			PortletConfig config, RenderRequest req, RenderResponse res)
			throws Exception{
		return updateStatus(mapping, form, config, req, res, AGMember.MEMBER_STATUS_ACTIVE);
	}
	
	private ActionForward updateStatus(ActionMapping mapping, ActionForm form,
			PortletConfig config, RenderRequest req, RenderResponse res,String memberStatus)
			throws Exception{
		String userIds = ParamUtil.getString(req, "userIds");
		if(StringUtils.isBlank(userIds)){
			return list(mapping, form, config, req, res);
		}
		
		StringTokenizer st = new StringTokenizer(userIds,",");
		String[] ids = new String[st.countTokens()];
		int i = 0;
		try{
			
			while(st.hasMoreElements()){
				String id = (String) st.nextElement();
				if(StringUtils.isNotBlank(id)){
					ids[i++] = id;
				}
			}
			
			MemberServiceUtil.updateMemberStatus(ids, memberStatus, true);
			
			return list(mapping, form, config, req, res);	
		}
		
		catch (Exception e) {
			if(log.isErrorEnabled()){
				log.error("update member status error!", e);
			}
			SessionErrors.add(req, CannotCatchedException.class.getName(), new CannotCatchedException());
			return list(mapping, form, config, req, res);
		}
	}

}