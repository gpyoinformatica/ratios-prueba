package gpyo.persistence.entity.admin;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="roles")
public class Role {
   
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
   
    private String role;
   
    @OneToMany(cascade=CascadeType.ALL)
    @JoinTable(name="user_roles",
        joinColumns = {@JoinColumn(name="role_id", referencedColumnName="id")},
        inverseJoinColumns = {@JoinColumn(name="user_id", referencedColumnName="id")}
    )
    private Set<Usuario> userRoles;


	public Role() {
		super();
	}


	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<Usuario> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<Usuario> userRoles) {
        this.userRoles = userRoles;
    }
   
}
