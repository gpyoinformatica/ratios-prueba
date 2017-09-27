package gpyo.service.businesslogic.impl;

import gpyo.persistence.dao.IUsuarioDao;
import gpyo.persistence.entity.admin.Usuario;
import gpyo.service.businesslogic.ILoginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio para el logueo de usuario.
 * @author Alberto Revilla Gil.
 * @version 1.0
 */
@Service
public class LoginService implements ILoginService{

	private IUsuarioDao userDao;

	@Transactional
	public Usuario getByLoginInformation(final String username, final String password) {
		Usuario user = userDao.getByLoginInformation(username, password);
		return user;
	}
	
	@Autowired
	public void setUserDao(IUsuarioDao userDao) {
		this.userDao = userDao;
	}

	@Transactional
	public void createUser(Usuario user) {
		userDao.saveEntity(user);
	}

}