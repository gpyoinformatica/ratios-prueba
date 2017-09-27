package gpyo.persistence.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import gpyo.persistence.dao.IGastoDao;
import gpyo.persistence.entity.admin.Gasto;

@Repository
public class GastoDao extends GenericDao<Gasto, Long> implements IGastoDao{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2551249081178221335L;
	private static final Log log = LogFactory.getLog(GastoDao.class);
	
	public void saveGasto(Gasto gasto){
		try {
			getSession().merge(gasto);
			getSession().flush();
		} catch (Exception e) {
			log.error(" ERROR Exception in GastoDao.saveGasto(gasto) ", e);
		}
	}
	
	public void updateGasto(Gasto gasto){
		try {
			getSession().merge(gasto);
			getSession().flush();
		} catch (Exception e) {
			log.error(" ERROR Exception in GastoDao.updateGasto(gasto) ", e);
		}
	}
	
	public void deleteGasto(Gasto gasto){
		try {
			getSession().delete(gasto);
			getSession().flush();
		} catch (Exception e) {
			log.error(" ERROR Exception in GastoDao.deleteGasto(gasto) ", e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Gasto> getGastos(){
		try {
			Query query = getSession().createQuery(
					"FROM Gasto");
			return (List<Gasto>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in GastoDao.getGastos() ", e);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Gasto> getGastosPorTitulo(long idTitulo){
		try {
			Query query = getSession().createQuery(
					"FROM Gasto where id_titulo_gasto="+idTitulo);
			return (List<Gasto>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in GastoDao.getGastosPorTitulo(idTitulo) ", e);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Gasto> getGastosPorObra(long idObra){
		try {
			Query query = getSession().createQuery(
					"FROM Gasto where id_obra="+idObra);
			return (List<Gasto>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in GastoDao.getGastosPorObra(idObra) ", e);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Gasto> getGastoYear(String year){
		try {
			Query query = getSession().createQuery(
					"FROM Gasto where fecha like '%%/%%/"+year+"'");
			return (List<Gasto>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in GastoDao.getGastoYear(year) ", e);
			return null;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Gasto> getGastoYearObraCategoria(String year, String obra, String categoria){
		try {
			Query query = getSession().createQuery(
					"FROM Gasto where fecha like '%%/%%/"+year+"' and categoria = '"+obra+"' and cat='"+categoria+"' ORDER BY fechaSort DESC ");
			return (List<Gasto>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in GastoDao.getGastoYearObraCategoria(year, obra, categoria) ", e);
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	public List<Gasto> getGastoYearCategoria(String year, String categoria){
		try {
			Query query = getSession().createQuery(
					"FROM Gasto where fecha like '%%/%%/"+year+"' and cat='"+categoria+"' ORDER BY fechaSort DESC ");
			return (List<Gasto>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in GastoDao.getGastoYearObraCategoria(year, obra, categoria) ", e);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Gasto> getGastoYearMonthObraCategoria(String year, String obra, String categoria, String month){
		try {
			Query query = getSession().createQuery(
					"FROM Gasto where fecha like '%%/"+month+"/"+year+"' and categoria = '"+obra+"' and cat='"+categoria+"' ORDER BY fechaSort DESC ");
			return (List<Gasto>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in GastoDao.getGastoYearMonthObraCategoria(year, obra, categoria, month) ", e);
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	public List<Gasto> getGastoYearMonthCategoria(String year, String categoria, String month){
		try {
			Query query = getSession().createQuery(
					"FROM Gasto where fecha like '%%/"+month+"/"+year+"' and cat='"+categoria+"' ORDER BY fechaSort DESC ");
			return (List<Gasto>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in GastoDao.getGastoYearMonthObraCategoria(year, obra, categoria, month) ", e);
			return null;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Gasto> getGastoYearObra(String year, String obra){
		try {
			Query query = getSession().createQuery(
					"FROM Gasto where fecha like '%%/%%/"+year+"' and categoria = '"+obra+"' ORDER BY fechaSort DESC ");
			return (List<Gasto>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in GastoDao.getGastoYearObra(year, obra) ", e);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Gasto> getGastoYearMonthObra(String year, String obra, String month){
		try {
			Query query = getSession().createQuery(
					"FROM Gasto where fecha like '%%/"+month+"/"+year+"' and categoria = '"+obra+"' ORDER BY fechaSort DESC ");
			return (List<Gasto>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in GastoDao.getGastoYearMonthObra(year, obra, month) ", e);
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	public List<Gasto> getGastoYearMonth(String year, String month){
		try {
			Query query = getSession().createQuery(
					"FROM Gasto where fecha like '%%/"+month+"/"+year+"' ORDER BY fechaSort DESC ");
			return (List<Gasto>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in GastoDao.getGastoYearMonthObraCategoria(year, obra, categoria, month) ", e);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public float pendientePago(String nFactura){
		float pendiente = 0;
		List<Gasto> gastos = new ArrayList<Gasto>();
		try {
			Query query = getSession().createQuery(
					"FROM Gasto where factura='"+nFactura+"'");
			gastos = query.list();
			for(Gasto gasto : gastos)
				pendiente += gasto.getPagado();
			return pendiente;
		} catch (Exception e) {
			log.error(" ERROR Exception in GastoDao.pendientePago(nFactura) ", e);
			return 0;
		}
	}
}
