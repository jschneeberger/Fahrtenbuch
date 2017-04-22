package de.dit.pms.ctrl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller("homeController")
public class HomeController {
	private static Logger log = Logger.getLogger(HomeController.class);
	
    @RequestMapping("/all/index.html")
    public ModelAndView edit() {
    	log.info("Controller für index.html");
		ModelAndView mv = new ModelAndView();
		mv.addObject("title", "Herzlich Willkommen!");
		mv.addObject("message", "Sie können hier Boote bzw. Personen anlegen und Fahrten absolvieren.");
		mv.setViewName("home");
		return mv;
    }


}
