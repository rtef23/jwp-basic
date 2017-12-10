package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.JsonView;
import core.mvc.JspView;
import core.mvc.ModelAndView;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import org.h2.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class DeleteQuestionController extends AbstractController {
	private QuestionDao questionDao = new QuestionDao();
	private AnswerDao answerDao = new AnswerDao();

	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String questionIdString = request.getParameter("qid");

		if (StringUtils.isNullOrEmpty(questionIdString)) {
			throw new IllegalArgumentException();
		}

		Long questionIdLong = Long.parseLong(questionIdString);

		Question question = questionDao.findById(questionIdLong);

		if (question.getCountOfComment() != 0) {
			List<Answer> answers = answerDao.findAllByQuestionId(questionIdLong);

			for (Answer answer : answers) {
				if (answer.getWriter().equals(question.getWriter()) == false) {
					return new ModelAndView(new JspView("redirect:/qna/show?questionId=" + questionIdLong));
				}
			}

			for (Answer answer : answers) {
				answerDao.delete(answer.getAnswerId());
			}
		}

		questionDao.deleteQuestion(questionIdLong);

		boolean isMobile = false;

		if (isMobile) {
			return new ModelAndView(new JsonView());
		}
		return new ModelAndView(new JspView("redirect:/"));
	}
}
