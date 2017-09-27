package gpyo.persistence.entity.admin;

import java.io.Serializable;

public class CheckRatios implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3967316899902230131L;
	
	private String fecha;
	private String user;
	private float horas;
	private float horasFaltantes;
	private String dia;
	
	
	public CheckRatios() {
		super();
	}
	
	
	
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public float getHoras() {
		return horas;
	}
	public void setHoras(float horas) {
		this.horas = horas;
	}
	public float getHorasFaltantes() {
		return horasFaltantes;
	}
	public void setHorasFaltantes(float horasFaltantes) {
		this.horasFaltantes = horasFaltantes;
	}
	public String getDia() {
		return dia;
	}
	public void setDia(String dia) {
		this.dia = dia;
	}



	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}



	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}
	
}
