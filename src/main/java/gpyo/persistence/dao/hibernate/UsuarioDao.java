package gpyo.persistence.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import gpyo.persistence.dao.IUsuarioDao;
import gpyo.persistence.entity.admin.Usuario;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Dao para enlazar con la tabla de usuarios.
 * 
 * @author Alberto Revilla Gil
 * @version 1.0
 */
@Repository
public class UsuarioDao extends GenericDao<Usuario, Long> implements IUsuarioDao{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5595998719502036277L;
	private static final Log log = LogFactory.getLog(UsuarioDao.class);
	
	/**
	 * Inserta un usuario en la base de datos.
	 * @param usuario a guardar.
	 * @exception
	 */
	@Override
	public void insertUsuario(Usuario usuario) {
		try {
			getSession().save(usuario);
		} catch (Exception e) {
			log.error(" ERROR Exception in UsuarioDao.insertUsuario(usuario) ", e);
		}
	}

	/**
	 * Selecciona un usuario filtrando por su nombre.
	 * @param nombre de usuario.
	 * @return usuario
	 * @exception
	 */
	@Override
	public Usuario getUsuarioPorNombre(String nombreUsuario) {
		try {
			Query query = getSession().createQuery(
					"FROM Usuario WHERE nombreUsuario='"+nombreUsuario+"'");
			return (Usuario) query.list().get(0);
		} catch (Exception e) {
			log.error(" ERROR Exception in UsuarioDao.getUsuarioPorNombre() ", e);
			return null;
		}
	}
	
	/**
	 * Elimina un usuario de la base de datos.
	 * @param usuario.
	 * @exception
	 */
	@Override
	public void borrarUsuario(Usuario usuario) {
		try {
			
			getSession().delete(usuario);
		} catch (Exception e) {
			log.error(" ERROR Exception in UsuarioDao.borrarUsuario(usuario) ", e);
		}
	}

	/**
	 * Selecciona todos los usuario.
	 * @return todos los usuarios.
	 * @exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> getUsuarios() {
		try {
			Query query = getSession().createQuery(
					"FROM Usuario");
			return query.list();
		} catch (Exception e) {
			log.error(" ERROR Exception in UsuarioDao.getUsuarios() ", e);
			return null;
		}
	}
	
	/**
	 * Selecciona un usuario filtrando por su nombre y su contraseña.
	 * @param nombre de usuario.
	 * @param contraseña.
	 * @return usuario
	 * @exception
	 */
	public Usuario getByLoginInformation(String username, String password){
		try {
			Query query = getSession().createQuery(
					"FROM Usuario WHERE nombreUsuario='"+username+" ' and password='"+password+"'");
			return (Usuario) query.list().get(0);
		} catch (Exception e) {
			log.error(" ERROR Exception in UsuarioDao.getByLoginInformation(username,password) ", e);
			return null;
		}
	}
	
	/**
	 * Actualiza la información de un usuario en la base de datos.
	 * @param usuario.
	 * @exception
	 */
	public void updateUsuario(Usuario usuario){
		try {
			getSession().update(usuario);
		} catch (Exception e) {
			log.error(" ERROR Exception in UsuarioDao.updateUsuario(usuario) ", e);
		}
	}
	
	@Autowired
    private SessionFactory sessionFactory;
   
    private Session openSession() {
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings("unchecked")
	public Usuario getUser(String login) {
        List<Usuario> userList = new ArrayList<Usuario>();
        Query query = openSession().createQuery("from Usuario u where u.nombreUsuario = :login");
        query.setParameter("login", login);
        userList = query.list();
        if (userList.size() > 0)
            return userList.get(0);
        else
            return null;   
    } 

}
