package com.web.jdbc;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	private StudentDBUtil stuDBUtil;
	@Resource(name = "jdbc/web_student_tracker")
	private DataSource dataSrc;

	@Override
	public void init() throws ServletException {
		super.init();
		try {
			stuDBUtil = new StudentDBUtil(dataSrc); 
		}
		catch(Exception e ) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String cmd = request.getParameter("command");
			
			if(cmd == null )
				cmd = "LIST";
			
			switch(cmd) {
			case "LIST":
				listStudents(request, response);
				break;
			case "ADD":
				addStudent(request,response);
				break;
			default: 
				listStudents( request, response);
			
			}
			
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//read data from form
		
		//create new student
		Student temp = createStudent(request);
		// add it to db
		stuDBUtil.addStudent(temp);
		//redirect to home page
		listStudents(request, response);
	}

	private Student createStudent(HttpServletRequest request) {
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		return new Student(firstName, lastName, email);
	}

	private void listStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Student> students = stuDBUtil.getStudents();
		request.setAttribute("students", students);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/listStudents.jsp");
		dispatcher.forward(request, response);
	}

}
