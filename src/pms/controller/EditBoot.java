package pms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pms.dao.BootDao;
import pms.om.Boot;


/** 
 * Controller to generate the Home Page basics to be rendered by a view. 
 * It extends the convenience class AbstractController that encapsulates most 
 * of the drudgery involved in handling HTTP requests. 
 * @author josef@dr-schneeberger.de
 */
public class EditBoot extends AbstractController {
	private BootDao bootDao;

	public void setBootDao(BootDao bootDao) {
		this.bootDao = bootDao;
	}

	protected ModelAndView handleRequestInternal(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws Exception {
		ModelAndView mv = new ModelAndView();
		try {
			int id = Integer.parseInt(httpServletRequest.getParameter("id"));
			// wenn id nicht geparsed werden kann, dann wird ein neues Objekt angelegt
			mv.addObject("title", "Bootsbeschreibung bearbeiten");
			mv.addObject("message", "Ändern Sie die Beschreibung des Boots und klicken Sie dann auf 'Änderungen übernehmen'.");
			mv.addObject(bootDao.findById(id));
		} catch (NumberFormatException e) {
			mv.addObject("title", "Neues Boot anlegen");
			mv.addObject("message", "Geben Sie eine Beschreibung für das neue Boot an und klicken Sie dann auf 'Änderungen übernehmen'.");
			mv.addObject(new Boot("Neues Boot"));
		}
		mv.setViewName("edit-boot");
		return mv;
	}

}
