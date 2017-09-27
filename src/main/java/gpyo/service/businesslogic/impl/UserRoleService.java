package gpyo.service.businesslogic.impl;

import gpyo.persistence.dao.IUserRoleDao;
import gpyo.persistence.entity.admin.UserRole;
import gpyo.service.businesslogic.IUserRoleService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class UserRoleService implements IUserRoleService {
   
    @Autowired
    private IUserRoleDao userRoleDao;
    
    public void insertUserRole(UserRole userRole){
    	userRoleDao.insertRole(userRole);
    }

}