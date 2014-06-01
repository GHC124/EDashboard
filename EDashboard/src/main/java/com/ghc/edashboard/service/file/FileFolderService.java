package com.ghc.edashboard.service.file;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ghc.edashboard.domain.FileFolder;

public interface FileFolderService {
	Page<FileFolder> findAllByUser(Integer userId, Pageable pageable);

	FileFolder save(FileFolder fileFolder);
}
