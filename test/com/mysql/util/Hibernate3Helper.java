package com.mysql.util;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.mapping.RootClass;

public class Hibernate3Helper {

	private static final String HIBERNATE_CONFIGURE = "/test.cfg.xml";
	private static SessionFactory sessionFactory = null;
	private static SessionFactoryImplementor sessionFactoryImpl = null;
	private static Configuration config = null;
	private static boolean show = true;
	private static final Class[] PO_CLASSES = new Class[] {
	};

	private static final ThreadLocal session = new ThreadLocal();

	static {
		init();
	}

	private synchronized static void init() {
		config = new Configuration();
		Exception exp = null;
		Map map = null;
		Map temp = new Hashtable();		// store config temporary
		String sessionFactoryName = "";
		// read configure file, default is /hibernate.cfg.xml
		try {
			config.configure(HIBERNATE_CONFIGURE);
			addMapClasses(config, PO_CLASSES);
			map = config.getProperties();
			sessionFactoryName = (String)map.get(Environment.SESSION_FACTORY_NAME);
			temp.putAll(map);
		} catch(Throwable e) {
			throw new RuntimeException("Read hibernate3 configure file failed!", e);
		}
		// if session factory bound into datasource jndi context, get it
		if(sessionFactory == null) {
			try {
				Context ctx = new InitialContext();
				sessionFactory = (SessionFactory)ctx.lookup(sessionFactoryName);
				if(!valid(sessionFactory, PO_CLASSES)) sessionFactory = null;
			} catch(Exception e) {
				exp = e;
			}
		}
		/**
		 * if not found session factory, build it in datasource manner
		 * (remove connection.driver_class ... items first)
		 */ 
		if(sessionFactory == null) {
			try {
				map.remove(Environment.DRIVER);
				map.remove(Environment.USER);
				map.remove(Environment.PASS);
				map.remove(Environment.URL);
				map.remove(Environment.POOL_SIZE);
				if(map.get(Environment.DATASOURCE) != null) sessionFactory = config.buildSessionFactory();
			} catch(Exception e) {
				exp = e;
			} 
		}
		/**
		 * if build failed (no datasource),
		 * build it again in connection pools manner
		 * (remove connection.datasource configure item first)
		 */
		if(sessionFactory == null) {
			try {
				map.putAll(temp);
				map.remove(Environment.DATASOURCE);
				sessionFactory = config.buildSessionFactory();
			} catch(Exception e) {
				exp = e;
			}
		}
		if(sessionFactory == null) throw new RuntimeException("Initialize hibernate3 failed!", exp);
		try {
			if(SessionFactoryImplementor.class.isInstance(sessionFactory)) {
				SessionFactoryImplementor sfi = (SessionFactoryImplementor) sessionFactory;
				show = sfi.getSettings().isShowSqlEnabled();
			}
		} catch(Exception e) {}
		System.out.println("Initialize hibernate3 successful!");
	}

	/**
	 * register classes of this module
	 * @param config
	 * @param classes
	 * @throws Exception
	 */
	private static void addMapClasses(Configuration config, Class[] classes) throws Exception {
		List map = new ArrayList();
		RootClass rc;
		for(Iterator it = config.getClassMappings(); it.hasNext(); ){
			rc = (RootClass)it.next();
			map.add(rc.getMappedClass());
		}
		if(classes == null) classes = new Class[0];
		for(int i = 0; i < classes.length; i++) {
			if(!map.contains(classes[i])) config.addClass(classes[i]);
		}
	}

	/**
	 * validate if the session factory includes all po classes of this module
	 * @param sessionFactory
	 * @return
	 */
	private static boolean valid(SessionFactory sessionFactory, Class[] classes) {
		boolean r = true;
		if(classes == null) classes = new Class[0];
		for(int i = 0; i < classes.length; i++) {
			try {
				if(sessionFactory.getClassMetadata(classes[i]) == null) r = false;
			} catch(Exception e) {
				r = false;
			}
			if(!r) break;
		}
		return r;
	}

	private Hibernate3Helper() {}

	public static void noop(){}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static Map getProperties() {
		return (config == null) ? null : config.getProperties();
	}

	public static boolean isShowSqlEnabled() {
		return show;
	}

	/**
	 * 取得一个会话
	 * @return Session
	 * @throws HibernateException
	 */	
	public static Session currentSession() throws HibernateException {
		Session s = (Session) session.get();
		if (s == null) {
			s = sessionFactory.openSession();
			//s = HibernateUtil.openSession();
			session.set(s);
		}
		if (!s.isConnected()) {
			s.reconnect();
		}
		return s;
		
	}
	
	/**
	 * 取得一个会话
	 * @return Session
	 * @throws HibernateException
	 */	
	public static Session currentSession(Connection conn) throws HibernateException { 
		Session s = (Session) session.get();
		if (s == null) {
			s = sessionFactory.openSession(conn);
			session.set(s);
		}
		if (!s.isConnected()) {
			s.reconnect();
		}
		return s;
	}
	
	/**
	 * 关闭会话
	 * @throws HibernateException
	 */
	public static void closeSession() throws HibernateException {
		Session s = (Session) session.get();
		if (s != null) {
			s.close();
		}
		session.set(null);
	}	

	public static void closeSession(Session s) throws HibernateException {
		if (s != null) s.close();
	}
}
