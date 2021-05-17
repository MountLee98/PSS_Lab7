package lab.pai.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import lab.pai.model.Delegation;
import lab.pai.model.Role;
import lab.pai.model.User;
import lab.pai.model.VerificationToken;
import lab.pai.repository.DelegationRepo;
import lab.pai.repository.RoleRepo;
import lab.pai.repository.UserRepo;
import lab.pai.repository.VerificationTokenRepo;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	DelegationRepo delegationRepo;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	RoleRepo roleRepo;
	
    @Autowired
    private VerificationTokenRepo tokenRepo;
	
	public User registerUser(User user) {
		// TODO Auto-generated method stub
		return userRepo.save(user);
	}

	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return userRepo.findAll();
	}

	public void changePassword(long userId, String newPassword) {
		// TODO Auto-generated method stub
		Optional<User> u = userRepo.findById(userId);
		if(u.isPresent()) {
			User user = u.get();
			user.setPassword(newPassword);
		}
		
	}

	public boolean deleteUserById(long userId) {
		// TODO Auto-generated method stub
		Optional<User> u = userRepo.findById(userId);
		if(u.isPresent()) {
			User user = u.get();
			for (Delegation delegation : user.getDelegation()) {				
				delegationRepo.delete(delegation);
			}
			userRepo.deleteById(userId);
			return true;
		}
		return false;
	}

	public List<User> getAllUsersByRoleName(String roleName) {
		// TODO Auto-generated method stub
//		List<Role> roles = roleRepo.findAll().
//				stream().
//				filter(x -> x.getRoleName().contains(roleName)).
//				collect(Collectors.toList());
//		List<User> users = null;
//		for(int i=0; i<roles.size(); i++) {
//			users = roles.get(i).getUser();
//		}
//		return users;
		List<User> users = userRepo.findAll();//.stream().
//				filter(x -> x.getRole().
//						forEach(y -> y.getRoleName().contains(roleName);)).
//				collect(Collectors.toList());
		List<User> usersWithRoleName = new ArrayList<>();
		int i=0;
		for(User user : users) {
			for(Role role : user.getRole()) {
				if(role.getRoleName().contains(roleName)) {
					usersWithRoleName.add(user);
				}
			}
			i++;
		}
		return usersWithRoleName;
	}
	
    public User findUserByEmail(String email){
        return userRepo.findByEmail(email);
    }

	public List<Delegation> getAllDelByUserOrderByDateStartDesc(long userId) {
		// TODO Auto-generated method stub
		Optional<User> u= userRepo.findById(userId);
		if(u.isPresent()) {
			User user = u.get();
			List<Delegation> del = user.getDelegation();
			return del.stream().
					sorted(Comparator.comparing(Delegation::getDateTimeStart, Comparator.reverseOrder())).
					collect(Collectors.toList());
		}
		return null;
	}
	
	   public void makeAdmin(long userId){
//	        userRepository.findById((int)userId).map(user1 -> {
//	            user1.setRoles(new HashSet<>(Arrays.asList(new Role(RoleTypes.ADMIN ))));
//	            return userRepository.save(user1);
//	        });
		   Optional<User> u = userRepo.findById(userId);
		   if(u.isPresent()) {
			   User user = u.get();
			   List<Role> userRole = user.getRole();
				Long roleId = 2L;
				Optional<Role> r = roleRepo.findById(roleId);
				if(r.isPresent()) {
					Role role = r.get();
					userRole.add(role);
					user.setRole(userRole);
					//userRepo.findById(userId).map(User::setRole)
							//user -> {
						//user.setRole(userRole);
					//})
					//;
				}
		   }	
	    }

	    public void makeUser(long userId){
//	        userRepository.findById((int)userId).map(user1 -> {
//	            user1.setRoles(new HashSet<>(Arrays.asList(new Role(RoleTypes.USER))));
//	            return userRepository.save(user1);
//	        });
	    	Optional<User> u = userRepo.findById(userId);
	    	if(u.isPresent()) {
	    		User user = u.get();
				List<Role> userRole = user.getRole();
				Long roleId = 1L;
				Optional<Role> r = roleRepo.findById(roleId);
				if(r.isPresent()) {
					Role role = r.get();
					userRole.add(role);
					user.setRole(userRole);
				}
	    	}	
	    }
	    
	    public UserDetails loadUserByUsername(String email) 
	    		  throws UsernameNotFoundException {
	    		 
	    		    boolean enabled = true;
	    		    boolean accountNonExpired = true;
	    		    boolean credentialsNonExpired = true;
	    		    boolean accountNonLocked = true;
	    		    try {
	    		        User user = userRepo.findByEmail(email);
	    		        if (user == null) {
	    		            throw new UsernameNotFoundException(
	    		              "No user found with username: " + email);
	    		        }
	    		        
	    		        return new org.springframework.security.core.userdetails.User(
	    		          user.getEmail(), 
	    		          user.getPassword().toLowerCase(), 
	    		          user.isEnabled(), 
	    		          accountNonExpired, 
	    		          credentialsNonExpired, 
	    		          accountNonLocked, 
	    		          getAuthorities(user.getRole()));
	    		    } catch (Exception e) {
	    		        throw new RuntimeException(e);
	    		    }
	    }
	    
	    @Override
	    public User registerNewUserAccount(User user) {
	        
	        if (emailExist(user.getEmail())) {
//	            throw new UserAlreadyExistException(
//	              "There is an account with that email adress: " 
//	              + userDto.getEmail());
	        }
	        
	        User user2 = new User();
	        user.setFirstName(user.getFirstName());
	        user.setLastName(user.getLastName());
	        user.setPassword(user.getPassword());
	        user.setEmail(user.getEmail());
	        user.setRole(new Role(Integer.valueOf(1), user));
	        return userRepo.save(user);
	    }

	    private boolean emailExist(String email) {
	        return userRepo.findByEmail(email) != null;
	    }
	    
	    @Override
	    public User getUser(String verificationToken) {
	        User user = tokenRepo.findByToken(verificationToken).getUser();
	        return user;
	    }
	    
	    @Override
	    public VerificationToken getVerificationToken(String VerificationToken) {
	        return tokenRepo.findByToken(VerificationToken);
	    }
	    
	    @Override
	    public void saveRegisteredUser(User user) {
	        userRepo.save(user);
	    }
	    
	    @Override
	    public void createVerificationToken(User user, String token) {
	        VerificationToken myToken = new VerificationToken(token, user);
	        tokenRepo.save(myToken);
	    }
}
