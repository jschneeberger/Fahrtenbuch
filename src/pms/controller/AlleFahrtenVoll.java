package pms.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pms.dao.FahrtDao;
import pms.dto.FahrtBootPersonDTO;


/**
 * Introduced to demonstrate the use of data transfer objects (DTO).
 * @author josef@dr-schneeberger.de
 */
public class AlleFahrtenVoll extends AbstractController {
	private FahrtDao fahrtDao;

	public void setFahrtDao(FahrtDao fahrtDao) {
		this.fahrtDao = fahrtDao;
	}

	public ModelAndView handleRequestInternal(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws Exception {
		ModelAndView mv = new ModelAndView();
		List<FahrtBootPersonDTO> fahrten = fahrtDao.findAllVoll();
		mv.addObject("title", "Alle Fahrten aller Personen und Boote");
		mv.addObject("message", "Die Liste aller Fahrten mit allen Personen und Booten");
		mv.addObject("fahrten", fahrten);
		mv.addObject("unempty", (fahrten.size() > 0 ? true : false));
		mv.setViewName("list-fahrten-voll");
		return mv;
	}
}
