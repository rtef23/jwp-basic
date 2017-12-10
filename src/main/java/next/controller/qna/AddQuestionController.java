package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.JspView;
import core.mvc.ModelAndView;
import core.mvc.NoResourceFoundException;
import next.controller.UserSessionUtils;
import next.controller.user.AuthenticationFailException;
import next.dao.QuestionDao;
import next.model.Question;
import next.model.User;
import org.h2.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddQuestionController extends AbstractController {
	private final String GET = "GET";
	private final String POST = "POST";

	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (GET.equals(request.getMethod())) {
			return doGet(request, response);
		}

		if (POST.equals(request.getMethod())) {
			return doPost(request, response);
		}

		throw new NoResourceFoundException();
	}

	private ModelAndView doGet(HttpServletRequest request, HttpServletResponse response) {
		return null;
	}

	private ModelAndView doPost(HttpServletRequest request, HttpServletResponse response) {
		User requester = UserSessionUtils.getUserFromSession(request.getSession());

		if (requester == null) {
			throw new AuthenticationFailException();
		}

		String writer = requester.getUserId();
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");

		if (StringUtils.isNullOrEmpty(writer) || StringUtils.isNullOrEmpty(title) || StringUtils.isNullOrEmpty(contents)) {
			throw new IllegalArgumentException();
		}

		Question question = new Question(writer, title, contents);

		Question insertedQuestion = new QuestionDao().insert(question);

		return new ModelAndView(new JspView("redirect:/qna/show?questionId=" + insertedQuestion.getQuestionId()));
	}
}
