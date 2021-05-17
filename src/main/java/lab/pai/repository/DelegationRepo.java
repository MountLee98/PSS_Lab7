package lab.pai.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import lab.pai.model.Delegation;

public interface DelegationRepo extends JpaRepository<Delegation, Long>{
	List<Delegation> getAllDelegationsByOrderByDateTimeStartDesc();

}
