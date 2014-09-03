package com.agloco.action;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.agloco.Constants;
import com.agloco.exception.EmptyMemberCodeException;
import com.agloco.form.DecryptMemberInfoForm;
import com.agloco.util.CryptUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.util.Base64;
import com.liferay.util.servlet.SessionErrors;

public class DecryptMemberInfoAction extends PortletAction {

	public void processAction(ActionMapping mapping, ActionForm form,
			PortletConfig config, ActionRequest req, ActionResponse res)
			throws Exception {
		DecryptMemberInfoForm decryptMemberInfoForm = (DecryptMemberInfoForm) form;
		String encryptMemberCode = decryptMemberInfoForm.getEncryptMemberCode();

		if (StringUtils.isBlank(encryptMemberCode)) {
			SessionErrors.add(req, EmptyMemberCodeException.class.getName(),
					new EmptyMemberCodeException());
			return;
			// req.setAttribute("decryptErrorHappen", "decryptErrorHappen");
			// return;
		}
		String decryptMemberCode = "";
		try {
			byte[] b = Base64.decode(encryptMemberCode);
			decryptMemberCode = CryptUtil.AESDecrypt(b,
					Constants.COMMON_AESKEY, Constants.DATABASE_CHARSET);
		} catch (Exception e) {
			SessionErrors.add(req, EmptyMemberCodeException.class.getName(),
					new EmptyMemberCodeException());
			return;
			// req.setAttribute("decryptErrorHappen", "decryptErrorHappen");
			// return;
		}
		if (decryptMemberCode == null) {
			SessionErrors.add(req, EmptyMemberCodeException.class.getName(),
					new EmptyMemberCodeException());
			return;
		}
		decryptMemberInfoForm.setDecryptMemberCode(decryptMemberCode);
		req.setAttribute("decryptMemberInfoForm", decryptMemberInfoForm);
		req.setAttribute("decryptMemberInfo", "decryptMemberInfo");
	}

	public ActionForward render(ActionMapping mapping, ActionForm form,
			PortletConfig config, RenderRequest req, RenderResponse res)
			throws Exception {
		if ("decryptMemberInfo".equals(req.getAttribute("decryptMemberInfo"))) {
			form = (ActionForm) req.getAttribute("decryptMemberInfoForm");
		}
		return mapping.findForward("portlet.decryptmemberinfo.view");

	}
}
