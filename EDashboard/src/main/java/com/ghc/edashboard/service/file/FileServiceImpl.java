package com.ghc.edashboard.service.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ghc.edashboard.domain.File;
import com.ghc.edashboard.repository.file.JpaFileRepository;

@Service("fileService")
@Transactional
public class FileServiceImpl implements FileService {

	@Autowired
	private JpaFileRepository fileRepository;

	@Override
	public File save(File file) {
		return fileRepository.save(file);
	}
}
