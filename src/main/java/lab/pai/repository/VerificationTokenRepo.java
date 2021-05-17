package lab.pai.repository;

import lab.pai.model.User;
import lab.pai.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepo extends JpaRepository<VerificationToken, Long>{
	VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);
}
