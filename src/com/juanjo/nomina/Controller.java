package com.juanjo.nomina;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Controller
 */
@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	TestDAO test;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void init() throws ServletException {
    	String jdbcURL=getServletContext().getInitParameter("jdbcURL");
    	String jdbcUserName=getServletContext().getInitParameter("jdbcUserName");
    	String jdbcPassword=getServletContext().getInitParameter("jdbcPassword");
    	System.out.println(jdbcURL);
    	try {
			test = new TestDAO(jdbcURL,jdbcUserName,jdbcPassword);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String opcion = request.getParameter("opcion");
		
		if (opcion.equals("mostrar")) {
			try {
			
			List<Empleado> lista = new ArrayList<>();
			lista=test.mostrar();
			
			request.setAttribute("lista", lista);
			RequestDispatcher requesDispatcher = request.getRequestDispatcher("Mostrar.jsp");
			requesDispatcher.forward(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatosNoCorrectosException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		} else {if (opcion.equals("buscaDni")) {
			RequestDispatcher requesDispatcher = request.getRequestDispatcher("BuscaDni.jsp");
			requesDispatcher.forward(request, response);
		} else {
			
			try {
				String dniB = request.getParameter("dniB"); 
				Map<String, Integer> mapa = new HashMap<String, Integer>();
				mapa=test.mostrarSalario(dniB);
				request.setAttribute("mapa", mapa);
				RequestDispatcher requesDispatcher = request.getRequestDispatcher("Salario.jsp");
				requesDispatcher.forward(request, response);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DatosNoCorrectosException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		}
			
		
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
