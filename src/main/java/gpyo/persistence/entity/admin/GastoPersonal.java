package gpyo.persistence.entity.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GastoPersonal implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 833831810815945225L;
	
	private Usuario usuario;
	private String year;
	private List<Float> horasMes = new ArrayList<Float>();
	
	public GastoPersonal() {
		super();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Float> getHorasMes() {
		return horasMes;
	}

	public void setHorasMes(List<Float> horasMes) {
		this.horasMes = horasMes;
	}

	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}

}
