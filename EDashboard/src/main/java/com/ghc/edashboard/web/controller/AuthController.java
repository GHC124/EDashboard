package com.ghc.edashboard.web.controller;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ghc.edashboard.web.form.Message;

@Controller
@RequestMapping(value = "/public/auth")
public class AuthController extends AbstractController {

	@RequestMapping(value = "/login")
	public ModelAndView login(@RequestParam(required = false) String authNo,
			Locale locale) {
		ModelAndView modelAndView = new ModelAndView("login");
		if (StringUtils.hasText(authNo)) {
			modelAndView.addObject(new Message(Message.ERROR, getMessage(
					"auth.login.fail", locale)));
		}		
		return modelAndView;
	}
}
