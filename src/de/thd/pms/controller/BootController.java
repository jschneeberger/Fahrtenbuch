package de.thd.pms.controller;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import de.thd.pms.model.Boot;
import de.thd.pms.service.BootDao;
import de.thd.pms.service.DaoException;

@Controller
@RequestMapping("/boot")
public class BootController {
	private static Logger log = LogManager.getLogger(BootController.class);
	@Autowired
	private BootDao bootDao;

	/**
     * <p> Boot form request.</p>
     * 
     * <p>Expected HTTP GET and request '/EditBoot.html'.</p>
     * @return 
     */
    @RequestMapping(value="/edit", method=RequestMethod.GET)
    public ModelAndView edit(@RequestParam(required=false) Integer id) {
    	log.debug("edit");
    	ModelAndView mv = new ModelAndView();
    	if (id == null) {
    		mv.addObject(new Boot());
    	} else { 
    		mv.addObject(bootDao.findById(id));
    	}
    	mv.setViewName("edit-boot");
    	return mv;
    }

    /**
     * <p>Saves a {@link Boot}.</p>
     * 
     * <p>Expected HTTP POST and request '/SaveBoot.html'.</p>
     * @return 
     */
    @RequestMapping(value="/save", method=RequestMethod.POST)
    public String save(Boot boot, Model model) { // Der Parameter Model ist nicht notwendig
    	// Die Felder des Html Formulars müssen mit den Instanzenvariablen des Objekts
    	// Boot übereinstimmen
        if (boot.getCreated() == null) { // Wenn das Feld created der Instanz boot null ist,
        	// dann wird das aktuelle Datum in dieses Feld geschrieben
            boot.setCreated(new Date());
        }
        bootDao.save(boot);
        return "redirect:findAll";
    }

    /**
     * <p>Deletes a Boot.</p>
     * 
     * <p>Expected HTTP POST and request '/LoescheBoot.html'.</p>
     */
    @RequestMapping(value="/delete", method=RequestMethod.GET)
    public ModelAndView delete(@RequestParam(required=true) Integer id) {
        try {
			bootDao.delete(id);
	        return new ModelAndView("redirect:AlleBoote.html");
		} catch (DaoException e) {
			ModelAndView mv = new ModelAndView("error");
			mv.addObject("message", e.getMessage());
			return mv;
		}
    }

    /**
     * <p>Searches for all boats and returns them in a 
     * <code>Collection</code>.</p>
     * 
     * <p>Expected HTTP GET and request '/AlleBoote.html'.</p>
     */
    @RequestMapping(value="/findAll", method=RequestMethod.GET)
    public ModelAndView findAll() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("title", "Boote");
		mv.addObject("message", "Alle Boote des Vereins");
		mv.addObject("boote", bootDao.findAll());
		mv.addObject("edit", true);
		mv.setViewName("list-boote");
		return mv;
    }

    /**
     * <p>Searches for all free foats and returns them in a 
     * <code>Collection</code>.</p>
     * 
     * <p>Expected HTTP GET and request '/FreieBoote.html'.</p>
     */
    @RequestMapping(value="/freieBoote", method=RequestMethod.GET)
    public ModelAndView freieBoote() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("title", "Fahrt beginnen");
		mv.addObject("message", "Folgende Boote sind für eine Fahrt verfügbar. Wählen Sie ein Boot aus:");
		mv.addObject("boote", bootDao.findFreie());
		mv.addObject("edit", false);
		mv.setViewName("list-boote");
		return mv;
    }

}
