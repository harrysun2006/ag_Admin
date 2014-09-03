package com.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.InetAddress;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.security.auth.login.AccountExpiredException;
import javax.security.auth.login.CredentialExpiredException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

import com.agloco.mail.ContentType;
import com.agloco.mail.MailEngine;
import com.agloco.mail.MailMessage;
import com.agloco.mail.Part;
import com.agloco.model.AGMember;
import com.agloco.model.AGMemberTemp;
import com.agloco.model.AGMemberTree;
import com.agloco.service.util.MailServiceUtil;
import com.agloco.service.util.MemberServiceUtil;
import com.agloco.service.util.ViewbarServiceUtil;
import com.agloco.util.ConvertUtil;
import com.agloco.util.Generator;
import com.agloco.util.HibernateUtil;
import com.agloco.util.MailUtil;
import com.agloco.util.MiscUtil;
import com.agloco.util.VelocityUtil;
import com.liferay.portal.security.pwd.PwdToolkitUtil;
import com.liferay.portal.spring.util.SpringUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.journal.service.spring.JournalArticleLocalServiceUtil;
import com.mysql.encrypt.rule.UserRule;
import com.mysql.encrypt.vo.User;
import com.mysql.tools.rule.ToolsRule;
import com.test.model.DynaTable;
import com.test.spring.TestService;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

public class Test {

	private static Log _log = LogFactory.getLog(Test.class);

	/**
	 * alter tables type engine & default charset
	 * @throws Exception
	 */
	private static void alertTablesType() throws Exception {
		List tables = ToolsRule.getUserTestList(null);
		String table;
		String[] specs = {"engine = InnoDB", "DEFAULT CHARSET = utf8"};
		int count = 0;
		for (Iterator it = tables.iterator(); it.hasNext();) {
			table = (String) it.next();
			try {
				ToolsRule.alterTable(table, specs, null);
				count++;
			} catch(Exception e) {}
		}
		System.out.println(count + " tables are altered!");
	}

	/**
	 * show all tables in database
	 * @throws Exception
	 */
	private static void showTables() throws Exception {
		List tables = ToolsRule.getUserTestList(null);
		for (Iterator it = tables.iterator(); it.hasNext();) {
			System.out.println(it.next());
		}
	}

	/**
	 * show variables of database & current connecting session
	 * @throws Exception
	 */
	private static void showVariables() throws Exception {
		List tables = ToolsRule.getVariables("c%" ,null);
		String[] variable;
		for (Iterator it = tables.iterator(); it.hasNext();) {
			variable = (String[]) it.next();
			System.out.println(variable[0] + " = " + variable[1]);
		}
	}

	private static void setVariable(String setvar) throws Exception {
		ToolsRule.setVariable(setvar ,null);
	}

	/**
	 * test encrypt data add
	 * @throws Exception
	 */
	private static void testEncryptAdd() throws Exception {
		User u;
		u = new User("弘宇", "pass");
		UserRule.addUser(u);
		System.out.println("add user " + u);
	}

	/**
	 * test encrypt data update
	 * @throws Exception
	 */
	private static void testEncryptUpdate() throws Exception {
		User u = UserRule.getUser(1);
		u.setPassword("playgame");
		UserRule.updateUser(u);
		System.out.println("update user " + u);
	}

	/**
	 * test encrypt data query
	 * @throws Exception
	 */
	private static void testEncryptQuery() throws Exception {
		User u = new User();
		u.setName("弘");
		List list = UserRule.getUserList(u);
		System.out.println("total " + list.size() + " users!");
		for(Iterator it = list.iterator(); it.hasNext(); ) {
			u = (User) it.next();
			System.out.println(u);
		}
	}

	private static void testGenerateMemberCode() throws Exception {
		String memberCode = Generator.generateMemberCode(AGMemberTemp.class.getName());
		System.out.println(memberCode);
	}

