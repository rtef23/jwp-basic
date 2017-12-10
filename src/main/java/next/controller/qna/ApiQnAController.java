package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.JsonView;
import core.mvc.ModelAndView;
import next.dao.QuestionDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ApiQnAController extends AbstractController {
	private QuestionDao questionDao = new QuestionDao();

	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JsonView jsonView = new JsonView();
		ModelAndView modelAndView = new ModelAndView(jsonView);

		modelAndView.addObject("qnaList", questionDao.findAll());

		return modelAndView;
	}
}
