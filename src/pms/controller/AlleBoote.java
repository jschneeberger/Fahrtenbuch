package pms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pms.dao.BootDao;


/** 
 * <p>
 * Controller to generate the Home Page basics to be rendered by a view. 
 * It extends the convenience class AbstractController that encapsulates most 
 * of the drudgery involved in handling HTTP requests. 
 * </p>
 * 
 * <p>
 * ModelAndView contains
 * <ul>
 * <li>title: Will be used as a headline in the resulting view</li>
 * <li>message: Some hints for the user</li>
 * <li>boote: A list/set of boats</li>
 * <li>command: A command that will be used in the links of the list of boats (on the view)</li>
 * </ul>
 * The name of the selected view will be: 'list-boote.jsp'.
 * </p>
 * 
 * @see BootDao
 * @author josef@dr-schneeberger.de
 */
public class AlleBoote extends AbstractController {
    private static Log log = LogFactory.getLog(AlleBoote.class);
	private BootDao bootDao;

	public void setBootDao(BootDao bootDao) {
		this.bootDao = bootDao;
	}
	
	public BootDao getBootDao() {
		return bootDao;
	}

	protected ModelAndView handleRequestInternal(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws Exception {
		log.info(httpServletRequest.getPathTranslated());
		ModelAndView mv = new ModelAndView();
		mv.addObject("title", "Liste der Boote");
		mv.addObject("message", "Alle Boote im Ruderverein.");
		mv.addObject("boote", bootDao.findAll());
		mv.addObject("edit", true);
		mv.setViewName("list-boote");
		return mv;
	}

}
