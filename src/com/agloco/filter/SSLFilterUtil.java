package com.agloco.filter;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import com.liferay.portal.model.User;
import com.liferay.portal.servlet.PortalSessionContext;
import com.liferay.portal.util.WebKeys;

public class SSLFilterUtil
{
	public static HashMap getSessionMap()
	{
		return SSLFilter.getSessionMap();
	}

	/**
	 * removeSession
	 * @param userId
	 * When url redirect from https to http or reverse, it will create a new session
	 * This method will remove the old session
	 */
	public static void removeSession(String userId)
	{
		HashMap sessionMap = getSessionMap();
		if (sessionMap.containsKey(userId))
		{
			String sessionId = (String) sessionMap.get(userId);
			HttpSession session = PortalSessionContext.get(sessionId);
			if (session != null)
				session.invalidate();
			sessionMap.remove(userId);
		}
	}

	/**
	 * removeSession
	 * @param userId
	 * When url redirect from https to http or reverse, it will create a new session
	 * This method will remove the old session
	 */
	public static void removeSession(HttpSession session)
	{
		String userId = (String)session.getAttribute(WebKeys.USER_ID);
		HashMap sessionMap = getSessionMap();
		if (sessionMap.containsKey(userId))
			sessionMap.remove(userId);
	}

	/**
	 * @param userId
	 * @param sessionId
	 * When url redirect from https to http or reverse, it will create a new session
	 * This method will add the new session
	 */
	public static void addSession(String userId, String sessionId)
	{
		if (userId == null
				|| userId.equals(User.getDefaultUserId("agloco.com")))
			return;

		HashMap sessionMap = getSessionMap();
		if (sessionMap.containsValue(sessionId))
			return;
		sessionMap.put(userId, sessionId);
	}

	/**
	 * @param userId
	 * @param sessionId
	 * When url redirect from https to http or reverse, it will create a new session
	 * This method will add the new session and remove the old session
	 */
	public static void addNewSession(String userId, HttpSession session)
	{
		if (userId == null
				|| userId.equals(User.getDefaultUserId("agloco.com")))
			return;

		HashMap sessionMap = getSessionMap();
		if (sessionMap.containsKey(userId))
		{
			String tmpSessionId = (String) sessionMap.get(userId);
			HttpSession oldSession = PortalSessionContext.get(tmpSessionId);
			if (oldSession != null)
			{
				// Fetch memberCode from old session then put it into the new session
				String memberCode = (String)oldSession.getAttribute(com.agloco.Constants.MEMBERCODE);
				session.setAttribute(com.agloco.Constants.MEMBERCODE, memberCode);
				
				oldSession.invalidate();
			}
		}

		sessionMap.put(userId, session.getId());
	}


	/**
	 * isMemberLogin
	 * @param userId
	 * @return boolean
	 */
	public static boolean isMemberLogin(String userId)
	{
		if (userId == null
				|| userId.equals(User.getDefaultUserId("agloco.com")))
			return false;
		HashMap sessionMap = getSessionMap();
		String userId_bak = userId + ".bak";
		if (sessionMap.containsKey(userId))
		{
			String sessionId = (String) sessionMap.get(userId);
			HttpSession session = PortalSessionContext.get(sessionId);
			if (session != null)
				return true;
			else
				sessionMap.remove(userId);
		}
		if (sessionMap.containsKey(userId_bak))
		{
			String sessionId = (String) sessionMap.get(userId_bak);
			HttpSession session = PortalSessionContext.get(sessionId);
			if (session != null)
				return true;
			else
				sessionMap.remove(userId_bak);
		}
		return false;
	}
}
