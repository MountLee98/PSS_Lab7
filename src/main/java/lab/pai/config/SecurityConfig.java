package lab.pai.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Qualifier("customUserDetailsServiceImpl")
    @Autowired
    public UserDetailsService userDetailsService;
	
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
    	auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//http.csrf().disable().authorizeRequests()
		http.csrf().disable().headers().frameOptions().sameOrigin().and().authorizeRequests()
        .antMatchers("/rest/delegation/adduserdelegtion").hasAnyRole("ROLE_USER")
        .antMatchers("/rest/delegation/delete").hasAnyRole("ROLE_USER")
        .antMatchers("/rest/delegation/changedelegation").hasAnyRole("ROLE_USER")
        .antMatchers("/rest/delegation/getall").hasAnyRole("ROLE_USER")
        .antMatchers("/rest/delegation/getdate").hasAnyRole("ROLE_USER")
        .antMatchers("/rest/user/registeruser").hasAnyRole("ROLE_USER")
        .antMatchers("/rest/user/allusers").hasAnyRole("ROLE_USER")
        .antMatchers("/rest/user/changepassword").hasAnyRole("ROLE_USER")
        .antMatchers("/rest/user/deleteuser").hasAnyRole("ROLE_USER")
        .antMatchers("/rest/user/getdelegation").hasAnyRole("ROLE_USER")
        .antMatchers("/rest/user/getallbyrolename").hasAnyRole("ROLE_USER")
        .antMatchers("/swagger-ui.html/**").hasAnyRole("ROLE_USER")
        .and().formLogin().loginPage("/login").loginProcessingUrl("/login")
        .and().oauth2Login().loginPage("/login")
        .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        .logoutSuccessUrl("/login");
	}
	
	@Bean
    public  BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
