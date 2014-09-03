package com.agloco.action;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.agloco.exception.CannotCatchedException;
import com.agloco.exception.FileFormatException;
import com.agloco.service.util.MemberServiceUtil;
import com.agloco.util.FileUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.ParamUtil;
import com.liferay.util.servlet.SessionErrors;
import com.liferay.util.servlet.UploadPortletRequest;

public class SuspendMemberAction extends PortletAction {


		
	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			ActionRequest req, ActionResponse res)
		throws Exception {
		
		Set set = new HashSet(); 		//email
		List memberList = new ArrayList();
		List memberTempList = new ArrayList();
		String actionName = ParamUtil.getString(req, "actionName");
		
		if("upload".equals(actionName)){
			set = upload(mapping, form, req, res);
			listMember(req,set, memberList, memberTempList);
			req.getPortletSession().setAttribute("memberList", memberList);
			req.getPortletSession().setAttribute("memberTempList", memberTempList);
			req.setAttribute("memberList", memberList);
			req.setAttribute("memberTempList", memberTempList);
			req.setAttribute("displayMember", Boolean.TRUE);
			req.setAttribute("actionName", actionName);
		}
		else if("suspend".equals(actionName)){
			memberList = (List) req.getPortletSession().getAttribute("memberList");
			memberTempList = (List) req.getPortletSession().getAttribute("memberTempList");
			suspendMember(req,memberList,memberTempList);
			if(SessionErrors.size(req) < 1){
				req.setAttribute("suspendResult", Boolean.TRUE);	
			}
			req.setAttribute("actionName", actionName);
			
		}
		else if("enable".equals(actionName)){
			memberList = (List) req.getPortletSession().getAttribute("memberList");
			memberTempList = (List) req.getPortletSession().getAttribute("memberTempList");
			enableMember(req,memberList,memberTempList);
			if(SessionErrors.size(req) < 1){
				req.setAttribute("enableResult", Boolean.TRUE);	
			}
			req.setAttribute("actionName", actionName);
			
		}
		else if("cancel".equals(actionName)){
			req.setAttribute("cancelResult", Boolean.TRUE);
			req.setAttribute("actionName", actionName);
		}
		
		

	}

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			RenderRequest req, RenderResponse res)
		throws Exception {
		return mapping.findForward("portlet.suspend.member");
	}
	
	//query member or member temp according emailAddress
	private void listMember(ActionRequest req, Set set, List memberList, List memberTempList){
		
		try{
			
			if(set == null || set.size() < 1){
				return;
			}
			
			String[] emails = new String[set.size()];
			int i = 0;
			for(Iterator it = set.iterator(); it.hasNext();){
				emails[i++] = (String) it.next();
			}
			List mList = MemberServiceUtil.listAGMemberByEmail(emails);
			memberList.addAll(mList);
			List mtList = MemberServiceUtil.listAGMemberTempByEmail(emails);
			memberTempList.addAll(mtList);
			
		}
		catch(Exception e){
			SessionErrors.add(req, CannotCatchedException.class.getName(), new CannotCatchedException());
		}

		
	}
	
	private void suspendMember(ActionRequest req, List memberList, List memberTempList){
		int i = 0;
		try{
			
			if(memberList != null && memberList.size() > 0){
				i += memberList.size();
				MemberServiceUtil.suspendAGMember(memberList);
			}
			
			if(memberTempList != null && memberTempList.size() > 0){
				i += memberTempList.size();
				MemberServiceUtil.suspendAGMemberTemp(memberTempList);
			}
			
			
		}
		catch(Exception e){
			SessionErrors.add(req, CannotCatchedException.class.getName(), new CannotCatchedException());
		}

		req.setAttribute("suspendNumber", new Integer(i));
	}
	
	private void enableMember(ActionRequest req, List memberList, List memberTempList){
		int i = 0;
		try{
		
			if(memberList != null && memberList.size() > 0){
				i += memberList.size();
				MemberServiceUtil.enableAGMember(memberList);
			}
			
			if(memberTempList != null && memberTempList.size() > 0){
				i += memberTempList.size();
				MemberServiceUtil.enableAGMemberTemp(memberTempList);
			}
			
		}
		catch(Exception e){
			SessionErrors.add(req, CannotCatchedException.class.getName(), new CannotCatchedException());
		}
		req.setAttribute("enableNumber", new Integer(i));
		
	}

	//read file and put data into set
	private Set upload(
			ActionMapping mapping, ActionForm form,
			ActionRequest req, ActionResponse res)
		throws Exception {
		
		Set set = null;
		try{
			//get file
			UploadPortletRequest urequest = PortalUtil.getUploadPortletRequest(req);
			File f = urequest.getFile("uploadFileName");
			
			//check file format
			if(f.getName().indexOf(".txt") < 0){
				throw new FileFormatException();
			}
			
			//read file and put data into set
			set = FileUtil.readFile(f);
			if(set == null || set.size() < 1){
				return null;
			}
			
		}
		catch(Exception e){
			if(e instanceof FileFormatException){
				SessionErrors.add(req, e.getClass().getName(), e);
			}
			else{
				SessionErrors.add(req, CannotCatchedException.class.getName(), new CannotCatchedException());
			}
			
		}
		return set;
		
	}
	
}
