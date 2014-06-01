package com.ghc.edashboard.service.file;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ghc.edashboard.domain.File;

public interface FileService {
	Page<File> findAllByFolder(Integer folderId, Pageable pageable);
}
