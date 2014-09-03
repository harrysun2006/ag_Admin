package com.agloco.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.liferay.portal.util.PropsUtil;

/**
 * 
 * @author terry_zhao
 * @date Apr 30, 2007
 */
public class ViewbarUtil {

//	private final static Pattern p = Pattern.compile(PropsUtil.get("viewbar.download.link.pattern"));
	
	private final static Log log = LogFactory.getLog(ViewbarUtil.class);
	private final static String VIEWBAR_DOWNLOAD_LINK_PATTERN = "viewbar.download.link.pattern";
	private final static String DEFAULT_COMPANY_ID = "default.company.id";
	private static Pattern p = null;
	
	
	public static boolean displayDownloadLink(String memberCode){
	
		try {
			
			if(StringUtils.isBlank(memberCode)){
				return false;
			}
			
			String vdlp = ConfigUtil.getConfig(VIEWBAR_DOWNLOAD_LINK_PATTERN, PropsUtil.get(DEFAULT_COMPANY_ID));
			if(StringUtils.isBlank(vdlp)){
				return true;
			}
			
			if(p == null){	
				p = Pattern.compile(vdlp);
			}
			
			Matcher m = p.matcher(memberCode);
			if(m.matches()){
				return true;
			}
			
				
		} catch (Exception e) {
			if(log.isErrorEnabled()){
				log.error("display download link error!", e);
			}
		}
		
		return false;
		
	}
}
