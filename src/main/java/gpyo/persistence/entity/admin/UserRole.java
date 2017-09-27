package gpyo.persistence.entity.admin;


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="user_roles")
public class UserRole implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6058230973463007396L;
	
	private long id_usuario;
	private long id_role;
	
	
	public UserRole(long id_usuario, long id_role) {
		super();
		this.id_usuario = id_usuario;
		this.id_role = id_role;
	}
	
	public long getId_usuario() {
		return id_usuario;
	}
	public void setId_usuario(long id_usuario) {
		this.id_usuario = id_usuario;
	}
	public long getId_role() {
		return id_role;
	}
	public void setId_role(long id_role) {
		this.id_role = id_role;
	}

}
