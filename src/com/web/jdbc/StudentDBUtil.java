package com.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
		
		String sql = "select * from student order by last_name";
		try {
			myRs = executeSelectAllQuery(myConn, myStmt, sql);
			
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
	private ResultSet executeSelectAllQuery(Connection myConn, Statement myStmt,String sql) throws SQLException {
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

	public void addStudent(Student temp) throws Exception{
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = dataSource.getConnection();
			//create sql for insert
			String sql = "insert into student"
						+"(first_name, last_name, email)"
						+"values(?,?,?)";
			myStmt = myConn.prepareStatement(sql);
			//set the value for student
			myStmt.setString(1, temp.getFirstName());
			myStmt.setString(2, temp.getLastName());
			myStmt.setString(3, temp.getEmail());
			//execute sql statement
			myStmt.executeUpdate();
		}
		finally {
			//clean up 
			close(myConn, myStmt, null);
		}	
	}
	
	public Student getStudent(String string) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
	
		try {
			int id= Integer.parseInt(string) ;
			//get connection
			myConn = dataSource.getConnection();
			//create sql for select 
			String sql = "select * from student where id = ?";
			//create prepared statement
			myStmt = myConn.prepareStatement(sql);
			//setting the param values
			myStmt.setInt(1, id);
			//execute the query 
			myRs = myStmt.executeQuery();
			
			//retrieve data from result set
			if(myRs.next()) {
				return createStudent(myRs);
			}
			else {
				throw new Exception("No Student found with that ID");
			}
		}finally {
			close(myConn, myStmt, myRs);
		}
	}

	public void updateStudent(Student temp ) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = dataSource.getConnection();
			//setting sql
			String sql = "update student "
					+"set first_name=?,last_name=?, email=?"
					+"where id=?";
			
			//prepare statement 
			myStmt = myConn.prepareStatement(sql);
			
			//setting param values
			myStmt.setString(1, temp.getFirstName());
			myStmt.setString(2, temp.getLastName());
			myStmt.setString(3, temp.getEmail());
			myStmt.setInt(4, temp.getId());

			//execute query
			myStmt.execute();
			
		}finally {
			close(myConn, myStmt, null);
		}
		
	}

	public void deleteStudent(int id) throws Exception {
		String sql = "delete from student where id="+id;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = dataSource.getConnection();
			myStmt = myConn.prepareStatement(sql);
			myStmt.execute();
			
		}finally {
			close(myConn, myStmt, null);
		}
		
	}
}