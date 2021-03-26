package com.compassouol.entrevista.config.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	//Configuracoes de autorizacao
	@Override
    protected void configure(final HttpSecurity http) throws Exception {
		http.headers().frameOptions().disable();
		http.httpBasic().and()
        .csrf().disable()
        .authorizeRequests()
        .antMatchers(HttpMethod.GET, "/cidade/**").permitAll()
        .antMatchers(HttpMethod.GET, "/cliente/**").permitAll()
        .antMatchers("/h2-console/**").permitAll()
        .anyRequest().authenticated()
        .and().formLogin().permitAll()
        .and().logout()
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
    }
	//Configuracoes de autenticacao
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
    	auth.inMemoryAuthentication()
        	.withUser("teste").password("{noop}teste").roles("ADMIN");
    } 
    
    @Bean
    public UserDetailsService userDetailsService() {
        @SuppressWarnings("deprecation")
		User.UserBuilder users = User.withDefaultPasswordEncoder();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(users.username("teste").password("teste").roles("ADMIN").build());

        return manager;

    }
    //Configuracoes de recursos estaticos(js, css, imagens, etc.)
  	@Override
  	public void configure(WebSecurity web) throws Exception {
  		web.ignoring().antMatchers("/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**", "/swagger-resources/**");
  	}
}