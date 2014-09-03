package com.agloco.spring;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;

import com.agloco.service.impl.CommonServiceImpl;
import com.agloco.service.impl.MemberServiceImpl;
import com.agloco.service.util.CommonServiceUtil;
import com.agloco.service.util.MemberServiceUtil;

public class ClearClusterCacheAdvice implements AfterReturningAdvice {

	public void afterReturning(Object returnValue, Method method,
			Object[] args, Object target) throws Throwable {
		
		if(CommonServiceImpl.class.getName().equals(target.getClass().getName())){
			CommonServiceUtil.clearCommonServiceUtilPool();
		}
		if(MemberServiceImpl.class.getName().equals(target.getClass().getName())){
			MemberServiceUtil.clearMemberServiceUtilPool();
		}
	}

}
