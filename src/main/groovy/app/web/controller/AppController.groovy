package app.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/app")
class AppController {
	@RequestMapping(value="/{name}", method=RequestMethod.GET)
	public String hello(@PathVariable(name="name", required=true) String name) {
		return "Hello, ${name}";
	}
}
