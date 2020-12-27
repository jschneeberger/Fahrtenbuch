package de.thd.pms.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import de.thd.pms.controller.HomeController;

/**
 * Spring Security configuration class. Enables all necessary Spring Security
 * functions.
 * 
 * @author tlang
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	private static Logger log = LoggerFactory.getLogger(SecurityConfiguration.class);

	private static String encryptedPasswordHashAdmin = null;
	
	static {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String clearPasswordAdmin = "123456";
		encryptedPasswordHashAdmin = encoder.encode(clearPasswordAdmin);
	}

	/**
	 * Creates the AuthenticationManager with the given values.
	 *
	 * @param auth the AuthenticationManagerBuilder
	 * @throws Exception a given exception
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.passwordEncoder(passwordEncoder())
			.withUser("admin")
			.password(encryptedPasswordHashAdmin)
			.authorities("Role_Admin");
	}

	/**
	 * A given password encoding mechanism.
	 *
	 * @return PasswordEncoder
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Sets the security mechanism.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
				.antMatchers("/", "/index", "/welcome", "/fahrt/**").permitAll()
				.antMatchers("/person/**", "/boot/**").hasAnyAuthority("Role_Admin", "Role_Manager").anyRequest().fullyAuthenticated()
				.and()
			.formLogin()
				.loginPage("/login").permitAll()
				.defaultSuccessUrl("/index", true)
				.failureUrl("/login")
				.and()
			.logout()
				.invalidateHttpSession(true)
				.logoutSuccessUrl("/login")
				.logoutUrl("/logout")
				.and()
			.exceptionHandling();

	}

}
