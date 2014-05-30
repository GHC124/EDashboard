package com.ghc.edashboard.repository.file;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.ghc.edashboard.domain.FileFolder;

public interface JpaFileFolderRepository extends
		PagingAndSortingRepository<FileFolder, Integer> {
	
	@Query("from FileFolder where userId=:userId")
	Page<FileFolder> findAllByUser(@Param("userId") Integer userId, Pageable pageable);
}
