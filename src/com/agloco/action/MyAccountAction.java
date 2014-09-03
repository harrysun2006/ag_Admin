package com.agloco.action;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
import com.agloco.model.AGMember;
import com.agloco.model.AGMemberCount;
import com.agloco.model.AGMemberTree;
import com.agloco.model.VBTimeTotal;
import com.agloco.service.util.MemberServiceUtil;
import com.agloco.service.util.ViewbarServiceUtil;
import com.agloco.util.ConfigUtil;
import com.agloco.util.ViewbarUtil;
import com.liferay.portal.UserIdException;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.util.servlet.SessionErrors;

public class MyAccountAction extends PortletAction {

	private final static Log log = LogFactory.getLog(MyAccountAction.class);
	private Calendar getMonthFirstDate(Calendar today) {
		Calendar r = null;
		if(today != null) {
			r = today;
			r.set(Calendar.DAY_OF_MONTH, 1);
			r.set(Calendar.HOUR_OF_DAY, 0);
			r.set(Calendar.MINUTE, 0);
			r.set(Calendar.SECOND, 0);
		} else {
			r = Calendar.getInstance();
		}
		return r;
	}

	public ActionForward render(ActionMapping mapping, ActionForm form,
			PortletConfig config, RenderRequest req, RenderResponse res)
			throws Exception
	{
		try {
			ThemeDisplay themeDisplay = (ThemeDisplay) req.getAttribute(WebKeys.THEME_DISPLAY);

			User user = themeDisplay.getUser();
			String userId = user.getUserId();

			if(StringUtils.isBlank(userId)){
				throw new UserIdException();
			}
			int COL_COUNT = 4;
			int ROW_COUNT = 4;
			List[] cols = new List[COL_COUNT];
			AGMember member = MemberServiceUtil.getAGMemberByUserId(userId);
			if(member == null) {
				SessionErrors.add(req, PrincipalException.class.getName());
				return mapping.findForward("portal.login");
			}
			AGMember temp = new AGMember();
			temp.setMemberId(member.getMemberId());
			String calcLevel = ConfigUtil.getConfig(Constants.REFERRAL_TREE_CALCLEVEL, themeDisplay.getCompanyId());
			long level = 0;
			try {
				level = Long.parseLong(calcLevel);
			} catch(NumberFormatException nfe) {}
			if(level <=0 || level > Constants.DEF_REFERRAL_TREE_MAXLEVEL) level = Constants.DEF_REFERRAL_TREE_CALCLEVEL;
			cols[0] = MemberServiceUtil.getChildrenCountByLevel(temp, level, AGMemberTree.STRING_AGMEMBER_STATUS_N);
			Calendar firstDate = getMonthFirstDate(Calendar.getInstance());
			temp.setCreateDate(firstDate);
			cols[1] = MemberServiceUtil.getChildrenCountByLevel(temp, level, AGMemberTree.STRING_AGMEMBER_STATUS_N);
//			for(int j = 2; j < COL_COUNT; j++) {
//				cols[j] = new ArrayList();
//				for(int i = 0; i < ROW_COUNT; i++) {
//					cols[j].add(new Long(0));
//				}
//			}
			
			int maxLevel = Constants.DEF_REFERRAL_TREE_CALCLEVEL;
			try{
				maxLevel = Integer.parseInt(
								ConfigUtil.getConfig(Constants.REFERRAL_TREE_CALCLEVEL, 
													 PropsUtil.get(Constants.DEFAULT_COMPANY_ID)));
					
			}
			catch(Exception e){
				if(log.isErrorEnabled()){
					log.error("get default referral tree calculate level error! ", e);
				}
			}
			
			double rate = Constants.DEFAULT_GLOBAL_EARNING_RATE;
			try{
				rate = Double.parseDouble(
								ConfigUtil.getConfig(Constants.AGLOCO_GLOBAL_EARNING_RATE, 
													 PropsUtil.get(Constants.DEFAULT_COMPANY_ID)));
					
			}
			catch(Exception e){
				if(log.isErrorEnabled()){
					log.error("get agloco global earning rate error! ", e);
				}
			}
			
			//count column 3
			int selfTime             = MemberServiceUtil.getSurfTime(Constants.TB_VB_TIME_SUBTOTAL, member.getMemberId());
			int directReferralTime   = MemberServiceUtil.getSurfTimeofReferral(Constants.TB_VB_TIME_SUBTOTAL, member.getMemberId(), selfTime, 1, 1);
			int extendedReferralTime = MemberServiceUtil.getSurfTimeofReferral(Constants.TB_VB_TIME_SUBTOTAL, member.getMemberId(), selfTime, 2, maxLevel);
			int totalReferralTime    = directReferralTime + extendedReferralTime;
			double hST  = (selfTime*1.0)/(60*60);
			double hDRT = (directReferralTime*rate)/(60*60);
			double hERT = (extendedReferralTime*rate)/(60*60);
			double hTRT = (totalReferralTime*rate)/(60*60);
			List stList = new ArrayList();
			stList.add(new Double(hST));
			stList.add(new Double(hDRT));
			stList.add(new Double(hERT));
			stList.add(new Double(hTRT));
			
			//count column 4
			long tSelfTime = 0;
			long tDirectReferralTime = 0;
			long tExtendedReferralTime = 0;
			long tTotalReferralTime = 0;
			VBTimeTotal tt = ViewbarServiceUtil.getTimeTotal(member.getMemberId());
			if(tt != null){
				tSelfTime += tt.getSelfTime().longValue();
				tDirectReferralTime += tt.getDirectReferralTime().longValue();
				tExtendedReferralTime += tt.getExtendedReferralTime().longValue();
				tTotalReferralTime += (tDirectReferralTime + tExtendedReferralTime); 
			}
			
			double thST  = ((tSelfTime + selfTime)*1.0)/(60*60);
			double thDRT = ((tDirectReferralTime + directReferralTime)*rate)/(60*60);
			double thERT = ((tExtendedReferralTime + extendedReferralTime)*rate)/(60*60);
			double thTRT = ((tTotalReferralTime + totalReferralTime)*rate)/(60*60);
			
			List tstList = new ArrayList();
			tstList.add(new Double(thST));
			tstList.add(new Double(thDRT));
			tstList.add(new Double(thERT));
			tstList.add(new Double(thTRT));
			
			//set value to matrix
			Double[][] matrix = new Double[ROW_COUNT][COL_COUNT];
			long count = 0;
			long total = 0;
			Object[] values = null;
			Double l, c;
			for(int j = 0; j < 2; j++) {
				count = 0;
				total = 0;
				matrix[0][j] = (j == 0) ? Double.valueOf(1) : Double.valueOf(0);
				matrix[1][j] = new Double(0);
				if(cols[j].size() > 0) {
					values = (Object[]) cols[j].get(0);
					l = new Double(((Long)values[0]).longValue());
					c = new Double(((Long)values[1]).longValue());
					if(l.longValue() == 1) {
						matrix[1][j] = c;
						total += (c == null) ? 0 : c.longValue();
					} else {
						count += (c == null) ? 0 : c.longValue();
					}
				}
				for(int i = 1; i < cols[j].size(); i++) {
					values = (Object[]) cols[j].get(i);
					c = new Double(((Long)values[1]).longValue());
					count += (c == null) ? 0 : c.longValue();
				}
				total += count;
				matrix[2][j] = new Double(count);
				matrix[3][j] = new Double(total);
			}
			
			// set value to column 3
			for(int j = 2; j < 3; j++) {
				for(int i = 0; i < ROW_COUNT; i++) {
					matrix[i][j] = (Double) stList.get(i);
				}
			}
			//set value to column 4
			for(int j = 3; j < COL_COUNT; j++) {
				for(int i = 0; i < ROW_COUNT; i++) {
					matrix[i][j] = (Double) tstList.get(i);
				}
			}
			
			List[] rows = new List[ROW_COUNT];
			for(int i = 0; i < ROW_COUNT; i++) {
				rows[i] = new ArrayList();
				for(int j = 0; j < COL_COUNT; j++) {
					if(j < 2){
						rows[i].add(new DecimalFormat("#").format(((Double)matrix[i][j]).doubleValue()));	
					}
					else{
						rows[i].add(new DecimalFormat("0.0").format(((Double)matrix[i][j]).doubleValue()));
					}
					
				}
				req.setAttribute("ROWS" + i, rows[i]);
			}
			
			double rank = 0.0;
			AGMemberCount agmc = MemberServiceUtil.getAGMemberCount(member.getMemberId());
			if(agmc !=null && agmc.getCount().longValue() > 0){
				String s = String.valueOf(agmc.getCount().longValue());
				int order = MemberServiceUtil.getAGMemberCountOrder(Integer.parseInt(s));
				int totalNum = MemberServiceUtil.getAGMemberCountWithReferrals();
				if(order > 0 && totalNum > 0){
					rank = (double)order/totalNum;	
				}
			}
			
			req.setAttribute("member", member);
			req.setAttribute("firstDate", firstDate.getTime());
			req.setAttribute("rank", (rank-0.0) > 0 ? new Double(rank) : new Double(0));
			//add at 2007-04-30
			req.setAttribute("displayDownloadLink", Boolean.valueOf(ViewbarUtil.displayDownloadLink(member.getMemberCode())));
			
			
		} catch (Exception e) {
			if(log.isErrorEnabled()){
				log.error("my account action error", e);
			}
			throw e;
		}
		
		return mapping.findForward("portlet.ag_account.view");
	}

}
