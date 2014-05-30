package com.ghc.edashboard.web.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ghc.edashboard.repository.user.UserRepository;

public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository usersRepository;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		com.ghc.edashboard.domain.User user = usersRepository.findByUserName(username);
		if (user == null) {
			throw new UsernameNotFoundException("user name not found");
		}
				
		return buildUserFromAccount(user);
	}

	private User buildUserFromAccount(com.ghc.edashboard.domain.User account) {
		String username = account.getUserName();
		String password = account.getPassword();
		boolean enabled = account.getIsActive() != null
				&& account.getIsActive() != 0;
		boolean accountNonExpired = enabled;
		boolean credentialsNonExpired = enabled;
		boolean accountNonLocked = enabled;

		// additional information goes here
		Integer userId = account.getId();

		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(account.getAuthority()));

		CustomUserDetails user = new CustomUserDetails(username, password,
				enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, authorities, userId);

		return user;
	}
}
