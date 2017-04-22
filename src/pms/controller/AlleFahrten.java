package pms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pms.dao.FahrtDao;


/** 
 * Controller to generate the Home Page basics to be rendered by a view. 
 * It extends the convenience class AbstractController that encapsulates most 
 * of the drudgery involved in handling HTTP requests. 
 * 
 * This class is a super type of SaveFahrt and StopFahrt. If is used as a next view
 * after successfully executing those controllers.
 * @author josef@dr-schneeberger.de
 */
public class AlleFahrten extends AbstractController {
	private FahrtDao fahrtDao;
	private boolean beende;

	public void setFahrtDao(FahrtDao fahrtDao) {
		this.fahrtDao = fahrtDao;
	}

	public FahrtDao getFahrtDao() {
		return fahrtDao;
	}

	public void setBeende(boolean beende) {
		this.beende = beende;
	}

	protected ModelAndView handleRequestInternal(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.addObject("title", "Liste der aktuellen Fahrten");
		if (beende) {
			mv.addObject("message", "Klicken Sie auf eine Fahrt, un diese Fahrt zu beenden.");
			mv.addObject("fahrten", fahrtDao.findNichtBeendetDTO());
		} else {
			mv.addObject("message", "Alle Fahrten, beendete so wie aktuell laufende.");
			mv.addObject("fahrten", fahrtDao.findAllDTO());
		}
		mv.setViewName("list-fahrten");
		return mv;
	}

}
