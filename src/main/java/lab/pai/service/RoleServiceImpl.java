package lab.pai.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import lab.pai.model.Role;
import lab.pai.model.User;
import lab.pai.repository.RoleRepo;

@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	RoleRepo roleRepo;
	
	public Role addRole(Role role) {
		// TODO Auto-generated method stub
		return roleRepo.save(role);
	}

	@Override
	public Role getRoleById(Long id) {
		// TODO Auto-generated method stub
		Optional<Role> r = roleRepo.findById(id);
		if(r.isPresent()) {
			Role role = r.get();
			return role;
		}
		return null;
	}

}
