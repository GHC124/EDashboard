package com.ghc.edashboard.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/secured/content/file")
public class FileController extends AbstractController{
	
	@RequestMapping
	public ModelAndView file(){
		ModelAndView modelAndView = new ModelAndView("file");
		
		return modelAndView;		
	}
}
