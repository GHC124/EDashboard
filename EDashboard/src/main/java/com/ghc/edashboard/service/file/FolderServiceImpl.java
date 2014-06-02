package com.ghc.edashboard.service.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ghc.edashboard.domain.Folder;
import com.ghc.edashboard.repository.file.JpaFolderRepository;

@Service("folderService")
@Transactional
public class FolderServiceImpl implements FolderService {

	@Autowired
	private JpaFolderRepository folderRepository;

	@Transactional(readOnly = true)
	@Override
	public Page<Folder> findAllByUser(Integer userId, Pageable pageable) {
		return folderRepository.findAllByUser(userId, pageable);
	}

	@Override
	public Folder save(Folder fileFolder) {
		return folderRepository.save(fileFolder);
	}

}
