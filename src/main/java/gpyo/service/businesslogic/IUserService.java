package gpyo.service.businesslogic;

import java.util.List;

import gpyo.persistence.entity.admin.Usuario;

/**
 * Interfaz para conectar con el DAO que gestiona a los usuarios.
 * @author Alberto Revilla Gil
 * @version 1.0
 */
public interface IUserService {
	public void createUser(Usuario user);
	public void deleteUser(Usuario user);
	public void updateUsuario(Usuario usuario);
	public List<Usuario> allUsers();
	public Usuario getUserByName(String userName);
	 public Usuario getUser(String login); 
}