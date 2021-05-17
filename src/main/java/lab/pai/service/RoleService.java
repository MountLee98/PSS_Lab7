package lab.pai.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import lab.pai.model.Role;

public interface RoleService {
	Role addRole(Role role);
	Role getRoleById(Long id);
}
