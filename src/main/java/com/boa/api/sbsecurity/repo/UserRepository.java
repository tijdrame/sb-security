package com.boa.api.sbsecurity.repo;



import com.boa.api.sbsecurity.domain.AppUser;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String username);
}
