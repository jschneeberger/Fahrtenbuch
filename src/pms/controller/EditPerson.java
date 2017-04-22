package pms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pms.dao.PersonDao;
import pms.om.Person;


/** 
 * Controller to generate the Home Page basics to be rendered by a view. 
 * It extends the convenience class AbstractController that encapsulates most 
 * of the drudgery involved in handling HTTP requests. 
 * @author josef@dr-schneeberger.de
 */
public class EditPerson extends AbstractController {
	private PersonDao personDao;
	
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
			mv.addObject("title", "Personsbeschreibung bearbeiten");
			mv.addObject("message", "Ändern Sie die Beschreibung des Persons und klicken Sie dann auf 'Änderungen übernehmen'.");
			mv.addObject("person", personDao.findById(id));
		} catch (NumberFormatException e) {
			mv.addObject("title", "Neue Person anlegen");
			mv.addObject("message", "Geben Sie eine Beschreibung für die neue Person an und klicken Sie dann auf 'Änderungen übernehmen'.");
			mv.addObject("person", new Person());
		}
		mv.setViewName("edit-person");
		return mv;
	}

}
