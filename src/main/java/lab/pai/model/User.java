package lab.pai.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.data.relational.core.mapping.Table;

@Entity
//@Table
public class User {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
	@Column(name = "companyName", nullable = false)
    private String companyName;
	@Column(name = "companyAddress", nullable = false)    
	private String companyAddress;
	@Column(name = "companyNip", nullable = false)
    private String companyNip;
	@Column(name = "name", nullable = false)
    private String name;    
	@Column(name = "lastName", nullable = false)
    private String lastName;
	@Column(name = "email", nullable = false)
	@Pattern(regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:"
			+ "[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b"
			+ "\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*"
			+ "[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|"
			+ "[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-"
			+ "\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])", 
			message = "Wrong Email")
    private String email;
	@Column(name = "password", nullable = false)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$", 
    message = "Password must contain: one digit, "
    		+ "at least one upper case letter, "
    		+ "at least one lower case letter, "
    		+ "at least 8 characters without whitespaces")
    private String password;
    private Boolean status = true;
    @Column(name = "registrationName")
	private LocalDate registrationDate = LocalDate.now(); 
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "roleId")
    @Column(name = "role")
    private List<Role> role;
    @OneToMany(cascade = CascadeType.ALL)
    @Column(name = "delegation")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Delegation> delegation;
    @Column(name = "enabled")
    private boolean enabled;
    
    public User(){
        delegation = new ArrayList<Delegation>();
        role = new ArrayList<Role>();
        this.enabled=false;
    }
    
    public User(String companyName, String companyAddress, String companyNip, String name, String lastName, String email,
			String password) {
    	this.companyName = companyName;
    	this.companyAddress = companyAddress;
        this.companyNip = companyNip;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.enabled=false;
        
        delegation = new ArrayList<Delegation>();
        role = new ArrayList<Role>();
	}

	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	public String getCompanyNip() {
		return companyNip;
	}
	public void setCompanyNip(String companyNip) {
		this.companyNip = companyNip;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public LocalDate getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(LocalDate registrationDate) {
		this.registrationDate = registrationDate;
	}
	public List<Role> getRole() {
		return role;
	}
	public void setRole(List<Role> role) {
		this.role = role;
	}
	public List<Delegation> getDelegation() {
		return delegation;
	}
	public void setDelegation(List<Delegation> delegation) {
		this.delegation = delegation;
	}

	public boolean isEnabled() {
		this.enabled=true;
		return enabled;
	}
}
