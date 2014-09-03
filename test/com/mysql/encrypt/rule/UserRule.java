package com.mysql.encrypt.rule;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Session;

import com.mysql.encrypt.dao.UserDAO;
import com.mysql.encrypt.po.HUser;
import com.mysql.encrypt.vo.User;
import com.sophia.pagination.Page;

public class UserRule {

	public UserRule() {
	}

	private static HUser getPO(User vo) throws Exception {
		HUser po = null;
		if (vo != null) {
			po = new HUser();
			BeanUtils.copyProperties(po, vo);
		}
		return po;
	}

	private static User getVO(HUser po) throws Exception {
		User vo = null;
		if (po != null) {
			vo = new User();
			BeanUtils.copyProperties(vo, po);
		}
		return vo;
	}

	public static User getUser(int id) throws Exception {
		return getUser(new Long(id), null);
	}

	public static User getUser(Long id) throws Exception {
		return getUser(id, null);
	}

	public static User getUser(Long id, Session session)
			throws Exception {
		UserDAO dao = new UserDAO();
		User vo = null;
		try {
			HUser po = dao.getUser(id, session);
			vo = getVO(po);
		} catch (Exception e) {
			throw e;
		}
		return vo;
	}

	public static void addUser(User vo) throws Exception {
		addUser(vo, null);
	}

	public static void addUser(User vo, Session session)
			throws Exception {
		UserDAO dao = new UserDAO();
		try {
			HUser po = getPO(vo);
			dao.addUser(po, session);
			vo.setId(po.getId());
		} catch (Exception e) {
			throw e;
		}
	}

	public static void updateUser(User vo) throws Exception {
		updateUser(vo, null);
	}

	public static void updateUser(User vo, Session session)
			throws Exception {
		UserDAO dao = new UserDAO();
		try {
			HUser po = getPO(vo);
			dao.updateUser(po, session);
		} catch (Exception e) {
			throw e;
		}
	}

	public static void deleteUser(int id) throws Exception {
		deleteUser(new Long(id));
	}

	public static void deleteUser(Long id) throws Exception {
		User vo = new User();
		vo.setId(id);
		deleteUser(vo, null);
	}

	public static void deleteUser(User vo) throws Exception {
		deleteUser(vo, null);
	}

	public static void deleteUser(User vo, Session session)
			throws Exception {
		UserDAO dao = new UserDAO();
		try {
			HUser po = getPO(vo);
			dao.deleteUser(po, session);
		} catch (Exception e) {
			throw e;
		}
	}

	public static void saveUser(User vo) throws Exception {
		saveUser(vo, null);
	}

	public static void saveUser(User vo, Session session)
			throws Exception {
		try {
			if (vo.getId() > 0L)
				updateUser(vo, session);
			else
				addUser(vo, session);
		} catch (Exception e) {
			throw e;
		}
	}

	public static List getUserList(User vo) throws Exception {
		return getUserList(vo, null, null);
	}

	public static List getUserList(User vo, Session session)
			throws Exception {
		return getUserList(vo, null, session);
	}

	public static List getUserList(User vo, Page page, Session session)
			throws Exception {
		List poList = null;
		List voList = new ArrayList();
		try {
			HUser po = getPO(vo);
			UserDAO dao = new UserDAO();
			poList = dao.getUserList(po, page, session);
			for (Iterator it = poList.iterator(); it.hasNext(); ) {
				po = (HUser) it.next();
				vo = getVO(po);
				voList.add(vo);
			}
			poList.clear();
			poList = null;
		} catch (Exception e) {
			throw e;
		}
		return voList;
	}

} 