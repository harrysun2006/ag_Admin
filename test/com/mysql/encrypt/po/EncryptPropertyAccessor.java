package com.mysql.encrypt.po;

import java.lang.reflect.Method;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.PropertyNotFoundException;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.property.Getter;
import org.hibernate.property.PropertyAccessor;
import org.hibernate.property.Setter;

public class EncryptPropertyAccessor implements PropertyAccessor {

	public static final class EncryptGetter implements Getter {

		public Object get(Object owner) throws HibernateException {
			return owner;
		}

		public Object getForInsert(Object owner, SessionImplementor session)
				throws HibernateException {
			return owner;
		}

		public Object getForInsert(Object owner, Map mergeMap,
				SessionImplementor session) throws HibernateException {
			return owner;
		}

		public Class getReturnType() {
			return String.class;
		}

		public String getMethodName() {
			return null;
		}

		public Method getMethod() {
			return null;
		}

	}

	public static final class EncryptSetter implements Setter {

		public void set(Object target, Object value, SessionFactoryImplementor factory) throws HibernateException {
			System.out.println(target);
			System.out.println(value);
		}

		public String getMethodName() {
			return null;
		}

		public Method getMethod() {
			return null;
		}
	}

	public Getter getGetter(Class clazz, String name) throws PropertyNotFoundException {
		return new EncryptGetter();
	}

	public Setter getSetter(Class clazz, String name) throws PropertyNotFoundException {
		return new EncryptSetter();
	}

}
