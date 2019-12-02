package app.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/app")
class AppController {
	@RequestMapping(value="/{name}", method=RequestMethod.GET)
	public ModelAndView welcome(@PathVariable(name="name", required=true) String name) {
		ModelAndView model = new ModelAndView("welcome");
		model.addObject("user", name);

		return model;
	}
}
