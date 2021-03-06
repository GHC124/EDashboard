package com.ghc.edashboard.web.controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ghc.edashboard.domain.File;
import com.ghc.edashboard.domain.Folder;
import com.ghc.edashboard.service.file.FileService;
import com.ghc.edashboard.service.file.FolderService;
import com.ghc.edashboard.util.JpaUtil;
import com.ghc.edashboard.web.form.DataGrid;
import com.ghc.edashboard.web.form.ErrorMessage;
import com.ghc.edashboard.web.form.ValidationResponse;
import com.ghc.edashboard.web.util.UploadUtil;

@Controller
@RequestMapping(value = "/secured/content/file")
public class FileController extends AbstractController {

	@Autowired
	private FolderService folderService;

	@Autowired
	private FileService fileService;

	@RequestMapping
	public ModelAndView file() {
		ModelAndView modelAndView = new ModelAndView("file");

		File file = new File();
		file.setFolderId(0);
		file.setSize(0l);
		file.setDateUp(new LocalDateTime());
		modelAndView.addObject("file", file);

		Folder folder = new Folder();
		folder.setUserId(getUserId());
		modelAndView.addObject("folder", folder);

		return modelAndView;
	}

	@RequestMapping(value = "/createFile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ValidationResponse createFile(
			@ModelAttribute(value = "file") @Valid File file,
			BindingResult result, MultipartHttpServletRequest request,
			Locale locale) {
		ValidationResponse res = new ValidationResponse();
		res.setStatus(ValidationResponse.FAIL);
		if (result.hasErrors()) {
			List<FieldError> allErrors = result.getFieldErrors();
			for (FieldError objectError : allErrors) {
				res.addErrorMessage(new ErrorMessage(objectError.getField(),
						objectError.getDefaultMessage()));
			}
		} else {
			// get the file from the request object
			Iterator<String> itr = request.getFileNames();
			if (!itr.hasNext()) {
				res.addErrorMessage(new ErrorMessage("downloadUrl", getMessage(
						"validation.NotEmpty", locale)));
				return res;
			}
			MultipartFile mpf = request.getFile(itr.next());
			try {
				String originalName = mpf.getOriginalFilename();
				String contentType = mpf.getContentType();
				long size = mpf.getSize();
				if (UploadUtil.isValidFile(originalName)) {
					String rootDirectory = getUploadRootDirectory();
					String downloadUrl = UploadUtil.saveFile(getUsername(), rootDirectory,
							originalName, mpf.getInputStream());
					file.setDownloadUrl(downloadUrl);
					file.setSize(size);
					file.setContentType(contentType);
					fileService.save(file);
					res.setStatus(ValidationResponse.SUCCESS);
				} else {
					res.addErrorMessage(new ErrorMessage("downloadUrl",
							getMessage("validation.InvalidType", locale)));
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		return res;
	}
	
	@RequestMapping(value = "/createFolder", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ValidationResponse createFolder(Model model,
			@ModelAttribute(value = "folder") @Valid Folder folder,
			BindingResult result) {
		ValidationResponse res = new ValidationResponse();
		if (result.hasErrors()) {
			res.setStatus(ValidationResponse.FAIL);
			List<FieldError> allErrors = result.getFieldErrors();
			for (FieldError objectError : allErrors) {
				res.addErrorMessage(new ErrorMessage(objectError.getField(),
						objectError.getDefaultMessage()));
			}
		} else {
			// Reassign user id
			folder.setUserId(getUserId());
			Folder saveEntity = folderService.save(folder);
			res.setStatus(ValidationResponse.SUCCESS);
			res.setExtraData(saveEntity.getId().toString());
		}

		return res;
	}
	
	@RequestMapping(value = "/getFolder", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Folder getFolder(@RequestParam(value = "folderId", required = true) Integer folderId) {
		Folder folder = folderService.findById(folderId);

		return folder;
	}
	
	@RequestMapping(value = "/getFile", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public File getFile(@RequestParam(value = "fileId", required = true) Integer fileId) {
		File file = fileService.findById(fileId);

		return file;
	}
	
	@RequestMapping(value = "/editFolder", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ValidationResponse editFolder(Model model,
			@ModelAttribute(value = "folder") @Valid Folder folder,
			BindingResult result, @RequestParam(value = "folderId", required = true) Integer folderId) {
		ValidationResponse res = new ValidationResponse();
		if (result.hasErrors()) {
			res.setStatus(ValidationResponse.FAIL);
			List<FieldError> allErrors = result.getFieldErrors();
			for (FieldError objectError : allErrors) {
				res.addErrorMessage(new ErrorMessage(objectError.getField(),
						objectError.getDefaultMessage()));
			}
		} else {
			res.setStatus(ValidationResponse.SUCCESS);
			folder.setId(folderId);
			folderService.save(folder);
		}

		return res;
	}

	@RequestMapping(value = "/listFolder", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public DataGrid<Folder> getFolderList(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			@RequestParam(value = "sidx", required = false) String sortBy,
			@RequestParam(value = "sord", required = false) String order) {
		PageRequest pageRequest = JpaUtil.getPageRequest(page, rows, sortBy,
				order);
		Integer userId = getUserId();
		Page<Folder> dataPage = folderService.findAllByUser(userId,
				pageRequest);
		DataGrid<Folder> dataGrid = JpaUtil.getDataGrid(dataPage);

		return dataGrid;
	}

	@RequestMapping(value= "/listFile", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public DataGrid<File> getFileList(
			@RequestParam(value = "folderId", required = true) Integer folderId,
			@RequestParam(value = "contentType", required = false) String contentType,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			@RequestParam(value = "sidx", required = false) String sortBy,
			@RequestParam(value = "sord", required = false) String order) {
		PageRequest pageRequest = JpaUtil.getPageRequest(page, rows, sortBy,
				order);		
		Page<File> dataPage = null;
		if(StringUtils.hasText(contentType)){
			List<String> types = getListContentType(contentType);
			dataPage = fileService.findAllByFolderAndContentType(folderId, types, pageRequest);
		}else{
			dataPage = fileService.findAllByFolder(folderId, pageRequest);
		}
		DataGrid<File> dataGrid =JpaUtil.getDataGrid(dataPage);

		return dataGrid;
	}
	
	@RequestMapping(value = "/deleteFolder", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void deleteFolder(@RequestParam(value="folderId", required = true) Integer folderId, Locale locale) {
		ValidationResponse res = new ValidationResponse();
		res.setStatus(ValidationResponse.SUCCESS);
		
		List<File> files = fileService.findAllByFolder(folderId);
		boolean errorDelete = false;
		for (File file : files) {
			String downloadUrl = file.getDownloadUrl();
			// remove file
			try {
				UploadUtil.deleteFile(getUploadRootDirectory(), downloadUrl);
			} catch (IOException e) {
				e.printStackTrace();
				errorDelete = true;
			}
		}
		if(errorDelete){
			res.setStatus(ValidationResponse.FAIL);
			res.addErrorMessage(new ErrorMessage("deleteFolderError",
					getMessage("message.delete.fail", locale)));
		}
		
		folderService.delete(folderId);
	}
}
