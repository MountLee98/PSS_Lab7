package lab.pai.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lab.pai.model.Role;
import lab.pai.model.User;
import lab.pai.repository.UserRepo;

@Service("customUserDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
    UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		User user = userRepo.findByEmail(email);
        if (user == null) throw new UsernameNotFoundException(email);

        ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        for (Role role : user.getRole()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleName().toString()));
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), passwordEncoder.encode(user.getPassword()), grantedAuthorities);
	}
	
	

}
