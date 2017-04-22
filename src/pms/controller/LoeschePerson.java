package pms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import pms.dao.PersonDeleteException;


/**
 * @author josef@dr-schneeberger.de
 */
public class LoeschePerson extends AllePersonen {
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws Exception {
		int id = Integer.parseInt(httpServletRequest.getParameter("id"));
		try {
			getPersonDao().deleteById(id);
			return super.handleRequestInternal(httpServletRequest, httpServletResponse);
		} catch (PersonDeleteException e) {
			ModelAndView mv = new ModelAndView();
			mv.addObject("title", "Ein Fehler ist aufgetreten");
			mv.addObject("message", e.getMessage());
			mv.setViewName("home");
			return mv;
		}
	}
}
