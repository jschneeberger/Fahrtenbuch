package pms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

/** 
 * Controller to generate the Home Page basics to be rendered by a view. 
 * It extends the convenience class AbstractController that encapsulates most 
 * of the drudgery involved in handling HTTP requests. 
 * @author josef@dr-schneeberger.de
 */
public class StopFahrt extends AlleFahrten {
	protected ModelAndView handleRequestInternal(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws Exception {
		int id;
		try {
			id = Integer.parseInt(httpServletRequest.getParameter("id"));
		} catch (NumberFormatException e) {
			id = 0;
		}
		getFahrtDao().beende(id);
		return super.handleRequestInternal(httpServletRequest, httpServletResponse);
	}

}
