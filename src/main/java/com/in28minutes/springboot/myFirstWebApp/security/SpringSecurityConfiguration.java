package com.in28minutes.springboot.myFirstWebApp.security;



import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SpringSecurityConfiguration {
	
	@Bean
	public InMemoryUserDetailsManager createUserDetailsManager() {
		UserDetails user1 = createNewUser("in28minutes", "dummy");
		UserDetails user2 = createNewUser("bharath", "raja");
		
		return new InMemoryUserDetailsManager(user1,user2);
	}
	
	private UserDetails createNewUser(String username, String password) {
		Function<String, String> passwordEncoder = input -> passwordEncoder().encode(input);
		return  User
				.builder()
				.passwordEncoder(passwordEncoder)
				.username(username)
				.password(password)
				.roles("USER","ADMIN")
				.build();
	}
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		/* 1. Authorize all request 
		 * and the below will throw un-handled type exception 
		 * so we are throwing the exception in method signature
		*/  
		http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
		
		/*
		 * 2. Show login form and we have to make use of all the defaults that make use of formLogins, 
		 * the withDefaults() is the static method in the Customizer class 
		 * so we are static importing the Customizer interface here
		 * import static org.springframework.security.config.Customizer.withDefaults;
		*/
		http.formLogin(withDefaults());
		
		// 3. Disabling Cross-Site Request Forgery.
		http.csrf().disable();
		
		
		// 4. Disabling frame options
		http.headers().frameOptions().disable();
		
		// 5. Returning the SecurityFilterChain by building it from HttpSecurity 
		return http.build();
	}
}
