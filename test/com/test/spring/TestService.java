package com.test.spring;

import java.util.List;

public interface TestService {
	
	/**
	 * 
	 * @param hql
	 * @param parameters
	 * @param values
	 * @return
	 */
	public long getCount(String hql, String[] parameters, Object[] values);

	public List queryList(String hql, String[] parameters, Object[] values);

}
