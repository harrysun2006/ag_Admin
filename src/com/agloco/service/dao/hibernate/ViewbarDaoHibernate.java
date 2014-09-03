package com.agloco.service.dao.hibernate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.agloco.model.VBTimeTotal;
import com.agloco.service.dao.ViewbarDao;
/**
 * 
 * @author terry_zhao
 * @date May 8, 2007
 */
public class ViewbarDaoHibernate extends HibernateDaoSupport implements
		ViewbarDao {

	public VBTimeTotal getTimeTotal(Long memberId) {
		return (VBTimeTotal)getHibernateTemplate().get(VBTimeTotal.class, memberId);
	}

	public void updateTimeTotal(VBTimeTotal timeTotal) {
		getHibernateTemplate().saveOrUpdate(timeTotal);
	}

	public List getSubtotalTableNames(String baseName) throws Exception {
//		return getSession().createSQLQuery("SHOW TABLES LIKE :baseName")
//			.setParameter("baseName", baseName + "%").list();
//		return getHibernateTemplate().findByNamedQueryAndNamedParam("LIST_TABLES", "baseName", baseName + "%");
		List list = new ArrayList();
		Session session = getSession();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "show tables like ?";
		try {
			conn = session.connection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, baseName + '%');
			rs = ps.executeQuery();
			while(rs.next()) {
				list.add(rs.getString(1));
			}
		} catch(Exception e) {
			throw e;
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch(Exception ex) {}
			}
			if(ps != null) {
				try {
					ps.close();
				} catch(Exception ex) {}
			}
		}
		return list;
	}
}
