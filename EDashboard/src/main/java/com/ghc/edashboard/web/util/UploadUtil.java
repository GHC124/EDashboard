package com.ghc.edashboard.web.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;

import org.apache.commons.io.IOUtils;

import com.ghc.edashboard.util.LogUtil;

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
		Path binaryPath = Paths.get(rootDirectory);
		if (!Files.exists(binaryPath)) {
			Files.createDirectories(binaryPath);
		}
	}

	public static boolean isValidFile(String originalName) {
		return true;
	}

	public static String getFileExt(String originalName) {
		int index = originalName.lastIndexOf(".");
		if (index != -1) {
			String type = originalName.substring(index + 1);
			return type;
		}
		return "";
	}

	public static String saveFile(String username, String rootDirectory, String originalName,
			InputStream inputStream) throws IOException {
		Calendar calendar = Calendar.getInstance();
		String fileName = String.format("%s_file_%s.%s", username,
				calendar.getTimeInMillis(), getFileExt(originalName));
		// Format: root\year\month\file_name.ext
		String folderPath = String.format("%s\\%s", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1);
		String fullFolderPath = String.format("%s\\%s", rootDirectory, folderPath);
		String filePath = String.format("%s\\%s", folderPath, fileName);
		String fullFilePath = String.format("%s\\%s", fullFolderPath, fileName);		

		LogUtil.error("%s %s %s %s", folderPath, fullFolderPath, filePath, fullFilePath);
		
		// Create folder path
		createUploadFolder(fullFolderPath);
		
		try(OutputStream outputStream = new FileOutputStream(fullFilePath)){
			IOUtils.copy(inputStream, outputStream);
		}

		return filePath;
	}
	
	public static byte[] getFile(String rootDirectory, String fileUrl)
			throws IOException {
		String filePath = String.format("%s\\%s", rootDirectory, fileUrl);
		Path path = Paths.get(filePath);
		if (Files.exists(path)) {
			try(InputStream inputStream = new FileInputStream(filePath)){
				byte[] data = IOUtils.toByteArray(inputStream);
				return data;
			}
		}

		return null;
	}

	public static void deleteFile(String rootDirectory, String downloadUrl) throws IOException {
		String filePath = String.format("%s\\%s", rootDirectory, downloadUrl);
		Path path = Paths.get(filePath);
		if (Files.exists(path)) {
			Files.delete(path);
		}
	}
}
