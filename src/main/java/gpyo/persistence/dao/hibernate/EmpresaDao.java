package gpyo.persistence.dao.hibernate;


import java.util.List;

import gpyo.persistence.dao.IEmpresaDao;
import gpyo.persistence.entity.admin.Empresa;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository
public class EmpresaDao  extends GenericDao<Empresa, Long> implements IEmpresaDao{

	/**
	 * 
	 */
	private static final long serialVersionUID = -740114536826375702L;

	private static final Log log = LogFactory.getLog(BancoDao.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Empresa> getEmpresas(){
		try {
			Query query = getSession().createQuery(
					"FROM Empresa");
			return (List<Empresa>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in EmpresaDao.getEmpresas() ", e);
			return null;
		}
	}
	
	@Override
	public Empresa getEmpresa(String nombre){
		try {
			Query query = getSession().createQuery(
					"FROM Empresa where nombre='"+nombre+"'");
			return (Empresa) query.list().get(0);
		} catch (Exception e) {
			log.error(" ERROR Exception in EmpresaDao.getEmpresa(nombre) ", e);
			return null;
		}
	}
	
	public void saveEmpresa(Empresa empresa){
		try {
			getSession().merge(empresa);
			getSession().flush();
		} catch (Exception e) {
			log.error(" ERROR Exception in EmpresaDao.saveEmpresa(empresa) ", e);
		}
	}

}