package com.ghc.edashboard.web;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.ghc.edashboard.util.LogUtil;

/**
 * Global Variables for entire application
 *
 */
public class GlobalVariables {
	private static GlobalVariables INSTANCE;

	private String dateFormatPattern;
	private String uploadRootDirectory;
	private String contentTypeImage;
	
	private GlobalVariables() {
		dateFormatPattern="";
		uploadRootDirectory="";
		contentTypeImage="";
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

	public void init() {
		GlobalVariables globalVariables = getInstance();
		// Load properties
		try {
			Properties properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource("database.properties"));
			globalVariables.dateFormatPattern = properties.getProperty("application.date_format_pattern");
			globalVariables.uploadRootDirectory = properties.getProperty("application.upload_root_directory");
			globalVariables.contentTypeImage = properties.getProperty("application.content_type.image");			
		} catch (IOException e) {
			LogUtil.error(e);
		}
	}

	public String getDateFormatPattern() {
		return dateFormatPattern;
	}

	public String getUploadRootDirectory() {
		return uploadRootDirectory;
	}

	public String getContentTypeImage() {
		return contentTypeImage;
	}	
}
