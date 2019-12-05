package app.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import app.web.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/app")
class AppController {
	private static final Logger log = LoggerFactory.getLogger(AppController.class);

	@RequestMapping(value="/{name}", method=RequestMethod.GET)
	public ModelAndView welcome(@PathVariable(name="name", required=true) String name) {
		ModelAndView model = new ModelAndView("welcome.jsp");
		model.addObject("user", name);

		return model;
	}

	@RequestMapping(value="/user/{name}", method=RequestMethod.GET)
	public ModelAndView getUser(@PathVariable(name="name", required=true) String name) {
		User user = new User(name);

		ModelAndView model = new ModelAndView("user.jsp");
		model.addObject("user", user);

		return model;
	}

	@RequestMapping(value="/user", method=RequestMethod.POST)
	public ModelAndView updateUser(User user) {
		user.update();

		log.info("User updated: " + user);

		ModelAndView model = new ModelAndView("user.jsp");
		model.addObject("user", user);

		return model;
	}
}
