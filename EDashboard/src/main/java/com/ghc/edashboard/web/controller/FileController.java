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
				long size = mpf.getSize();
				if (UploadUtil.isValidFile(originalName)) {
					String rootDirectory = getUploadRootDirectory();
					String downloadUrl = UploadUtil.saveFile(rootDirectory,
							originalName, mpf.getInputStream());
					file.setDownloadUrl(downloadUrl);
					file.setSize(size);
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

	@RequestMapping(value = "/folders", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
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
		DataGrid<Folder> dataGrid = new DataGrid<>();
		dataGrid.setCurrentPage(dataPage.getNumber() + 1);
		dataGrid.setTotalPages(dataPage.getTotalPages());
		dataGrid.setTotalRecords(dataPage.getTotalElements());
		dataGrid.setData(dataPage.getContent());

		return dataGrid;
	}

	@RequestMapping(value="/files", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public DataGrid<File> getFileList(
			@RequestParam(value = "folderId", required = true) Integer folderId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			@RequestParam(value = "sidx", required = false) String sortBy,
			@RequestParam(value = "sord", required = false) String order) {		
		PageRequest pageRequest = JpaUtil.getPageRequest(page, rows, sortBy,
				order);
		Page<File> dataPage = fileService.findAllByFolder(folderId, pageRequest);
		DataGrid<File> dataGrid = new DataGrid<>();
		dataGrid.setCurrentPage(dataPage.getNumber() + 1);
		dataGrid.setTotalPages(dataPage.getTotalPages());
		dataGrid.setTotalRecords(dataPage.getTotalElements());
		dataGrid.setData(dataPage.getContent());

		return dataGrid;
	}
}
