package com.agloco.service.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

import com.agloco.Constants;
import com.agloco.exception.DataAccessException;
import com.agloco.model.VBTimeTotal;
import com.agloco.service.ViewbarService;
import com.agloco.util.ConfigUtil;
import com.liferay.portal.spring.util.SpringUtil;
import com.liferay.portal.util.PropsUtil;
/**
 * 
 * @author terry_zhao
 * @date May 8, 2007
 */
public class ViewbarServiceUtil {

	private ViewbarService viewbarService;
	private final static Log log = LogFactory.getLog(ViewbarServiceUtil.class);

	public static VBTimeTotal getTimeTotal(Long memberId) {
		VBTimeTotal tt = getViewbarService().getTimeTotal(memberId);
		if(tt == null) {
			return tt;
		}

		// modified at 2007-08-23 by Harry Sun, change accumulating time subtotal logic
		List timeSubtotalTables = null;
		try {
			timeSubtotalTables = getSubtotalTableNames(Constants.TB_VB_TIME_SUBTOTAL + "_");
		} catch(Exception e) {}
		updateTimeTotal(tt, timeSubtotalTables);
		/*
		Calendar now = Calendar.getInstance();

		if(tt.getModifiedDate() == null) {
			tt.setModifiedDate(Calendar.getInstance());
			tt.getModifiedDate().set(
					Integer.parseInt(PropsUtil.get(Constants.VIEWBAR_SURF_TIME_BEGIN_YEAR)),
					Integer.parseInt(PropsUtil.get(Constants.VIEWBAR_SURF_TIME_BEGIN_MONTH)),
					1);
		}

		List<String> betweenMonths = DateUtil.betweenMonths(tt.getModifiedDate(), now);
		if(betweenMonths != null && betweenMonths.size() > 0) {
			updateTimeTotal(tt, betweenMonths);
		}
		*/
		return tt;
	}

	private static void updateTimeTotal(VBTimeTotal tt, List timeSubtotalTables) {
		if(tt == null) return;
		int selfTime             = 0;
		int directReferralTime   = 0;
		int extendedReferralTime = 0;
		boolean needUpdate = false;
		if(timeSubtotalTables != null && timeSubtotalTables.size() > 0) {
			int maxLevel = Constants.DEF_REFERRAL_TREE_CALCLEVEL;
			try {
				maxLevel = Integer.parseInt(ConfigUtil.getConfig(Constants.REFERRAL_TREE_CALCLEVEL, 
					PropsUtil.get(Constants.DEFAULT_COMPANY_ID)));
			} catch(Exception e) {
				if(log.isErrorEnabled()) {
					log.error("get default referral tree calculate level error! ", e);
				}
			}
			DateFormat df = new SimpleDateFormat("yyyyMM");
			String modifiedDateStr = null;
			String nowDateStr = Constants.TB_VB_TIME_SUBTOTAL + "_" + df.format(new Date());
			if (tt.getModifiedDate() != null && tt.getModifiedDate().getTime() != null) {
				modifiedDateStr = Constants.TB_VB_TIME_SUBTOTAL + "_" + df.format(tt.getModifiedDate().getTime());
			} 
			for (int i = timeSubtotalTables.size() - 1; i >= 0; i--) {
				String tableName = (String) timeSubtotalTables.get(i);
				try {
					if (modifiedDateStr != null && modifiedDateStr.compareTo(tableName) > 0) break;
					if (nowDateStr.compareTo(tableName) <= 0) continue;
					int st  = MemberServiceUtil.getSurfTime(tableName.toString(), tt.getMemberId());
					int drt = MemberServiceUtil.getSurfTimeofReferral(tableName.toString(), tt.getMemberId(), st, 1, 1);
					int ert = MemberServiceUtil.getSurfTimeofReferral(tableName.toString(), tt.getMemberId(), st, 2, maxLevel);
					selfTime += st;
					directReferralTime += drt;
					extendedReferralTime += ert;
					needUpdate = true;
				} catch (DataAccessException e) {
					if(log.isErrorEnabled()){
						log.error("memberId=" + tt.getMemberId() + " query tableName=" + tableName + " error!", e);
					}
					throw e;
				}
			}
		}
		if (tt.getModifiedDate() == null) {
			if (tt.getSelfTime().longValue() > 0 || tt.getDirectReferralTime().longValue() > 0 || tt.getExtendedReferralTime().longValue() > 0) {
				tt.setSelfTime(new Long(0));
				tt.setDirectReferralTime(new Long(0));
				tt.setExtendedReferralTime(new Long(0));
				needUpdate = true;
			}
		}
		if (needUpdate) {
			tt.setSelfTime(tt.getSelfTime().longValue() + selfTime);
			tt.setDirectReferralTime(tt.getDirectReferralTime().longValue() + directReferralTime);
			tt.setExtendedReferralTime(tt.getExtendedReferralTime().longValue() + extendedReferralTime);
			Calendar modifiedDate = Calendar.getInstance();
			//modifiedDate.add(Calendar.MONTH, -1);
			tt.setModifiedDate(modifiedDate);
			updateTimeTotal(tt);
		}
	}
	
	private static void updateTimeTotal(VBTimeTotal timeTotal){
		getViewbarService().updateTimeTotal(timeTotal);
	}

	public static List getSubtotalTableNames(String baseName) throws Exception {
		return getViewbarService().getSubtotalTableNames(baseName);
	}

	private static ViewbarService getViewbarService(){
		ApplicationContext ctx = SpringUtil.getContext();
		ViewbarServiceUtil util = (ViewbarServiceUtil) ctx.getBean(ViewbarServiceUtil.class.getName());		
		return util.viewbarService;
	}
	
	public void setViewbarService(ViewbarService viewbarService) {
		this.viewbarService = viewbarService;
	}

}
