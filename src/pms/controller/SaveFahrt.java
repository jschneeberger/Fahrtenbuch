package pms.controller;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

/** 
 * Controller to save a Fahrt Object.
 * @author josef@dr-schneeberger.de
 */
public class SaveFahrt extends AlleFahrten {
	@SuppressWarnings("unchecked")
	protected ModelAndView handleRequestInternal(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws Exception {
		int bootId;
		try {
			bootId = Integer.parseInt(httpServletRequest.getParameter("id"));
		} catch (NumberFormatException e) {
			bootId = 0;
		}
		Set<Integer> personenIds = new HashSet<Integer>();
		for (Enumeration<String> parNames = httpServletRequest.getParameterNames(); parNames.hasMoreElements();) {
			String nextParam = parNames.nextElement();
			if (nextParam.startsWith("sitz")) {
				try {
					int personId = Integer.parseInt(httpServletRequest.getParameter(nextParam));
					personenIds.add(personId);
				} catch (Exception e) {
					// Nichts zu tun hier. Eigentlich sollte der Fall nicht auftreten.
				}
			}
		}
		getFahrtDao().beginne(bootId, personenIds);
		super.setBeende(false);
		return super.handleRequestInternal(httpServletRequest, httpServletResponse);
	}

}
