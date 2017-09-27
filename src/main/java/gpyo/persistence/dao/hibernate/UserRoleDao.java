package gpyo.persistence.dao.hibernate;


import gpyo.persistence.dao.IUserRoleDao;
import gpyo.persistence.entity.admin.UserRole;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;



@Repository
public class UserRoleDao implements IUserRoleDao {
	
	private static final Log log = LogFactory.getLog(RoleDAOImpl.class);
   
    @Autowired
    private SessionFactory sessionFactory;
   
	private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

	@Override
	public void insertRole(UserRole userRole) {
		getCurrentSession().save(userRole);
	}

	/**
	 * @return the log
	 */
	public static Log getLog() {
		return log;
	}

   

}
