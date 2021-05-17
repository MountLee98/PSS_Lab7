package lab.pai.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import lab.pai.model.Role;

public interface RoleRepo extends JpaRepository<Role, Long>{
	//Role findByRoleId(Long id);
}
