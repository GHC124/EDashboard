package com.ghc.edashboard.service.profile;

import com.ghc.edashboard.domain.Profile;

public interface ProfileService {
	Profile findById(Integer id);
	Profile save(Profile profile);
	void updateIcon(Integer iconId, Integer id);
}
