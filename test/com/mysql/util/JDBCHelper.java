package com.mysql.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author 2709
 * FIXME �����(�ӿ�)DBHelper�����Ժͷ���
 */
public class JDBCHelper {

	/**
	 * �黹��ر����ݿ����ӡ�
	 * @param connection ���ݿ����ӡ�
	 * @throws SQLException
	 */
	public static void closeConnection(Connection connection)
		throws SQLException {
		if (connection != null) {
			connection.close();
			connection = null;
		}
	}

	/**
	 * �ر����ݿ���Դ: �����(ResultSet)�����(Statement)
	 * @param rs ���������
	 * @param stmt ������
	 */
	public static void closeAll(
		ResultSet rs,
		Statement stmt) {
		closeAll(rs, stmt, null, false);
	}

	/**
	 * �ر��������ݿ���Դ: �����(ResultSet)�����(Statement)������(Connection)
	 * @param rs ���������
	 * @param stmt ������
	 * @param connection ���ݿ����Ӷ���
	 */
	public static void closeAll(
		ResultSet rs,
		Statement stmt,
		Connection connection) {
		closeAll(rs, stmt, connection, true);
	}

	/**
	 * �ر��������ݿ���Դ: �����(ResultSet)�����(Statement)������(Connection)
	 * @param rs ���������
	 * @param stmt ������
	 * @param connection ���ݿ����Ӷ���
	 * @param closeConnection �Ƿ�ر�����
	 */
	public static void closeAll(
		ResultSet rs,
		Statement stmt,
		Connection connection,
		boolean closeConnection) {
		try {
			if (rs != null) {
				if (stmt == null)
					stmt = rs.getStatement();
				rs.close();
				rs = null;
			}
		} catch (Exception e) {
		} finally {
			try {
				if (stmt != null) {
					if (connection == null)
						connection = stmt.getConnection();
					stmt.close();
					stmt = null;
				}
			} catch (Exception e) {
			} finally {
				try {
					if (closeConnection && connection != null)
						closeConnection(connection);
				} catch (Exception e) {
				}
			}
		}
	}

}
