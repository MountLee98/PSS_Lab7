package lab.pai.service;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import lab.pai.model.Delegation;

public interface DelegationService {
	
	void addDelegation(long userId, Delegation delegation);
	boolean removeDelegation(long userId, long delegationId);
	void changeDelegation(long DelegationId, Delegation delegation);
	List<Delegation> getAllByUserId(long userId);
	List<Delegation> getAllDelegations();
	List<Delegation> getAllDelegationsByOrderByDateTimeStartDesc();
	
}
