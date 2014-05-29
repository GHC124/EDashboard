package com.ghc.edashboard.web;

import org.springframework.security.authentication.encoding.PasswordEncoder;

import com.ghc.edashboard.util.LogUtil;

public class CustomPasswordEncoder implements PasswordEncoder{

	@Override
	public String encodePassword(String rawPass, Object salt) {
		LogUtil.error("Raw", rawPass);
		return "abc";
	}

	@Override
	public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
		LogUtil.error("Raw1", rawPass);
		LogUtil.error("Enc", encPass);
		
		return false;
	}

}
