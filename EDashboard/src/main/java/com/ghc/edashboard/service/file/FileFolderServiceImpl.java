package com.ghc.edashboard.service.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ghc.edashboard.domain.FileFolder;
import com.ghc.edashboard.repository.file.JpaFileFolderRepository;

@Service("fileFolderService")
@Transactional
public class FileFolderServiceImpl implements FileFolderService {

	@Autowired
	private JpaFileFolderRepository fileFolderRepository;

	@Transactional(readOnly = true)
	@Override
	public Page<FileFolder> findAllByUser(Integer userId, Pageable pageable) {
		return fileFolderRepository.findAllByUser(userId, pageable);
	}

	@Override
	public FileFolder save(FileFolder fileFolder) {
		return fileFolderRepository.save(fileFolder);
	}

}
