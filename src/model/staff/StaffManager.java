package model.staff;

import database.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StaffManager 
{
	
	/*
	 * Manages staff data to and from database.
	 */

	private PreparedStatement statement;
	private	ResultSet set;

	
	private	String  query;
	
	private	ObservableList<EmployeeData> tableData;
	private final String INSERT_QUERY_STRING = "insert into staff(fname,lname,dob,position,workDays,absentDays,email,password) values(?,?,?,?,?,?,?,?)";
	private final String REMOVE_QUERY_STRING = "delete from staff where fname=? and lname=? and dob=? and position=?";
	private final String SELECT_ALL_QUERY_STRING = "select * from staff";
	private final String SEARCH_QUERY_STRING = "select * from staff where email=? and password=?";
	private final String UPDATE_QUERY_STRING = "update staff set position=?,email=?,password=?,team=?,project=? where fname=? and lname=?";

	public boolean addEmployee(EmployeeData data)
	{
		if (!checkEmployee(data.getFname(), data.getLname()))
		{
			query = INSERT_QUERY_STRING;
			try(Connection database = dbConnection.getConnection()) {
				statement = database.prepareStatement(query);
				statement.setString(1, data.getFname());
				statement.setString(2, data.getLname());
				statement.setString(3, data.getDob());
				statement.setString(4, data.getPosition());
				statement.setInt(5, 3);     		//To be calculated from day of addition
				statement.setInt(6, 4);				//To be calculated from day of addition
				statement.setString(7, data.getEmail());
				statement.setString(8, data.getPassword());

				statement.execute();
				statement.close();
				database.close();
				return true;
			} catch (SQLException | NullPointerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}else {
			return false;
		}
	}

	public boolean removeEmployee(EmployeeData data) 
	{
		query = REMOVE_QUERY_STRING;
		try(Connection database = dbConnection.getConnection())
		{
			statement = database.prepareStatement(query);
			statement.setString(1, data.getFname());
			statement.setString(2, data.getLname());
			statement.setString(3, data.getDob());
			statement.setString(4, data.getPosition());

			return statement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public ObservableList<EmployeeData> getData() 
	{
		// For reference : 1) fname 2)lname 3)dob 4)position 5)workDays 6)absentDays 7)email 8)password 9) team 10) project
		tableData = FXCollections.observableArrayList();
		query = SELECT_ALL_QUERY_STRING;
		
		try(Connection database = dbConnection.getConnection()) {
			set = database.createStatement().executeQuery(query);
			
			while (set.next()) 
			{
				tableData.add(new EmployeeData.Builder()
						.fname(set.getString(1))
						.lname(set.getString(2))
						.dob(set.getString(3))
						.position(set.getString(4))
						.workDays(set.getInt(5))
						.absentDays(set.getInt(6))
						.email(set.getString(7))
						.password(set.getString(8))
						.team(set.getString(9))
						.project(set.getString(10))
						.build());
			}
			return tableData;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean checkEmployee(String email, String password)
	{
		query = SEARCH_QUERY_STRING; //and dob=? and position=? and workDays=? and absentDays=?";
		try(Connection connection = dbConnection.getConnection()) {
			statement = connection.prepareStatement(query);
			statement.setString(1, email);
			statement.setString(2, password);
			
			set = statement.executeQuery();
			
			if (set.next()) 
			{
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;
	}

	public boolean isEmployee(EmployeeData data)
	{
		return checkEmployee(data.getEmail(), data.getPassword());
	}


	//"1) position 2)email 3)password 4)team 5)project 6)fname 7)lname"
	public boolean updateEmployeeData(EmployeeData data)
	{
		try(Connection database = dbConnection.getConnection()) {
			statement = database.prepareStatement(UPDATE_QUERY_STRING);
			statement.setString(1, data.getPosition());
			statement.setString(2, data.getEmail());
			statement.setString(3, data.getPassword());
			statement.setString(4, data.getTeam());
			statement.setString(5, data.getProject());
			statement.setString(6, data.getFname());
			statement.setString(7, data.getLname());

			statement.execute();
			statement.close();
			database.close();
			return true;
		} catch (SQLException | NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

    public EmployeeData getEmployee(String email, String password) {
		try(Connection database = dbConnection.getConnection())
		{
			statement = database.prepareStatement(SEARCH_QUERY_STRING);
			statement.setString(1, email);
			statement.setString(2, password);

			ResultSet set = statement.executeQuery();

			if (set.next()) {
				EmployeeData.Builder builder = new EmployeeData.Builder();
				return builder
						.fname(set.getString(1))
						.lname(set.getString(2))
						.dob(set.getString(3))
						.position(set.getString(4))
						.workDays(set.getInt(5))
						.absentDays(set.getInt(6))
						.email(set.getString(7))
						.password(set.getString(8))
						.team(set.getString(9))
						.project(set.getString(10))
						.build();
			}else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
