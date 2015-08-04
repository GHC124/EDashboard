package com.ghc.edashboard.web.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ghc.edashboard.web.form.Karaoke;
import com.ghc.edashboard.web.form.Message;
import com.ghc.edashboard.web.util.KaraokeParser;
import com.ghc.edashboard.web.util.karaoke.Song;
import com.ghc.edashboard.web.util.karaoke.SongJson;

@Controller
@RequestMapping(value = "/public")
public class PublicController extends AbstractController {

	@RequestMapping(value = "/main")
	public ModelAndView mainPage() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("main");

		return modelAndView;
	}

	@RequestMapping(value = "/about")
	public ModelAndView aboutPage() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("about");

		return modelAndView;
	}

	@RequestMapping(value = "/contact")
	public ModelAndView contactPage() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("contact");

		return modelAndView;
	}

	@RequestMapping(value = "/karaoke")
	public ModelAndView karaokePage() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("karaoke");

		Karaoke karaoke = new Karaoke();
		modelAndView.addObject("karaoke", karaoke);

		return modelAndView;
	}

	@RequestMapping(value = "/karaoke", method = RequestMethod.POST)
	public void getKaraoke(
			@ModelAttribute(value = "karaoke") @Valid Karaoke karaoke,
			BindingResult result, Locale locale, HttpServletResponse response) throws ServletException, IOException {
		
		// Set the response type and specify the boundary string
		response.setContentType("multipart/x-mixed-replace;boundary=END");

		// Set the content type based on the file type you need to download
		String contentType = "Content-type: " + MediaType.APPLICATION_JSON_VALUE;
		ServletOutputStream out = response.getOutputStream();
		// Print the boundary string
		out.println();
		out.println("--END");
		
		System.out.println("Download START");

		String page = karaoke.getPage();
		int first = karaoke.getFirst();
		int last = karaoke.getLast();
		for (int i = first; i <= last; i++) {
			
			System.out.println("Downloading ... " + i);
			
			KaraokeParser karaokeParser = new KaraokeParser(page, first, last);
			List<Song> songs = karaokeParser.parseSongs();
			SongJson songJson = karaokeParser.convertToJson(songs);
			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			String json = "";
			try {
				json = ow.writeValueAsString(songJson);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// Print the content type
			out.println(contentType);
			out.println("Content-Disposition: attachment; filename=" + String.format("karaoke_%1$d", i));
			out.println();

			// Write the contents of the file
			out.write(json.getBytes());
			
			// Print the boundary string
			out.println();
			out.println("--END");
			out.flush();
		}
		// Print the ending boundary string
		out.println("--END--");
		out.flush();
		out.close();
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Set the response type and specify the boundary string
		response.setContentType("multipart/x-mixed-replace;boundary=END");

		// Set the content type based on the file type you need to download
		String contentType = "Content-type: "
				+ MediaType.APPLICATION_JSON_VALUE;
		ServletOutputStream out = response.getOutputStream();
		// Print the boundary string
		out.println();
		out.println("--END");

		 System.out.println("Download START");
		
		String page = "";
		int first = 0;
		int last = 0;
		for (int i = first; i <= last; i++) {
			KaraokeParser karaokeParser = new KaraokeParser(page, first, last);
			List<Song> songs = karaokeParser.parseSongs();
			SongJson songJson = karaokeParser.convertToJson(songs);
			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			String json = "";
			try {
				json = ow.writeValueAsString(songJson);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// Print the content type
			out.println(contentType);
			out.println("Content-Disposition: attachment; filename=" + String.format("karaoke_%1$d", i));
			out.println();

			System.out.println("Downloading ... " + i);
			
			// Write the contents of the file
			out.write(json.getBytes());
			
			// Print the boundary string
			out.println();
			out.println("--END");
			out.flush();
		}
		// Print the ending boundary string
		out.println("--END--");
		out.flush();
		out.close();
	}
}
