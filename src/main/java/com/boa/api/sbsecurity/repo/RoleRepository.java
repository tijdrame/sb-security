package com.boa.api.sbsecurity.repo;


import com.boa.api.sbsecurity.domain.Role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
