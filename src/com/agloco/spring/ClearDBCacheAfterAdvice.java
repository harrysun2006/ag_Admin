package com.agloco.spring;

import java.lang.reflect.Method;

import com.liferay.portal.spring.hibernate.CacheRegistry;

public class ClearDBCacheAfterAdvice implements
		org.springframework.aop.AfterReturningAdvice {

	public void afterReturning(Object returnValue, Method method, Object[] args,
			Object target) {
		CacheRegistry.clear();
	}

}
