package com.agloco.action;

import java.util.ArrayList;
import java.util.Iterator;
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

import com.agloco.Constants;
import com.agloco.exception.CannotCatchedException;
import com.agloco.form.MailMessageForm;
import com.agloco.mail.MailMessage;
import com.agloco.model.AGMailMessage;
import com.agloco.service.util.CommonServiceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.util.ParamUtil;
import com.liferay.util.servlet.SessionErrors;

/**
 * 
 * @author terry_zhao
 *
 */
public class ListMailQueueAction extends PortletAction {

	private final static Log log = LogFactory.getLog(ListMailQueueAction.class);
	private final static String FORWARD_LIST = "portlet.list.mail.queue";
	private final static String FORWARD_VIEW = "portlet.view.mail.message";

	
	public void processAction(
			ActionMapping mapping, 
			ActionForm form, 
			PortletConfig config,
			ActionRequest req, 
			ActionResponse res)
		throws Exception {
		
		if(StringUtils.isNotBlank(ParamUtil.getString(req, "pageNumber"))){
			req.setAttribute("pageNumber", ParamUtil.getString(req, "pageNumber"));
		}
		
		if(StringUtils.isNotBlank(ParamUtil.getString(req, "messageId"))){
			req.setAttribute("messageId", ParamUtil.getString(req, "messageId"));
		}
		
		if(StringUtils.isNotBlank(ParamUtil.getString(req, "messageIds"))){
			req.setAttribute("messageIds", ParamUtil.getString(req, "messageIds"));
		}
	}
	
	public ActionForward render(
			ActionMapping mapping, 
			ActionForm form, 
			PortletConfig config,
			RenderRequest req,
			RenderResponse res)
		throws Exception {
		
		String actionName = ParamUtil.getString(req, "actionName");
		if("list".equalsIgnoreCase(actionName)){
			return listQueue(mapping, form, config, req, res);
		}
		else if("view".equalsIgnoreCase(actionName)){
			return messageInfo(mapping, form, config, req, res);
		}
		else if("delete".equalsIgnoreCase(actionName)){
			return deleteMessage(mapping, form, config, req, res);
		}
		
		return listQueue(mapping, form, config, req, res);
		
	}	
	

	public ActionForward listQueue(
			ActionMapping mapping, 
			ActionForm form, 
			PortletConfig config,
			RenderRequest req,
			RenderResponse res)
		throws Exception {
		
		int totalCount = 0; 
		int pageNumber = 1;
		int maxPage = 0;
		List list = null;
		List msgList = null;
		
		try {
			totalCount = CommonServiceUtil.getAGMailMessageNumber();
			
			req.setAttribute("totalCount", new Integer(totalCount));
			if(totalCount < 1){
				req.setAttribute("list", null);	
				return mapping.findForward(FORWARD_LIST);	
			}
			
			maxPage = totalCount%Constants.PAGE_SIZE == 0 ? totalCount/Constants.PAGE_SIZE : totalCount/Constants.PAGE_SIZE +1; 
			if(StringUtils.isNotBlank((String) req.getAttribute("pageNumber"))){
				pageNumber = Integer.parseInt((String) req.getAttribute("pageNumber"));
			}
			if(pageNumber < 1){
				pageNumber = 1;
			}
			if(pageNumber > maxPage){
				pageNumber = maxPage;
			}
			
			list = CommonServiceUtil.listAGMailMessage(pageNumber, Constants.PAGE_SIZE);
			if(list == null || list.size() < 1){
				req.setAttribute("list", null);	
				return mapping.findForward(FORWARD_LIST);
			}
			
			for(Iterator it = list.iterator(); it.hasNext();){
				AGMailMessage amm = (AGMailMessage)it.next();
				if(amm != null){
					MailMessageForm mf = new MailMessageForm();
					mf.setId(amm.getId());
					mf.setMessage((MailMessage)amm.getSerialiableMsg());
					if(msgList == null){
						msgList = new ArrayList();
					}
					msgList.add(mf);
				}

			}
			
			req.setAttribute("currentPage", new Integer(pageNumber));
			req.setAttribute("maxPage", new Integer(maxPage));
			req.setAttribute("list", msgList);
			
			return mapping.findForward(FORWARD_LIST);
			
		}
		catch (Exception e) {
			if(log.isErrorEnabled()){
				log.error("list mail message error!", e);
			}
			SessionErrors.add(req, CannotCatchedException.class.getName(), new CannotCatchedException());
			return mapping.findForward(FORWARD_LIST);
		}
	}
	
	
	public ActionForward messageInfo(
			ActionMapping mapping, 
			ActionForm form, 
			PortletConfig config,
			RenderRequest req,
			RenderResponse res)
		throws Exception {
		
		if(StringUtils.isBlank((String)req.getAttribute("messageId"))){
			return mapping.findForward(FORWARD_VIEW);
		}
		
		try{
			AGMailMessage amm = CommonServiceUtil.getAGMailMessage(Long.decode((String)req.getAttribute("messageId")));
			if(amm == null){
				return mapping.findForward(FORWARD_VIEW);
			}
			
			MailMessageForm mf = new MailMessageForm();
			mf.setId(amm.getId());
			mf.setMessage((MailMessage)amm.getSerialiableMsg());
			req.setAttribute("mailMessageForm", mf);
			
			return mapping.findForward(FORWARD_VIEW);	
		}
		
		catch (Exception e) {
			if(log.isErrorEnabled()){
				log.error("view mail message error!", e);
			}
			SessionErrors.add(req, CannotCatchedException.class.getName(), new CannotCatchedException());
			return mapping.findForward(FORWARD_VIEW);
		}
		
	}	
	
	public ActionForward deleteMessage(
			ActionMapping mapping, 
			ActionForm form, 
			PortletConfig config,
			RenderRequest req,
			RenderResponse res)
		throws Exception {
		
		String messagIds = (String)req.getAttribute("messageIds");
		if(StringUtils.isBlank(messagIds)){
			return mapping.findForward(FORWARD_VIEW);
		}
		
		StringTokenizer st = new StringTokenizer(messagIds,",");
		Long[] ids = new Long[st.countTokens()];
		int i = 0;
		
		try{
			
			while(st.hasMoreElements()){
				String id = (String) st.nextElement();
				if(StringUtils.isNotBlank(id)){
					ids[i++] = Long.decode(id);
				}
			}
			
			CommonServiceUtil.deleteAGMailMessage(ids);
			
			return listQueue(mapping, form, config, req, res);	
		}
		
		catch (Exception e) {
			if(log.isErrorEnabled()){
				log.error("delete mail message error!", e);
			}
			SessionErrors.add(req, CannotCatchedException.class.getName(), new CannotCatchedException());
			return listQueue(mapping, form, config, req, res);
		}
		
	}	
	
}
