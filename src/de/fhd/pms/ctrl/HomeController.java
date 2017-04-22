package de.fhd.pms.ctrl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
	private static Logger log = Logger.getLogger(HomeController.class);
	
    /**
     * Custom handler for the welcome view.
     * Note that this handler relies on the RequestToViewNameTranslator to
     * determine the logical view name based on the request URL: "/index.html"
     * @return View name "home" selecting view "/view/home.jsp"
     */
    @RequestMapping("/index.html")
    public ModelAndView edit() {
    	log.info("welcome");
		ModelAndView mv = new ModelAndView();
		mv.addObject("title", "Herzlich Willkommen!");
		mv.addObject("message", "Sie können hier Boote bzw. Personen anlegen und Fahrten absolvieren.");
		mv.setViewName("home");
		return mv;
    }


}
