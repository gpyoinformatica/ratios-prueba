package gpyo.persistence.dao.hibernate;

import java.util.List;

import gpyo.persistence.dao.ITesoreriaDao;
import gpyo.persistence.entity.admin.Tesoreria;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository
public class TesoreriaDao extends GenericDao<Tesoreria, Long> implements ITesoreriaDao{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8175907122448119597L;
	private static final Log log = LogFactory.getLog(TesoreriaDao.class);
	
	@Override
	public void saveTesoreria(Tesoreria tesoreria) {
		try {
			getSession().save(tesoreria);
			getSession().flush();
		} catch (Exception e) {
			log.error(" ERROR Exception in TesoreriaDao.saveTesoreria(tesoreria) ", e);
		}
	}
	
	@Override
	public void deleteTesoreria(Tesoreria tesoreria) {
		try {
			getSession().delete(tesoreria);
		} catch (Exception e) {
			log.error(" ERROR Exception in TesoreriaDao.deleteTesoreria(tesoreria) ", e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tesoreria> getTesorerias() {
		try {
			Query query = getSession().createQuery(
					"FROM Tesoreria");
			return (List<Tesoreria>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in TesoreriaDao.getTesorerias() ", e);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Tesoreria> getTesorerias(long idObra, int year) {
		try {
			Query query = getSession().createQuery(
					"FROM Tesoreria where tesoreriaTitulo.obra.idObra="+idObra+" and titulo='Ingresos' and year="+year);
			return (List<Tesoreria>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in TesoreriaDao.getTesorerias(idObra) ", e);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Tesoreria> getTesoreriaAnterior(int year, long idObra){
		try {
			Query query = getSession().createQuery(
					"FROM Tesoreria where tesoreriaTitulo.obra.idObra="+idObra+" and year<"+year);
			return (List<Tesoreria>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in TesoreriaDao.getTesoreriaAnterior(year) ", e);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Tesoreria> getFlujoCaja(long idObra, int year) {
		try {
			Query query = getSession().createQuery(
					"FROM Tesoreria where tesoreriaTitulo.obra.idObra="+idObra+" and titulo='Flujo de caja' and year="+year);
			return (List<Tesoreria>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in TesoreriaDao.getTesorerias(idObra) ", e);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Tesoreria> getTesoreriasPorTitulo(long idTitulo, int year) {
		try {
			Query query = getSession().createQuery(
					"FROM Tesoreria where id_tesoreria_titulo="+idTitulo+" and year="+year);
			return (List<Tesoreria>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in TesoreriaDao.getTesoreriasPorTitulo(idTitulo) ", e);
			return null;
		}
	}
	
	@Override
	public void updateTesoreria(Tesoreria tesoreria) {
		try {
			getSession().merge(tesoreria);
		} catch (Exception e) {
			log.error(" ERROR Exception in TesoreriaDao.updateTesoreria(tesoreria) ", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Tesoreria> getTesoreriaPorTitulo(long idTitulo, String concepto, int year){
		try {
			Query query = getSession().createQuery(
					"FROM Tesoreria where id_tesoreria_titulo="+idTitulo+" and titulos='"+concepto+"' and year='"+year+"'");
			return (List<Tesoreria>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in TesoreriaDao.getTesoreriaPorTitulo(idTitulo, concepto) ", e);
			return null;
		}
	}
	
	public Tesoreria getTesoreriaPorTitulo(long idTitulo, String concepto, String tituloObra){
		try {
			Query query = getSession().createQuery(
					"FROM Tesoreria where tesoreriaTitulo="+idTitulo+" and titulos='"+concepto+"' and tesoreriaTitulo.titulo='"+tituloObra+"'");
			return (Tesoreria) query.list().get(0);
		} catch (Exception e) {
			log.error(" ERROR Exception in TesoreriaDao.getTesoreriaPorTitulo(idTitulo, concepto, tituloObra) ", e);
			return null;
		}
	}
	
	@Override
	public Tesoreria getTesoreriaParaTitulo(long idTesoreria) {
		try {
			Query query = getSession().createQuery(
					"FROM Tesoreria where id_tesoreria="+idTesoreria);
			return (Tesoreria) query.list().get(0);
		} catch (Exception e) {
			log.error(" ERROR Exception in TesoreriaDao.getTesoreriasParaTitulo(idTesoreria) ", e);
			return null;
		}
	}

}
