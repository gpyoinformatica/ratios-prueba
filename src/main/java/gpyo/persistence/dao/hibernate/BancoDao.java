package gpyo.persistence.dao.hibernate;


import java.util.List;

import gpyo.persistence.dao.IBancoDao;
import gpyo.persistence.entity.admin.Banco;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository
public class BancoDao  extends GenericDao<Banco, Long> implements IBancoDao{

	/**
	 * 
	 */
	private static final long serialVersionUID = -740114536826375702L;

	private static final Log log = LogFactory.getLog(BancoDao.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Banco> getBancos(){
		try {
			Query query = getSession().createQuery(
					"FROM Banco");
			return (List<Banco>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in BancoDao.getBancos() ", e);
			return null;
		}
	}
	
	@Override
	public Banco getBanco(String nombreBanco){
		try {
			Query query = getSession().createQuery(
					"FROM Banco where nombre='"+nombreBanco+"'");
			return (Banco) query.list().get(0);
		} catch (Exception e) {
			log.error(" ERROR Exception in BancoDao.getBancos() ", e);
			return null;
		}
	}
	
	public void updateFondos(Banco banco){
		try {
			getSession().merge(banco);
			getSession().flush();
		} catch (Exception e) {
			log.error(" ERROR Exception in BancoDao.updateBanco(banco) ", e);
		}
	}

}
