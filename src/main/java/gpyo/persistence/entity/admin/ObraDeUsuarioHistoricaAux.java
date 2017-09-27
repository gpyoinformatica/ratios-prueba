package gpyo.persistence.entity.admin;


import java.io.Serializable;


/**
 * POJO para las obras de usuario históricas.
 * @author Alberto Revilla Gil
 * @version 1.0
 *
 */
public class ObraDeUsuarioHistoricaAux implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6080529829394119847L;
	
	/**
	 * Identificador de las obras de usuario históricas.
	 */
	private long idObraUsuarioHistoricaAux;
	/**
	 * Usuario al que pertenece el ratio.
	 */
	private String usuario;
	/**
	 * Código de la obra asociada al ratio.
	 */
	private String codigoObra;
	/**
	 * Título de la obra asociada al ratio.
	 */
	private String titulo;
	/**
	 * Nombre de la obra asociada al ratio.
	 */
	private String nombreObra;
	/**
	 * Horas técnicas del ratio.
	 */
	private float horasTecnicas;
	/**
	 * Horas de administración del ratio.
	 */
	private float horasAdmin;
	/**
	 * Horas de visita del ratio.
	 */
	private float visita;
	/**
	 * Horas totales empleadas.
	 */
	private float horasTotal;
	/**
	 * Año al que pertenecen los ratios.
	 */
	private String yearRatio;
	
	
	public ObraDeUsuarioHistoricaAux() {
	}

	public String getUsuario() {
		return usuario;
	}


	public void setUsuario(String usuario) {
		this.usuario = usuario;
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

	public float getHorasTotal() {
		return horasTotal;
	}


	public void setHorasTotal(float horasTotal) {
		this.horasTotal = horasTotal;
	}

	/**
	 * @return the yearRatio
	 */
	public String getYearRatio() {
		return yearRatio;
	}

	/**
	 * @param yearRatio the yearRatio to set
	 */
	public void setYearRatio(String yearRatio) {
		this.yearRatio = yearRatio;
	}

	public float getVisita() {
		return visita;
	}

	public void setVisita(float visita) {
		this.visita = visita;
	}

	/**
	 * @return the idObraUsuarioHistoricaAux
	 */
	public long getIdObraUsuarioHistoricaAux() {
		return idObraUsuarioHistoricaAux;
	}

	/**
	 * @param idObraUsuarioHistoricaAux the idObraUsuarioHistoricaAux to set
	 */
	public void setIdObraUsuarioHistoricaAux(long idObraUsuarioHistoricaAux) {
		this.idObraUsuarioHistoricaAux = idObraUsuarioHistoricaAux;
	}
	
	

}
