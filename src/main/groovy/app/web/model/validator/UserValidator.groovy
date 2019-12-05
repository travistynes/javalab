package app.web.model.validator;

import org.springframework.validation.Validator;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import app.web.model.User;

/*
See: https://docs.spring.io/spring/docs/4.2.x/spring-framework-reference/html/validation.html
*/
class UserValidator implements Validator {
	@Override
	public boolean supports(Class clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object o, Errors e) {
		User user = (User)o;

		ValidationUtils.rejectIfEmpty(e, "name", "error.user.name.empty");

		if(user.getAge() < 0) {
			e.rejectValue("age", "error.user.age.low");
		} else if(user.getAge() > 40) {
			e.rejectValue("age", "error.user.age.high");
		}
	}
}
