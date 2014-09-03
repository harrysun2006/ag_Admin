package com.test.spring;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;

import com.agloco.Constants;
import com.liferay.portal.spring.hibernate.HibernateConfiguration;
import com.liferay.portal.util.PropsUtil;

public class BeanFactoryAglocoProcessor implements BeanFactoryPostProcessor {

	private final static Log _log = LogFactory.getLog(BeanFactoryAglocoProcessor.class);
	protected final static String STRING_HIBERNATE_DATA_SOURCE = "hibernate.datasource.name";
	protected final static String STRING_HIBERNATE_DATA_SOURCE_ALTERNATE = "hibernate.datasource.alternate";
	private final static String beanNameLiferayDataSource = PropsUtil.get(STRING_HIBERNATE_DATA_SOURCE);
	private final static String beanNameUserLocalServiceProfessional = "com.liferay.portal.service.spring.UserLocalService.professional";
	private final static String beanNameAlternateDataSource = PropsUtil.get(STRING_HIBERNATE_DATA_SOURCE_ALTERNATE);
	private final static String propNameTargetDataSource = "targetDataSource";
	private final static String beanNameSessionFactory = "liferaySessionFactory";

	private static ConfigurableListableBeanFactory beanFactory;

	static BeanFactory getBeanFactory() {
		return beanFactory;
	}

	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
			throws BeansException {
		BeanFactoryAglocoProcessor.beanFactory = beanFactory;
		hookUserLocalServiceProfessionalBean();
		hookLiferayDataSource();
		hookHibernateConfiguration();
	}

	/**
	 * redefine the com.liferay.portal.service.spring.UserLocalService.professional bean's class
	 * to point it to our implement class
	 *
	 */
	private void hookUserLocalServiceProfessionalBean() {
		try {
			AbstractBeanDefinition bd = (AbstractBeanDefinition) beanFactory.getBeanDefinition(beanNameUserLocalServiceProfessional);
			bd.setBeanClass(UserLocalServiceImpl.class);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * redefine the liferayDataSource to use jdbc drivermanager datasource if there is no jndi datasource
	 *
	 */
	private void hookLiferayDataSource() {
		boolean noJndi = false;
		DataSource ds = null;
		Connection conn = null;
		try {
			ds = (DataSource) beanFactory.getBean(beanNameLiferayDataSource);
		} catch(Exception e) {
			noJndi = true;
		}
		try {
			// if no jndi datasource, then use driver manager datasource instead.
			if(noJndi) {
				AbstractBeanDefinition bd = (AbstractBeanDefinition) beanFactory.getBeanDefinition(beanNameLiferayDataSource);
				MutablePropertyValues props = bd.getPropertyValues();
				PropertyValue[] propValues = props.getPropertyValues();
				PropertyValue propValue = null;
				int at = -1;
				for(int i = 0; i < propValues.length; i++) {
					propValue = propValues[i];
					if(propNameTargetDataSource.equals(propValue.getName())) {
						at = i;
						break;
					}
				}
				// try the alternate datasource and use the first connectable
				if(at >= 0 && propValue != null) {
					RuntimeBeanReference ref = (RuntimeBeanReference) propValue.getValue();
					RuntimeBeanReference newRef = null;
					String[] beanNames = beanNameAlternateDataSource.split(",");
					for(int i = 0; i < beanNames.length; i++) {
						newRef = new RuntimeBeanReference(beanNames[i].trim(), ref.isToParent());
						propValue = new PropertyValue(propValue.getName(), newRef);
						props.setPropertyValueAt(propValue, at);
						try {
							ds = (DataSource) BeanFactoryAglocoProcessor.beanFactory.getBean(newRef.getBeanName());
							conn = ds.getConnection();
						} catch(Exception e) {
							conn = null;
						}
						if(conn != null) break;
					}
					if(conn != null && _log.isInfoEnabled()) {
						StringBuffer sb = new StringBuffer();
						sb
							.append(beanNameLiferayDataSource)
							.append("'s [").append(propValue.getName()).append("]property has been changed: ")
							.append(ref.getBeanName()).append(" ==> ").append(newRef.getBeanName());
						_log.info(sb.toString());
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
	}

	private void hookHibernateConfiguration() {
		try {
			AbstractBeanDefinition bd = (AbstractBeanDefinition) beanFactory.getBeanDefinition(beanNameSessionFactory);
			bd.setBeanClass(HibernateConfiguration.class);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
