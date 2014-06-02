package com.ghc.edashboard.repository.file;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.ghc.edashboard.domain.File;

public interface JpaFileRepository extends
		PagingAndSortingRepository<File, Integer> {
}
