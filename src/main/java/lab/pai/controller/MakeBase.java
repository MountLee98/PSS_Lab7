package lab.pai.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lab.pai.model.Delegation;
import lab.pai.model.Role;
import lab.pai.model.User;
import lab.pai.service.DelegationService;
import lab.pai.service.RoleService;
import lab.pai.service.UserService;

@RequestMapping("/make")
@RestController
@ComponentScan("lab.pai.service")
public class MakeBase {

	@Autowired
	DelegationService delegationService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	RoleService roleService;
	
	@GetMapping("/base") 
	String makeBase() {
		roleService.addRole(new Role("ROLE_USER"));
		roleService.addRole(new Role("ROLE_ADMIN"));
		
		Role user = new Role();
		user = roleService.getRoleById(0L);
		
		Role admin = new Role();
		admin = roleService.getRoleById(1L);
		
		List<Role> roleUser = new ArrayList<>();
		List<Role> roleUserAdmin = new ArrayList<>();
		
		roleUser.add(user);
		roleUserAdmin.add(user);
		roleUserAdmin.add(admin);
		
		User p = new User("Piekny", "Piekna 5", "65478654321", "Adam", "Ganczewski", "agan@gmail.com", "Piekna2137");
		User b = new User("Brzydki", "Brzydka 1", "23453213456", "Marcin", "Zmudlowski", "mzmu@gmail.com", "Brzydki5643");
		User j = new User("Jakas", "Jakas 8", "87934567321", "Robert", "Adamowski", "rada@gmail.com", "Jakas9876");
		
		p.setRole(roleUser);
		b.setRole(roleUser);
		j.setRole(roleUserAdmin);
		
		userService.registerUser(p);
		userService.registerUser(b);
		userService.registerUser(j);
		
		LocalDateTime dataStart = LocalDateTime.now().plusMonths(1);
        LocalDateTime dataStop = dataStart.plusMonths(3);
		
		Delegation p1 = new Delegation(dataStart, dataStop);
		
		dataStart = LocalDateTime.now().plusMonths(4);
        dataStop = dataStart.plusMonths(5);		
		
		Delegation p2 = new Delegation(dataStart, dataStop);
		
		dataStart = LocalDateTime.now().plusMonths(2);
        dataStop = dataStart.plusMonths(4);
        
        Delegation b1 = new Delegation(dataStart, dataStop);
		
		delegationService.addDelegation(p.getUserId(), p1);
		delegationService.addDelegation(p.getUserId(), p2);
		delegationService.addDelegation(b.getUserId(), b1);		
		
		return "ok";
	}
}
