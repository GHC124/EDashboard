package com.ghc.edashboard.repository.profile;

import com.ghc.edashboard.domain.Profile;

public interface ProfileRepository {
	Profile findById(Integer id);
	Profile save(Profile profile);
}
