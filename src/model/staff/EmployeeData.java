package model.staff;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EmployeeData 
{
	public static class Builder
	{
		private  StringProperty fname;
		private  StringProperty lname;
		private StringProperty position;
		private  StringProperty dob;
		private  StringProperty email;
		private  StringProperty password;
		private StringProperty team;
		private StringProperty project;

		private  IntegerProperty workDays;
		private  IntegerProperty absentDays;

		public  Builder fname(final String name)
		{
			fname = new SimpleStringProperty(name);
			return this;
		}

		public Builder lname(String lname) {
			this.lname = new SimpleStringProperty(lname);
			return this;
		}

		public Builder position(String position) {
			this.position = new SimpleStringProperty(position);
			return this;
		}

		public Builder dob(String dob) {
			this.dob = new SimpleStringProperty(dob);
			return this;
		}

		public Builder email(String email) {
			this.email = new SimpleStringProperty(email);
			return this;
		}

		public Builder password(String password) {
			this.password = new SimpleStringProperty(password);
			return this;
		}

		public Builder workDays(int workDays) {
			this.workDays = new SimpleIntegerProperty(workDays);
			return this;
		}

		public Builder absentDays(int absentDays) {
			this.absentDays= new SimpleIntegerProperty(absentDays);
			return this;
		}

		public Builder team(String teamName) {
			this.team = new SimpleStringProperty(teamName);
			return this;
		}

		public Builder project(String projectName) {
			this.project = new SimpleStringProperty(projectName);
			return this;
		}

		public EmployeeData build()
		{
			if (fname != null || lname != null || email != null || password != null) {
				return new EmployeeData(this);
			}else{
				throw new IllegalStateException("EmployeeData not valid");
			}
		}
    }

	private final StringProperty fname;
	private final StringProperty lname;
	private StringProperty position;
	private final StringProperty dob;
	private final StringProperty email;
	private final StringProperty password;
	private final StringProperty team;
	private final StringProperty project;
	
	private final IntegerProperty workDays;
	private final IntegerProperty absentDays;
	
	private EmployeeData(Builder builder)
	{
		fname = builder.fname;
		lname = builder.lname;
		position = builder.position;
		email = builder.email;
		password =builder.password;
		
		workDays = builder.workDays;
		absentDays =builder.absentDays;
		
		dob = builder.dob;

		team = builder.team;
		project = builder.project;
		
		System.out.println(fname);
		System.out.println(lname);
	}
	
	public void setPosition(String newPosition) {
		position = new SimpleStringProperty(newPosition);
	}

	public String getDob() {
		return dob.get();
	}

	public String getFname() {
		return fname.get();
	}

	public String getLname() {
		return lname.get();
	}

	public String getPosition() {
		return position.get();
	}

	public int getWorkDays() {
		return workDays.get();
	}

	public int getAbsentDays() {
		return absentDays.get();
	}

	public String getPassword() {
		return password.get();
	}

	public String getEmail() {
		return email.get();
	}

	public String getTeam(){return  team.get();}

	public String getProject(){return  project.get();}
	
	@Override
	public String toString() 
	{
		return "Name: " + getFname() + " " + getLname() +
				"\nDate of Birth: " + getDob() +
				"\nPosition: " + getPosition();
	}

}
