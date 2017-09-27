package gpyo.service.businesslogic.impl;

import java.util.List;

import gpyo.persistence.dao.RoleDAO;
import gpyo.persistence.entity.admin.Role;
import gpyo.service.businesslogic.RoleService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class RoleServiceImpl implements RoleService {
   
    @Autowired
    private RoleDAO roleDAO;

    public Role getRole(int id) {
        return roleDAO.getRole(id);
    }
    
    public List<Role> getRoles(){
    	return roleDAO.getRoles();
    }
    
    public Role getRole(String role){
    	return roleDAO.getRole(role);
    }

}
