package lab.pai.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
//@EnableOAuth2Sso
public class SecurityConfig extends WebSecurityConfigurerAdapter{

//	@Autowired
//	public SecurityConfigBcrypt securityConfigBcrypt;
	
    @Autowired
    OAuth2AuthorizedClientRepository authorizedClientRepository;

    @Autowired
    OAuth2AuthorizedClientService authorizedClientService;
	
	@Qualifier("customUserDetailsServiceImpl")
    @Autowired
    public UserDetailsService userDetailsService;
	
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
    	auth.userDetailsService(userDetailsService).passwordEncoder(/*securityConfigBcrypt.*/bCryptPasswordEncoder());
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//http.csrf().disable().authorizeRequests()
		http.csrf().disable()
		.requestCache().requestCache(new RequestCache())
		.and().headers().frameOptions().sameOrigin().and().authorizeRequests()
		//.requestCache().requestCache(new RequestCache())
		.antMatchers("/login").permitAll()
		.antMatchers("/register").permitAll()
		.requestMatchers(SecurityUti::isFrameworkInternalRequest).permitAll()
		.antMatchers("/rest/delegation/adduserdelegtion").hasAnyAuthority("ROLE_USER")
        .antMatchers("/rest/delegation/delete").hasAnyAuthority("ROLE_USER")
        .antMatchers("/rest/delegation/changedelegation").hasAnyAuthority("ROLE_USER")
        .antMatchers("/rest/delegation/getall").hasAnyAuthority("ROLE_USER")
        .antMatchers("/rest/delegation/getdate").hasAnyAuthority("ROLE_USER")
        .antMatchers("/rest/user/registeruser").hasAnyAuthority("ROLE_USER")
        .antMatchers("/rest/user/allusers").hasAnyAuthority("ROLE_USER")
        .antMatchers("/rest/user/changepassword").hasAnyAuthority("ROLE_USER")
        .antMatchers("/rest/user/deleteuser").hasAnyAuthority("ROLE_USER")
        .antMatchers("/rest/user/getdelegation").hasAnyAuthority("ROLE_USER")
        .antMatchers("/rest/user/getallbyrolename").hasAnyAuthority("ROLE_USER")
        .antMatchers("/swagger-ui.html/**").hasAnyAuthority("ROLE_ADMIN")
        .antMatchers("/admin/delegation").hasAnyAuthority("ROLE_ADMIN")
        .antMatchers("/admin/users").hasAnyAuthority("ROLE_ADMIN")
        .antMatchers("/main").hasAnyAuthority("ROLE_USER")
        .antMatchers("/delegation").hasAnyAuthority("ROLE_USER")
        .antMatchers("/VAADIN/**").permitAll()
        .and().formLogin().loginPage("/login").loginProcessingUrl("/login")
        .and().oauth2Login().loginPage("/login")
        .and().logout().logoutUrl("/logout").logoutSuccessUrl("/login");
        //.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        //.logoutSuccessUrl("/login");
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web.ignoring().antMatchers(
	            // Vaadin Flow static resources // 
	            //"/VAADIN/static/**",

	            // the standard favicon URI
	            "/favicon.ico",

	            // the robots exclusion standard
	            "/robots.txt",

	            // web application manifest // 
	            "/manifest.webmanifest",
	            "/sw.js",
	            "/offline-page.html",

	            // (development mode) static resources // 
	            "/frontend/**",

	            // (development mode) webjars // 
	            "/webjars/**",

	            // (production mode) static resources // 
	            "/frontend-es5/**", "/frontend-es6/**");
	}
	
	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
