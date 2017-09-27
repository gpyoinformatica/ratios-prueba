package gpyo.persistence.entity.admin;

import java.io.Serializable;

public class InformeObraUsuario implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2323776389083000196L;
	
	private String titulo;
	private String nombreObra;
	private float horasTecnicas;
	private float horasAdmin;
	private int visitas;
	private float porcentaje;
	
	
	public InformeObraUsuario() {
		super();
	}


	public String getTitulo() {
		return titulo;
	}


	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	public String getNombreObra() {
		return nombreObra;
	}


	public void setNombreObra(String nombreObra) {
		this.nombreObra = nombreObra;
	}


	public float getHorasTecnicas() {
		return horasTecnicas;
	}


	public void setHorasTecnicas(float horasTecnicas) {
		this.horasTecnicas = horasTecnicas;
	}


	public float getHorasAdmin() {
		return horasAdmin;
	}


	public void setHorasAdmin(float horasAdmin) {
		this.horasAdmin = horasAdmin;
	}


	public int getVisitas() {
		return visitas;
	}


	public void setVisitas(int visitas) {
		this.visitas = visitas;
	}


	/**
	 * @return the porcentaje
	 */
	public float getPorcentaje() {
		return porcentaje;
	}


	/**
	 * @param porcentaje the porcentaje to set
	 */
	public void setPorcentaje(float porcentaje) {
		this.porcentaje = porcentaje;
	}
	
	

}
