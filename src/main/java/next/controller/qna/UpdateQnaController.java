package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.JspView;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.controller.user.AuthenticationFailException;
import next.dao.QuestionDao;
import next.model.User;
import org.h2.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateQnaController extends AbstractController {
	private QuestionDao questionDao = new QuestionDao();

	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String questionIdString = request.getParameter("qid");

		if (StringUtils.isNullOrEmpty(questionIdString)) {
			throw new IllegalArgumentException();
		}

		Long questionIdLong = Long.parseLong(questionIdString);

		User writer = questionDao.findWriterById(questionIdLong);

		if (UserSessionUtils.isSameUser(request.getSession(), writer) == false) {
			throw new AuthenticationFailException();
		}

		ModelAndView modelAndView = new ModelAndView(new JspView("updateForm.jsp"));
		modelAndView.addObject("question", questionDao.findById(questionIdLong));

		return modelAndView;
	}
}
