package app.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Autowired;
import app.web.model.User;
import app.web.model.validator.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/app")
class AppController {
	private static final Logger log = LoggerFactory.getLogger(AppController.class);

	@Autowired
	private UserValidator userValidator;

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
	public ModelAndView updateUser(@ModelAttribute("user") User user, BindingResult result) {
		ModelAndView model = new ModelAndView("user.jsp");

		userValidator.validate(user, result);

		if(result.hasErrors()) {
			log.info("Form data has errors.");
			return model;
		}

		user.update();

		log.info("User updated: " + user);

		// User is added to the model by @ModelAttribute, but other attributes can be added with:
		//model.addObject("user", user);

		return model;
	}
}
