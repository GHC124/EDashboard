package com.ghc.edashboard.service.file;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ghc.edashboard.domain.File;

public interface FileService {
	File save(File file);	
	
	List<File> findAllByFolder(Integer folderId);
	
	Page<File> findAllByFolder(Integer folderId, Pageable pageable);

	File findById(Integer fileId);
}
