package com.ghc.edashboard.web.controller;

import java.beans.PropertyEditorSupport;
import java.util.Locale;

import javax.annotation.PostConstruct;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	
	private String dateFormatPattern;
	
	@Value("${application.upload_root_directory}")
	private String uploadRootDirectory;
	
	@Value("${application.content_type.image}")
	private String contentTypeImage;
		
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(LocalDateTime.class, new DateTimeEditor());
	}

	@PostConstruct
	public void init() {
		GlobalVariables globalVariables = GlobalVariables.getInstance();
		globalVariables.init(messageSource, uploadRootDirectory);
		dateFormatPattern = globalVariables.getDateFormatPattern();		
	}

	protected String getMessage(String code, Locale locale){
		return messageSource.getMessage(code, null, locale);
	}
	
	protected String getMessage(String code, Object[] args, Locale locale){
		return messageSource.getMessage(code, args, locale);
	}
	
	protected String getDateFormatPattern(){
		return dateFormatPattern;
	}	
	
	protected String getUploadRootDirectory() {
		return uploadRootDirectory;
	}		

	protected String getContentTypeImage() {
		return contentTypeImage;
	}

	protected String[] getArrayContentType(String contentType) {
		String types[] = new String[]{};
		switch(contentType){
		case "image":
			if(StringUtils.hasText(contentTypeImage)){
				types = contentTypeImage.split(";");
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
			
	private class DateTimeEditor extends PropertyEditorSupport {

		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			if (StringUtils.hasText(text)) {
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
				DateTimeFormatter dtfOut = DateTimeFormat.forPattern(dateFormatPattern);
				s = dtfOut.print((LocalDateTime) getValue());
			}
			return s;
		}
	}
}