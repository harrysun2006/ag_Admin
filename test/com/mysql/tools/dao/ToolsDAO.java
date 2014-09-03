package com.mysql.tools.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import com.mysql.util.Hibernate3Helper;
import com.mysql.util.JDBCHelper;

public class ToolsDAO {

	private static final Log log = LogFactory.getLog(ToolsDAO.class);

	public void alterTable(String table, String[] specs, Session session)
			throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sql = new StringBuffer();
		boolean isSessionFromOuter = true;
		try {
			sql.append("alter table ").append(table);
			if (specs == null)
				specs = new String[0];
			if (specs.length > 0)
				sql.append(" ").append(specs[0]);
			for (int i = 1; i < specs.length; i++)
				sql.append(", ").append(specs[i]);
			if (session == null) {
				session = Hibernate3Helper.currentSession();
				isSessionFromOuter = false;
			}
			conn = session.connection();
			ps = conn.prepareStatement(sql.toString());
			if (log.isDebugEnabled())
				log.debug(sql.toString());
			ps.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				JDBCHelper.closeAll(null, ps);
				if (!isSessionFromOuter)
					Hibernate3Helper.closeSession();
			} catch (Exception ex) {
			}
		}
	}

	public List getTables(Session session) throws Exception {
		List list = new ArrayList();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		boolean isSessionFromOuter = true;
		try {
			sql.append("show tables");
			if (session == null) {
				session = Hibernate3Helper.currentSession();
				isSessionFromOuter = false;
			}
			conn = session.connection();
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(rs.getString(1));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				JDBCHelper.closeAll(rs, ps);
				if (!isSessionFromOuter)
					Hibernate3Helper.closeSession();
			} catch (Exception ex) {
			}
		}
		return list;
	}

	public List getVariables(String criteria, Session session) throws Exception {
		List list = new ArrayList();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		boolean isSessionFromOuter = true;
		try {
			sql.append("show variables");
			if(criteria != null && criteria.trim().length() > 0)
				sql.append(" like ?");
			if (session == null) {
				session = Hibernate3Helper.currentSession();
				isSessionFromOuter = false;
			}
			conn = session.connection();
			ps = conn.prepareStatement(sql.toString());
			int index = 0;
			if(criteria != null && criteria.trim().length() > 0)
				ps.setString(++index, criteria);
			rs = ps.executeQuery();
			String[] variable;
			while (rs.next()) {
				variable = new String[]{rs.getString(1), rs.getString(2)};
				list.add(variable);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				JDBCHelper.closeAll(rs, ps);
				if (!isSessionFromOuter)
					Hibernate3Helper.closeSession();
			} catch (Exception ex) {
			}
		}
		return list;
	}

	public void setVariable(String setvar, Session session) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		boolean isSessionFromOuter = true;
		try {
			sql.append("set ").append(setvar);
			if (session == null) {
				session = Hibernate3Helper.currentSession();
				isSessionFromOuter = false;
			}
			conn = session.connection();
			ps = conn.prepareStatement(sql.toString());
			ps.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				JDBCHelper.closeAll(rs, ps);
				if (!isSessionFromOuter)
					Hibernate3Helper.closeSession();
			} catch (Exception ex) {
			}
		}
	}
}
