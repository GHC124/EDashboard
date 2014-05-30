package com.ghc.edashboard.repository.user;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.ghc.edashboard.domain.User;

@Repository("userRepository")
public class JpaUserRepository implements UserRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public User findByUserName(String userName) {
		String hql = "select u from User u where u.userName=:username";
		TypedQuery<User> query = this.entityManager.createQuery(hql,
				User.class).setParameter("username", userName);
		List<User> accounts = query.getResultList();

		return accounts.size() == 1 ? accounts.get(0) : null;
	}
}
