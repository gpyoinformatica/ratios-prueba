package gpyo.persistence.dao;

import java.util.List;

import gpyo.persistence.entity.admin.Usuario;
/**
 * Realiza las operaciones básicas del DAO.
 * 
 * @author Alberto Revilla Gil
 *
 * @param <T> Tipo de Objeto con las que se van a realizar las operaciones del DAO. 
 * @param <ID> Identificador del objeto.
 */
public interface IUsuarioDao extends IGenericDao<Usuario, Long>{
	
	public void insertUsuario(Usuario usuario);
	public Usuario getUsuarioPorNombre(String nombreUsuario);
	public void borrarUsuario(Usuario usuario);
	public void updateUsuario(Usuario usuario);
	public List<Usuario> getUsuarios();
	public Usuario getByLoginInformation(String username, String password);
	public Usuario getUser(String login); 
}
