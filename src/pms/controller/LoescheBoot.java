package pms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import pms.dao.BootDeleteException;

/**
 * @author josef@dr-schneeberger.de
 */
public class LoescheBoot extends AlleBoote {
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws Exception {
		int id = Integer.parseInt(httpServletRequest.getParameter("id"));
		try {
			getBootDao().deleteById(id);
			return super.handleRequestInternal(httpServletRequest, httpServletResponse);
		} catch (BootDeleteException e) {
			ModelAndView mv = new ModelAndView();
			mv.addObject("title", "Ein Fehler ist aufgetreten");
			mv.addObject("message", e.getMessage());
			mv.setViewName("home");
			return mv;
		}
	}

}
