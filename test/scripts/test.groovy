package scripts;

import test.Hello;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

class Test{
	static Map a()
	{
		println("test method");
		Map testMap = new HashMap();
		Calendar beginDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();
		beginDate.add(Calendar.HOUR, -1);
		
		testMap.put("param1", beginDate);
		testMap.put("param2", endDate);
		return testMap;
	}
	static void main(String[] args){
	
		System.out.println("Hello");
		
		Hello hello = new Hello();
		hello.testMethod();
	}

}
