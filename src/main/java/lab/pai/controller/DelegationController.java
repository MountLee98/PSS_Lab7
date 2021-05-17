package lab.pai.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lab.pai.model.Delegation;
import lab.pai.service.DelegationService;
import lab.pai.service.UserService;

@RequestMapping("/rest/delagation")
@RestController
@ComponentScan("lab.pai.service")
public class DelegationController {
	
	@Autowired
	DelegationService delegationService;
	
	@Autowired
	UserService userService;
	
	@PostMapping("/adduserdelegtion")
	void addDelegation(@RequestParam("userId")long userId, @RequestParam("dateTimeStart")LocalDateTime dateTimeStart, 
			@RequestParam("dateTimeStop")LocalDateTime dateTimeStop) {
		delegationService.addDelegation(userId, new Delegation(dateTimeStart,dateTimeStop));
	}
	
	@DeleteMapping("/delete")
	boolean removeDelegation(@RequestParam("userId") long userId, @RequestParam("delegationId") long delegationId) {
		return delegationService.removeDelegation(userId, delegationId);
	}
	
	@PutMapping("/changedelegation")
	void changeDelegation(@RequestParam("delegationId") long delegationId, @RequestBody Delegation delegation) {
		delegationService.changeDelegation(delegationId,delegation);
	}
	
	@GetMapping("/getall")
	List<Delegation> getAllDelegations() {
		List<Delegation> list = delegationService.getAllDelegations();
		return list;
	}
	
	@GetMapping("/getdate")
	List<Delegation> getAllDelegationsOrderByDateStartDesc() {
		return delegationService.getAllDelegationsByOrderByDateTimeStartDesc();
	}
	
}
