package com.ghc.edashboard.service.file;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.ghc.edashboard.domain.File;

public interface FileService {
	File save(File file);

	Page<File> findAllByFolder(Integer folderId, PageRequest pageRequest);
}
