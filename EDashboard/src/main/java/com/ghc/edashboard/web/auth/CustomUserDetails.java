package com.ghc.edashboard.web.auth;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomUserDetails extends User {	
	private static final long serialVersionUID = 1L;
	
	private Integer userId;

	public CustomUserDetails(String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities, Integer id) {
		super(username, password, enabled, accountNonExpired,
				credentialsNonExpired, accountNonLocked, authorities);
		userId = id;
	}

	public CustomUserDetails(String username, String password,
			Collection<? extends GrantedAuthority> authorities, Integer id) {
		super(username, password, authorities);
		userId = id;
	}

	public Integer getUserId() {
		return userId;
	}

}
