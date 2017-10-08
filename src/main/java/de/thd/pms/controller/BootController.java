package de.thd.pms.controller;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
	private static Log logger = LogFactory.getLog(BootController.class);
	@Autowired
	private BootDao bootDao;

	/**
     * <p> Boot form request.</p>
     * <p>Expected HTTP GET and request '/edit'.</p>
     * @return 
     */
    @RequestMapping(value="/edit", method=RequestMethod.GET)
    public ModelAndView edit(@RequestParam(required=false) Integer id) {
    	logger.debug("edit");
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
     * <p>Expected HTTP POST and request '/save'.</p>
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
     * <p>Expected HTTP GET and request '/delete'.</p>
     */
    @RequestMapping(value="/delete", method=RequestMethod.GET)
    public ModelAndView delete(@RequestParam(required=true) Integer id) {
        try {
			bootDao.delete(id);
	        return new ModelAndView("redirect:findAll");
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
     * <p>Expected HTTP GET and request '/findAll'.</p>
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
     * <p>Expected HTTP GET and request '/freieBoote'.</p>
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
