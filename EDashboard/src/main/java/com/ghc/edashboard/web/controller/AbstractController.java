package com.ghc.edashboard.web.controller;

import java.beans.PropertyEditorSupport;
import java.util.Locale;

import javax.annotation.PostConstruct;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.ghc.edashboard.web.GlobalVariables;

public abstract class AbstractController {
	@Autowired
	private MessageSource messageSource;
	
	private String dateFormatPattern;	
		
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(LocalDateTime.class, new DateTimeEditor());
	}

	@PostConstruct
	public void init() {
		GlobalVariables globalVariables = GlobalVariables.getInstance();
		globalVariables.init(messageSource);
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