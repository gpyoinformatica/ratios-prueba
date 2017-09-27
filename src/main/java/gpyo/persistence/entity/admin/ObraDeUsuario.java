package gpyo.persistence.entity.admin;


import java.io.Serializable;

import javax.persistence.AssociationOverrides;
import javax.persistence.AssociationOverride;
import javax.persistence.JoinColumn;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Table;
import javax.persistence.Transient;

import javax.persistence.Entity;

/**
 * POJO de la relación entre obra y usuario.
 * @author Alberto Revilla Gil
 * @version 1.0
 *
 */
@Entity
@Table(name="obra_usuario")
@AssociationOverrides({
	@AssociationOverride(name = "idObraDeUsuario.obra", 
		joinColumns = @JoinColumn(name = "id_obra")),
	@AssociationOverride(name = "idObraDeUsuario.usuario", 
		joinColumns = @JoinColumn(name = "id")) })
public class ObraDeUsuario implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1479980078115360686L;

	/**
	 * Identificador de la relación entre obra y usuario.
	 */
	private IdObraDeUsuario idObraDeUsuario;
	
	/**
	 * Horas técnicas utilizadas.
	 */
	private float horasTecnico;
	/**
	 * Horas de administración utilizadas.
	 */
	private float horasAdmin;
	/**
	 * Indica si se ha relizado alguna visita.
	 */
	private String visita;
	/**
	 * Fecha del ratio.
	 */
	private String fecha;
	/**
	 * Fecha en formato para ordenar el ratio.
	 */
	private String fechaSort;
	/**
	 * Tarea realizada por el usuario.
	 */
	private String tarea;
	
	public ObraDeUsuario() {
		super();
	}

	public ObraDeUsuario(IdObraDeUsuario idObraDeUsuario, long idUsuario, long idObra,
			float horasTecnico, float horasAdmin, String fecha, String tarea) {
		super();
		this.setIdObraDeUsuario(idObraDeUsuario);
		this.horasTecnico = horasTecnico;
		this.horasAdmin = horasAdmin;
		this.fecha = fecha;
		this.tarea = tarea;
	}
	
	@Column(name="horas_tecnico", nullable = true)
	public float getHorasTecnico() {
		return horasTecnico;
	}

	public void setHorasTecnico(float horasTecnico) {
		this.horasTecnico = horasTecnico;
	}
	
	@Column(name="horas_admin", nullable = true)
	public float getHorasAdmin() {
		return horasAdmin;
	}

	public void setHorasAdmin(float horasAdmin) {
		this.horasAdmin = horasAdmin;
	}
	
	@Column(name="fecha", nullable = true)
	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	@Column(name="tarea", nullable = true)
	public String getTarea() {
		return tarea;
	}

	public void setTarea(String tarea) {
		this.tarea = tarea;
	}


	/**
	 * @return the idObraDeUsuario
	 */
	@EmbeddedId
	public IdObraDeUsuario getIdObraDeUsuario() {
		return idObraDeUsuario;
	}

	/**
	 * @param idObraDeUsuario the idObraDeUsuario to set
	 */
	public void setIdObraDeUsuario(IdObraDeUsuario idObraDeUsuario) {
		this.idObraDeUsuario = idObraDeUsuario;
	}
	
	@Transient
	public Obra getObra() {
		return getIdObraDeUsuario().getObra();
	}
 
	public void setObra(Obra obra) {
		getIdObraDeUsuario().setObra(obra);
	}
	
	@Transient
	public Usuario getUsuario() {
		return getIdObraDeUsuario().getUsuario();
	}
 
	public void setUsuario(Usuario usuario) {
		getIdObraDeUsuario().setUsuario(usuario);
	}
	
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
 
		ObraDeUsuario that = (ObraDeUsuario) o;
 
		if (getIdObraDeUsuario() != null ? !getIdObraDeUsuario().equals(that.getIdObraDeUsuario())
				: that.getIdObraDeUsuario() != null)
			return false;
 
		return true;
	}
 
	public int hashCode() {
		return (getIdObraDeUsuario() != null ? getIdObraDeUsuario().hashCode() : 0);
	}

	/**
	 * @return the fecha_sort
	 */
	@Column(name="fecha_sort", nullable = true)
	public String getFechaSort() {
		return fechaSort;
	}

	/**
	 * @param fecha_sort the fecha_sort to set
	 */
	public void setFechaSort(String fechaSort) {
		this.fechaSort = fechaSort;
	}

	/**
	 * @return the visita
	 */
	@Column(name="visita")
	public String getVisita() {
		return visita;
	}

	/**
	 * @param visita the visita to set
	 */
	public void setVisita(String visita) {
		this.visita = visita;
	}

	
}
