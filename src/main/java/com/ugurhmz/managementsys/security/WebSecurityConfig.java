package com.ugurhmz.managementsys.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ugurhmz.managementsys.services.SecurityUserDetailsService;

//(3)


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		
		// UserDetailsService
		@Bean
		public UserDetailsService userDetailsService() {
			return new SecurityUserDetailsService();
		}
	
		
		
	
		// Password encoder
		@Bean
		public PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}
	
		
		
		//
		public DaoAuthenticationProvider authenticationProvider() {
			DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
			authProvider.setUserDetailsService(userDetailsService());
			authProvider.setPasswordEncoder(passwordEncoder());
			
			return authProvider;
		}
		
		
		
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.authenticationProvider(authenticationProvider());
		}
		
	
		
		//BEFORE AUTHENTICATON ,IF YOU WANNA USE
		@Override
		protected void configure(HttpSecurity http) throws Exception {
				http.authorizeRequests()
					.anyRequest().permitAll();
		}
		
		
		
		
		/*// LATER AUTHENTICATION
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests()
					.anyRequest().authenticated()
					.and()
					.formLogin()
						.loginPage("/login")
						.usernameParameter("email")
						.defaultSuccessUrl("/",true)
						.permitAll()
					.and().logout().permitAll()
					.and()
						.rememberMe()
							.key("AbcDefgHijKlmnoprs_123456789")
							.tokenValiditySeconds(7 * 24 * 60 * 60);
						
		}*/
		
		// FOR <img th:src ...
		@Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring()
				.antMatchers("/images/**","/js/**","/webjars/**","/css/**");
				
				
		}	
		
}









