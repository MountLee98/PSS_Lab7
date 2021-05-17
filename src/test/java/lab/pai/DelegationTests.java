package lab.pai;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import junit.framework.Assert;
import lab.pai.model.Delegation;
import lab.pai.model.Role;
import lab.pai.model.User;
import lab.pai.service.DelegationService;
import lab.pai.service.DelegationServiceImpl;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {PaiApplication.class})
@DataJpaTest
@ComponentScan("lab.pai.service")
public class DelegationTests {

	User userTest = new User("Test", "Test 1", "1111111111", "Test", "Testowy", "test0@test.com", "testowehaslo");
    List<Delegation> delegationList = new ArrayList<Delegation>();
    List<Role> roleList = new ArrayList<Role>();
	
    @Autowired
	DelegationService delegationService;
	
//	@Autowired
//    private UserService userService;
    
    @Autowired
    TestEntityManager entityManager;
    
    @Test
    public void addDelegation() throws Exception {
    	entityManager.persist(userTest);
        entityManager.flush();
        
        LocalDateTime dataStart = LocalDateTime.now();
        LocalDateTime dataStop = dataStart.plusMonths(1);

        Delegation delegationTest = new Delegation(dataStart, dataStop);
        
        delegationService.addDelegation(userTest.getUserId(), delegationTest);
        
        List<Delegation> delegations = delegationService.getAllDelegations();

        for (Delegation d: delegations) {
            if (d.equals(delegationTest)) {
                Assertions.assertThat(d).isEqualTo(delegationTest);
            }
        }
    }
    
    @Test
    public void removeDelegation() throws Exception {
    	entityManager.persist(userTest);
        entityManager.flush();
        
        List<Delegation> userDelegations = new ArrayList<>();
        
        LocalDateTime dataStart = LocalDateTime.now();
        LocalDateTime dataStop = dataStart.plusMonths(1);

        Delegation delegationTest1 = new Delegation(dataStart, dataStop);
        
        userDelegations.add(delegationTest1);
        entityManager.persist(delegationTest1);
        
        dataStart = LocalDateTime.now();
        dataStop = dataStart.plusMonths(2);

        Delegation delegationTest2 = new Delegation(dataStart, dataStop);
        
        userDelegations.add(delegationTest2);
        entityManager.persist(delegationTest2);
        entityManager.flush();
        
        userTest.setDelegation(userDelegations);
        
        List<Delegation> delegations = new ArrayList<>();
        delegations.add(delegationTest1);
        
        delegationService.removeDelegation(userTest.getUserId(), delegationTest2.getDelegationId());
        List<Delegation> allDelegations = delegationService.getAllDelegations();

        Assertions.assertThat(allDelegations).isEqualTo(delegations);
    }
    
    @Test
    public void changeDelegation() throws Exception {
        
        LocalDateTime dataStart = LocalDateTime.now();
        LocalDateTime dataStop = dataStart.plusMonths(1);

        Delegation delegationTest = new Delegation(dataStart, dataStop);
        
        entityManager.persist(delegationTest);
        entityManager.flush();
        
        delegationTest.setDescription("Delegacja");

        delegationService.changeDelegation(1, delegationTest);

        Delegation modifiedDelegation = entityManager.find(Delegation.class, delegationTest.getDelegationId());

        Assertions.assertThat(modifiedDelegation).isEqualTo(delegationTest);
    }
    
    @Test
    public void getAllDelegations() throws Exception {
        
        List<Delegation> delegations = new ArrayList<>();
        
        LocalDateTime dataStart = LocalDateTime.now();
        LocalDateTime dataStop = dataStart.plusMonths(1);

        Delegation delegationTest1 = new Delegation(dataStart, dataStop);
        
        delegations.add(delegationTest1);
        entityManager.persist(delegationTest1);
        entityManager.flush();
        
        dataStart = LocalDateTime.now();
        dataStop = dataStart.plusMonths(2);
        
        Delegation delegationTest2 = new Delegation(dataStart, dataStop);
        
        delegations.add(delegationTest2);
        entityManager.persist(delegationTest2);
        entityManager.flush();
        
        List<Delegation> allDelegations = delegationService.getAllDelegations();

        Assertions.assertThat(allDelegations).isEqualTo(delegations);
    }
    
	@Test
    public void getAllDelegationsOrderByDateStartDesc() throws Exception {
        
        List<Delegation> delegations = new ArrayList<>();
        
        LocalDateTime dataStart = LocalDateTime.now();
        LocalDateTime dataStop = dataStart.plusMonths(3);

        Delegation delegationTest1 = new Delegation(dataStart, dataStop);
        
        delegations.add(delegationTest1);
        
        dataStart = LocalDateTime.now();
        dataStop = dataStart.plusMonths(1);
        
        Delegation delegationTest2 = new Delegation(dataStart, dataStop);
        
        delegations.add(delegationTest2);
        
        dataStart = LocalDateTime.now();
        dataStop = dataStart.plusMonths(2);
        
        Delegation delegationTest3 = new Delegation(dataStart, dataStop);
        
        delegations.add(delegationTest3);
        
        entityManager.persist(delegationTest1);
        entityManager.persist(delegationTest2);
        entityManager.persist(delegationTest3);
        entityManager.flush();
        
        List<Delegation> orderedDelegations = delegationService.getAllDelegationsByOrderByDateTimeStartDesc();

        Assert.assertEquals(delegations, orderedDelegations);
    }
	
}
