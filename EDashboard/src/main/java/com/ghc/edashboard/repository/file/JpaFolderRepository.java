package com.ghc.edashboard.repository.file;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.ghc.edashboard.domain.Folder;

public interface JpaFolderRepository extends
		PagingAndSortingRepository<Folder, Integer> {
	
	@Query("select f from Folder f where f.userId = :userId")	
	Page<Folder> findAllByUser(@Param("userId") Integer userId, Pageable pageable);
}
