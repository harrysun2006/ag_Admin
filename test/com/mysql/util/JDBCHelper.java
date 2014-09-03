package com.mysql.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author 2709
 * FIXME 检查类(接口)DBHelper的属性和方法
 */
public class JDBCHelper {

	/**
	 * 归还或关闭数据库连接。
	 * @param connection 数据库连接。
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
	 * 关闭数据库资源: 结果集(ResultSet)、语句(Statement)
	 * @param rs 结果集对象
	 * @param stmt 语句对象
	 */
	public static void closeAll(
		ResultSet rs,
		Statement stmt) {
		closeAll(rs, stmt, null, false);
	}

	/**
	 * 关闭所有数据库资源: 结果集(ResultSet)、语句(Statement)和连接(Connection)
	 * @param rs 结果集对象
	 * @param stmt 语句对象
	 * @param connection 数据库连接对象
	 */
	public static void closeAll(
		ResultSet rs,
		Statement stmt,
		Connection connection) {
		closeAll(rs, stmt, connection, true);
	}

	/**
	 * 关闭所有数据库资源: 结果集(ResultSet)、语句(Statement)和连接(Connection)
	 * @param rs 结果集对象
	 * @param stmt 语句对象
	 * @param connection 数据库连接对象
	 * @param closeConnection 是否关闭连接
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
