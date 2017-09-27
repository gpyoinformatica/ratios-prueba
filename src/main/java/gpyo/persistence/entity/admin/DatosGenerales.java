package gpyo.persistence.entity.admin;

import java.io.Serializable;

public class DatosGenerales implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1965939632868590184L;
	
	private String codigoObra;
	private String titulo;
	private String nombreObra;
	private float horasTecnico;
	private float horasAdmin;
	private float horasTecnicoAnt;
	private float horasAdminAnt;
	private float horasTotal;
	private float visitas;
	private float visitasAnt;
	private float horasTecnicoTotal;
	private float horasAdminTotal;
	private float visitasTotal;
	
	public float getHorasTecnicoTotal() {
		return horasTecnicoTotal;
	}


	public void setHorasTecnicoTotal(float horasTecnicoTotal) {
		this.horasTecnicoTotal = horasTecnicoTotal;
	}


	public float getHorasAdminTotal() {
		return horasAdminTotal;
	}


	public void setHorasAdminTotal(float horasAdminTotal) {
		this.horasAdminTotal = horasAdminTotal;
	}


	public float getVisitasTotal() {
		return visitasTotal;
	}


	public void setVisitasTotal(float visitasTotal) {
		this.visitasTotal = visitasTotal;
	}


	public DatosGenerales() {
		super();
	}


	public String getCodigoObra() {
		return codigoObra;
	}


	public void setCodigoObra(String codigoObra) {
		this.codigoObra = codigoObra;
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


	public float getHorasTecnico() {
		return horasTecnico;
	}


	public void setHorasTecnico(float horasTecnico) {
		this.horasTecnico = horasTecnico;
	}


	public float getHorasAdmin() {
		return horasAdmin;
	}


	public void setHorasAdmin(float horasAdmin) {
		this.horasAdmin = horasAdmin;
	}


	public float getHorasTecnicoAnt() {
		return horasTecnicoAnt;
	}


	public void setHorasTecnicoAnt(float horasTecnicoAnt) {
		this.horasTecnicoAnt = horasTecnicoAnt;
	}


	public float getHorasAdminAnt() {
		return horasAdminAnt;
	}


	public void setHorasAdminAnt(float horasAdminAnt) {
		this.horasAdminAnt = horasAdminAnt;
	}


	public float getHorasTotal() {
		return horasTotal;
	}


	public void setHorasTotal(float horasTotal) {
		this.horasTotal = horasTotal;
	}


	public float getVisitas() {
		return visitas;
	}


	public void setVisitas(float visitas) {
		this.visitas = visitas;
	}


	public float getVisitasAnt() {
		return visitasAnt;
	}


	public void setVisitasAnt(float visitasAnt) {
		this.visitasAnt = visitasAnt;
	}


	
	
	
}
