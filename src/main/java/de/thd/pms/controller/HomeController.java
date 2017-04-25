package de.thd.pms.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
	private static Logger log = LogManager.getLogger(HomeController.class);
	
    /**
     * Custom handler for the welcome view.
     * Note that this handler relies on the RequestToViewNameTranslator to
     * determine the logical view name based on the request URL: "/welcome"
     * @return View name "home" selecting view "/view/home.jsp"
     */
    @RequestMapping("/welcome")
    public ModelAndView welcome() {
// Über die log Variable, können Sie Nachrichten auf die Konsole schreiben.
// Welche Nachrichten tatsächlich auf der Konsole erscheine, wird durch die
// Datei log4j.properties gesteuert.
//    	log.debug("Test");
    	log.info("Controller für welcome");
//    	log.warn("Warnung");
//    	log.error(message);
//    	log.fatal(message);
		ModelAndView mv = new ModelAndView();
		mv.addObject("title", "Herzlich Willkommen!");
		mv.addObject("message", "Sie können hier Boote bzw. Personen anlegen und Fahrten absolvieren.");
		mv.setViewName("home");
		return mv;
    }

}
