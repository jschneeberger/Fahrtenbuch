package pms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pms.dao.BootDao;


/** 
 * Controller to generate the Home Page basics to be rendered by a view. 
 * It extends the convenience class AbstractController that encapsulates most 
 * of the drudgery involved in handling HTTP requests. 
 * @author josef@dr-schneeberger.de
 */
public class FreieBoote extends AbstractController {
	private BootDao bootDao;

	public void setBootDao(BootDao bootDao) {
		this.bootDao = bootDao;
	}
	
	protected ModelAndView handleRequestInternal(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.addObject("title", "Fahrt beginnen");
		mv.addObject("message", "Wählen Sie eines der derzeit verfügbaren Boote:");
		mv.addObject("boote", bootDao.findFreie());
		mv.addObject("edit", false);
		mv.setViewName("list-boote");
		return mv;
	}

}
