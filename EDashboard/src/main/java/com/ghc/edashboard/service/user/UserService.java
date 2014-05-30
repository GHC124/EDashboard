package com.ghc.edashboard.service.user;

import com.ghc.edashboard.domain.User;

public interface UserService {
	User findByUserName(String userName);
}
