package com.boa.api.sbsecurity.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.boa.api.sbsecurity.domain.AppUser;
import com.boa.api.sbsecurity.domain.Role;
import com.boa.api.sbsecurity.repo.RoleRepository;
import com.boa.api.sbsecurity.repo.UserRepository;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder passwordEncoder) {
this.userRepo = userRepo;
this.roleRepo = roleRepo;
this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepo.findByUsername(username);
        if(user == null) {
            //log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        }else {
            //log.info("User found in the database [{}]", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(r -> authorities.add(new SimpleGrantedAuthority(r.getName())));
        return new User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public AppUser saveUser(AppUser user) {
        //log.info("saving new user [{}]", user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        //log.info("saving new role [{}]", role);
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        //log.info("addRoleToUser username [{}] role [{}]", username, roleName);
        AppUser user = userRepo.findByUsername(username);     
        Role role = roleRepo.findByName(roleName);   
        user.getRoles().add(role);
    }

    @Override
    public AppUser getUser(String username) {
        //log.info("getUser  username [{}]", username);
        return userRepo.findByUsername(username);
    }

    @Override
    public List<AppUser> getUsers() {
        //log.info("getUsers ");
        return userRepo.findAll();
    }
    
}
