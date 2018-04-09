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
			case "LOAD":
				loadStudent(request, response);
				break;
			case "UPDATE":
				updateStudent(request, response);
				break;
			default: 
				listStudents( request, response);
				break;
			}
			
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//read data from form and create a student
		int id = Integer.parseInt(request.getParameter("studentId"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		Student temp = new Student(id, firstName, lastName, email);
		//send data to db and then update it in there
		 stuDBUtil.updateStudent(temp);
		//list the students
		listStudents(request, response);
		
	}

	private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String id = request.getParameter("studentId");
		
		Student temp = stuDBUtil.getStudent(id);
		request.setAttribute("student", temp);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("updateStudentForm.jsp");
		dispatcher.forward(request, response);
		
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
