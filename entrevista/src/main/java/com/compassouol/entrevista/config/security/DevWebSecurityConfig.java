package com.compassouol.entrevista.config.security;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class DevWebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	//Configuracoes de autorização
	@Override
    protected void configure(final HttpSecurity http) throws Exception {
		http
	        .authorizeRequests()
	        .antMatchers("/**").permitAll()
	        .and().headers().frameOptions().disable()
	        .and().csrf().disable();
    }

}