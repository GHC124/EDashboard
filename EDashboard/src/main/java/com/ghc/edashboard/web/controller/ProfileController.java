package com.ghc.edashboard.web.controller;

import java.io.IOException;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ghc.edashboard.domain.File;
import com.ghc.edashboard.domain.Profile;
import com.ghc.edashboard.service.file.FileService;
import com.ghc.edashboard.service.profile.ProfileService;
import com.ghc.edashboard.web.form.Message;
import com.ghc.edashboard.web.util.UploadUtil;

@Controller
@RequestMapping(value = "/secured/content/profile")
public class ProfileController extends AbstractController {

	@Autowired
	private ProfileService profileService;

	@Autowired
	private FileService fileService;

	@RequestMapping
	public ModelAndView profile() {
		ModelAndView modelAndView = new ModelAndView("profile");

		Integer userId = getUserId();

		Profile profile = profileService.findById(userId);
		modelAndView.addObject(profile);

		return modelAndView;
	}

	@RequestMapping(value = "/picture", method = RequestMethod.GET)
	@ResponseBody
	public byte[] getPicture(
			@RequestParam(value = "iconId", required = true) Integer iconId) {
		if (iconId != null) {
			File file = fileService.findById(iconId);
			if (file != null) {
				String rootDirectory = getUploadRootDirectory();
				try {
					byte[] data = UploadUtil.getFile(rootDirectory, file.getDownloadUrl());
					return data;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView profile(
			@ModelAttribute(value = "profile") @Valid Profile profile,
			BindingResult result, Locale locale) {
		ModelAndView modelAndView = new ModelAndView("profile");

		if (result.hasErrors()) {
			modelAndView.addObject(new Message(Message.ERROR, getMessage(
					"message.save.fail", locale)));
			modelAndView.addObject(profile);

			return modelAndView;
		}

		Profile p = profileService.save(profile);
		modelAndView.addObject(p);
		modelAndView.addObject(new Message(Message.SUCCESS, getMessage(
				"message.save.success", locale)));

		return modelAndView;
	}
}
