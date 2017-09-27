package gpyo.persistence.dao;

import java.util.List;

import gpyo.persistence.entity.admin.Role;


public interface RoleDAO {
 
    public Role getRole(int id);
    public List<Role> getRoles();
    public Role getRole(String role);

}
