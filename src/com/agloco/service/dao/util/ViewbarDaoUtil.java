package com.agloco.service.dao.util;

import java.util.List;

import org.springframework.context.ApplicationContext;

import com.agloco.model.VBTimeTotal;
import com.agloco.service.dao.ViewbarDao;
import com.liferay.portal.spring.util.SpringUtil;

/**
 * 
 * @author terry_zhao
 * @date May 8, 2007
 */
public class ViewbarDaoUtil {

	private ViewbarDao viewbarDao;

	public static VBTimeTotal getTimeTotal(Long memberId) {
		return getViewbarDao().getTimeTotal(memberId);
	}

	public static void updateTimeTotal(VBTimeTotal timeTotal) {
		getViewbarDao().updateTimeTotal(timeTotal);
	}

	public static List getSubtotalTableNames(String baseName) throws Exception {
		return getViewbarDao().getSubtotalTableNames(baseName);
	}

	public static ViewbarDao getViewbarDao() {
		ApplicationContext ctx = SpringUtil.getContext();
		ViewbarDaoUtil util = (ViewbarDaoUtil) ctx.getBean(ViewbarDaoUtil.class.getName());		
		return util.viewbarDao;
	}

	public void setViewbarDao(ViewbarDao viewbarDao) {
		this.viewbarDao = viewbarDao;
	}
	
}
