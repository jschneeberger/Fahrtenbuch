package pms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/** 
 * <p>
 * Controller to generate the Home Page basics to be rendered by a view. 
 * It extends the convenience class AbstractController that encapsulates most 
 * of the drudgery involved in handling HTTP requests.
 * </p>
 * 
 * <p>
 * Spring Web MVC provides the method handleRequestInternal, which is overridden here.
 * handleRequestInternal has the same parameters as a simple Servlet: httpServletRequest
 * and httpServletResponse which are not used in this controller.
 * </p>
 * 
 * <p>
 * handleRequestInternal has to return a ModelAndView Object, which is a simple map
 * of names and values. Values may be all kinds of JavaBeans. ModelAndView also needs
 * a view name, which determines the view to select. Since the view name here is 'home'
 * the view 'home.jsp' will be selected. 
 * </p>
 * @see org.springframework.web.servlet.mvc.AbstractController
 * @see org.springframework.web.servlet.ModelAndView
 * @author josef@dr-schneeberger.de
 */
public class Home extends AbstractController {
	
	protected ModelAndView handleRequestInternal(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws Exception {
	    Logger log = Logger.getLogger(getClass());
	    log.debug("Logging Home ...");
	    // Es sind keine Parameter zu verarbeiten
	    
		ModelAndView mv = new ModelAndView();
		mv.addObject("title", "Herzlich Willkommen!");
		mv.addObject("message", "Sie können hier Boote bzw. Personen anlegen und Fahrten absolvieren.");
		mv.setViewName("home");

		return mv;
	}
}
