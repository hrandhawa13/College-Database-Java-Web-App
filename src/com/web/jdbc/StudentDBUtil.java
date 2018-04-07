package com.web.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class StudentDBUtil {

	private DataSource dataSource;
	
	public StudentDBUtil(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public List<Student> getStudents()throws Exception{
		
		List<Student> students = new ArrayList<>();
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			
			String sql = "select * from student order by last_name";
			myRs = executeQuery(myConn, myStmt, sql);
			while(myRs.next()) {
				students.add(createStudent(myRs));
			}
			return students;
		}
		finally {
			//close all the JDBC objects
			close(myConn,myStmt,myRs);
		}
	}

	private Student createStudent(ResultSet myRs) throws SQLException {
		int id = myRs.getInt("id");
		String firstName = myRs.getString("first_name");
		String lastName = myRs.getString("last_name");
		String email = myRs.getString("email");
		return new Student(id, firstName, lastName,email);
	}

	private ResultSet executeQuery(Connection myConn, Statement myStmt, String sql) throws SQLException {
		myConn = dataSource.getConnection();
		myStmt = myConn.createStatement();
		return myStmt.executeQuery(sql);
	}

	private void close(Connection myConn, Statement myStmt, ResultSet myRs) {
		try {
			if(myRs != null)
				myRs.close();
			if(myStmt != null )
				myStmt.close();
			if( myConn != null )
				myConn.close();
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
	}
}