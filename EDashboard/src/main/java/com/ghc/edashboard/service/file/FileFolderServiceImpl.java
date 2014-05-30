package com.ghc.edashboard.service.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ghc.edashboard.domain.FileFolder;
import com.ghc.edashboard.repository.file.FileFolderRepository;

@Service("fileFolderService")
@Transactional
public class FileFolderServiceImpl implements FileFolderService{
	
	@Autowired
	private FileFolderRepository fileFolderRepository;
	
	@Transactional(readOnly=true)
	@Override
	public Page<FileFolder> findAllByUser(Pageable pageable, Integer userId) {
		
		return null;
	}

}
