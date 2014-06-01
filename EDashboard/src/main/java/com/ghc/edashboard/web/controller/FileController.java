package com.ghc.edashboard.web.controller;

import java.util.List;

import javax.validation.Valid;

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
import org.springframework.web.servlet.ModelAndView;

import com.ghc.edashboard.domain.File;
import com.ghc.edashboard.domain.FileFolder;
import com.ghc.edashboard.service.file.FileFolderService;
import com.ghc.edashboard.service.file.FileService;
import com.ghc.edashboard.util.JpaUtil;
import com.ghc.edashboard.web.form.DataGrid;
import com.ghc.edashboard.web.form.ErrorMessage;
import com.ghc.edashboard.web.form.ValidationResponse;

@Controller
@RequestMapping(value = "/secured/content/file")
public class FileController extends AbstractController {

	@Autowired
	private FileFolderService fileFolderService;

	@Autowired
	private FileService fileService;
	
	@RequestMapping
	public ModelAndView file() {
		ModelAndView modelAndView = new ModelAndView("file");

		File file = new File();
		modelAndView.addObject("file", file);
		
		FileFolder fileFolder = new FileFolder();
		fileFolder.setUserId(getUserId());
		modelAndView.addObject("fileFolder", fileFolder);
		
		return modelAndView;
	}

	@RequestMapping(params = "createFolder", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ValidationResponse createFolder(Model model,
			@ModelAttribute(value = "fileFolder") @Valid FileFolder fileFolder,
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
			fileFolder.setUserId(getUserId());
			FileFolder saveEntity = fileFolderService.save(fileFolder);
			res.setStatus(ValidationResponse.SUCCESS);			
			res.setExtraData(saveEntity.getId().toString());
		}

		return res;
	}
	
	@RequestMapping(value = "/folders", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public DataGrid<FileFolder> getFolderList(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			@RequestParam(value = "sidx", required = false) String sortBy,
			@RequestParam(value = "sord", required = false) String order) {
		PageRequest pageRequest = JpaUtil.getPageRequest(page, rows, sortBy, order);
		Integer userId = getUserId();
		Page<FileFolder> dataPage = fileFolderService.findAllByUser(userId,	pageRequest);
		DataGrid<FileFolder> dataGrid = new DataGrid<>();
		dataGrid.setCurrentPage(dataPage.getNumber() + 1);
		dataGrid.setTotalPages(dataPage.getTotalPages());
		dataGrid.setTotalRecords(dataPage.getTotalElements());
		dataGrid.setData(dataPage.getContent());

		return dataGrid;
	}
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public DataGrid<File> getFileList(
			@RequestParam(value = "folderId", required = true) Integer folderId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			@RequestParam(value = "sidx", required = false) String sortBy,
			@RequestParam(value = "sord", required = false) String order) {
		PageRequest pageRequest = JpaUtil.getPageRequest(page, rows, sortBy, order);
		Page<File> dataPage = fileService.findAllByFolder(folderId, pageRequest);
		DataGrid<File> dataGrid = new DataGrid<>();
		dataGrid.setCurrentPage(dataPage.getNumber() + 1);
		dataGrid.setTotalPages(dataPage.getTotalPages());
		dataGrid.setTotalRecords(dataPage.getTotalElements());
		dataGrid.setData(dataPage.getContent());

		return dataGrid;
	}
}
