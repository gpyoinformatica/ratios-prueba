package gpyo.persistence.entity.admin;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="banco")
public class Banco implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 162890080486653711L;
	private long idBanco;
	private String nombre;
	private float fondos;
	
	public Banco(){
		
	}
	
	/**
	 * @return the idBanco
	 */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name="id_banco", unique = true)
	public long getIdBanco() {
		return idBanco;
	}

	/**
	 * @param idBanco the idBanco to set
	 */
	public void setIdBanco(long idBanco) {
		this.idBanco = idBanco;
	}
	@Column(name="nombre")
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	@Column(name="fondos")
	public float getFondos() {
		return fondos;
	}
	public void setFondos(float fondos) {
		this.fondos = fondos;
	}

	

}
