package lab.pai.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lab.pai.model.Delegation;
import lab.pai.model.Role;
import lab.pai.model.User;
import lab.pai.service.DelegationService;
import lab.pai.service.RoleService;
import lab.pai.service.UserService;

@RequestMapping("/rest/user")
@RestController
@ComponentScan("lab.pai.service")
public class UserController {

	@Autowired
	DelegationService delegationService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	RoleService roleService;
	
	@PostMapping("registeruser")
    public void registerUser(
    		@RequestParam("companyName")String companyName,
    		@RequestParam("companyAddress")String companyAddress,
            @RequestParam("companyNip")String companyNip,
            @RequestParam("name")String name,
            @RequestParam("lastName")String lastName,
            @RequestParam("email")String email,
            @RequestParam("password")String password
    ){
        userService.registerUser( new User(companyName, companyAddress,companyNip,name,lastName,email,password) );
    }
	
	@GetMapping("allusers")
	List<User> getAllusers() {
		List<User> list = userService.getAllUsers();
		return list;
	}
	
	@PutMapping("changepassword")
	void changeUserPassword(@RequestParam("userId")long userId, @RequestParam("newPassword")String newPassword) {
		userService.changePassword(userId, newPassword);
	}
	
	@DeleteMapping("deleteuser")
	boolean deleteUserById(@RequestParam("userId")long userId) {
		return userService.deleteUserById(userId);
	}
	
	@GetMapping("/getdelegation")
	List<Delegation> getAllDelByUserOrderByDateStartDesc(@RequestParam("userId")long userId) {
		return userService.getAllDelByUserOrderByDateStartDesc(userId);
	}
	
	@GetMapping("/getallbyrolename")
	List<User> getAllUsersByRoleName(@RequestParam("roleName")String roleName) {
		return userService.getAllUsersByRoleName(roleName);
	}
	
}
