package gpyo.persistence.entity.admin;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * POJO para las obras de usuario históricas.
 * @author Alberto Revilla Gil
 * @version 1.0
 *
 */
@Entity
@Table(name="obra_usuario_historica")
public class ObraDeUsuarioHistorica implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6080529829394119847L;
	
	/**
	 * Identificador de las obras de usuario históricas.
	 */
	private long idObraUsuarioHistorica;
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
	
	
	public ObraDeUsuarioHistorica() {
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name="id_obra_usuario_historica", unique = true, nullable = false)
	public long getIdObraUsuarioHistorica() {
		return idObraUsuarioHistorica;
	}


	public void setIdObraUsuarioHistorica(long idObraUsuarioHistorica) {
		this.idObraUsuarioHistorica = idObraUsuarioHistorica;
	}

	@Column(name="usuario", nullable = true)
	public String getUsuario() {
		return usuario;
	}


	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	@Column(name="codigo_obra", nullable = true)
	public String getCodigoObra() {
		return codigoObra;
	}


	public void setCodigoObra(String codigoObra) {
		this.codigoObra = codigoObra;
	}

	@Column(name="titulo", nullable = true)
	public String getTitulo() {
		return titulo;
	}


	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	@Column(name="nombre_obra", nullable = true)
	public String getNombreObra() {
		return nombreObra;
	}


	public void setNombreObra(String nombreObra) {
		this.nombreObra = nombreObra;
	}

	@Column(name="horas_tecnicas", nullable = true)
	public float getHorasTecnicas() {
		return horasTecnicas;
	}


	public void setHorasTecnicas(float horasTecnicas) {
		this.horasTecnicas = horasTecnicas;
	}

	@Column(name="horas_admin", nullable = true)
	public float getHorasAdmin() {
		return horasAdmin;
	}


	public void setHorasAdmin(float horasAdmin) {
		this.horasAdmin = horasAdmin;
	}

	@Column(name="horas_total", nullable = true)
	public float getHorasTotal() {
		return horasTotal;
	}


	public void setHorasTotal(float horasTotal) {
		this.horasTotal = horasTotal;
	}

	/**
	 * @return the yearRatio
	 */
	@Column(name="year_ratio", nullable=true)
	public String getYearRatio() {
		return yearRatio;
	}

	/**
	 * @param yearRatio the yearRatio to set
	 */
	public void setYearRatio(String yearRatio) {
		this.yearRatio = yearRatio;
	}

	@Column(name="visita")
	public float getVisita() {
		return visita;
	}

	public void setVisita(float visita) {
		this.visita = visita;
	}
	
	

}
