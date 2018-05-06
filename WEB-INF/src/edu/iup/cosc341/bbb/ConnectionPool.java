package edu.iup.cosc341.bbb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConnectionPool {
	private static String dsnName;
	private static String userName;
	private static String password;
	private static ArrayList<Connection> readConnections;
	private static ArrayList<Connection> writeConnections;

	public static void initializePool(String driverName, String dsnName,
			String userName, String password) throws ClassNotFoundException,
			SQLException {

		ConnectionPool.dsnName = dsnName;
		ConnectionPool.userName = userName;
		ConnectionPool.password = password;

		readConnections = new ArrayList<Connection>();
		writeConnections = new ArrayList<Connection>();

		Class.forName(driverName);

		readConnections.add(makeConnection());
	}

	private static Connection makeConnection() throws SQLException {
		return DriverManager.getConnection(dsnName, userName, password);
	}

	public static Connection getConnection() throws SQLException {
		Connection conn = null;
		synchronized (readConnections) {
			if (readConnections.size() == 0) {
				conn = makeConnection();
			} else {
				conn = (Connection) readConnections.remove(0);
			}
		}

		return conn;
	}

	public static Connection getTxWrConnection() throws SQLException {
		synchronized (writeConnections) {
			Connection conn;
			if (writeConnections.size() <= 0) {
				conn = makeConnection();
				conn.setAutoCommit(false);
			} else {
				conn = (Connection) writeConnections.remove(0);
			}

			return conn;
		}
	}

	public static void returnConnection(Connection connection)
			throws SQLException {
		if (connection.getAutoCommit() == true) {
			readConnections.add(connection);
		} else {
			writeConnections.add(connection);
		}
	}
}
