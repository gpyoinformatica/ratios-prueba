package gpyo.persistence.dao.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import gpyo.persistence.dao.IObraDao;
import gpyo.persistence.entity.admin.Obra;

/**
 * Dao para enlazar con la tabla de obras.
 * 
 * @author Alberto Revilla Gil
 * @version 1.0
 */
@Repository
public class ObraDao extends GenericDao<Obra, Long> implements IObraDao{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5374274273271053906L;
	private static final Log log = LogFactory.getLog(ObraDao.class);
	
	/**
	 * Borra la obra seleccionada.
	 * @param obra a borrar.
	 * @exception
	 */
	@Override
	public void borrarObra(Obra obra) {
		try {
			getSession().delete(obra);
		} catch (Exception e) {
			log.error(" ERROR Exception in ObraDao.borrarObra(obra) ", e);
		}
	}

	/**
	 * Selecciona una obra de la base de datos.
	 * @param nombre de la obra.
	 * @exception
	 * @return Obra 
	 */
	@Override
	public Obra getObra(String nombreObra) {
		try {
			Query query = getSession().createQuery(
					"FROM Obra WHERE nombreObra='"+nombreObra+"'");
			return (Obra) query.list().get(0);
		} catch (Exception e) {
			log.error(" ERROR Exception in ObraDao.getObra(nombreObra) ", e);
			return null;
		}
	}
	
	/**
	 * Selecciona la obra que coincide con el código pasado.
	 * @param código de la obra a buscar.
	 * @exception
	 * @return Obra
	 */
	@Override
	public Obra getObraPorCodigo(String codigoObra) {
		try {
			Query query = getSession().createQuery(
					"FROM Obra WHERE codigoObra='"+codigoObra+"'");
			return (Obra) query.list().get(0);
		} catch (Exception e) {
			log.error(" ERROR Exception in ObraDao.getObraPorCodigo(codigoObra) ", e);
			return null;
		}
	}

	/**
	 * Inserta una obra en la base de datos.
	 * @param Obra a insertar en la base de datos.
	 * @exception
	 */
	@Override
	public void insertObra(Obra obra) {
		try {
			getSession().save(obra);
		} catch (Exception e) {
			log.error(" ERROR Exception in ObraDao.insertObra(obra) ", e);
		}
	}

	/**
	 * Selecciona todas las obras de la base de datos.
	 * @exception
	 * @return Lista con todas las obras.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Obra> getObras() {
		try {
			Query query = getSession().createQuery(
					"FROM Obra order by codigoObra DESC");
			return (List<Obra>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in ObraDao.getObras() ", e);
			return null;
		}
	}
	
	/**
	 * Selecciona las que tienen distinto código.
	 * @exception
	 * @return Lsita con las obras.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Obra> getObrasCodigo() {
		try {
			
			Query query = getSession().createQuery(
					"Select distinct o.codigoObra FROM Obra o");
			return (List<Obra>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in ObraDao.getObrasCodigo() ", e);
			return null;
		}
	}
	
	/**
	 * Selecciona las obras con un título diferente.
	 * @exception
	 * @return Lista con las obras.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Obra> getObrasTitulo() {
		try {
			Query query = getSession().createQuery(
					"Select distinct o.titulo FROM Obra o");
			return (List<Obra>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in ObraDao.getObrasTitulo() ", e);
			return null;
		}
	}

	/**
	 * Actualiza una obra en la base de datos.
	 * @param Obra que se quiere actualizar.
	 * @exception
	 */
	@Override
	public void updateObra(Obra obra) {
		try {
			getSession().update(obra);
		} catch (Exception e) {
			log.error(" ERROR Exception in ObraDao.updateObra(obra) ", e);
		}
	}


	/**
	 * Selecciona las obras cuyo nombre coincide con el string pasado.
	 * @param nombre de la obra a buscar.
	 * @return Lista de obras.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Obra> getObras2(String obra) {
		try {
			Query query = getSession().createQuery(
					"FROM Obra where nombreObra='"+obra+"'");
			return (List<Obra>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in ObraDao.getObras2(obra) ", e);
			return null;
		}
	}
	
	/**
	 * Selecciona las obras filtrando por código de obra.
	 * @param Código de la obra.
	 * @return Lista de obras.
	 * @exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Obra> getObras(String codigo) {
		try {
			Query query = getSession().createQuery(
					"FROM Obra where codigoObra='"+codigo+"'");
			return (List<Obra>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in ObraDao.getObras(codigo) ", e);
			return null;
		}
	}
	/**
	 * Selecciona las obras del mismo título.
	 * @param Título de las obras.
	 * @return Lista de obras.
	 * @exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Obra> getObrasPorTitulo(String titulo) {
		try {
			Query query = getSession().createQuery(
					"FROM Obra where titulo='"+titulo+"'");
			return (List<Obra>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in ObraDao.getObrasPorTitulo(titulo) ", e);
			return null;
		}
	}

}
