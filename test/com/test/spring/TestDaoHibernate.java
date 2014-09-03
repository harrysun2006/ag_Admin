package com.test.spring;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.liferay.portal.spring.hibernate.HibernateUtil;

public class TestDaoHibernate extends HibernateDaoSupport {

	public long getCount(final String hql, final String[] parameters, final Object[] values) {
		return getCount2(hql, parameters, values);
	}

	public List queryList(String hql, String[] parameters, Object[] values) {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			Query q = session.createQuery(hql);
			if(parameters != null && values != null){
				if(parameters.length != values.length){
					throw new HibernateException("parameters and values are mismatch!");
				}
				for(int i = 0; i < parameters.length; i++){
					q.setParameter(parameters[i], values[i]);
				}
			}
			return q.list();
		} finally {
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * 
	 * @param hql
	 * @param parameters
	 * @param values
	 * @return
	 */
	public long getCount1(final String hql, final String[] parameters, final Object[] values) {
		setHibernateTemplate(new HibernateTemplate(HibernateUtil.getSessionFactory()));
		HibernateTemplate template = getHibernateTemplate();
		System.out.println("template = " + template + ", session factory = " + template.getSessionFactory());
		Number number = (Number)template.execute(new HibernateCallback(){

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				System.out.println("session = " + session.hashCode() + ", session factory = " + session.getSessionFactory());
				Query q = session.createQuery(hql);
				if(parameters != null && values != null){
					if(parameters.length != values.length){
						throw new HibernateException("parameters and values are mismatch!");
					}
					for(int i = 0; i < parameters.length; i++){
						q.setParameter(parameters[i], values[i]);
					}
				}
				return q.uniqueResult();
			}
		}, true);
		return (number == null) ? 0 : number.longValue();
	}

	public long getCount2(final String hql, final String[] parameters, final Object[] values) {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			System.out.println("session = " + session.hashCode() + ", session factory = " + session.getSessionFactory());
			Query q = session.createQuery(hql);
			if(parameters != null && values != null){
				if(parameters.length != values.length){
					throw new HibernateException("parameters and values are mismatch!");
				}
				for(int i = 0; i < parameters.length; i++){
					q.setParameter(parameters[i], values[i]);
				}
			}
			Long count = (Long) q.uniqueResult();
			return (count == null) ? 0 : count.longValue();
		} finally {
			HibernateUtil.closeSession(session);
		}
	}

}
