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
public class SaveBoot extends AlleBoote {
	protected ModelAndView handleRequestInternal(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws Exception {
		httpServletRequest.setCharacterEncoding("UTF-8");
		int id = Integer.parseInt(httpServletRequest.getParameter("id"));
		String name = httpServletRequest.getParameter("name");
		int sitze = Integer.parseInt(httpServletRequest.getParameter("sitze"));
		String klasse = httpServletRequest.getParameter("klasse");
		if (id == 0) {
			getBootDao().create(name, sitze, klasse);
		} else {
			getBootDao().modify(id, name, sitze, klasse);
		}
		return super.handleRequestInternal(httpServletRequest, httpServletResponse);
	}

}
