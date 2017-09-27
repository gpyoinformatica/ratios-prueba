package gpyo.persistence.entity.admin;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import static javax.persistence.GenerationType.IDENTITY;

/**
 * POJO del objeto Obra. Se relaciona con un usuario por medio de la clase ObraDeUsuario y ObraDeUsuarioHistorica.
 * @author Alberto Revilla Gil
 * @version 1.0
 *
 */
@Entity
@Table(name="obra")
public class Obra implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Identificador de la obra.
	 */
	private long idObra;
	/**
	 * Código de la obra.
	 */
	private String codigoObra;
	/**
	 * Nombre de la obra.
	 */
	private String nombreObra;
	/**
	 * Título de la obra.
	 */
	private String titulo;
	/**
	 * Set para asociar la obra con los usuarios.
	 */
	private Set<ObraDeUsuario> obraDeUsuario = new HashSet<ObraDeUsuario>(0);
	
	private Set<Gasto> gasto = new HashSet<Gasto>(0);
	
/*	private Set<GastoAcumulado> gastoAcumulado = new HashSet<GastoAcumulado>(0);
	private Set<IngresoAcumulado> ingresoAcumulado = new HashSet<IngresoAcumulado>(0);*/
//	private Set<TesoreriaTitulo> tesoreriaTitulo = new HashSet<TesoreriaTitulo>(0);
	
	/**
	 * Sumatorio de las horas empleadas en la obra.
	 */
	private float horasSum;
	/**
	 * Sumatorio de las horas técnicas empleadas en la obra.
	 */
	private float horasTecnicoSum;
	/**
	 * Sumatorio de las horas de administración empleadas en la obra.
	 */
	private float horasAdminSum;
	/**
	 * Sumatorio de las horas de visita empleadas en la obra.
	 */
	private float horasVisitaSum;
	/**
	 * Previsión de horas inicial para la obra.
	 */
	private float pInicial;
	/**
	 * Previsión de horas previstas para la obra.
	 */
	private float pPrevista;
	/**
	 * Previsión de horas variable de la obra.
	 */
	private float pVariable;
	
	private Float presupuestoSinIVA;
	
	private Float iva;
	/**
	 * Fecha de creación de la obra.
	 */
	private String fecha;
	
	private String fechaFin;
	/**
	 * Fecha de creación de la obra para poder ordenar por fecha correctamente.
	 */
	private String fechaSort;
	
	private boolean activa = true;
	
	public Obra(){
		
	}

	public Obra(String nombreObra) {
		super();
		this.setNombreObra(nombreObra);
	}

	/**
	 * @return the idObra
	 */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name="id_obra", unique = true, nullable = false)
	public long getIdObra() {
		return idObra;
	}

	/**
	 * @param idObra the idObra to set
	 */
	public void setIdObra(long idObra) {
		this.idObra = idObra;
	}

	/**
	 * @return the nombreObra
	 */
	@Column(name="nombre_obra", unique = true, nullable = false, length = 200)
	public String getNombreObra() {
		return nombreObra;
	}

	/**
	 * @param nombreObra the nombreObra to set
	 */
	public void setNombreObra(String nombreObra) {
		this.nombreObra = nombreObra;
	}

	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo the titulo to set
	 */
	@Column(name="titulo", unique = true, nullable = true, length = 100)
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * @return the stockCategories
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "idObraDeUsuario.obra", cascade=CascadeType.ALL)
	public Set<ObraDeUsuario> getObraDeUsuario() {
		return obraDeUsuario;
	}

	/**
	 * @param stockCategories the stockCategories to set
	 */
	public void setObraDeUsuario(Set<ObraDeUsuario> obraDeUsuario) {
		this.obraDeUsuario = obraDeUsuario;
	}

	/**
	 * @return the codigoObra
	 */
	@Column(name="codigo_obra", unique = true, nullable = true)
	public String getCodigoObra() {
		return codigoObra;
	}

	/**
	 * @param codigoObra the codigoObra to set
	 */
	public void setCodigoObra(String codigoObra) {
		this.codigoObra = codigoObra;
	}
	

	/**
	 * @return the horasSum
	 */
	@Column(name="horas_sum", nullable = true)
	public float getHorasSum() {
		return horasSum;
	}

	/**
	 * @param horasSum the horasSum to set
	 */
	public void setHorasSum(float horasSum) {
		this.horasSum = horasSum;
	}

	@Column(name="fecha", nullable = true)
	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the horasTecnicoSum
	 */
	@Column(name="horas_tecnicas_sum", nullable = true)
	public float getHorasTecnicoSum() {
		return horasTecnicoSum;
	}

	/**
	 * @param horasTecnicoSum the horasTecnicoSum to set
	 */
	public void setHorasTecnicoSum(float horasTecnicoSum) {
		this.horasTecnicoSum = horasTecnicoSum;
	}

	/**
	 * @return the horasAdminSum
	 */
	@Column(name="horas_admin_sum", nullable = true)
	public float getHorasAdminSum() {
		return horasAdminSum;
	}

	/**
	 * @param horasAdminSum the horasAdminSum to set
	 */
	public void setHorasAdminSum(float horasAdminSum) {
		this.horasAdminSum = horasAdminSum;
	}

	/**
	 * @return the fechaSort
	 */
	@Column(name="fecha_sort", nullable = true)
	public String getFechaSort() {
		return fechaSort;
	}

	/**
	 * @param fechaSort the fechaSort to set
	 */
	public void setFechaSort(String fechaSort) {
		this.fechaSort = fechaSort;
	}

	/**
	 * @return the horasVisitaSum
	 */
	@Column(name="visita_sum")
	public float getHorasVisitaSum() {
		return horasVisitaSum;
	}

	/**
	 * @param horasVisitaSum the horasVisitaSum to set
	 */
	public void setHorasVisitaSum(float horasVisitaSum) {
		this.horasVisitaSum = horasVisitaSum;
	}

	/**
	 * @return the pInicial
	 */
	@Column(name="prevision_inicial")
	public float getpInicial() {
		return pInicial;
	}

	/**
	 * @param pInicial the pInicial to set
	 */
	public void setpInicial(float pInicial) {
		this.pInicial = pInicial;
	}

	/**
	 * @return the pPrevista
	 */
	@Column(name="prevision_prevista")
	public float getpPrevista() {
		return pPrevista;
	}

	/**
	 * @param pPrevista the pPrevista to set
	 */
	public void setpPrevista(float pPrevista) {
		this.pPrevista = pPrevista;
	}

	/**
	 * @return the pVariable
	 */
	@Column(name="prevision_variable")
	public float getpVariable() {
		return pVariable;
	}

	/**
	 * @param pVariable the pVariable to set
	 */
	public void setpVariable(float pVariable) {
		this.pVariable = pVariable;
	}

	/**
	 * @return the tituloGasto
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "idGasto", cascade=CascadeType.ALL)
	public Set<Gasto> getGasto() {
		return gasto;
	}

	/**
	 * @param tituloGasto the tituloGasto to set
	 */
	public void setGasto(Set<Gasto> gasto) {
		this.gasto = gasto;
	}

	/**
	 * @return the presupuestoSinIVA
	 */
	@Column(name="presupuesto_sin_iva", nullable=true)
	public Float getPresupuestoSinIVA() {
		return presupuestoSinIVA;
	}

	/**
	 * @param presupuestoSinIVA the presupuestoSinIVA to set
	 */
	public void setPresupuestoSinIVA(Float presupuestoSinIVA) {
		this.presupuestoSinIVA = presupuestoSinIVA;
	}

	/**
	 * @return the iva
	 */
	@Column(name="iva", nullable=true)
	public Float getIva() {
		return iva;
	}

	/**
	 * @param iva the iva to set
	 */
	public void setIva(Float iva) {
		this.iva = iva;
	}

	/**
	 * @return the fechaFin
	 */
	@Column(name="fecha_fin", nullable = true)
	public String getFechaFin() {
		return fechaFin;
	}

	/**
	 * @param fechaFin the fechaFin to set
	 */
	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	/**
	 * @return the activa
	 */
	@Column(name="activa")
	public boolean isActiva() {
		return activa;
	}

	/**
	 * @param activa the activa to set
	 */
	public void setActiva(boolean activa) {
		this.activa = activa;
	}

	
}
