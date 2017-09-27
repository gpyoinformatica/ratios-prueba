package gpyo.gui.model;

import java.io.Serializable;

import gpyo.persistence.entity.admin.Usuario;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * Bean que controla las sesiones de los usuarios.
 * 
 * @author Alberto Revilla
 * @version 1.0
 */
@Component
@Scope("session")
public class SessionBean implements Serializable {

	private static final long serialVersionUID = -2477891600149910534L;
	private String mandant = "default";
	private Usuario user;

	public SessionBean() { }


	public String getMandant() {
        return mandant;
	}

	public void setMandant(String mandant) {
		this.mandant = mandant;		
	}
	
	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}
	
	public boolean isLoggedIn(){
		return user != null;
	}

}