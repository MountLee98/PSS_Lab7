package lab.pai.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

import org.springframework.data.relational.core.mapping.Table;

@Entity
//@Table
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;
    @Column(name = "roleName")
    private String roleName;
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "userId")
//    private List<User> user;
    
	public Role(String roleName) {
		// TODO Auto-generated constructor stub
		this.roleName = roleName;
	}
	public Role() {
		// TODO Auto-generated constructor stub
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
//	public List<User> getUser() {
//		return user;
//	}
//	public void setUser(List<User> user) {
//		this.user = user;
//	}
    
}
