package com.ghc.edashboard.web.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ghc.edashboard.web.form.Karaoke;
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
			BindingResult result, Locale locale, HttpServletResponse response)
			throws ServletException, IOException {

		// Set the content type based to zip
		response.setContentType("Content-type: text/zip");
		response.setHeader("Content-Disposition",
				"attachment; filename=karaoke_"+ System.currentTimeMillis() + ".zip");

		// Set the content type based on the file type you need to download
		ServletOutputStream out = response.getOutputStream();
		ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(out));

		System.out.println("Download START");

		String page = karaoke.getPage();
		int first = karaoke.getFirst();
		int last = karaoke.getLast();		
		// Get 10 pages each time
		int i = first;
		int total = 0;
		for (; i + 9 <= last; i += 10) {

			System.out.print("Downloading ... " + i + " " + (i + 9));

			KaraokeParser karaokeParser = new KaraokeParser(page, i, i + 9);
			List<Song> songs = karaokeParser.parseSongs();
			total += songs.size();
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
			
			System.out.println(" : " + songs.size());

			zos.putNextEntry(new ZipEntry(String.format("karaoke_%1$d_%2$d.txt", i, i + 9)));
			// Write the contents of the file
			zos.write(json.getBytes("UTF-8"));
			zos.closeEntry();
		}
		// Remain pages
		if(i <= last){
			System.out.print("Downloading ... " + i + " " + last);

			KaraokeParser karaokeParser = new KaraokeParser(page, i, last);
			List<Song> songs = karaokeParser.parseSongs();
			total += songs.size();
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
			
			System.out.println(" : " + songs.size());

			zos.putNextEntry(new ZipEntry(String.format("karaoke_%1$d_%2$d.txt", i, last)));
			// Write the contents of the file
			zos.write(json.getBytes("UTF-8"));
			zos.closeEntry();			
		}
		
		System.out.println("Downloading FINISH - " + total);
		
		zos.close();
	}
}
