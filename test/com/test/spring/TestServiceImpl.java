package com.test.spring;

import java.util.List;

public class TestServiceImpl implements TestService {

	private TestDaoHibernate dao;

	public long getCount(String hql, String[] parameters, Object[] values) {
		return dao.getCount(hql, parameters, values);
	}

	public List queryList(String hql, String[] parameters, Object[] values) {
		return dao.queryList(hql, parameters, values);
	}

	public void setDao(TestDaoHibernate dao) {
		this.dao = dao;
	}

}
