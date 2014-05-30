package com.ghc.edashboard.repository.user;

import com.ghc.edashboard.domain.User;

public interface UserRepository {
	User findByUserName(String userName);	
}
