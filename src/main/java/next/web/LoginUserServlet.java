package next.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.db.DataBase;
import next.model.User;

/**
 * Servlet implementation class LoginUserServlet
 */
@WebServlet("/user/login")
public class LoginUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginUserServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/user/login.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		String id = request.getParameter("userId");
		String password = request.getParameter("password");

		User targetUser = DataBase.findUserById(id);

		if (targetUser != null && targetUser.getPassword().equals(password)) {
			HttpSession session = request.getSession();
			session.setAttribute("user", targetUser);
			response.sendRedirect("/user/list");
			return;
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("/user/login_failed.jsp");
		dispatcher.forward(request, response);
	}

}
