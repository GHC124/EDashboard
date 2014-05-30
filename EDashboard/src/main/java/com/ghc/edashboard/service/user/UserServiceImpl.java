package com.ghc.edashboard.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ghc.edashboard.domain.User;
import com.ghc.edashboard.repository.user.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository usersRepository;

	@Transactional(readOnly = true)
	@Override
	public User findByUserName(String userName) {
		return usersRepository.findByUserName(userName);
	}

}
