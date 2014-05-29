package com.ghc.edashboard.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/secured/dashboard")
public class DashboardController extends AbstractController{
	
	@RequestMapping
	public ModelAndView dashboard(){
		ModelAndView modelAndView = new ModelAndView("dashboard");
		return modelAndView;
	}
}
