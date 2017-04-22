package de.hdu.pms.ctrl;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import de.hdu.pms.dao.PersonDao;
import de.hdu.pms.model.Person;

@Controller
public class PersonController {
	private static Logger log = Logger.getLogger(PersonController.class);
	private PersonDao personDao = null; //warum gleich null?

    public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}

    @RequestMapping(value="/EditPerson.html", method=RequestMethod.GET)
    public ModelAndView edit(@RequestParam(required=false) Integer id) {
    	log.debug("EditPerson.html");
    	ModelAndView mv = new ModelAndView();
    	if (id == null) {
    		mv.addObject(new Person());
    	} else { 
    		mv.addObject(personDao.findById(id));
    	}
    	mv.setViewName("edit-person");
    	return mv;
    }
    
    /**
     * <p>Saves a person.</p>
     * 
     * <p>Expected HTTP POST and request '/SavePerson.html'.</p>
     * @return 
     */
    @RequestMapping(value="/SavePerson.html", method=RequestMethod.POST)
    public String save(Person person, Model model) {
        if (person.getCreated() == null) {
            person.setCreated(new Date());
        }
        Person result = personDao.save(person);
        // set id from create
        if (person.getId() == null) {
            person.setId(result.getId());
        }
        model.addAttribute("statusMessageKey", "person.form.msg.success");
        return "redirect:AllePersonen.html";
    }

    /**
     * <p>Deletes a person.</p>
     * 
     * <p>Expected HTTP POST and request '/LoeschePerson.html'.</p>
     */
    @RequestMapping(value="/LoeschePerson.html", method=RequestMethod.GET)
    public String delete(Person person) {
        personDao.delete(person);
        return "redirect:AllePersonen.html";
    }

    /**
     * <p>Searches for all persons and returns them in a 
     * <code>Collection</code>.</p>
     * 
     * <p>Expected HTTP GET and request '/AllePersonen.html'.</p>
     */
    @RequestMapping(value="/AllePersonen.html", method=RequestMethod.GET)
    public ModelAndView findAll() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("title", "Vereinsmitglieder");
		mv.addObject("message", "Alle Mitglieder des Vereins");
		mv.addObject("personen", personDao.findAll());
		mv.setViewName("list-personen");
		return mv;
    }

}
