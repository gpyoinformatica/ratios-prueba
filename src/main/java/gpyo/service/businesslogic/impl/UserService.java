package gpyo.service.businesslogic.impl;


import java.util.ArrayList;
import java.util.List;

import gpyo.persistence.dao.IUsuarioDao;
import gpyo.persistence.entity.admin.Usuario;
import gpyo.service.businesslogic.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio para conectar con el DAO de usuarios.
 * @author Alberto Revilla Gil.
 * @version 1.0
 */
@Service
public class UserService implements IUserService{

	@Autowired
	private IUsuarioDao usuarioDao;
	private List<Usuario> users=new ArrayList<Usuario>();
	
	@Transactional
	public void createUser(Usuario user) {
		usuarioDao.saveEntity(user);
	}
	
	@Transactional
	public void deleteUser(Usuario user){
		usuarioDao.borrarUsuario(user);
	}

	@Transactional
	public void updateUsuario(Usuario usuario){
		usuarioDao.updateUsuario(usuario);
	}

	public List<Usuario> getUsers() {
		return users;
	}

	public void setUsers(List<Usuario> users) {
		this.users = users;
	}

	@Transactional
	public Usuario getUserByName(String userName){
		return usuarioDao.getUsuarioPorNombre(userName);
	}

	@Transactional
	public List<Usuario> allUsers(){
		users=usuarioDao.getUsuarios();
		return users;
	}


	    public Usuario getUser(String login) {
	        return usuarioDao.getUser(login);
	    } 

	/**
	 * @return the usuarioDao
	 */
	public IUsuarioDao getUsuarioDao() {
		return usuarioDao;
	}

	/**
	 * @param usuarioDao the usuarioDao to set
	 */
	public void setUsuarioDao(IUsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}


}