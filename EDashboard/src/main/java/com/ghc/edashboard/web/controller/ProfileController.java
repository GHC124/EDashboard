package com.ghc.edashboard.web.controller;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ghc.edashboard.domain.Profile;
import com.ghc.edashboard.service.profile.ProfileService;
import com.ghc.edashboard.web.form.Message;

@Controller
@RequestMapping(value = "/secured/content/profile")
public class ProfileController extends AbstractController {

	@Autowired
	private ProfileService profileService;

	@RequestMapping
	public ModelAndView profile() {
		ModelAndView modelAndView = new ModelAndView("profile");

		Integer userId = getUserId();

		Profile profile = profileService.findById(userId);
		modelAndView.addObject(profile);

		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView profile(
			@ModelAttribute(value = "profile") @Valid Profile profile, BindingResult result, Locale locale) {
		ModelAndView modelAndView = new ModelAndView("profile");

		if(result.hasErrors()){
			modelAndView.addObject(new Message(Message.ERROR, getMessage("message.save.fail", locale)));
			modelAndView.addObject(profile);
			
			return modelAndView;
		}
		
		Profile p = profileService.save(profile);
		modelAndView.addObject(p);
		modelAndView.addObject(new Message(Message.SUCCESS, getMessage("message.save.success", locale)));
		
		return modelAndView;
	}
}
