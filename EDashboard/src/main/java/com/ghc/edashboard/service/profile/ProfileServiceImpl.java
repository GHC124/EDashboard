package com.ghc.edashboard.service.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ghc.edashboard.domain.Profile;
import com.ghc.edashboard.repository.profile.ProfileRepository;

@Service
@Transactional
public class ProfileServiceImpl implements ProfileService {

	@Autowired
	private ProfileRepository profileRepository;

	@Transactional(readOnly = true)
	@Override
	public Profile findById(Integer id) {
		return profileRepository.findById(id);
	}

	@Override
	public Profile save(Profile profile) {
		return profileRepository.save(profile);
	}

}
