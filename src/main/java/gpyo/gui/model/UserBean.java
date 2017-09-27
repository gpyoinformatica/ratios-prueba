package gpyo.gui.model;


import gpyo.persistence.entity.admin.Usuario;
import gpyo.service.businesslogic.IUserService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.ploin.web.faces.BaseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Bean que controla el registro de usuarios.
 * 
 * @author Alberto Revilla Gil
 * @version 1.0
 */
@Component
@Scope("session")
public class UserBean extends BaseBean implements Serializable  {
	private static final long serialVersionUID = 7706201854421179190L;
	
	private Usuario user=new Usuario();
	private List<Usuario> allUsers = new ArrayList<Usuario>();
	@Autowired
	private IUserService userService;
	
	private List<String> userNames=new ArrayList<String>();
	
	private String userName;
	
	
	/**
	 * Registra al usuario y lo crea en la BD.
	 * @return
	 */
	public String doRegister(){
		userService.createUser(user);
		return "/page/firstPage";
	}
	

	
	/**
	 * Borra a un usuario de la base de datos.
	 * @return
	 */
	public String deleteUser(){
		userService.deleteUser(user);
		return "/page/firstPage";
	}
	
	
	
	/**
	 * Muestra todos los usuarios existentes.
	 */
	public void showAllUsers(){
		allUsers = userService.allUsers();
	}
	
	/* Setters y getters */
	

	public IUserService getUserService() {
		return userService;
	}
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}

	public List<Usuario> getAllUsers() {
		return allUsers;
	}

	public void setAllUsers(List<Usuario> allUsers) {
		this.allUsers = allUsers;
	}  
	


	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
		this.user=userService.getUserByName(userName);
	}

	/**
	 * @return the userNames
	 */
	public List<String> getUserNames() {
		List<Usuario> users=new ArrayList<Usuario>();
		users=getAllUsers();
		for(int i=0;i<users.size();i++){
			userNames.add(users.get(i).getNombreUsuario());
		}
		return userNames;
	}

	/**
	 * @param userNames the userNames to set
	 */
	public void setUserNames(List<String> userNames) {
		List<Usuario> users=new ArrayList<Usuario>();
		users=getAllUsers();
		for(int i=0;i<users.size();i++){
			userNames.add(users.get(i).getNombreUsuario());
		}
		this.userNames = userNames;
	}





}