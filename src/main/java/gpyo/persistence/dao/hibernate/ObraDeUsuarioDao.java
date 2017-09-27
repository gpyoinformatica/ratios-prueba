package gpyo.persistence.dao.hibernate;

import gpyo.persistence.dao.IObraDeUsuarioDao;
import gpyo.persistence.entity.admin.ObraDeUsuario;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Dao para enlazar con la tabla de obras de usuario.
 * 
 * @author Alberto Revilla Gil
 * @version 1.0
 */
@Repository
public class ObraDeUsuarioDao extends GenericDao<ObraDeUsuario, Long> implements IObraDeUsuarioDao{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5374274273271053906L;
	private static final Log log = LogFactory.getLog(ObraDeUsuarioDao.class);
	


	/**
	 * Selecciona las obras de usuario por usuario y fecha.
	 * @param identificador de usuario.
	 * @param fecha.
	 * @return Lista de obras de usuario.
	 * @exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ObraDeUsuario> getObras(long idUsuario, String fecha) {
		try {
			Query query = getSession().createQuery(
					"FROM ObraDeUsuario WHERE fecha='"+fecha+"' and idObraDeUsuario.usuario='"+idUsuario+"'");
			return (List<ObraDeUsuario>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in ObraDeUsuarioDao.getObras() ", e);
			return null;
		}
	}
	
	public double horasDia(long idUsuario, String fecha){
		try{
			Query query = getSession().createQuery("select sum(horasTecnico+horasAdmin) from ObraDeUsuario " +
					"where idObraDeUsuario.usuario="+idUsuario+" and fechaSort='"+fecha+"'");
			if(query.list().get(0)!=null)
				return (Double) query.list().get(0);
			else 
				return 0;
		}	catch ( Exception e){
			log.error(" ERROR Exception in ObraDeUsuarioDao.horasDia(idUsuario, fecha) ", e);
			return 0;
		}
	}
	
	/**
	 * Selecciona todas las obras de usuario.
	 * @return Lista de obras de usuario.
	 * @exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ObraDeUsuario> getObrasDeUsuario() {
		try {
			Query query = getSession().createQuery(
					"FROM ObraDeUsuario o ORDER BY o.fechaSort DESC");
			return (List<ObraDeUsuario>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in ObraDeUsuarioDao.getObrasDeUsuario() ", e);
			return null;
		}
	}
	
	/**
	 * Selecciona las obras de usuario por usuario, fecha y obra.
	 * @param identificador de usuario.
	 * @param fecha.
	 * @param nombre de obra.
	 * @return Lista de obras de usuario.
	 * @exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ObraDeUsuario> getObras(long idUsuario, String fecha, String obra) {
		try {
			Query query = getSession().createQuery(
					"FROM ObraDeUsuario WHERE fecha='"+fecha+"' and idObraDeUsuario.obra.nombreObra='"+obra+"' and idObraDeUsuario.usuario='"+idUsuario+"'");
			return (List<ObraDeUsuario>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in ObraDeUsuarioDao.getObras() ", e);
			return null;
		}
	}
	
	/**
	 * Selecciona las obras de usuario por usuario y obra.
	 * @param identificador de usuario.
	 * @param nombre de la obra.
	 * @return Lista de obras de usuario.
	 * @exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ObraDeUsuario> getObras2(long idUsuario, String obra) {
		try {
			Query query = getSession().createQuery(
					"FROM ObraDeUsuario o WHERE idObraDeUsuario.obra.nombreObra='"+obra+"' and idObraDeUsuario.usuario='"+idUsuario+"' ORDER BY o.fechaSort DESC");
			return (List<ObraDeUsuario>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in ObraDeUsuarioDao.getObras() ", e);
			return null;
		}
	}
	
	/**
	 * Selecciona las obras de usuario por usuario y mes.
	 * @param identificador de usuario.
	 * @param código del mes.
	 * @return Lista de obras de usuario.
	 * @exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ObraDeUsuario> getObrasMes(long idUsuario, int code) {
		try {
			Query query = null;
			if(code==1 || code==2 || code==3 || code==4 || code==5 || code==6 || code==7)
				query= getSession().createQuery(
						"FROM ObraDeUsuario o WHERE fecha like '%%-0"+code+"-%%%%' and idObraDeUsuario.usuario.id='"+idUsuario+"' ORDER BY o.fechaSort DESC");
			else{
				code=code-2;
				if(code==8 || code==9)
				query= getSession().createQuery(
						"FROM ObraDeUsuario o WHERE fecha like '%%-0"+code+"-%%%%' and idObraDeUsuario.usuario.id='"+idUsuario+"' ORDER BY o.fechaSort DESC");
				else
					query= getSession().createQuery(
							"FROM ObraDeUsuario o WHERE fecha like '%%-"+code+"-%%%%' and idObraDeUsuario.usuario.id='"+idUsuario+"' ORDER BY o.fechaSort DESC");
			}
			return (List<ObraDeUsuario>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in ObraDeUsuarioDao.getObras() ", e);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<ObraDeUsuario> getObrasPorYear(int yearFilter){
		try {
			Query query = null;
				query= getSession().createQuery(
						"FROM ObraDeUsuario o WHERE fecha like '%%-%%-"+yearFilter+"' ORDER BY o.fechaSort DESC");
			return (List<ObraDeUsuario>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in ObraDeUsuarioDao.getObras() ", e);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<ObraDeUsuario> getObrasPorYearUsuario(int yearFilter, String idUsuario){
		try {
			Query query = null;
				query= getSession().createQuery(
						"FROM ObraDeUsuario o WHERE fecha like '%%-%%-"+yearFilter+"' and idObraDeUsuario.usuario.nombreUsuario='"+idUsuario+"' ORDER BY o.fechaSort DESC");
			return (List<ObraDeUsuario>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in ObraDeUsuarioDao.getObras() ", e);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<ObraDeUsuario> getObrasPorYearObraUsuario(int yearFilter, String idUsuario, String obra){
		try {
			Query query = null;
				query= getSession().createQuery(
						"FROM ObraDeUsuario o WHERE fecha like '%%-%%-"+yearFilter+"' and idObraDeUsuario.obra.nombreObra='"+obra+"' and idObraDeUsuario.usuario.nombreUsuario='"+idUsuario+"' ORDER BY o.fechaSort DESC");			
				return (List<ObraDeUsuario>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in ObraDeUsuarioDao.getObras() ", e);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<ObraDeUsuario> getObrasPorYearObra(int yearFilter, String nombreObraFilter){
		try {
			Query query = null;
				query= getSession().createQuery(
						"FROM ObraDeUsuario o WHERE fecha like '%%-%%-"+yearFilter+"' and idObraDeUsuario.obra.nombreObra='"+nombreObraFilter+"' ORDER BY o.fechaSort DESC");			
				return (List<ObraDeUsuario>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in ObraDeUsuarioDao.getObras() ", e);
			return null;
		}
	}
	
	/**
	 * Selecciona las obras de usuario por usuario, obra y mes.
	 * @param identificador de usuario.
	 * @param código del mes.
	 * @param nombre de la obra.
	 * @return Lista de obras de usuario.
	 * @exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ObraDeUsuario> getObrasMesObra(long idUsuario, int code, String obra) {
		try {
			Query query = null;
			if(code==1 || code==2 || code==3 || code==4 || code==5 || code==6 || code==7)
				query= getSession().createQuery(
						"FROM ObraDeUsuario o WHERE fecha like '%%-0"+code+"-%%%%' and idObraDeUsuario.obra.nombreObra='"+obra+"' ORDER BY o.fechaSort DESC");
			else{
				code=code-2;
				if(code==8 || code==9)
				query= getSession().createQuery(
						"FROM ObraDeUsuario o WHERE fecha like '%%-0"+code+"-%%%%' and idObraDeUsuario.obra.nombreObra='"+obra+"' ORDER BY o.fechaSort DESC");
				else
					query= getSession().createQuery(
							"FROM ObraDeUsuario o WHERE fecha like '%%-"+code+"-%%%%' and idObraDeUsuario.obra.nombreObra='"+obra+"' ORDER BY o.fechaSort DESC");
			}
			return (List<ObraDeUsuario>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in ObraDeUsuarioDao.getObras() ", e);
			return null;
		}
	}


	/**
	 * Inserta una obra de usuario en la base de datos.
	 * @param obra de usuario.
	 * @exception
	 */
	@Override
	public void insertObraDeUsuario(ObraDeUsuario obra) {
		try {
			getSession().beginTransaction();
			getSession().save(obra);
			getSession().flush();
		} catch (Exception e) {
			log.error(" ERROR Exception in ObraDeUsuarioDao.insertObraDeUsuario(obraDeUsuario) ", e);
		}
	}


