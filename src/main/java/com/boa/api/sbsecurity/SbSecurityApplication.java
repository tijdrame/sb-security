package com.boa.api.sbsecurity;

import org.springframework.boot.CommandLineRunner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.boa.api.sbsecurity.service.UserService;

@SpringBootApplication
public class SbSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbSecurityApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	/*@Bean
	CommandLineRunner run(UserService service){
		return args -> {
			service.saveRole(new Role(null, "ROLE_USER"));
			service.saveRole(new Role(null, "ROLE_MANAGER"));
			service.saveRole(new Role(null, "ROLE_ADMIN"));
			service.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

			service.saveUser(new AppUser(null, "Admin", "admin", "admin", new ArrayList<>()));
			service.saveUser(new AppUser(null, "User", "user", "user", new ArrayList<>()));

			service.addRoleToUser("admin", "ROLE_USER");
			service.addRoleToUser("admin", "ROLE_MANAGER");
			service.addRoleToUser("admin", "ROLE_ADMIN");
			service.addRoleToUser("admin", "ROLE_SUPER_ADMIN");
			service.addRoleToUser("admin", "ROLE_USER");
			service.addRoleToUser("user", "ROLE_USER");
			service.addRoleToUser("user", "ROLE_MANAGER");
		};
	}*/

}
