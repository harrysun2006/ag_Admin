package com.mysql.encrypt.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mysql.encrypt.po.HUser;
import com.mysql.util.Hibernate3Helper;
import com.sophia.pagination.Hibernate3Counter;
import com.sophia.pagination.Page;

public class UserDAO {

	public UserDAO() {
	}

	public int addUser(HUser user, Session session) throws Exception {
		int result = 0;
		Transaction tx = null;
		boolean isSessionFromOuter = true;
		try {
			if (session == null) {
				session = Hibernate3Helper.currentSession();
				tx = session.beginTransaction();
				isSessionFromOuter = false;
			}
			user.encrypt();
			session.save(user);
			if (!isSessionFromOuter && tx != null)
				tx.commit();
			result = 1;
		} catch (Exception e) {
			try {
				if (!isSessionFromOuter && tx != null)
					tx.rollback();
			} catch (Exception exception) {
			}
			throw e;
		} finally {
			try {
				if (!isSessionFromOuter)
					Hibernate3Helper.closeSession();
			} catch (Exception ex) {
			}
		}
		return result;
	}

	public int updateUser(HUser user, Session session)
			throws Exception {
		int result = 0;
		Transaction tx = null;
		boolean isSessionFromOuter = true;
		try {
			if (session == null) {
				session = Hibernate3Helper.currentSession();
				tx = session.beginTransaction();
				isSessionFromOuter = false;
			}
			HUser temp = new HUser();
			temp = (HUser) session.get(HUser.class, user.getId());
			if (temp != null) {
				BeanUtils.copyProperties(temp, user);
				temp.encrypt();
				session.update(temp);
				result = 1;
			}
			if (!isSessionFromOuter && tx != null)
				tx.commit();
		} catch (Exception e) {
			try {
				if (!isSessionFromOuter && tx != null)
					tx.rollback();
			} catch (Exception exception) {
			}
			throw e;
		} finally {
			try {
				if (!isSessionFromOuter)
					Hibernate3Helper.closeSession();
			} catch (Exception ex) {
			}
		}
		return result;
	}

	public int deleteUser(HUser user, Session session)
			throws Exception {
		int result = 0;
		Transaction tx = null;
		boolean isSessionFromOuter = true;
		try {
			if (session == null) {
				session = Hibernate3Helper.currentSession();
				tx = session.beginTransaction();
				isSessionFromOuter = false;
			}
			if (user != null) {
				session.delete(user);
				result = 1;
			}
			if (!isSessionFromOuter && tx != null)
				tx.commit();
		} catch (Exception e) {
			try {
				if (!isSessionFromOuter && tx != null)
					tx.rollback();
			} catch (Exception exception) {
			}
			throw e;
		} finally {
			try {
				if (!isSessionFromOuter)
					Hibernate3Helper.closeSession();
			} catch (Exception ex) {
			}
		}
		return result;
	}

	public HUser getUser(Long id, Session session) throws Exception {
		HUser user = null;
		boolean isSessionFromOuter = true;
		try {
			if (session == null) {
				session = Hibernate3Helper.currentSession();
				isSessionFromOuter = false;
			}
			user = (HUser) session.get(HUser.class, id);
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (!isSessionFromOuter)
					Hibernate3Helper.closeSession();
			} catch (Exception ex) {
			}
		}
		return user;
	}

	public void loadUser(HUser user, Session session)
			throws Exception {
		boolean isSessionFromOuter = true;
		try {
			if (session == null) {
				session = Hibernate3Helper.currentSession();
				isSessionFromOuter = false;
			}
			session.load(user, user.getId());
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (!isSessionFromOuter)
					Hibernate3Helper.closeSession();
			} catch (Exception ex) {
			}
		}
	}

	public List getUserList(HUser user, Page page, Session session)
			throws Exception {
		Query query = null;
		List list = new ArrayList();
		StringBuffer hql = new StringBuffer();
		boolean isSessionFromOuter = true;
		try {
			hql.append("select ut from HUser ut where 1 = 1");
			if (user != null) {
				if (user.getName() != null
						&& user.getName().trim().length() > 0)
					hql.append(" and ut.name like :name");
			}
			if (session == null) {
				session = Hibernate3Helper.currentSession();
				isSessionFromOuter = false;
			}
			query = session.createQuery(hql.toString());
			if (user != null) {
				if (user.getName() != null
						&& user.getName().trim().length() > 0)
					query.setParameter("name", "%" + user.getName() + "%");
			}
			if (page != null && page != null)
				page.paginate(new Hibernate3Counter(query, session));
			list = query.list();
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (!isSessionFromOuter)
					Hibernate3Helper.closeSession();
			} catch (Exception ex) {
			}
		}
		return list;
	}
}