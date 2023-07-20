package com.movie.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// 로그인에 대한 설정
		// 1. 모든 사용자가 로그인없이 접근할 수 있도록 설정
		http.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/css/**", "/js/**", "/img/**","/images/**", "/fonts/**" ).permitAll()
				.requestMatchers("/", "/members/**", "/movie/**").permitAll()
				.requestMatchers("/favicon.ico", "/error").permitAll()
				.requestMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().authenticated()
				)
		  
		// 2. 로그인에 관련된 설정
		.formLogin(formLogin -> formLogin
				.loginPage("/members/login")
				.defaultSuccessUrl("/")
				.usernameParameter("email")
				.failureUrl("/members/login/error")
				)
		
		// 3.로그아웃에 관련된 설정
		.logout(logout -> logout
				.logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
				.logoutSuccessUrl("/")
				)
		
		// 4. 인증되지않은 사용자가 리소스에 접근했을 때 설정
		.exceptionHandling(handling -> handling
				.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
				)
		.rememberMe(Customizer.withDefaults());
		
		
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
