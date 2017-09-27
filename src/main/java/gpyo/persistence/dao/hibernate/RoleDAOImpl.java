package gpyo.persistence.dao.hibernate;

import java.util.List;

import gpyo.persistence.dao.RoleDAO;
import gpyo.persistence.entity.admin.Role;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;



@Repository
public class RoleDAOImpl implements RoleDAO {
	
	private static final Log log = LogFactory.getLog(RoleDAOImpl.class);
   
    @Autowired
    private SessionFactory sessionFactory;
   
    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public Role getRole(int id) {
        Role role = (Role) getCurrentSession().load(Role.class, id);
        return role;
    }
    
    @SuppressWarnings("unchecked")
	public List<Role> getRoles(){
    	try {
			Query query = getCurrentSession().createQuery(
					"FROM Role");
			return (List<Role>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in RoleDao.getRoles() ", e);
			return null;
		}
    }
    
    public Role getRole(String role) {
    	try {
			Query query = getCurrentSession().createQuery(
					"FROM Role where role='"+role+"'");
			return (Role) query.list().get(0);
		} catch (Exception e) {
			log.error(" ERROR Exception in RoleDao.getRole(role) ", e);
			return null;
		}
    }

}
