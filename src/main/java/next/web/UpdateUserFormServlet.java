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
 * Servlet implementation class UpdateUserFormServlet
 */
@WebServlet("/user/update")
public class UpdateUserFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateUserFormServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/user/update.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		HttpSession session = request.getSession();

		if (session == null) {
			response.sendError(401);
			return;
		}

		User requestUser = (User)session.getAttribute("user");

		if (requestUser == null) {
			response.sendError(401);
			return;
		}

		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String email = request.getParameter("email");

		requestUser.setPassword(password);
		requestUser.setName(name);
		requestUser.setEmail(email);

		DataBase.addUser(requestUser);

		response.sendRedirect("/user/list");
	}
}
