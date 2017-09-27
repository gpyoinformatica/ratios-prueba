package gpyo.persistence.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import gpyo.persistence.dao.IIngresoDao;
import gpyo.persistence.entity.admin.Ingreso;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository
public class IngresoDao extends GenericDao<Ingreso, Long> implements IIngresoDao{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2551249081178221335L;
	private static final Log log = LogFactory.getLog(ObraDao.class);
	
	public void saveIngreso(Ingreso ingreso){
		try {
			getSession().merge(ingreso);
			getSession().flush();
		} catch (Exception e) {
			log.error(" ERROR Exception in IngresoDao.saveIngreso(ingreso) ", e);
		}
	}
	
	public void deleteIngreso(Ingreso ingreso){
		try{
			getSession().delete(ingreso);
			getSession().flush();
		} catch (Exception e){
			log.error(" ERROR Exception in IngresoDao.deleteIngreso(ingreso) ", e);
		}
	}

	@Override
	public void updateIngreso(Ingreso ingreso) {
		try {
			getSession().merge(ingreso);
			getSession().flush();
		} catch (Exception e) {
			log.error(" ERROR Exception in IngresoDao.updateIngreso(ingreso) ", e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Ingreso> getIngresos() {
		try {
			Query query = getSession().createQuery(
					"FROM Ingreso order by fechaSort");
			return (List<Ingreso>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in IngresoDao.getIngresos() ", e);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Ingreso> getIngresosPorObra(long idObra) {
		try {
			Query query = getSession().createQuery(
					"FROM Ingreso where id_obra="+idObra+" order by fechaSort");
			return (List<Ingreso>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in IngresoDao.getIngresos() ", e);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Ingreso> getIngresosPorMes(String mes, long obra) {
		try {
			Query query = getSession().createQuery(
					"FROM Ingreso where id_obra="+obra+" and fecha like '__-"+mes+"-____' order by fechaSort");
			return (List<Ingreso>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in IngresoDao.getIngresosPorMes(mes) ", e);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public float pendientePago(String nFactura){
		float pendiente = 0;
		List<Ingreso> ingresos = new ArrayList<Ingreso>();
		try {
			Query query = getSession().createQuery(
					"FROM Ingreso where n_factura='"+nFactura+"'");
			ingresos = query.list();
			for(Ingreso ingreso : ingresos)
				pendiente += ingreso.getIngreso();
			return pendiente;
		} catch (Exception e) {
			log.error(" ERROR Exception in IngresoDao.pendientePago(nFactura) ", e);
			return 0;
		}
	}

}
