package lab.pai.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import lab.pai.model.Delegation;
import lab.pai.model.User;
import lab.pai.repository.DelegationRepo;
import lab.pai.repository.UserRepo;

@Service
public class DelegationServiceImpl implements DelegationService{
	
	@Autowired
	DelegationRepo delegationRepo;
	
	@Autowired
	UserRepo userRepo;

	public void addDelegation(long userId, Delegation delegation) {
		// TODO Auto-generated method stub
		List<Delegation> userDelegation = userRepo.findById(userId).map(User::getDelegation).get();
		userDelegation.add(delegation);
        userRepo.findById(userId).map(user -> {
            user.setDelegation(userDelegation);
            return delegationRepo.save(delegation);
        });
//		Optional<User> u = userRepo.findById(userId);
//		if(u.isPresent()) {
//			User user = u.get();
//			delegation.setUser(user);
//		}
//		delegationRepo.save(delegation);
	}

	public boolean removeDelegation(long userId, long delegationId) {
		// TODO Auto-generated method stub
		Optional<User> u = userRepo.findById(userId);
		if(u.isPresent()) {
			User user = u.get();
			int i=0;
			for(Delegation delegation : user.getDelegation()) {
				if(delegation.getDelegationId() == delegationId) {
					user.getDelegation().remove(i);
					delegationRepo.deleteById(delegationId);
					return true;
				}
				i++;
			}
		}		
		
//		Optional<Delegation> d = delegationRepo.findById(delegationId);
//		if(d.isPresent()) {
//			Delegation delegation = d.get();
//			Optional<User> u = userRepo.findById(userId);
//			if(u.isPresent()) {
//				User user = u.get();
//				if(user.getUserId() == userId) {
//					delegationRepo.deleteById(delegationId);
//					return true;
//				}
//			}
//		}
		return false;
	}
	
    public List<Delegation> getAllByUserId(long userId) {
        return userRepo.findById(userId).map(User::getDelegation).get();
    }

	public void changeDelegation(long delegationId, Delegation delegation) {
		// TODO Auto-generated method stub
        if(delegationRepo.existsById(delegationId)){
            delegationRepo.save(delegation);
        }
	}

	public List<Delegation> getAllDelegations() {
		// TODO Auto-generated method stub
		List<Delegation> delegations = delegationRepo.findAll();
		return delegations;
	}

	public List<Delegation> getAllDelegationsByOrderByDateTimeStartDesc() {
		// TODO Auto-generated method stub
		return delegationRepo.getAllDelegationsByOrderByDateTimeStartDesc();
	}

	
}
