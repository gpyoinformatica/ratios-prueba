package gpyo.gui.model;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import gpyo.persistence.entity.admin.Usuario;
import gpyo.service.businesslogic.IUserService;

@ManagedBean
@ViewScoped
@Controller
public class RoleBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -958081803500889325L;
	@Autowired
	private IUserService userService;
	private boolean admin = false;
	private boolean tesoreria = false;
	
	/**
	 * Comprueba el rol del usuario.
	 * @param nombre
	 */
	public void role(String nombre){
		isTesoreria(nombre);
		isAdmin(nombre);
		
	}

	/**
	 * Comprueba si el rol del usuario es el de administrador de la tesorería.
	 * @param nombre
	 * @return
	 */
	public boolean isTesoreria(String nombre) {
		Usuario user = null;
		//String nombre = SecurityContextHolder.getContext().getAuthentication().getName();
		user = userService.getUserByName(nombre);
		try {
			if (user.getRole().getRole().equals("tesoreria"))
				setTesoreria(true);
			else
				setTesoreria(false);
		} catch (NullPointerException e) {
			System.out.println("Error al acceder a la aplicación: " + e);
		}
		return tesoreria;
	}

	/**
	 * Comprueba si el rol del usuario es el de administrador de la aplicación.
	 * @param nombre
	 * @return
	 */
	public boolean isAdmin(String nombre) {
		Usuario user = null;
//		String nombre = SecurityContextHolder.getContext().getAuthentication().getName();
		user = userService.getUserByName(nombre);
		try {
			if (user.getRole().getRole().equals("admin"))
				setAdmin(true);
			else
				setAdmin(false);
		} catch (NullPointerException e) {
			System.out.println("Error al acceder a la aplicación: " + e);
		}
		return admin;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
	public boolean isAdmin(){
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public boolean isTesoreria(){
		return tesoreria;
	}
	
	public void setTesoreria(boolean tesoreria) {
		this.tesoreria = tesoreria;
	}
}
