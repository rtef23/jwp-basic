package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.JsonView;
import core.mvc.JspView;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.controller.user.AuthenticationFailException;
import next.dao.QuestionDao;
import next.dao.UserDao;
import next.model.Question;
import next.model.User;
import org.h2.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ApiModifyQuestionController extends AbstractController {
	private QuestionDao questionDao = new QuestionDao();
	private UserDao userDao = new UserDao();

	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long questionId = Long.parseLong(request.getParameter("qid"));

		String title = request.getParameter("title");
		String contents = request.getParameter("contents");

		if (StringUtils.isNullOrEmpty(title) || StringUtils.isNullOrEmpty(contents)) {
			throw new IllegalArgumentException();
		}

		HttpSession httpSession = request.getSession();

		if (httpSession == null) {
			throw new AuthenticationFailException();
		}

		User requester = UserSessionUtils.getUserFromSession(request.getSession());
		User writer = questionDao.findWriterById(questionId);

		if (requester.isSameUser(writer) == false) {
			throw new AuthenticationFailException();
		}

		if (questionDao.findById(questionId) == null) {
			throw new IllegalArgumentException();
		}

		Question updateData = new Question(writer.getUserId(), title, contents);
		questionDao.update(questionId, updateData);

		ModelAndView modelAndView = new ModelAndView(new JspView("redirect:/qna/show?questionId=" + questionId));

		return modelAndView;
	}
}
