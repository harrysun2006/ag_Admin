/*
 * Created on 2004-12-9:10:16:14 by 2709
 *
 * ====================================================================
 * 
 * Project : design-mode
 * File : TestGroovy.java
 *
 */
package com.test;

import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import groovy.lang.GroovyShell;

import java.io.File;
import java.util.Map;


class GroovyTest {

	public static void main(String[] agrs)
	{
	
		Binding binding = new Binding();

		GroovyShell shell = new GroovyShell(binding);

		ClassLoader parent = GroovyTest.class.getClassLoader();
		GroovyClassLoader loader = new GroovyClassLoader(parent);
		Class groovyClass;
		
		
		try
		{
			groovyClass = loader.parseClass(new File("addon/scripts/tt.groovy"));
			GroovyObject groovyObject = (GroovyObject) groovyClass.newInstance();
			Map paramMap = (Map)groovyObject.invokeMethod("processResult", null);
			Object[] paramNameArr = (Object[])paramMap.keySet().toArray();
			for(int i=0;i<paramNameArr.length;i++)
			{
				System.out.println((String)paramNameArr[i]+" "+paramMap.get(paramNameArr[i]));
			}
			

		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//lets call some method on an instance

	}
}
