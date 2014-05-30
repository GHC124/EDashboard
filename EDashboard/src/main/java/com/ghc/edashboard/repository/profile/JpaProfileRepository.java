package com.ghc.edashboard.repository.profile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.ghc.edashboard.domain.Profile;

@Repository("profileRepository")
public class JpaProfileRepository implements ProfileRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Profile findById(Integer id) {
		return entityManager.find(Profile.class, id);
	}

	@Override
	public Profile save(Profile profile) {
		if (profile.getId() != null) {
			return this.entityManager.merge(profile);
		}
		this.entityManager.persist(profile);
		return profile;
	}
}
