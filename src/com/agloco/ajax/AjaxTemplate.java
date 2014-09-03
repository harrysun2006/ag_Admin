package com.agloco.ajax;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import uk.ltd.getahead.dwr.WebContextFactory;

import com.agloco.Constants;
import com.liferay.portal.events.EventsProcessor;
import com.liferay.portal.struts.ActionException;
import com.liferay.portal.util.PropsUtil;

/**
 * 
 * @author terry_zhao
 *
 */
public class AjaxTemplate {

	private final static Log log = LogFactory.getLog(AjaxTemplate.class);
	
	public HttpServletRequest getRequest(){
		return WebContextFactory.get().getHttpServletRequest();
	}
	
	public HttpServletResponse getResponse(){
		return WebContextFactory.get().getHttpServletResponse();
	}
	
	public Object execute(AjaxCallback callback){
		executePreProcess();
		Object obj =  callback.doInAjax();
		executePostProcess();
		return obj;
	}
	
	private void executePreProcess(){
		try {
			EventsProcessor.process(PropsUtil.getArray(
					Constants.AJAX_SERVICE_EVENTS_PRE), getRequest(), getResponse());
		} catch (ActionException e) {
			if(log.isErrorEnabled()){
				log.error("executePreProcess method error", e);
			}
		}
	}
	
	private void executePostProcess(){
		try {
			EventsProcessor.process(PropsUtil.getArray(
					Constants.AJAX_SERVICE_EVENTS_POST), getRequest(), getResponse());
		} catch (ActionException e) {
			if(log.isErrorEnabled()){
				log.error("executePreProcess method error", e);
			}
		}
		
	}
}
