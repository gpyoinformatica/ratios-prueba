package gpyo.service.businesslogic;

import java.util.List;

import gpyo.persistence.entity.admin.Role;


public interface RoleService {
 
    public Role getRole(int id);
    public List<Role> getRoles();
    public Role getRole(String role);

}
