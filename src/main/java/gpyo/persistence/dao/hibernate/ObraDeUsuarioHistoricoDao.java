package gpyo.persistence.dao.hibernate;


import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import gpyo.persistence.dao.IObraDeUsuarioHistoricoDao;
import gpyo.persistence.entity.admin.ObraDeUsuarioHistorica;

/**
 * Dao para enlazar con la tabla de obras históricas de usuario.
 * 
 * @author Alberto Revilla Gil
 * @version 1.0
 */
@Repository
public class ObraDeUsuarioHistoricoDao extends GenericDao<ObraDeUsuarioHistorica, Long> implements IObraDeUsuarioHistoricoDao{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5260011637645472625L;
	private static final Log log = LogFactory.getLog(ObraDeUsuarioHistoricoDao.class);
	
	
	/**
	 * Guarda una obra historica en la base de datos.
	 * @param obra de usuario histórica.
	 * @exception
	 */
	public void saveObraHistorico(ObraDeUsuarioHistorica obraHistorica){
		try {
			getSession().save(obraHistorica);
		} catch (Exception e) {
			log.error(" ERROR Exception in ObraDeUsuarioDao.saveObraHistorico(obraHistorica) ", e);
		}
	}
	
	/**
	 * Selecciona todas las obras históricas de la base de datos.
	 * @return Lista de obras históricas.
	 * @exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ObraDeUsuarioHistorica> getObrasHistorico() {
		try {
			Query query = getSession().createQuery(
					"FROM ObraDeUsuarioHistorica");
			return (List<ObraDeUsuarioHistorica>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in ObraDeUsuarioDao.getObrasHistorico() ", e);
			return null;
		}
	}
	
	/**
	 * Selecciona las obras históricas filtrando por nombre de obra.
	 * @param nombre de la obra.
	 * @return Obras históricas.
	 * @exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ObraDeUsuarioHistorica> getObrasHistoricoPorObra(String nombreObra) {
		try {
			Query query = getSession().createQuery(
					"FROM ObraDeUsuarioHistorica WHERE nombreObra='"+nombreObra+"'");
			return (List<ObraDeUsuarioHistorica>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in ObraDeUsuarioDao.getObrasHistoricoPorObra(nombreObra) ", e);
			return null;
		}
	}
	
	/**
	 * Selecciona las obras históricas filtrando por código de obra.
	 * @param código de la obra.
	 * @return Obras históricas.
	 * @exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ObraDeUsuarioHistorica> getObrasHistoricoPorCodigo(String codigoObra) {
		try {
			Query query = getSession().createQuery(
					"FROM ObraDeUsuarioHistorica WHERE codigoObra='"+codigoObra+"'");
			return (List<ObraDeUsuarioHistorica>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in ObraDeUsuarioDao.getObrasHistoricoPorCodigo(codigoObra) ", e);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ObraDeUsuarioHistorica> getObrasHistoricoPorTitulo(String titulo) {
		try {
			Query query = getSession().createQuery(
					"FROM ObraDeUsuarioHistorica WHERE titulo='"+titulo+"'");
			return (List<ObraDeUsuarioHistorica>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in ObraDeUsuarioDao.getObrasHistoricoPorTitulo(titulo) ", e);
			return null;
		}
	}
	
	/**
	 * Selecciona las obras históricas filtrando por usuario.
	 * @param nombre de usuario.
	 * @return Obras históricas.
	 * @exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ObraDeUsuarioHistorica> getObrasHistoricoPorUsuario(String usuarioObra) {
		try {
			Query query = getSession().createQuery(
					"FROM ObraDeUsuarioHistorica WHERE usuario='"+usuarioObra+"'");
			return (List<ObraDeUsuarioHistorica>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in ObraDeUsuarioDao.getObrasHistoricoPorUsuario(usuarioObra) ", e);
			return null;
		}
	}
	
	/**
	 * Selecciona las obras históricas filtrando por año.
	 * @param año de la obra.
	 * @return Obras históricas.
	 * @exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ObraDeUsuarioHistorica> getObrasHistoricoPorYear(String year) {
		try {
			Query query = getSession().createQuery(
					"FROM ObraDeUsuarioHistorica WHERE yearRatio like '%%-%%-"+year+"'");
			return (List<ObraDeUsuarioHistorica>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in ObraDeUsuarioDao.getObrasHistoricoPorUsuario(usuarioObra) ", e);
			return null;
		}
	}

}
