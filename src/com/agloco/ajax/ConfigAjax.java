package com.agloco.ajax;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.agloco.model.AGConfig;
import com.agloco.service.util.CommonServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;

/**
 * 
 * @author terry_zhao
 *
 */
public class ConfigAjax extends BaseAjax{

	private static Log log = LogFactory.getLog(ConfigAjax.class);
	
	public final static String SUCCESS = "success";
	public final static String FAILURE = "failure";
	public final static String EMPTY   = "empty";
	public final static String NO_AUTHORITY = "permission.deny";
	
	public String updateConfig(final String configName,final String companyId,final String configValue,final String configType){
		return (String) getAjaxTemplate().execute(new AjaxCallback(){

			public Object doInAjax() {
				try {
					boolean hasPerssion = hasAdminPermission();
					if(!hasPerssion){
						return NO_AUTHORITY;
					}
				} catch (PortalException e1) {
					if(log.isErrorEnabled()){
						log.error("update config error", e1);	
					}
					return FAILURE;
				} catch (SystemException e1) {
					if(log.isErrorEnabled()){
						log.error("update config error", e1);	
					}
					return FAILURE;
				}
				
				AGConfig config = null;
				try{
					
					//check first 
					if(StringUtils.isBlank(configValue)){
						return EMPTY;
					}
					
					//get config according primary key
					 config = CommonServiceUtil.getAGConfig(configName, companyId);
					 if(config == null){
						 return FAILURE;
					 }
					 
					//set type according the type submited 
					if(AGConfig.AG_CONFIG_TYPE_DISABLE.equalsIgnoreCase(configType)){
						config.setType(AGConfig.AG_CONFIG_TYPE_DISABLE);
					}
					else{
						//if enabe set type according the text value
						if(StringUtils.isNotBlank(config.getValue())){
							config.setType(AGConfig.AG_CONFIG_TYPE_SHORT_TEXT);
						}
						else if(StringUtils.isNotBlank(config.getContent())){
							config.setType(AGConfig.AG_CONFIG_TYPE_TEXT);
						}
					}
					
					//set value according the type 
					if(config.getType().equalsIgnoreCase(AGConfig.AG_CONFIG_TYPE_SHORT_TEXT)){
						config.setValue(configValue);
					}
					else if(config.getType().equalsIgnoreCase(AGConfig.AG_CONFIG_TYPE_TEXT)){
						config.setContent(configValue);
					}
					
					CommonServiceUtil.updateAGConfig(config);
					return SUCCESS;
					
				}
				catch(Exception e){
					if(log.isErrorEnabled()){
						log.error("update config error", e);	
					}
					return FAILURE;
//					throw new AjaxException();
				}
			}
			
		});
		
		
	}
	
	
	public String[] cancelConfig(final String configName,final String companyId){
		return (String[]) getAjaxTemplate().execute(new AjaxCallback(){

			public Object doInAjax() {
				AGConfig config = null;
				String[] result = new String[3];
				
				try {
					boolean hasPerssion = hasAdminPermission();
					if(!hasPerssion){
						result[0] = NO_AUTHORITY;
						return result;
					}
				} catch (PortalException e1) {
					if(log.isErrorEnabled()){
						log.error("update config error", e1);	
					}
					result[0] = FAILURE;
					return result;
				} catch (SystemException e1) {
					if(log.isErrorEnabled()){
						log.error("update config error", e1);	
					}
					result[0] = FAILURE;
					return result;
				}
				
				try{
					//get config according primary key
					config = CommonServiceUtil.getAGConfig(configName, companyId);
					if(config == null){
						result[0] = FAILURE;
						return result;
					}
					 
					result[1] =  config.getType();
					if(StringUtils.isNotBlank(config.getValue())){
						result[2] = config.getValue();
					}
					else if(StringUtils.isNotBlank(config.getContent())){
						result[2] = config.getContent();
					}
					else{
						result[2] = "";
					}
					
					result[0] = SUCCESS;
					return result;

					
				}
				catch(Exception e){
					if(log.isErrorEnabled()){
						log.error("update config error", e);	
					}
					result[0] = FAILURE;
					return result;
				}
			}
			
		});

		
		

	}
	
	
}
