package com.ghc.edashboard.repository.file;

import static com.ghc.edashboard.repository.SQLConstants.LIMIT;
import static com.ghc.edashboard.repository.SQLConstants.OFFSET;
import static com.ghc.edashboard.repository.SQLConstants.ORDER_BY;
import static com.ghc.edashboard.repository.SQLConstants.SORT;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.ghc.edashboard.domain.FileFolder;
import com.ghc.edashboard.util.JpaUtil;

@Repository(value="fileFolderRepository")
public class JpaFileFolderRepository implements FileFolderRepository {
	private final String FILL_ALL_BY_USER_SQL = "";
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public FileFolder save(FileFolder fileFolder) {
		if(fileFolder.getId() != null){
			return entityManager.merge(fileFolder);
		}
		entityManager.persist(fileFolder);		
		return fileFolder;
	}

	@Override
	public Page<FileFolder> findAllByUser(Pageable pageable, long total,
			Integer userId) {
		// TODO use setParameter
		String sql = FILL_ALL_BY_USER_SQL;
		String[] orderAndSort = JpaUtil.getOrderbyAndSort(pageable);
		String orderBy = orderAndSort[0];
		String sort = orderAndSort[1];		
		sql = sql.replace(ORDER_BY, orderBy); 
		sql = sql.replace(SORT, sort);
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter(LIMIT, pageable.getPageSize());
		query.setParameter(OFFSET, pageable.getOffset());
		
		List<FileFolder> result = JpaUtil.getResultList(query,
				FileFolder.class);
		Page<FileFolder> page = new PageImpl<>(result, pageable, total);

		return page;
	}

	@Override
	public Integer count() {
		
	}

}
