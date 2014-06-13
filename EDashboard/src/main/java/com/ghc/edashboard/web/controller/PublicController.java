package com.ghc.edashboard.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/public")
public class PublicController extends AbstractController {
		
	@RequestMapping(value="/main")
	public ModelAndView mainPage(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("main");
		
		return modelAndView;
	}
	
	@RequestMapping(value="/about")
	public ModelAndView aboutPage(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("about");
		
		return modelAndView;
	}
	
	@RequestMapping(value="/contact")
	public ModelAndView contactPage(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("contact");
		
		return modelAndView;
	}
}
