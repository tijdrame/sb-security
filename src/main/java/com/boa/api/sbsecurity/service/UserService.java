package com.boa.api.sbsecurity.service;

import java.util.List;

import com.boa.api.sbsecurity.domain.AppUser;
import com.boa.api.sbsecurity.domain.Role;


public interface UserService {
    
    AppUser saveUser(AppUser user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    AppUser getUser(String username);
    List<AppUser> getUsers();
}
