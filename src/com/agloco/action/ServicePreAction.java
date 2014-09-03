package com.agloco.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.spring.GroupLocalServiceUtil;
import com.liferay.portal.service.spring.LayoutLocalServiceUtil;
import com.liferay.portal.service.spring.RoleLocalServiceUtil;
import com.liferay.portlet.admin.util.OmniadminUtil;
import com.liferay.util.dao.hibernate.QueryUtil;

public class ServicePreAction extends
		com.liferay.portal.events.ServicePreAction {

	protected Object[] getDefaultLayout(User user, boolean signedIn)
			throws PortalException, SystemException {

		Layout layout = null;
		List layouts = null;

		if (signedIn) {
			// is Omniadmin
			if (OmniadminUtil.isOmniadmin(user.getUserId())){
				Group userGroup = user.getGroup();
				layouts = LayoutLocalServiceUtil.getLayouts(Layout.PRIVATE
						+ userGroup.getGroupId(),
						Layout.DEFAULT_PARENT_LAYOUT_ID);
				if (layouts.size() == 0) {
					Group guestGroup = GroupLocalServiceUtil.getGroup(user
							.getActualCompanyId(), Group.GUEST);
					layouts = LayoutLocalServiceUtil.getLayouts(Layout.PUBLIC
							+ guestGroup.getGroupId(),
							Layout.DEFAULT_PARENT_LAYOUT_ID);
				}
			}else if (isAdministrator(user)){
				layouts = LayoutLocalServiceUtil.getLayouts(Layout.PUBLIC
						+ "1007", Layout.DEFAULT_PARENT_LAYOUT_ID);
			}else if (isPowerUser(user)){
				Group userGroup = user.getGroup();
				layouts = LayoutLocalServiceUtil.getLayouts(Layout.PRIVATE
						+ userGroup.getGroupId(),
						Layout.DEFAULT_PARENT_LAYOUT_ID);
			}else {
				Group guestGroup = GroupLocalServiceUtil.getGroup(user
						.getActualCompanyId(), Group.GUEST);
				layouts = LayoutLocalServiceUtil.getLayouts(Layout.PUBLIC
						+ guestGroup.getGroupId(),
						Layout.DEFAULT_PARENT_LAYOUT_ID);

				layout = (Layout) layouts.get(1);

				layouts = LayoutLocalServiceUtil.getLayouts(Layout.PUBLIC
						+ guestGroup.getGroupId(), layout.getLayoutId());
			}
			
			
//			
//			Group userGroup222 = user.getGroup();
//			System.out.println("get default user layouts Group Id is :"+userGroup222.getGroupId());
//			List userRoles=RoleLocalServiceUtil.getUserRoles(user.getUserId());
//			for (int i=0;i<userRoles.size();i++){
//				Role tempRole=(Role) userRoles.get(i);
//				if (tempRole.getRoleId().equals("1003")||tempRole.getRoleId().equals("1003")){
//					System.out.println("UserRole ID is " + tempRole.getRoleId());
//				}
//				
//				
//			}
//
//			
//			
//			
//			
//			
//			
//			
//			if (user.getUserId().equals("agloco.com.1")
//					|| user.getUserId().equals("agloco.com.2")
//					|| user.getUserId().equals("agloco.com.105212")) {
//				if (user.getUserId().equals("agloco.com.105212")) {
//					layouts = LayoutLocalServiceUtil.getLayouts(Layout.PUBLIC
//							+ "1007", Layout.DEFAULT_PARENT_LAYOUT_ID);
//				} else {
//					Group userGroup = user.getGroup();
//
//					layouts = LayoutLocalServiceUtil.getLayouts(Layout.PRIVATE
//							+ userGroup.getGroupId(),
//							Layout.DEFAULT_PARENT_LAYOUT_ID);
//				}
//				if (layouts.size() == 0) {
//					Group guestGroup = GroupLocalServiceUtil.getGroup(user
//							.getActualCompanyId(), Group.GUEST);
//					layouts = LayoutLocalServiceUtil.getLayouts(Layout.PUBLIC
//							+ guestGroup.getGroupId(),
//							Layout.DEFAULT_PARENT_LAYOUT_ID);
//				}
//			} else {
//
//				Group guestGroup = GroupLocalServiceUtil.getGroup(user
//						.getActualCompanyId(), Group.GUEST);
//				layouts = LayoutLocalServiceUtil.getLayouts(Layout.PUBLIC
//						+ guestGroup.getGroupId(),
//						Layout.DEFAULT_PARENT_LAYOUT_ID);
//
//				layout = (Layout) layouts.get(1);
//
//				layouts = LayoutLocalServiceUtil.getLayouts(Layout.PUBLIC
//						+ guestGroup.getGroupId(), layout.getLayoutId());
//
//			}

			// Check the user's personal layouts
			// Group guestGroup = GroupLocalServiceUtil.getGroup(user
			// .getActualCompanyId(), Group.GUEST);
			// Group userGroup = user.getGroup();

			// layouts = LayoutLocalServiceUtil.getLayouts(Layout.PUBLIC
			// + guestGroup.getGroupId(), Layout.DEFAULT_PARENT_LAYOUT_ID);
			//
			// if (user.getUserId().equals("agloco.com.1"))
			// {
			// layout = (Layout) layouts.get(4);
			// }
			// else if (user.getUserId().equals("agloco.com.2"))
			// {
			// layout = (Layout) layouts.get(5);
			// }
			// else
			// {
			// layout = (Layout) layouts.get(1);
			// }
			//
			// layouts = LayoutLocalServiceUtil.getLayouts(Layout.PUBLIC
			// + guestGroup.getGroupId(), layout.getLayoutId());

			if (layouts.size() > 0) {
				layout = (Layout) layouts.get(0);
			}

			// Check the user's communities

			if (layout == null) {
				Map groupParams = new HashMap();

				groupParams.put("usersGroups", user.getUserId());

				List groups = GroupLocalServiceUtil.search(user.getCompanyId(),
						null, null, groupParams, QueryUtil.ALL_POS,
						QueryUtil.ALL_POS);

				for (int i = 0; i < groups.size(); i++) {
					Group group = (Group) groups.get(i);

					layouts = LayoutLocalServiceUtil.getLayouts(Layout.PRIVATE
							+ group.getGroupId(),
							Layout.DEFAULT_PARENT_LAYOUT_ID);

					if (layouts.size() == 0) {
						layouts = LayoutLocalServiceUtil.getLayouts(
								Layout.PUBLIC + group.getGroupId(),
								Layout.DEFAULT_PARENT_LAYOUT_ID);
					}

					if (layouts.size() > 0) {
						layout = (Layout) layouts.get(0);

						break;
					}
				}
			}
		} else {

			// Check the guest community

			Group guestGroup = GroupLocalServiceUtil.getGroup(user
					.getActualCompanyId(), Group.GUEST);
			layouts = LayoutLocalServiceUtil.getLayouts(Layout.PUBLIC
					+ guestGroup.getGroupId(), Layout.DEFAULT_PARENT_LAYOUT_ID);

			layout = (Layout) layouts.get(0);

			layouts = LayoutLocalServiceUtil.getLayouts(Layout.PUBLIC
					+ guestGroup.getGroupId(), layout.getLayoutId());

			if (layouts.size() > 0) {
				layout = (Layout) layouts.get(0);
			}
		}

		return new Object[] { layout, layouts };
	}

	protected Object[] getViewableLayouts(Layout layout, List layouts,
			PermissionChecker permissionChecker, HttpServletRequest req)
			throws PortalException, SystemException {

		return new Object[] { layout, layouts };
	}

	protected void addDefaultLayouts(User user) throws PortalException,
			SystemException {

		if (user.hasPrivateLayouts()) {
			return;
		}
		return;
//		Group userGroup = user.getGroup();
//		System.out.println("Add default layouts user group is :"+ userGroup.getGroupId());
//		
//		String name = PropsUtil.get(PropsUtil.DEFAULT_USER_LAYOUT_NAME);
//
//		Layout layout = LayoutLocalServiceUtil.addLayout(
//				userGroup.getGroupId(), user.getUserId(), true,
//				Layout.DEFAULT_PARENT_LAYOUT_ID, name, Layout.TYPE_PORTLET,
//				false, null);
//
//		LayoutTypePortlet layoutTypePortlet = (LayoutTypePortlet) layout
//				.getLayoutType();
//
//		String layoutTemplateId = PropsUtil
//				.get(PropsUtil.DEFAULT_USER_LAYOUT_TEMPLATE_ID);
//
//		layoutTypePortlet.setLayoutTemplateId(layoutTemplateId);
//
//		for (int i = 0; i < 10; i++) {
//			String columnId = "column-" + i;
//			String portletIds = PropsUtil
//					.get(PropsUtil.DEFAULT_USER_LAYOUT_COLUMN + i);
//
//			if (portletIds != null) {
//				layoutTypePortlet.setPortletIds(columnId, portletIds);
//			}
//		}
//
//		LayoutLocalServiceUtil.updateLayout(layout.getLayoutId(), layout
//				.getOwnerId(), layout.getTypeSettings());
	}
	public static boolean isAdministrator(User user) throws PortalException,SystemException{
		boolean isAdmin=false;
		List userRoles=RoleLocalServiceUtil.getUserRoles(user.getUserId());
		for (int i=0;i<userRoles.size();i++){
			Role tempRole=(Role) userRoles.get(i);
			if (tempRole.getRoleId().equals("1001")){
				isAdmin= true;
			}
		}
		return isAdmin;
	}
	public static boolean isPowerUser(User user) throws PortalException,SystemException{
		boolean isAdmin=false;
		List userRoles=RoleLocalServiceUtil.getUserRoles(user.getUserId());
		for (int i=0;i<userRoles.size();i++){
			Role tempRole=(Role) userRoles.get(i);
			if (tempRole.getRoleId().equals("1003")){
				isAdmin= true;
			}
		}
		return isAdmin;
	}
}