	/**
	 * update a temp member
	 * 
	 * @throws Exception
	 */
	private static void testUpdateMemberTemp() throws Exception {
		AGMemberTemp mt = MemberServiceUtil.getAGMemberTemp(new Long(16));
		mt.setUserId("liferay.com.1002");
		mt.setAddress1("sip park1");
		mt.setAddress2("sip park2");
		mt.setBirthDate("197603");
		mt.setCity("suzhou");
		mt.setCountry("China");
		mt.setCreateDate(Calendar.getInstance());
		mt.setEmailAddress("harry_sun@amaxgs.com");
		mt.setFirstName("弘宇");
		mt.setLastName("孙");
		mt.setMemberCode("AMAX0001");
		mt.setPassword("liferay");
		mt.setPostCode("215021");
		mt.setState("JiangSu");
		MemberServiceUtil.updateAGMemberTemp(mt);
		System.out.println(mt.getMemberId() + " is updated!");
	}

	/**
	 * get a temp member
	 * 
	 * @throws Exception
	 */
	private static void testGetMemberTemp() throws Exception {
		AGMemberTemp po = MemberServiceUtil.getAGMemberTemp(new Long(6));
		StringBuffer sb = new StringBuffer();
//		sb
//			.append("temp member:[")
//			.append(po.getUserId()).append(", ")
//			.append(po.getFirstName()).append(" ").append(po.getLastName()).append(", ")
//			.append(po.getMemberCode()).append(", ")
//			.append(po.getEmailAddress()).append(", ")
//			.append(po.getPassword()).append("]");
//		System.out.println(sb.toString());
		String text = po.getFirstName();
		text = new String(text.getBytes(), "UTF-8");
		System.out.println("first name: " + text);
	}

	/**
	 * get member's ancestors
	 * 
	 * @throws Exception
	 */
	private static void testGetMemberAncestors() throws Exception {
		AGMember member = MemberServiceUtil.getAGMember(new Long(5));
		List ancestors = MemberServiceUtil.getAncestors(member, AGMemberTree.STRING_AGMEMBER_STATUS_N);
		AGMember ancestor;
		for(Iterator it = ancestors.iterator(); it.hasNext(); ) {
			ancestor = (AGMember) it.next();
			System.out.print(ancestor.getMemberCode() + "(" + ancestor.getMemberTree().getLevel() + ") - ");
		}
		System.out.println(member.getMemberCode() + "(0)");
	}

	private static void testGetChildrenByLevel() throws Exception {
		AGMember member = new AGMember();
		member.setMemberId(new Long(3));
		List counts = MemberServiceUtil.getChildrenCountByLevel(member, AGMemberTree.STRING_AGMEMBER_STATUS_N);
		Object[] values;
		Long level, count;
		for(Iterator it = counts.iterator(); it.hasNext(); ) {
			values = (Object[]) it.next();
			level = (Long) values[0];
			count = (Long) values[1];
			System.out.println("level: " + level + ", count: " + count);
		}
	}

	/**
	 * send forgot member code mail
	 * 
	 * @throws Exception
	 */
	private static void testSendForgotMemberCodeMail() throws Exception {
		AGMember m = MemberServiceUtil.getAGMember(new Long(4));
		MailServiceUtil.sendForgotMemberCodeMail(m, Locale.SIMPLIFIED_CHINESE);
		System.out.println("forgot member code email sent to " + m.getEmailAddress());
	}

	/**
	 * get a member
	 * 
	 * @throws Exception
	 */
	private static void testGetMember() throws Exception {
		AGMember po = MemberServiceUtil.getAGMember(new Long(3));
		StringBuffer sb = new StringBuffer();
		sb
			.append("member:[")
			.append(po.getUserId()).append(", ")
			.append(po.getFirstName()).append(" ").append(po.getLastName()).append(", ")
			.append(po.getMemberCode()).append(", ")
			.append(po.getEmailAddress()).append(", ")
			.append(po.getPassword()).append("]");
		System.out.println(sb.toString());
	}

	/**
	 * get member's children count by level
	 * 
	 * @throws Exception
	 */
	private static void testGetMemberChildrenCountByLevel() throws Exception {
		AGMember member = new AGMember();
		member.setMemberId(new Long(1));
		List children = MemberServiceUtil.getChildrenCountByLevel(member, AGMemberTree.STRING_AGMEMBER_STATUS_N);
		Object[] values;
		for(Iterator it = children.iterator(); it.hasNext(); ) {
			values = (Object[]) it.next();
			System.out.print(values[0] + ": " + values[1] + ", ");
		}
	}

