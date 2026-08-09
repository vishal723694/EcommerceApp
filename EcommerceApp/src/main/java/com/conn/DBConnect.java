package com.conn;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Database Connection provider for SQLite / MySQL.
 * Automatically resolves portable database file paths for seamless cross-platform deployment.
 * 
 * @author Vishal
 */
public class DBConnect 
{
	private static Connection conn = null;
	
	public static Connection getConn()
	{
		try {
			if (conn != null && !conn.isClosed()) {
				return conn;
			}
			
			Class.forName("org.sqlite.JDBC");
			
			// Primary check: Local portable database file in execution directory
			File localDb = new File("mydatabase.db");
			if (localDb.exists()) {
				conn = DriverManager.getConnection("jdbc:sqlite:" + localDb.getAbsolutePath());
			} else {
				// Fallback to configured relative path
				conn = DriverManager.getConnection("jdbc:sqlite:mydatabase.db");
			}
			
		} catch (Exception e) {
			try {
				// Secondary fallback try MySQL driver if SQLite is not active
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/EcommerceDB", "root", "root");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		return conn;
	}
}