	/**
	 * Elimina una obra de usuario de la base de datos.
	 * @param obra de usuario.
	 * @deprecated
	 * @exception
	 */
	@Override
	public void borrarObra(ObraDeUsuario obra) {
		try {
			getSession().delete(obra);
		} catch (Exception e) {
			log.error(" ERROR Exception in ObraDeUsuarioDao.borrarObra(obraDeUsuario) ", e);
		}
	}


	@Override
	public ObraDeUsuario getObra(String nombreObra) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Elimina una obra de usuario de la base de datos.
	 * @param obra de usuario.
	 * @exception
	 */
	public void deleteObraUsuario(ObraDeUsuario obra){
		try {
			getSession().delete(obra);
		} catch (Exception e) {
			log.error(" ERROR Exception in ObraDeUsuarioDao.deleteObraUsuario(obraDeUsuario) ", e);
		}
	}
	
	/**
	 * Actualiza una obra de usuario en la base de datos.
	 * @param obra de usuario.
	 * @exception
	 */
	public void updateRatio(ObraDeUsuario obraNueva){
		try {
			getSession().update(obraNueva);
		} catch (Exception e) {
			log.error(" ERROR Exception in ObraDeUsuarioDao.updateRatio(obraNueva) ", e);
		}
	}
	
	/**
	 * Selecciona las obras de usuario por usuario.
	 * @param identificador de usuario.
	 * @return Lista de obras de usuario.
	 * @exception
	 */
	@SuppressWarnings("unchecked")
	public List<ObraDeUsuario> getTodasObras(long idUsuario){
		try {
			getSession().beginTransaction();
			Query query = getSession().createQuery(
					"FROM ObraDeUsuario o WHERE idObraDeUsuario.usuario='"+idUsuario+"' ORDER BY o.fechaSort DESC");
			getSession().flush();
			return (List<ObraDeUsuario>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in ObraDeUsuarioDao.getTodasObras(idUsuario) ", e);
			return null;
		}
	}
	
	/**
	 * Selecciona las obras de usuario por fecha.
	 * @param fecha.
	 * @return Lista de obras de usuario.
	 * @exception
	 */
	@SuppressWarnings("unchecked")
	public List<ObraDeUsuario> getObrasPorFecha(String fecha){
		try {
			Query query = getSession().createQuery(
					"FROM ObraDeUsuario WHERE fecha='"+fecha+"'");
			return (List<ObraDeUsuario>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in ObraDeUsuarioDao.getObrasPorFecha(fecha) ", e);
			return null;
		}
	}
	
	/**
	 * Selecciona las obras de usuario por nombre de obra.
	 * @param nombre de obra.
	 * @return Lista de obras de usuario.
	 * @exception
	 */
	@SuppressWarnings("unchecked")
	public List<ObraDeUsuario> getObrasPorNombre(String nombre){
		try {
			Query query = getSession().createQuery(
					"FROM ObraDeUsuario o WHERE idObraDeUsuario.obra.nombreObra='"+nombre+"' ORDER BY o.fechaSort DESC");
			return (List<ObraDeUsuario>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in ObraDeUsuarioDao.getObrasPorNombre(nombre) ", e);
			return null;
		}
	}
	
	/**
	 * Selecciona las obras de usuario por código de obra.
	 * @param código de obra.
	 * @return Lista de obras de usuario.
	 * @exception
	 */
	@SuppressWarnings("unchecked")
	public List<ObraDeUsuario> getObrasPorCodigo(String codigo){
		try {
			Query query = getSession().createQuery(
					"FROM ObraDeUsuario o WHERE o.idObraDeUsuario.obra.codigoObra='"+codigo+"' ORDER BY o.fechaSort DESC ");
			return (List<ObraDeUsuario>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in ObraDeUsuarioDao.getObrasPorCodigo(codigo) ", e);
			return null;
		}
	}
	
	/**
	 * Selecciona las obras de usuario por usuario.
	 * @param nombre de usuario.
	 * @return Lista de obras de usuario.
	 * @exception
	 */
	@SuppressWarnings("unchecked")
	public List<ObraDeUsuario> getObrasPorUsuario(String nombreUsuario){
		try {
			Query query = getSession().createQuery(
					"FROM ObraDeUsuario o WHERE idObraDeUsuario.usuario.nombreUsuario='"+nombreUsuario+"' ORDER BY o.fechaSort DESC");
			return (List<ObraDeUsuario>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in ObraDeUsuarioDao.getObrasPorUsuario(usuario) ", e);
			return null;
		}
	}
	
	/**
	 * Selecciona las obras de usuario por mes.
	 * @param código de mes.
	 * @return Lista de obras de usuario.
	 * @exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ObraDeUsuario> getObrasPorMes(int code) {
		try {
			Query query = null;
			if(code==1 || code==2 || code==3 || code==4 || code==5 || code==6 || code==7)
				query= getSession().createQuery(
						"FROM ObraDeUsuario o WHERE fecha like '%%-0"+code+"-%%%%' ORDER BY o.fechaSort DESC");
			else{
				code=code-2;
				if(code==8 || code==9)
				query= getSession().createQuery(
						"FROM ObraDeUsuario o WHERE fecha like '%%-0"+code+"-%%%%' ORDER BY o.fechaSort DESC");
				else
					query= getSession().createQuery(
							"FROM ObraDeUsuario o WHERE fecha like '%%-"+code+"-%%%%' ORDER BY o.fechaSort DESC");
			}
			return (List<ObraDeUsuario>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in ObraDeUsuarioDao.getObrasPorMes(code) ", e);
			return null;
		}
	}
	
	/**
	 * Selecciona las obras de usuario por mes y nombre de obra.
	 * @param código de mes.
	 * @param nombre de la obra.
	 * @return Lista de obras de usuario.
	 * @exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ObraDeUsuario> getObrasPorMesObra(int code, String nombreObra) {
		try {
			Query query = null;
			if(code==1 || code==2 || code==3 || code==4 || code==5 || code==6 || code==7)
				query= getSession().createQuery(
						"FROM ObraDeUsuario o WHERE fecha like '%%-0"+code+"-%%%%' and idObraDeUsuario.obra.nombreObra='"+nombreObra+"' ORDER BY o.fechaSort DESC");
			else{
				code=code-2;
				if(code==8 || code==9)
				query= getSession().createQuery(
						"FROM ObraDeUsuario o WHERE fecha like '%%-0"+code+"-%%%%' and idObraDeUsuario.obra.nombreObra='"+nombreObra+"' ORDER BY o.fechaSort DESC");
				else
					query= getSession().createQuery(
							"FROM ObraDeUsuario o WHERE fecha like '%%-"+code+"-%%%%' and idObraDeUsuario.obra.nombreObra='"+nombreObra+"' ORDER BY o.fechaSort DESC");
			}
			return (List<ObraDeUsuario>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in ObraDeUsuarioDao.getObrasPorMesObra(code, nombreObra) ", e);
			return null;
		}
	}
	
	/**
	 * Selecciona las obras de usuario por usuario y mes.
	 * @param identificador de usuario.
	 * @param código de mes.
	 * @return Lista de obras de usuario.
	 * @exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ObraDeUsuario> getObrasPorUsuarioMes(String usuario, int code) {
		try {
			Query query = null;
			if(code==1 || code==2 || code==3 || code==4 || code==5 || code==6 || code==7)
				query= getSession().createQuery(
						"FROM ObraDeUsuario o WHERE fecha like '%%-0"+code+"-%%%%' and idObraDeUsuario.usuario.nombreUsuario='"+usuario+"' ORDER BY o.fechaSort DESC");
			else{
				code=code-2;
				if(code==8 || code==9)
				query= getSession().createQuery(
						"FROM ObraDeUsuario o WHERE fecha like '%%-0"+code+"-%%%%' and idObraDeUsuario.usuario.nombreUsuario='"+usuario+"' ORDER BY o.fechaSort DESC");
				else
					query= getSession().createQuery(
							"FROM ObraDeUsuario o WHERE fecha like '%%-"+code+"-%%%%' and idObraDeUsuario.usuario.nombreUsuario='"+usuario+"' ORDER BY o.fechaSort DESC");
			}
			return (List<ObraDeUsuario>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in ObraDeUsuarioDao.getObrasPorUsuarioMes(usuario, code) ", e);
			return null;
		}
	}
	
	/**
	 * Selecciona las obras de usuario por usuario, mes y nombre de obra.
	 * @param nombre de usuario.
	 * @param código de mes.
	 * @param nombre de obra.
	 * @return Lista de obras de usuario.
	 * @exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ObraDeUsuario> getObrasPorUsuarioMesObra(String usuario, int code, String nombreObra) {
		try {
			Query query = null;
			if(code==1 || code==2 || code==3 || code==4 || code==5 || code==6 || code==7)
				query= getSession().createQuery(
						"FROM ObraDeUsuario o WHERE fecha like '%%-0"+code+"-%%%%' and idObraDeUsuario.usuario.nombreUsuario='"+usuario+"' and idObraDeUsuario.obra.nombreObra='"+nombreObra+"' ORDER BY o.fechaSort DESC");
			else{
				code=code-2;
				if(code==8 || code==9)
				query= getSession().createQuery(
						"FROM ObraDeUsuario o WHERE fecha like '%%-0"+code+"-%%%%' and idObraDeUsuario.usuario.nombreUsuario='"+usuario+"' and idObraDeUsuario.obra.nombreObra='"+nombreObra+"' ORDER BY o.fechaSort DESC");
				else
					query= getSession().createQuery(
							"FROM ObraDeUsuario o WHERE fecha like '%%-"+code+"-%%%%' and idObraDeUsuario.usuario.nombreUsuario='"+usuario+"' and idObraDeUsuario.obra.nombreObra='"+nombreObra+"' ORDER BY o.fechaSort DESC");
			}
			return (List<ObraDeUsuario>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in ObraDeUsuarioDao.getObrasPorUsuarioMesObra(usuario, code, nombreObra) ", e);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ObraDeUsuario> getObrasPorUsuarioYearMesObra(String usuario, int code, int year) {
		try {
			Query query = null;
			if(code==1 || code==2 || code==3 || code==4 || code==5 || code==6 || code==7)
				query= getSession().createQuery(
						"FROM ObraDeUsuario o WHERE fecha like '%%-%%-"+year+"' and fecha like '%%-0"+code+"-%%%%' and idObraDeUsuario.usuario.nombreUsuario='"+usuario+"'  ORDER BY o.fechaSort DESC");
			else{
				code=code-2;
				if(code==8 || code==9)
				query= getSession().createQuery(
						"FROM ObraDeUsuario o WHERE fecha like '%%-%%-"+year+"' and  fecha like '%%-0"+code+"-%%%%' and idObraDeUsuario.usuario.nombreUsuario='"+usuario+"'  ORDER BY o.fechaSort DESC");
				else
					query= getSession().createQuery(
							"FROM ObraDeUsuario o WHERE fecha like '%%-%%-"+year+"' and  fecha like '%%-"+code+"-%%%%' and idObraDeUsuario.usuario.nombreUsuario='"+usuario+"' ORDER BY o.fechaSort DESC");
			}
			return (List<ObraDeUsuario>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in ObraDeUsuarioDao.getObrasPorUsuarioYearMesObra(usuario, code, nombreObra) ", e);
			return null;
		}
	}
	
	/**
	 * Selecciona las obras de usuario por usuario y obra.
	 * @param nombre de usuario.
	 * @param nombre de obra.
	 * @return Lista de obras de usuario.
	 * @exception
	 */
	@SuppressWarnings("unchecked")
	public List<ObraDeUsuario> getObrasPorUsuarioObra(String usuario, String nombreObra){
		try {
			Query query = getSession().createQuery(
					"FROM ObraDeUsuario o WHERE idObraDeUsuario.usuario.nombreUsuario='"+usuario+"' and idObraDeUsuario.obra.nombreObra='"+nombreObra+"' ORDER BY o.fechaSort DESC");
			return (List<ObraDeUsuario>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in ObraDeUsuarioDao.getObrasPorUsuario(usuario) ", e);
			return null;
		}
	}
	
	/**
	 * Selecciona las obras de usuario por titulo.
	 * @param titulo de la obra.
	 * @return Lista de obras de usuario.
	 * @exception
	 */
	@SuppressWarnings("unchecked")
	public List<ObraDeUsuario> getObrasDeUsuarioPorTitulo(String titulo){
		try {
			Query query = getSession().createQuery(
					"FROM ObraDeUsuario o WHERE idObraDeUsuario.obra.titulo='"+titulo+"' ORDER BY o.fechaSort DESC");
			return (List<ObraDeUsuario>) query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in ObraDeUsuarioDao.getObrasDeUsuarioPorTitulo(titulo) ", e);
			return null;
		}
	}
}
