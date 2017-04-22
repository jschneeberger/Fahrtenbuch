package de.hdu.pms.ctrl;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import de.hdu.pms.dao.BootDao;
import de.hdu.pms.dao.DaoException;
import de.hdu.pms.dao.FahrtDao;
import de.hdu.pms.dao.PersonDao;
import de.hdu.pms.dto.FahrtBootPersonDTO;
import de.hdu.pms.model.Fahrt;

@Controller
public class FahrtController {
    private FahrtDao fahrtDao = null;
    private PersonDao personDao = null;
    private BootDao bootDao = null;
    
    public void setFahrtDao(FahrtDao fahrtDao) {
		this.fahrtDao = fahrtDao;
	}

	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}

	public void setBootDao(BootDao bootDao) {
		this.bootDao = bootDao;
	}

	/**
     * <p>Searches for all {@link Fahrt} objects and returns them in a 
     * <code>Collection</code>.</p>
     * 
     * <p>Expected HTTP GET and request '/AlleFahrten.html'.</p>
     */
    @RequestMapping(value="/AlleFahrten.html", method=RequestMethod.GET)
    public ModelAndView findAll() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("title", "Fahrten");
		mv.addObject("message", "Alle Fahrten aller Boote im Verein.");
		mv.addObject("fahrten", fahrtDao.findAllDTO());
		mv.setViewName("list-fahrten");
		return mv;
    }
    
    @RequestMapping(value="/AlleFahrtenVoll.html", method=RequestMethod.GET)
    public ModelAndView findAllVoll() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("title", "Alle Fahrten");
		mv.addObject("message", "Alle Fahrten aller Boote im Verein (Admin).");
		List<FahrtBootPersonDTO> fahrten = fahrtDao.findAllVoll();
		mv.addObject("fahrten", fahrten);
		mv.addObject("unempty", new Boolean(fahrten.size() > 0));
		mv.setViewName("list-fahrten-voll");
		return mv;
    }
    
    @RequestMapping(value="/BeginneFahrt.html", method=RequestMethod.GET)
    public ModelAndView beginneFahrt(@RequestParam(required=true) Integer id) {
    	ModelAndView mv = new ModelAndView();
		mv.addObject("title", "Fahrt eintragen");
		mv.addObject("message", "Tragen Sie hier die Teilnehmer der Fahrt ein.");
		mv.addObject("boot", bootDao.findById(id));
		mv.addObject("personen", personDao.findAll());
		mv.setViewName("edit-fahrt");
    	return mv;
    }

    /**
     * <p>Saves a Fahrt.</p>
     * 
     * <p>Expected HTTP POST and request '/SaveFahrt.html'.</p>
     * @return 
     */
    @RequestMapping(value="/SaveFahrt.html", method=RequestMethod.POST)
    public ModelAndView save(@RequestParam(required=true) Integer id, Integer[] sitz) {
    	try {
			fahrtDao.beginne(id, sitz);
			return new ModelAndView("redirect:index.html");
		} catch (DaoException e) {
			ModelAndView mv = new ModelAndView("error");
			mv.addObject("message", e.getMessage());
			return mv;
		}
    }

	/**
     * <p>Searches for {@link Fahrt} objects that are not yet terminated and returns them in a 
     * <code>Collection</code>.</p>
     * 
     * <p>Expected HTTP GET and request '/AktuelleFahrten.html'.</p>
     */
    @RequestMapping(value="/AktuelleFahrten.html", method=RequestMethod.GET)
    public ModelAndView findAktuelle() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("title", "Aktuelle Fahrten");
		mv.addObject("message", "Alle Fahrten die noch nicht beendet sind. Klicken Sie auf ein Boot um die Fahrt zu beenden.");
		mv.addObject("fahrten", fahrtDao.findNichtBeendetDTO());
		mv.setViewName("list-fahrten");
		return mv;
    }

    /**
     * <p>Terminates a Fahrt.</p>
     * 
     * <p>Expected HTTP POST and request '/StopFahrt.html?id=5'.</p>
     * @return 
     */
    @RequestMapping(value="/StopFahrt.html", method=RequestMethod.GET)
    public String save(@RequestParam(required=true) Integer id) {
    	fahrtDao.beende(id);
        return "redirect:AktuelleFahrten.html";
    }

}