	/**
	 * test resource bundle
	 * @throws Exception
	 */
	private static void testResourceBundle() throws Exception {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		ResourceBundle rb = ResourceBundle.getBundle("code", Locale.getDefault());
		String text = rb.getString("value");
		String a = "AGLO0006";
		System.out.println(a.substring(0, 4));
		int b = Integer.parseInt(a.substring(4, a.length()));
		System.out.println(b);
	}

	/**
	 * regular expression test
	 * @throws Exception
	 */
	private static void testRegEx() throws Exception {
		String text;
		String regex;
		// member code
		text = "ABCD0001";
		regex = "[A-Z]{4}\\d{4}";
		// english email address
		text = "archzerg@gmail.com";
		regex = "[\\w\\-\\.]+@[\\w\\-]+(\\.[\\w\\-]+)+";
		// english & chinese email address
		text = "孙弘宇@新浪.com";
		regex = "[\u4E00-\u9FA5\\w\\-\\.]+@[\u4E00-\u9FA5\\w\\-]+(\\.[\\w\\-]+)+";
		// filtered email address
		text = "arch_zerg@Hotmail.com.cn";
		text = "archzerg@gmail.com";
		regex = ".*(@hotmail.com|@msn.com).*";
		Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(text);
		System.out.println("find = " + m.find() + ", matches = " + m.matches());
	}

