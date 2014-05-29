package com.ghc.edashboard.web.auth;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.util.StringUtils;

import com.ghc.edashboard.util.EncryptUtil;
import com.ghc.edashboard.util.LogUtil;

public class CustomPasswordEncoder implements PasswordEncoder {

	@Override
	public String encodePassword(String rawPass, Object salt) {
		String encPass = null;
		if (StringUtils.hasText(rawPass)) {
			try {
				encPass = EncryptUtil.generatePBKDF2(rawPass);
			} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
				LogUtil.error(e);
			}
		}
		return encPass;
	}

	@Override
	public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
		boolean match = false;
		if (StringUtils.hasText(rawPass) && StringUtils.hasText(encPass)) {
			try {
				match = EncryptUtil.validatePBKDF2(rawPass, encPass);
			} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
				LogUtil.error(e);
			}
		}
		return match;
	}

}
