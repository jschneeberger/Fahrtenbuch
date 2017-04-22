package pms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pms.dao.PersonDao;


/** 
 * Controller to generate the Home Page basics to be rendered by a view. 
 * It extends the convenience class AbstractController that encapsulates most 
 * of the drudgery involved in handling HTTP requests. 
 * 
 * This class is a super type of SavePerson and LoeschePerson. If is used as a next view
 * after successfully executing those controllers.
 * @author josef@dr-schneeberger.de
 */
public class AllePersonen extends AbstractController {
	private PersonDao personDao;
	
	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}

	public PersonDao getPersonDao() {
		return personDao;
	}

	protected ModelAndView handleRequestInternal(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.addObject("title", "Vereinsmitglieder");
		mv.addObject("message", "Alle Mitglieder des Vereins");
		mv.addObject("personen", personDao.findAll());
		mv.setViewName("list-personen");
		return mv;
	}
}
