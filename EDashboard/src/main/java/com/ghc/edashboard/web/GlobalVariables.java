package com.ghc.edashboard.web;

import java.util.Locale;

import org.springframework.context.MessageSource;

/**
 * Global Variables for entire application
 *
 */
public class GlobalVariables {
	private static GlobalVariables INSTANCE;

	private String dateFormatPattern;
	private String uploadRootDirectory;
	
	private GlobalVariables() {
		dateFormatPattern = "";
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
		globalVariables.dateFormatPattern = messageSource.getMessage(
				"application.date_format_pattern", new Object[] {}, Locale.US);
		globalVariables.uploadRootDirectory = messageSource.getMessage(
				"application.upload_root_directory", new Object[] {}, Locale.US);
	}

	public String getDateFormatPattern() {
		return dateFormatPattern;
	}

	public String getUploadRootDirectory() {
		return uploadRootDirectory;
	}
}
