package com.agloco.spring;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.Writer;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.AfterReturningAdvice;

import com.agloco.log4j.LogUtil;
import com.agloco.model.AdminLogConfig;
import com.agloco.model.AdminMessageObject;
import com.agloco.model.ThreadSession;
import com.agloco.service.util.CommonServiceUtil;
import com.agloco.util.ThreadSessionUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class AdminLogAfterAdvice implements AfterReturningAdvice {

	private final static Log log = LogFactory.getLog(AdminLogAfterAdvice.class);
	
	public void afterReturning(Object returnValue, Method method,
			Object[] args, Object target) throws Throwable {
		
		
		try{
			
			AdminMessageObject amo = new AdminMessageObject();
			ThreadSession ts = (ThreadSession)ThreadSessionUtil.get();
			if(ts != null){
				amo.setUserId(ts.getUserId());
				amo.setIp(ts.getIp());
			}
			amo.setClassName(target.getClass().getName());
			amo.setMethod(method.toString());
			amo.setOperate(method.getName());
			AdminLogConfig logCfg = CommonServiceUtil.getAdminLogConfig(amo.getClassName(),amo.getMethod());

			if(logCfg != null){
				if(StringUtils.isBlank(logCfg.getScript())){
					amo.setDescription("script is blank");
				}
				Configuration cfg = null;
				Template t = null; 
				try {
				
					t = new Template("script",new StringReader(logCfg.getScript()),cfg);
					Writer out = new OutputStreamWriter(new ByteArrayOutputStream());
					Map map = new HashMap();
					map.put("adminLog", amo);
					map.put("parameters", args);
					t.process(map, out);
					out.flush();

					
				} catch (IOException e) {
					amo.setDescription("execute script error:IOException");
					if(log.isErrorEnabled()){
						log.error("process script error", e);
					}
				} catch (TemplateException e) {
					amo.setDescription("execute script error:TemplateException");
					if(log.isErrorEnabled()){
						log.error("process script error", e);
					}
				}
			}
			else{
				amo.setDescription("no script configuration");
			}

			if(log.isInfoEnabled()){
				log.info(amo);
			}
		}
		catch(Exception e){
			LogUtil.error("AdminLogAfterAdvice is error", e);
		}
		
	}

}
