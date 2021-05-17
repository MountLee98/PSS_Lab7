package lab.pai.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import lab.pai.model.User;

public interface UserRepo extends JpaRepository<User, Long>{
	User findByEmail(String email);
}
