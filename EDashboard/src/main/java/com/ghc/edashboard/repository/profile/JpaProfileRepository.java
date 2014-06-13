package com.ghc.edashboard.repository.profile;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ghc.edashboard.domain.Profile;

@Repository("profileRepository")
public interface JpaProfileRepository extends CrudRepository<Profile, Integer> {
	
	@Modifying
	@Transactional
	@Query("update Profile p set p.iconId = :iconId where p.id = :id")
	void updateIcon(@Param("iconId") Integer iconId, @Param("id") Integer id);
}
