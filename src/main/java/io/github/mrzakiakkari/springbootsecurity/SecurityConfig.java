package io.github.mrzakiakkari.springbootsecurity;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		PasswordEncoder encoder
				= PasswordEncoderFactories.createDelegatingPasswordEncoder();
		auth.inMemoryAuthentication()
				.withUser("alan")
				.password(encoder.encode("password"))
				.roles("USER")
				.and()
				.withUser("brendan")
				.password(encoder.encode("password"))
				.roles("USER", "ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		http.authorizeRequests().antMatchers("/").permitAll()
				.antMatchers("/*hello/**").access("hasRole('USER')")
				.antMatchers("/goodbye/**").access("hasRole('ADMIN')").and()
				.formLogin();
	}
}
