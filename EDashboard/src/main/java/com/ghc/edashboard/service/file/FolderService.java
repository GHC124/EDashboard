package com.ghc.edashboard.service.file;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ghc.edashboard.domain.Folder;

public interface FolderService {
	Page<Folder> findAllByUser(Integer userId, Pageable pageable);

	Folder save(Folder fileFolder);
}
