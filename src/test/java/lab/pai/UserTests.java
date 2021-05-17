package lab.pai;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import lab.pai.model.Delegation;
import lab.pai.model.Role;
import lab.pai.model.User;
import lab.pai.service.UserService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {PaiApplication.class})
@DataJpaTest
@ComponentScan("lab.pai.service")
public class UserTests {

    User userTest = new User("Test5", "Test 5", "1111111111", "Test", "Testowy", "test0@test.com", "testowehaslo");
    List<Delegation> delegationList = new ArrayList<Delegation>();
    List<Role> roleList = new ArrayList<Role>();
	
    @Autowired
    private UserService userService;
    
    @Autowired
    private TestEntityManager entityManager;
    
    @Test
    public void registerUser() throws Exception {
    	userTest.setDelegation(delegationList);
    	userTest = userService.registerUser(userTest);
    	List<User> foundUsers = userService.getAllUsers();
    	
    	for (User u: foundUsers) {
            if (u.equals(userTest)) {
                Assertions.assertThat(u).isEqualTo(userTest);
            }
        }
      
    }
    
    @Test
    public void getAllUsers() throws Exception {
        List<User> users = new ArrayList<User>();

	    User user1 = new User("Test1", "Test 1", "1111111111", "Test1", "Testowy1", "test1@test.com", "testowehaslo1");
	    User user2 = new User("Test2","Test 2", "2222222222", "Test2", "Testowy2", "test2@test.com", "testowehaslo2");
	    User user3 = new User("Test3", "Test 3", "3333333333", "Test3", "Testowy3", "test3@test.com", "testowehaslo3");
	    User user4 = new User("Test4", "Test 4", "444444444", "Test4", "Testowy4", "test4@test.com", "testowehaslo4");
	
	    users.add(user1);
	    users.add(user2);
	    users.add(user3);
	    users.add(user4);
	
	    entityManager.persist(user1);
	    entityManager.persist(user2);
	    entityManager.persist(user3);
	    entityManager.persist(user4);
	    entityManager.flush();
	    
	    List<User> allUsers = userService.getAllUsers();

        Assertions.assertThat(allUsers).isEqualTo(users);
    }
    
    @Test
    public void changePassword() throws Exception {
    	entityManager.persist(userTest);

    	userTest.setPassword("noweHasloTestowe");
        userService.changePassword(1, "noweHasloTestowe");
        
        User addedUser = entityManager.find(User.class, userTest.getUserId());

        Assertions.assertThat(addedUser.getPassword()).isEqualTo(userTest.getPassword());
    }
    
    @Test
    public void deleteUserById() throws Exception {
    	List<User> users = new ArrayList<User>();

	    User user1 = new User("Test1", "Test 1", "1111111111", "Test1", "Testowy1", "test1@test.com", "testowehaslo1");
	    User user2 = new User("Test2", "Test 2", "2222222222", "Test2", "Testowy2", "test2@test.com", "testowehaslo2");
	    
	    entityManager.persist(user1);
        entityManager.persist(user2);

        users.add(user1);
        users.add(user2);

        userService.deleteUserById(user2.getUserId());
        users.remove(1);
        
        List<User> allUsers = userService.getAllUsers();

        Assertions.assertThat(allUsers).isEqualTo(users);
    }
    
    @Test
    public void getAllUsersByRoleName() throws Exception {

    	List<Role> roleUserList = new ArrayList<Role>();
        Role userRole = new Role("ROLE_USER");
        Role adminRole = new Role("ROLE_ADMIN");
        roleList.add(userRole);
        roleList.add(adminRole);
        roleUserList.add(userRole);

        entityManager.persist(userRole);
        entityManager.persist(adminRole);
        entityManager.flush();

        User user1 = new User("Test1", "Test 1", "1111111111", "Test1", "Testowy1", "test1@test.com", "testowehaslo1");
        user1.setRole(roleList);
	    User user2 = new User("Test2", "Test 2", "2222222222", "Test2", "Testowy2", "test2@test.com", "testowehaslo2");
	    user2.setRole(roleUserList);

	    List<User> adminRoles = new ArrayList<User>();
	    adminRoles.add(user1);
	    
        entityManager.persist(user1);
        entityManager.persist(user2);
        entityManager.flush();
        
        List<User> listByRoleName = userService.getAllUsersByRoleName("ROLE_ADMIN");
        Assert.assertEquals(adminRoles, listByRoleName);
    }
    
    @Test
    public void getAllDelByUserOrderByDateStartDesc() throws Exception {
    	entityManager.persist(userTest);
        entityManager.flush();
        
        List<Delegation> userDelegations = new ArrayList<>();
        List<Delegation> sortDelegations = new ArrayList<>();
        
        LocalDateTime dataStart = LocalDateTime.now().plusMonths(2);
        LocalDateTime dataStop = dataStart.plusMonths(3);

        Delegation delegationTest1 = new Delegation(dataStart, dataStop);
        
        dataStart = LocalDateTime.now();
        dataStop = dataStart.plusMonths(1);
        
        Delegation delegationTest2 = new Delegation(dataStart, dataStop);
        
        dataStart = LocalDateTime.now().plusMonths(1);
        dataStop = dataStart.plusMonths(2);
        
        Delegation delegationTest3 = new Delegation(dataStart, dataStop);
        
        userDelegations.add(delegationTest1);
        userDelegations.add(delegationTest2);
        userDelegations.add(delegationTest3);

        userTest.setDelegation(userDelegations);
        
        sortDelegations.add(delegationTest1);
        sortDelegations.add(delegationTest3);
        sortDelegations.add(delegationTest2);
        
        entityManager.persist(delegationTest1);
        entityManager.persist(delegationTest2);
        entityManager.persist(delegationTest3);
        entityManager.flush();
        
        List<Delegation> orderedDelegations = userService.getAllDelByUserOrderByDateStartDesc(userTest.getUserId());

        Assert.assertEquals(sortDelegations, orderedDelegations);
    }
    
}
