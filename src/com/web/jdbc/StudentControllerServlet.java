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
		System.out.println("In init method");
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
			System.out.println("Before the list ");
			listStudents(request, response);
			System.out.println("after the list");
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	private void listStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Student> students = stuDBUtil.getStudents();
		request.setAttribute("students", students);
		System.out.println("From listStudents");
		System.out.println(students.toString());
		RequestDispatcher dispatcher = request.getRequestDispatcher("/listStudents.jsp");
		dispatcher.forward(request, response);
	}

}
