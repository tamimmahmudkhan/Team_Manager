package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnection 
{
	private static final String SQCONN = "jdbc:sqlite:src/database/inventory.sqlite";
	
	public static Connection getConnection()
	{
		try {
			Class.forName("org.sqlite.JDBC");
			return DriverManager.getConnection(SQCONN);
		}catch(ClassNotFoundException | SQLException ext) {
			ext.printStackTrace();
		}
		return null;
	}
}
