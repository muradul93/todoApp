package com.murad.todoApp.config;

import com.murad.todoApp.models.UsersModel;
import com.murad.todoApp.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class RunOnApplicationStart implements CommandLineRunner {

	@Autowired
	UsersService usersService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {

		if(!usersService.isAnyUserExists()){
			UsersModel user = new UsersModel();
			user.setEnabled(true);
			user.setFirstName("Muradul");
			user.setLastName("Mostafa");
			user.setUsername("admin");
			user.setPassword(passwordEncoder.encode("admin"));

			usersService.saveUser(user);
		}
	}
}
