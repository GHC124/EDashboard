package com.ghc.edashboard.web.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UploadUtil {
	private static final long KILOBYTES = 1024;
	private static final long MEGABYTES = (KILOBYTES * 1024);
	private static final long GIGABYTES = (MEGABYTES * 1024);

	public static String getFileSize(long bytes) {
		String fileSizeString = "";
		if (bytes > GIGABYTES) {
			fileSizeString = (bytes / GIGABYTES) + " GB";
		} else if (bytes > MEGABYTES) {
			fileSizeString = (bytes / MEGABYTES) + " MB";
		} else if (bytes > KILOBYTES) {
			fileSizeString = (bytes / KILOBYTES) + " KB";
		} else {
			fileSizeString = bytes + " b";
		}

		return fileSizeString;
	}

	/**
	 * Create upload folder
	 * 
	 * @throws IOException
	 */
	public static void createUploadFolder(String rootDirectory)
			throws IOException {
		Path iconPath = Paths.get(rootDirectory + "\\AppIcon");
		Path binaryPath = Paths.get(rootDirectory + "\\AppBinary");

		if (!Files.exists(iconPath)) {
			Files.createDirectories(iconPath);
		}
		if (!Files.exists(binaryPath)) {
			Files.createDirectories(binaryPath);
		}
	}
}
