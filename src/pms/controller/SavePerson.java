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
public class SavePerson extends AllePersonen {
	protected ModelAndView handleRequestInternal(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws Exception {
		httpServletRequest.setCharacterEncoding("UTF-8");
		int id = Integer.parseInt(httpServletRequest.getParameter("id"));
		String vorname = httpServletRequest.getParameter("vorname");
		String nachname = httpServletRequest.getParameter("nachname");
		String telefon = httpServletRequest.getParameter("telefon");
		if (id == 0) {
			getPersonDao().create(vorname, nachname, telefon);
		} else {
			getPersonDao().modify(id, vorname, nachname, telefon);
		}
		return super.handleRequestInternal(httpServletRequest, httpServletResponse);
	}

}
