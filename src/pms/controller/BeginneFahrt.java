package pms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pms.dao.BootDao;
import pms.dao.PersonDao;


/** 
 * Controller to generate the Home Page basics to be rendered by a view. 
 * It extends the convenience class AbstractController that encapsulates most 
 * of the drudgery involved in handling HTTP requests. 
 * @author josef@dr-schneeberger.de
 */
public class BeginneFahrt extends AbstractController {
	private BootDao bootDao;
	private PersonDao personDao;

	public void setBootDao(BootDao bootDao) {
		this.bootDao = bootDao;
	}

	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}

	protected ModelAndView handleRequestInternal(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws Exception {
		ModelAndView mv = new ModelAndView();
		try {
			int id = Integer.parseInt(httpServletRequest.getParameter("id"));
			// wenn id nicht geparsed werden kann, dann wird ein neues Objekt angelegt
			mv.addObject("title", "Eine Fahrt beginnen.");
			mv.addObject("message", "Tragen Sie alle Mitfahrer ein und klicken Sie dann auf 'Änderungen übernehmen'.");
			mv.addObject("boot", bootDao.findById(id));
			mv.addObject("personen", personDao.findAll());
		} catch (NumberFormatException e) {
			
		}
		mv.setViewName("edit-fahrt");
		return mv;
	}

}
