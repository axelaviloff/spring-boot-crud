package com.compassouol.entrevista.config.security;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Profile("prod")
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	//Configuracoes de autorização
	@Override
    protected void configure(final HttpSecurity http) throws Exception {
		http
	        .authorizeRequests()
	        .antMatchers(HttpMethod.GET, "/cidade/**").permitAll()
	        .antMatchers(HttpMethod.GET, "/cliente/**").permitAll()
	        .antMatchers("/h2-console/**").permitAll()
	        .anyRequest().authenticated()
	        .and().httpBasic()
	        .and().headers().frameOptions().disable()
	        .and().formLogin().permitAll()
	        .and().logout()
	        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	        .and().csrf().disable();
    }
	
	//Configuracoes de autenticação
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
    	auth.inMemoryAuthentication()
        	.withUser("teste")
        	.password("{noop}teste")
        	.roles("ADMIN");
    } 

    //Configuracoes de recursos estáticos(js, css, imagens, etc.)
  	@Override
  	public void configure(WebSecurity web) throws Exception {
  		web.ignoring().antMatchers("/v2/api-docs",
  	            "/swagger-resources",
  	            "/swagger-resources/**",
  	            "/configuration/ui",
  	            "/configuration/security",
  	            "/swagger-ui.html",
  	            "/webjars/**",
  	            "/v3/api-docs/**",
  	            "/swagger-ui/**");
  	}
}