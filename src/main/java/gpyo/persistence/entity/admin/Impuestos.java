package gpyo.persistence.entity.admin;

import java.io.Serializable;

public class Impuestos implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7776375933185959403L;
	
	private String titulo;
	private float primerTrimestre;
	private float segundoTrimestre;
	private float tercerTrimestre;
	private float cuartoTrimestre;
	private float total;
	
	public Impuestos() {
	}


	public String getTitulo() {
		return titulo;
	}


	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	public float getPrimerTrimestre() {
		return primerTrimestre;
	}


	public void setPrimerTrimestre(float primerTrimestre) {
		this.primerTrimestre = primerTrimestre;
	}


	public float getSegundoTrimestre() {
		return segundoTrimestre;
	}


	public void setSegundoTrimestre(float segundoTrimestre) {
		this.segundoTrimestre = segundoTrimestre;
	}


	public float getTercerTrimestre() {
		return tercerTrimestre;
	}


	public void setTercerTrimestre(float tercerTrimestre) {
		this.tercerTrimestre = tercerTrimestre;
	}


	/**
	 * @return the cuartoTrimestre
	 */
	public float getCuartoTrimestre() {
		return cuartoTrimestre;
	}


	/**
	 * @param cuartoTrimestre the cuartoTrimestre to set
	 */
	public void setCuartoTrimestre(float cuartoTrimestre) {
		this.cuartoTrimestre = cuartoTrimestre;
	}


	/**
	 * @return the total
	 */
	public float getTotal() {
		return total;
	}


	/**
	 * @param total the total to set
	 */
	public void setTotal(float total) {
		this.total = total;
	}
	

}
