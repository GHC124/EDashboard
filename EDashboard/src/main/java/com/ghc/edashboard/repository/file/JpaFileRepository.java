package com.ghc.edashboard.repository.file;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.ghc.edashboard.domain.File;

public interface JpaFileRepository extends
		PagingAndSortingRepository<File, Integer> {
	
	@Query("select f from File f where f.folderId = :folderId")	
	Page<File> findAllByFolder(@Param("folderId") Integer folderId, Pageable pageable);
}
