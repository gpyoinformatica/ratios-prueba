package gpyo.persistence.entity.admin;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="ingreso")
public class Ingreso implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1926340582426622477L;
	
	private long idIngreso;
	private float ingreso;
	private float baseImponible;
	private String operacion;
	private String fecha;
	private String fechaSort;
	private float iva;
	private float irpfSp;
	private float irpfAlquiler;
	private Obra obra;
	private Usuario usuario;
	private String nFactura;
	private String cif;
	private String cliente;
	private float totalIngreso;
	private String estado;
	private float pendiente;
	private String fechaCobro;
	private String fechaCobroSort;
	private float perIva;
	private float perIrpfSp;
	private float perIrpfAlquiler;
	private String prevision;
	private String banco;
	private String fechaVencimiento;
	
	
	
	
	public Ingreso() {
		super();
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name="id_ingreso", unique = true)
	public long getIdIngreso() {
		return idIngreso;
	}


	public void setIdIngreso(long idIngreso) {
		this.idIngreso = idIngreso;
	}


	/**
	 * @return the usuario
	 */
	@ManyToOne
	@JoinColumn(name = "id", nullable = false)
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the fecha
	 */
	@Column(name="fecha")
	public String getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the fechaSort
	 */
	@Column(name="fecha_sort")
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
	 * @return the iva
	 */
	@Column(name="iva")
	public float getIva() {
		return iva;
	}

	/**
	 * @param iva the iva to set
	 */
	public void setIva(float iva) {
		this.iva = iva;
	}

	@ManyToOne
	@JoinColumn(name = "id_obra", nullable = false)
	public Obra getObra() {
		return obra;
	}

	public void setObra(Obra obra) {
		this.obra = obra;
	}

	/**
	 * @return the irpfSp
	 */
	@Column(name="irpf_sp")
	public float getIrpfSp() {
		return irpfSp;
	}

	/**
	 * @param irpfSp the irpfSp to set
	 */
	public void setIrpfSp(float irpfSp) {
		this.irpfSp = irpfSp;
	}

	/**
	 * @return the irpfAlquiler
	 */
	@Column(name="irpf_alquiler")
	public float getIrpfAlquiler() {
		return irpfAlquiler;
	}

	/**
	 * @param irpfAlquiler the irpfAlquiler to set
	 */
	public void setIrpfAlquiler(float irpfAlquiler) {
		this.irpfAlquiler = irpfAlquiler;
	}

	/**
	 * @return the nFactura
	 */
	@Column(name="n_factura")
	public String getnFactura() {
		return nFactura;
	}

	/**
	 * @param nFactura the nFactura to set
	 */
	public void setnFactura(String nFactura) {
		this.nFactura = nFactura;
	}

	/**
	 * @return the cif
	 */
	@Column(name="cif")
	public String getCif() {
		return cif;
	}

	/**
	 * @param cif the cif to set
	 */
	public void setCif(String cif) {
		this.cif = cif;
	}

	/**
	 * @return the ingreso
	 */
	@Column(name="ingreso")
	public float getIngreso() {
		return ingreso;
	}

	/**
	 * @param ingreso the ingreso to set
	 */
	public void setIngreso(float ingreso) {
		this.ingreso = ingreso;
	}

	/**
	 * @return the baseImponible
	 */
	@Column(name="base_imponible")
	public float getBaseImponible() {
		return baseImponible;
	}

	/**
	 * @param baseImponible the baseImponible to set
	 */
	public void setBaseImponible(float baseImponible) {
		this.baseImponible = baseImponible;
	}

	/**
	 * @return the cliente
	 */
	@Column(name="cliente")
	public String getCliente() {
		return cliente;
	}

	/**
	 * @param cliente the cliente to set
	 */
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	/**
	 * @return the operacion
	 */
	@Column(name="operacion")
	public String getOperacion() {
		return operacion;
	}

	/**
	 * @param operacion the operacion to set
	 */
	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	@Column(name="total_ingreso")
	public float getTotalIngreso() {
		return totalIngreso;
	}

	public void setTotalIngreso(float totalIngreso) {
		this.totalIngreso = totalIngreso;
	}

	@Column(name="estado")
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Column(name="pendiente")
	public float getPendiente() {
		return pendiente;
	}

	public void setPendiente(float pendiente) {
		this.pendiente = pendiente;
	}

	@Column(name="fecha_cobro")
	public String getFechaCobro() {
		return fechaCobro;
	}

	public void setFechaCobro(String fechaCobro) {
		this.fechaCobro = fechaCobro;
	}

	@Column(name="fecha_cobro_sort")
	public String getFechaCobroSort() {
		return fechaCobroSort;
	}

	public void setFechaCobroSort(String fechaCobroSort) {
		this.fechaCobroSort = fechaCobroSort;
	}

	@Column(name="per_iva")
	public float getPerIva() {
		return perIva;
	}

	public void setPerIva(float perIva) {
		this.perIva = perIva;
	}

	@Column(name="per_irpf_sp")
	public float getPerIrpfSp() {
		return perIrpfSp;
	}

	public void setPerIrpfSp(float perIrpfSp) {
		this.perIrpfSp = perIrpfSp;
	}

	@Column(name="per_irpf_alquiler")
	public float getPerIrpfAlquiler() {
		return perIrpfAlquiler;
	}

	public void setPerIrpfAlquiler(float perIrpfAlquiler) {
		this.perIrpfAlquiler = perIrpfAlquiler;
	}

	/**
	 * @return the prevision
	 */
	@Column(name="prevision")
	public String getPrevision() {
		return prevision;
	}

	/**
	 * @param prevision the prevision to set
	 */
	public void setPrevision(String prevision) {
		this.prevision = prevision;
	}

	/**
	 * @return the banco
	 */
	@Column(name="banco")
	public String getBanco() {
		return banco;
	}

	/**
	 * @param banco the banco to set
	 */
	public void setBanco(String banco) {
		this.banco = banco;
	}

	/**
	 * @return the fechaVencimiento
	 */
	@Column(name="fecha_vencimiento")
	public String getFechaVencimiento() {
		return fechaVencimiento;
	}

	/**
	 * @param fechaVencimiento the fechaVencimiento to set
	 */
	public void setFechaVencimiento(String fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

}
