package com.ghc.edashboard.service.file;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	@Override
	public Page<File> findAllByFolder(Integer folderId, Pageable pageable) {
		return fileRepository.findAllByFolder(folderId, pageable);
	}

	@Override
	public List<File> findAllByFolder(Integer folderId) {
		return fileRepository.findAllByFolder(folderId);
	}

	@Override
	public File findById(Integer fileId) {
		return fileRepository.findOne(fileId);
	}

	@Override
	public Page<File> findAllByFolderAndContentType(Integer folderId,
			String[] contentType, Pageable pageable) {
		return fileRepository.findAllByFolderAndContentType(folderId, contentType, pageable);
	}
}