	/**
	 * generate initial dictionary sql script
	 * @throws Exception
	 */
	private static void genDictionary() throws Exception {
		InputStream is = ClassLoader.getSystemResourceAsStream("country.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line;
		Pattern p = Pattern.compile("<option\\s+value=\"(\\w+)\">(.*)</option>");
		Matcher m;
		StringBuffer dsb = new StringBuffer();
		StringBuffer ddsb = new StringBuffer();
		String value, text;
		int count = 0;
		while((line = br.readLine()) != null) {
			m = p.matcher(line);
			if(m.find() && m.groupCount() == 2) {
				count++;
				value = m.group(1).replaceAll("'", "''");
				text = m.group(2).replaceAll("'", "''");
				dsb
					.append("insert into AG_Dictionary(dictionaryId, code, value_, order_, active_) values(")
					.append(count).append(", 'country', ")
					.append("'").append(value).append("', ")
					.append(count).append(", 1);\r\n");
				ddsb
					.append("insert into AG_DictionaryD(dictionaryDetailId, dictionaryId, language_, country, text_) values(")
					.append(count).append(", ")
					.append(count).append(", '', '', '").append(text).append("');\r\n");
			}
		}
		System.out.println(dsb.toString());
		System.out.println(ddsb.toString());
	}

	/**
	 * test quartz scheduled task
	 * @throws Exception
	 */
	private static void testQuartz() throws Exception {
		ApplicationContext context = SpringUtil.getContext();
		Thread.sleep(60000);
	}

	/**
	 * test password generate
	 * @throws Exception
	 */
	private static void testPasswordGen() throws Exception {
		String password = PwdToolkitUtil.generate();
		System.out.println(password);
	}

	/**
	 * test journal article read
	 * @throws Exception
	 */
	private static void testGetJournalArticle() throws Exception {
		String text = JournalArticleLocalServiceUtil.getArticleContent("agloco.com", "AGS_SIGNUP_EMAIL", "zh_CN", "");
		text = new String(text.getBytes(), "UTF-8");
		System.out.println(text);
	}

	/**
	 * test application context
	 * @throws Exception
	 */
	private static void testContextHelper() throws Exception {
		ContextHelper.addValue("test", new Long(33));
		Context ctx = new InitialContext();
		Object value = ctx.lookup("java:comp/env/test");
		System.out.println(value);
	}

	private static void testSendMail() throws Exception {
		Session session = MailEngine.getSession();
		System.out.println(session);
		InternetAddress from = new InternetAddress("agloco@amaxgs.com", "AGLOCO TEST");
		InternetAddress to = new InternetAddress("harry_sun@amaxgs.com", "Harry Sun");
		MailMessage message = new MailMessage(from, to, "test", "test OK!", true);
		MailEngine.send(message);
	}

	private static void genClassPath() throws Exception {
		StringBuffer sb = new StringBuffer();
		File dir = new File("ROOT/WEB-INF/lib");
		String[] files = dir.list();
		for(int i = 0; i < files.length; i++) {
			sb.append("\"$WLIB\"/").append(files[i]).append(":");
		}
		dir = new File("lib");
		files = dir.list();
		for(int i = 0; i < files.length; i++) {
			sb.append("\"$LIB\"/").append(files[i]).append(":");
		}
		System.out.println(sb.toString());
	}

	/**
	 * 测试字符串比较
	 * d, e前加修饰符final后a == g
	 * 编译期间和运行期间String的NewInstance, String是ProtoType模式
	 * @throws Exception
	 */
	private static void testStringCompare() throws Exception {
		String a = "JAVA";
		String d = "JA";
		String e = "VA";
		String f = "JA" + "VA";
		String g = d + e;
		System.out.println("a = \"" + a + "\"");
		System.out.println("d = \"" + d + "\"");
		System.out.println("e = \"" + e + "\"");
		System.out.println("f = \"JA\" + \"VA\"");
		System.out.println("g = d + e");
		System.out.println("a == d : " + (a == d));//false
		System.out.println("a == f : " + (a == f));//true
		System.out.println("a == g : " + (a == g));//false
		System.out.println("f == g : " + (f == g));//false
		System.out.println("d + e == f : " + ((d + e) == f));//false
		System.out.println("d + e == g : " + ((d + e) == g));//false
	}

	private static void testLocale() throws Exception {
		String languageId = "zh";
		int i = languageId.indexOf('_');
		String language = (i < 0) ? languageId : languageId.substring(0, i);
		String country = (i < 0) ? "" : languageId.substring(i + 1);
		Locale locale = new Locale(language, country);
		System.out.println(locale);
	}

	private static void testTimeZone() throws Exception {
		String[] timezones = TimeZone.getAvailableIDs();
		for(int i = 0; i < timezones.length; i++) {
			System.out.println(timezones[i]);
		}
	}

	private static void testCalendar() {
		Calendar nowUTC = Calendar.getInstance();
		Calendar nowPST = Calendar.getInstance(TimeZone.getTimeZone("PST"));
		System.out.println(nowUTC);
		System.out.println(nowPST);
		DateFormat df = new SimpleDateFormat("z(Z) yyyy-MM-dd HH:mm:ss");
		System.out.println("user timezone: " + System.getProperty("user.timezone") + ", now: " + df.format(nowUTC.getTime()));
		df.setTimeZone(TimeZone.getTimeZone("UTC"));
		System.out.println("user timezone: " + System.getProperty("user.timezone") + ", now: " + df.format(nowUTC.getTime()));
		df.setTimeZone(TimeZone.getTimeZone("PST"));
		System.out.println("user timezone: " + System.getProperty("user.timezone") + ", now: " + df.format(nowPST.getTime()));
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		Calendar now = Calendar.getInstance();
		System.out.println(now);
		df = new SimpleDateFormat("z(Z) yyyy-MM-dd HH:mm:ss");
		System.out.println("user timezone: " + System.getProperty("user.timezone") + ", now: " + df.format(nowUTC.getTime()));
		System.out.println("user timezone: " + System.getProperty("user.timezone") + ", now: " + df.format(now.getTime()));
	}

	private static void testVelocity() throws Exception {
		ApplicationContext context = SpringUtil.getContext();
		InputStream is = ClassLoader.getSystemResourceAsStream("query1.vm");
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line;
		StringBuffer in = new StringBuffer();
		Map map = new Hashtable();
		map.put("NUM1", new Double(3.14));
		map.put("NUM2", new Long(6));
		map.put("RETURN", "\r\n");
		while((line = br.readLine()) != null) {
			in.append(line);
		}
		String out = VelocityUtil.evaluate(in.toString(), map);
		System.out.println(out);
	}

	private static void testFreeMarker1() throws Exception {
		String s = ClassLoader.getSystemResource("query1.ftl").getFile();
		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(new File(s).getParentFile());
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		//Template t = new Template("test", new StringReader(in.toString()), cfg);
		Template t = cfg.getTemplate("query1.ftl");
		StringWriter writer = new StringWriter();
		Map map = new HashMap();
		map.put("NUM1", new Double(3.14));
		map.put("NUM2", new Long(6));
		t.process(map, writer);
		System.out.println(writer.toString());
	}

	private static void testFreeMarker2() throws Exception {
		InputStream is = ClassLoader.getSystemResourceAsStream("query1.ftl");
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line;
		StringBuffer in = new StringBuffer();
		while((line = br.readLine()) != null) {
			in.append(line).append("\n");
		}
		Configuration cfg = new Configuration();
		//cfg.setObjectWrapper(new DefaultObjectWrapper());
		Template t = new Template("test", new StringReader(in.toString()), cfg);
		StringWriter writer = new StringWriter();
		Map map = new HashMap();
		map.put("NUM1", new Double(3.14));
		map.put("NUM2", new Long(6));
		t.process(map, writer);
		System.out.println(writer.toString());
	}

	private static void testMail() throws Exception {
		String to = "\"=?gb2312?B?z/rK2w==?=\" <sm@greapo.com.cn>,\r\n"
			+ "  \"=?gb2312?B?y++669Pu?=\" <gp2709@greapo.com.cn>";
		InternetAddress[] addresses = MailUtil.getAddress(to);
		System.out.println(addresses.length);
		String cc = MailUtil.getAddressText(addresses);
		System.out.println(cc);
		InternetAddress[] bcc = new InternetAddress[] {
				new InternetAddress("harry_sun@amaxgs.com", "孙弘宇"),
				new InternetAddress("erick_kong@amaxgs.com", "孔玉"),
		};
		System.out.println(MailUtil.getAddressText(bcc));
		String subject = "=?gb2312?B?ytTK1Gh0bWwouqzNvMasLEZsYXNoLE1JRCm1xNPKvP4NytTK1Lbg0NC1xNb3zOI=?=";
		System.out.println(MailUtil.decodeText(subject));
		to = "Richard Sze <sze_richard@hotmail.com>, Alexander Tsai <atsai@emailisc.com>";
		MailMessage mail = new MailMessage();
		mail.setRecipient(to);
		InternetAddress[] tos = mail.getRecipients(Message.RecipientType.TO);
		System.out.println(tos.length);
	}

	private static void testContentType() {
		String source, regex;
		source = "multipart/mixed;\r\n\tboundary=\"--NEXT_001_E847BF_5AC266A9607CC04E2C1F1\";\r\n\tcharset = 	\"gb2312 utf-8\"";
		source = "multipart/mixed;\r\n\tboundary=\"--NEXT_001_E847BF_5AC266A9607CC04E2C1F1\";\r\n\tcharset = 	\"gb2312 utf-8\r\n";
		source = "multipart/mixed;\r\n\tboundary=\"--NEXT_001_E847BF_5AC266A9607CC04E2C1F1\";\r\n\tcharset = 	gb2312 utf-8\"\"\r\n";
		source = "multipart/mixed;\r\n\tboundary=\"--NEXT_001_E847BF_5AC266A9607CC04E2C1F1\";\r\n\tcharset=\"gb2312 utf-8\r\n";
		source = "multipart/mixed;\r\n\tboundary=\"--NEXT_001_E847BF_5AC266A9607CC04E2C1F1\";\r\n\tcharset= \"\"gb2312 utf-8\r\n";
		source = "Content-Type: multipart/mixed;\r\n\tboundary=\"--NEXT_001_E847BF_5AC266A9607CC04E2C1F1\";\r\n\tcharset = 	gb2312 utf-8\"\"\r\nContent-Transfer-Encoding";
		source = "multipart/mixed;\r\n\tboundary=\"--NEXT_001_E847BF_5AC266A9607CC04E2C1F1\";\r\n\tcharset = 	\"gb2312 utf-8\r\n";
		source = "text/plain; ";
		source = "charset=utf-8; text/html;";
		regex = "([^;\\r\\n]*)?[;\\r\\n]?(?:\\s*(\\w+)\\s*=\\s*\"*([^\"\\r\\n]*)\"*;?)?";
		regex = "(?:\\s*(\\w+)\\s*=\\s*\"*([^\";\\r\\n]*)\"*;*)|(?:\\s*[\\w-]+\\s*:\\s*)?([^;\\s\\r\\n]+)";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(source);
		while(m.find()) {
			for(int i = 0; i <= m.groupCount(); i++)
				System.out.println("group " + i + ": " + m.group(i) + "[" + m.start(i) + ", " + m.end(i) + "]");
		}
		Part.Type t = new Part.Type("text/html");
		System.out.println(t.getPrimaryType());
		System.out.println(t.getSubType());
		ContentType ct = new ContentType("text/plain; ");
		System.out.println(ct.toString());
		ct = new ContentType("charset=utf-8; ");
		ct.setType(Part.Type.TEXT);
		System.out.println(ct.toString());
	}

	private static void testDynaHbmMapping() throws Exception {
		ApplicationContext ctx = SpringUtil.getContext();
		TestService service = (TestService)ctx.getBean(TestService.class.getName());
		String hql;
		long count;
		List list;
		DynaTable item;

		// original class mapping com.test.model.DynaTable1 ==> AA_DynaTable1
		hql = "select count(dt) from DynaTable1 dt";
		count = service.getCount(hql, null, null);
		System.out.println("count = " + count);

		// class mapping altered com.test.model.DynaTable1 ==> AA_DynaTable2
		HibernateUtil.alterMapping("com.test.model.DynaTable1", "AA_DynaTable2");
		hql = "select count(dt) from DynaTable1 dt";
		count = service.getCount(hql, null, null);
		System.out.println("count = " + count);

		// class mapping added: com.test.model.DynaTable1 ==> AA_DynaTable2, dynaClass ==> AA_DynaTable3
		String className = HibernateUtil.addMapping("com.test.model.DynaTable1", "AA_DynaTable3");
		hql = "select count(dt) from " + className + " dt";
		count = service.getCount(hql, null, null);
		System.out.println("count = " + count);
		hql = "select dt from " + className + " dt";
		list = service.queryList(hql, null, null);
		for(Iterator it = list.iterator(); it.hasNext(); ) {
			item = (DynaTable) it.next();
			System.out.println(item);
		}

		// previous class mapping reserved: com.test.model.DynaTable1 ==> AA_DynaTable2
		hql = "select count(dt) from DynaTable1 dt";
		count = service.getCount(hql, null, null);
		System.out.println("count = " + count);
		hql = "select dt from DynaTable1 dt";
		list = service.queryList(hql, null, null);
		for(Iterator it = list.iterator(); it.hasNext(); ) {
			item = (DynaTable) it.next();
			System.out.println(item);
		}

	}

	private static void testDyanCreateTable() throws Exception {
		String script = HibernateUtil.generateSchemaCreationScript("com.test.model.DynaTable", "AA_DynaTable6");
		System.out.println(script);
		HibernateUtil.createTable("com.test.model.DynaTable", "AA_DynaTable6");
	}

	private static void testRedirect() throws Exception {
		String url = "http://www.agloco.com/r/bbbb-1123/xxx/";
		int begin = url.indexOf("/r/");
		int end = url.indexOf("/", begin + 3);
		if(end < 0) end = url.length();
		String referralCode = url.substring(begin + 3, end);
		System.out.println(referralCode);
		System.out.println(MiscUtil.getLocalIp(false));
		System.out.println(InetAddress.getLocalHost().getHostAddress());
	}

	private static void testJAAS() throws Exception {
		// -Djava.security.manager -Djava.security.policy=xxx -Djava.security.auth.policy=xxx -Djava.security.auth.login.config=xxx
		System.setProperty("java.security.auth.login.config", "jaas.config");
		LoginContext lc = null;
		try {
		    lc = new LoginContext("PortalRealm");
		} catch (LoginException le) {
		    le.printStackTrace();
		} 
		try {
			lc.login();
		} catch (AccountExpiredException aee) {
			System.out.println("Your account has expired!");
		} catch (CredentialExpiredException cee) {
			System.out.println("Your credentials have expired!");
		} catch (FailedLoginException fle) {
			System.out.println("Authentication Failed!");
		} catch (Exception e) {
			System.out.println("Unexpected Exception - unable to continue");
			e.printStackTrace();
		}
		// let's see what Principals we have
		Iterator principalIterator = lc.getSubject().getPrincipals().iterator();
		System.out.println("Authenticated user has the following Principals: ");
		while (principalIterator.hasNext()) {
		  Principal p = (Principal)principalIterator.next();
		  System.out.println("\t" + p.toString());
		}
		System.out.println("User has " +
			lc.getSubject().getPublicCredentials().size() +
			" Public Credential(s)");
		lc.logout();
	}

	private static void testCharset() throws Exception {
		String utf8 = "鐢ㄦ埛鍚嶅拰瀵嗙爜";
		String gb = "用户名和密码";
		System.out.println(new String(gb.getBytes("UTF-8")));
		System.out.println(new String(utf8.getBytes(), "UTF-8"));
	}

	private static void testListFiles() throws Exception {
		String root = "Z:/Harry/Rescue/Temp";
		File rf = new File(root);
		File[] subf = rf.listFiles();
		for(int i = 0; i < subf.length; i++) {
			System.out.println(subf[i].getName());
		}
		String[] subs = rf.list();
		for(int i = 0; i < subs.length; i++) {
			System.out.println(subs[i]);
		}
	}

	private static void testDateParse() throws Exception {
		String date = "33/45/2007";
		DateFormat df1 = new SimpleDateFormat("MM/dd/yyyy");
		DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
		Date d = df1.parse(date);
		System.out.println(df2.format(d));
	}

	private static void testGetSubtotalTableNames() throws Exception {
		List list = ViewbarServiceUtil.getSubtotalTableNames("VB_Time_Subtotal_");
		String name;
		for(Iterator it = list.iterator(); it.hasNext(); ) {
			name = (String) it.next();
			System.out.println(name);
		}
	}

	private static void testReadDate() throws Exception {
		InputStream is = System.in;
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = new Date();
		String r;
		System.out.println("Now is " + df.format(d) + "[" + d.getTime() + "]!");
		while(true) {
			System.out.print("please input a time in milliseconds: ");
			r = in.readLine();
			if(r.equalsIgnoreCase("q")) break;
			d.setTime(Long.parseLong(r));
			System.out.println(df.format(d));
		} 
	}

	private static void showSystemProperties() throws Exception {
    //print available locales 
    Locale list[] = Locale.getAvailableLocales();
    System.out.println("======System available locales:======== "); 
    for (int i = 0; i < list.length; i++) { 
        System.out.println(list[i].toString() + "\t" + list[i].getDisplayName()); 
    } 

    //print JVM default properties 
    System.out.println("======System property======== "); 
    System.getProperties().list(System.out); 
	}

	private static void testMD5() throws Exception {
		InputStream is = System.in;
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		String r;
		while(true) {
			System.out.print("please input a string: ");
			r = in.readLine();
			if(r.equalsIgnoreCase("q")) break;
			System.out.println(MD5HashUtil.hashCode(r));
		} 
	}

	public static void main(String[] args) throws Exception {
		try {
			ConvertUtil.noop();
			ContextHelper.addResource("test-mail.properties");
			ContextHelper.addResource("agloco-jdbc.properties");
			ContextHelper.addResource("viewbar-jdbc.properties");
			SpringHelper.addSpringConfig("META-INF/test-spring.xml");
			SpringHelper.addHibernateConfig("META-INF/test.hbm.xml");
			PropsUtil.set("hibernate.show_sql", "true");
//			alertTablesType();
//			showTables();
//			setVariable("character_set_results = utf8");
//			showVariables();
//			testEncryptAdd();
//			testEncryptQuery();
//			testEncryptUpdate();
//			testEncryptQuery();
//			testResourceBundle();
//			testGenerateMemberCode();
//			testUpdateMemberTemp();
//			testGetMemberTemp();
//			testGetMemberAncestors();
//			testGetChildrenByLevel();
//			testSendForgotMemberCodeMail();
//			testGetMember();
//			testGetMemberChildrenCountByLevel();
//			testResourceBundle();
//			testStringCompare();
//			testRegEx();
//			genDictionary();
//			testQuartz();
//			testPasswordGen();
//			testGetJournalArticle();
//			testContextHelper();
//			testSendMail();
//			genClassPath();
//			testLocale();
//			testTimeZone();
//			testCalendar();
//			testVelocity();
//			testFreeMarker1();
//			testFreeMarker2();
//			testMail();
//			testContentType();
//			testDynaHbmMapping();
//			testDyanCreateTable();
//			testRedirect();
//			testJAAS();
//			testCharset();
//			testListFiles();
//			testDateParse();
//			testGetSubtotalTableNames();
//			testReadDate();
//			showSystemProperties();
			testMD5();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
}
