package de.dit.pms.ctrl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import de.dit.pms.model.Boot;
import de.dit.pms.service.BootService;
import de.dit.pms.service.DaoException;

@Controller
public class BootController {
	private static Logger log = Logger.getLogger(BootController.class);
	@Autowired
	private BootService bootService = null;

	/**
     * <p> Boot form request.</p>
     * 
     * <p>Expected HTTP GET and request '/EditBoot.html'.</p>
     * @return 
     */
    @RequestMapping(value="/EditBoot.html", method=RequestMethod.GET)
    public ModelAndView edit(@RequestParam(required=false) Integer id) {
    	log.debug("EditBoot.html");
    	ModelAndView mv = new ModelAndView();
    	if (id == null) {
    		mv.addObject(new Boot());
    	} else { 
    		mv.addObject(bootService.findById(id));
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
    @RequestMapping(value="/SaveBoot.html", method=RequestMethod.POST)
    public String save(Boot boot) {
    	// Die Felder des Html Formulars müssen mit den Instanzenvariablen des Objekts
    	// Boot übereinstimmen
        bootService.save(boot);
        return "redirect:AlleBoote.html";
    }

    /**
     * <p>Deletes a Boot.</p>
     * 
     * <p>Expected HTTP POST and request '/LoescheBoot.html'.</p>
     */
    @RequestMapping(value="/LoescheBoot.html", method=RequestMethod.GET)
    public ModelAndView delete(@RequestParam(required=true) Integer id) {
        try {
			bootService.delete(id);
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
    @RequestMapping(value="/AlleBoote.html", method=RequestMethod.GET)
    public ModelAndView findAll() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("title", "Boote");
		mv.addObject("message", "Alle Boote des Vereins");
		mv.addObject("boote", bootService.findAll());
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
    @RequestMapping(value="/FreieBoote.html", method=RequestMethod.GET)
    public ModelAndView freieBoote() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("title", "Fahrt beginnen");
		mv.addObject("message", "Folgende Boote sind für eine Fahrt verfügbar. Wählen Sie ein Boot aus:");
		mv.addObject("boote", bootService.findFreie());
		mv.addObject("edit", false);
		mv.setViewName("list-boote");
		return mv;
    }

}
