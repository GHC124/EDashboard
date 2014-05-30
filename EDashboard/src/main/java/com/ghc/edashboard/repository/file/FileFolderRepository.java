package com.ghc.edashboard.repository.file;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ghc.edashboard.domain.FileFolder;

public interface FileFolderRepository {
	Integer count();
	
	FileFolder save(FileFolder fileFolder);

	Page<FileFolder> findAllByUser(Pageable pageable, long total, Integer userId);
}
