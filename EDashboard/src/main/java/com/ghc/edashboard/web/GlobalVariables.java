package com.ghc.edashboard.web;

import java.util.Locale;

import org.springframework.context.MessageSource;

/**
 * Global Variables for entire application
 *
 */
public class GlobalVariables {
	private static GlobalVariables INSTANCE;

	private String mDateFormatPattern;

	private GlobalVariables() {
		mDateFormatPattern = "";
	}

	public static GlobalVariables getInstance() {
		if (INSTANCE == null) {
			synchronized (GlobalVariables.class) {
				if (INSTANCE == null) {
					INSTANCE = new GlobalVariables();
				}
			}
		}
		return INSTANCE;
	}

	public void init(MessageSource messageSource) {
		GlobalVariables globalVariables = getInstance();

		// Load global variables
		globalVariables.mDateFormatPattern = messageSource.getMessage(
				"application.date_format_pattern", new Object[] {}, Locale.US);
	}

	public String getDateFormatPattern() {
		return mDateFormatPattern;
	}
}
