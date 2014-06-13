package com.ghc.edashboard.web.controller;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.ghc.edashboard.web.GlobalVariables;
import com.ghc.edashboard.web.auth.CustomUserDetails;

public abstract class AbstractController {
	@Autowired
	private MessageSource messageSource;
		
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(LocalDateTime.class, new DateTimeEditor());
	}

	@PostConstruct
	public void init() {
		GlobalVariables globalVariables = GlobalVariables.getInstance();
		globalVariables.init();	
	}

	protected String getMessage(String code, Locale locale){
		return messageSource.getMessage(code, null, locale);
	}
	
	protected String getMessage(String code, Object[] args, Locale locale){
		return messageSource.getMessage(code, args, locale);
	}
	
	protected String getDateFormatPattern(){
		return GlobalVariables.getInstance().getDateFormatPattern();
	}	
	
	protected String getUploadRootDirectory() {
		return GlobalVariables.getInstance().getUploadRootDirectory();
	}	
	
	protected String getContentTypeImage() {
		return GlobalVariables.getInstance().getContentTypeImage();
	}		

	protected String[] getArrayContentType(String contentType) {
		String types[] = new String[]{};
		switch(contentType){
		case "image":
			String contentTypeImage = getContentTypeImage();
			if(StringUtils.hasText(contentTypeImage)){
				types = contentTypeImage.split(";");
			}
			break;
		}		
		
		return types;
	}
	
	protected List<String> getListContentType(String contentType) {
		List<String> types= new ArrayList<>();
		switch(contentType){
		case "image":
			String contentTypeImage = getContentTypeImage();
			if(StringUtils.hasText(contentTypeImage)){
				types = Arrays.asList(contentTypeImage.split(";"));
			}
			break;
		}		
		
		return types;
	}
	
	protected Integer getUserId(){
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		return userDetails.getUserId();
	}
	
	protected String getUsername(){
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		return userDetails.getUsername();
	}
	
	private class DateTimeEditor extends PropertyEditorSupport {

		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			if (StringUtils.hasText(text)) {
				String dateFormatPattern = getDateFormatPattern();
				DateTimeFormatter dtf = DateTimeFormat.forPattern(dateFormatPattern);
				LocalDateTime jodatime = dtf.parseLocalDateTime(text);
				setValue(jodatime);
			} else {
				setValue(null);
			}
		}

		@Override
		public String getAsText() throws IllegalArgumentException {
			String s = "";
			if (getValue() != null) {
				String dateFormatPattern = getDateFormatPattern();
				DateTimeFormatter dtfOut = DateTimeFormat.forPattern(dateFormatPattern);
				s = dtfOut.print((LocalDateTime) getValue());
			}
			return s;
		}
	}
}