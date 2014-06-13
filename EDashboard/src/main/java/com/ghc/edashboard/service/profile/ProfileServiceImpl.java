package com.ghc.edashboard.service.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ghc.edashboard.domain.Profile;
import com.ghc.edashboard.repository.profile.JpaProfileRepository;

@Service
@Transactional
public class ProfileServiceImpl implements ProfileService {

	@Autowired
	private JpaProfileRepository profileRepository;

	@Transactional(readOnly = true)
	@Override
	public Profile findById(Integer id) {
		return profileRepository.findOne(id);
	}

	@Override
	public Profile save(Profile profile) {
		return profileRepository.save(profile);
	}

	@Override
	public void updateIcon(Integer iconId, Integer id) {
		profileRepository.updateIcon(iconId, id);
	}
}
