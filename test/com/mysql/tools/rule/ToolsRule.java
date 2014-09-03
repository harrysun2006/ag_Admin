package com.mysql.tools.rule;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import com.mysql.tools.dao.ToolsDAO;

public class ToolsRule {

	public static void alterTable(String table, String[] specs, Session session) throws Exception {
		try {
			ToolsDAO dao = new ToolsDAO();
			dao.alterTable(table, specs, session);
		} catch (Exception e) {
			throw e;
		}
	}

	public static List getUserTestList(Session session) throws Exception {
		List tables = new ArrayList();
		try {
			ToolsDAO dao = new ToolsDAO();
			tables = dao.getTables(session);
		} catch (Exception e) {
			throw e;
		}
		return tables;
	}

	public static List getVariables(String criteria, Session session) throws Exception {
		List variables = new ArrayList();
		try {
			ToolsDAO dao = new ToolsDAO();
			variables = dao.getVariables(criteria, session);
		} catch (Exception e) {
			throw e;
		}
		return variables;
	}

	public static void setVariable(String setvar, Session session) throws Exception {
		try {
			ToolsDAO dao = new ToolsDAO();
			dao.setVariable(setvar, session);
		} catch (Exception e) {
			throw e;
		}
	}
}
