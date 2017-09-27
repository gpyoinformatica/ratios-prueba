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
@Table(name="gasto")
public class Gasto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1926340582426622477L;
	
	private long idGasto;
	private float cantidad;
	private String concepto;
	//private String titulo;
	private String categoria;
	private String fecha;
	private String fechaSort;
	private float iva;
	private float iva2;
	private float irpfSp;
	private float irpfAlquiler;
	private Usuario usuario;
	private Obra obra;
	private String cif;
	private String empresa;
	private String factura;
	private float baseImponible;
	private float pendiente;
	private String estado;
	private float pagado;
	private String fechaPago;
	private String fechaPagoSort;
	private float perCosteDirecto;
	private float perCosteIndirecto;
	private String tipoCosteEstructural;
	private String vencimiento;
	private String cat;
	private String centroCoste;
	private String formaPago;
	private String banco;
	private float perIva;
	private float perIva2;
	private float perIrpfSp;
	private float perIrpfAlquiler;
	private String prevision;
	private String fechaRecepcionFactura;
	
	
	public Gasto() {
		super();
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name="id_gasto", unique = true)
	public long getIdGasto() {
		return idGasto;
	}

	public void setIdGasto(long idGasto) {
		this.idGasto = idGasto;
	}

	@Column(name="cantidad")
	public float getCantidad() {
		return cantidad;
	}

	public void setCantidad(float cantidad) {
		this.cantidad = cantidad;
	}

	@Column(name="concepto")
	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	/*@Column(name="titulo")
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}*/

	@Column(name="categoria")
	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
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

	/**
	 * @return the tituloGasto
	 */
	@ManyToOne
	@JoinColumn(name = "id_obra", nullable = false)
	public Obra getObra() {
		return obra;
	}

	/**
	 * @param tituloGasto the tituloGasto to set
	 */
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

	@Column(name="cif")
	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	@Column(name="empresa")
	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	@Column(name="factura")
	public String getFactura() {
		return factura;
	}

	public void setFactura(String factura) {
		this.factura = factura;
	}

	@Column(name="base_imponible")
	public float getBaseImponible() {
		return baseImponible;
	}

	public void setBaseImponible(float baseImponible) {
		this.baseImponible = baseImponible;
	}

	@Column(name="pendiente")
	public float getPendiente() {
		return pendiente;
	}

	public void setPendiente(float pendiente) {
		this.pendiente = pendiente;
	}

	@Column(name="estado")
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Column(name="pagado")
	public float getPagado() {
		return pagado;
	}

	public void setPagado(float pagado) {
		this.pagado = pagado;
	}

	@Column(name="fecha_pago")
	public String getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(String fechaPago) {
		this.fechaPago = fechaPago;
	}

	@Column(name="fecha_pago_sort")
	public String getFechaPagoSort() {
		return fechaPagoSort;
	}

	public void setFechaPagoSort(String fechaPagoSort) {
		this.fechaPagoSort = fechaPagoSort;
	}

	@Column(name="per_coste_directo")
	public float getPerCosteDirecto() {
		return perCosteDirecto;
	}

	public void setPerCosteDirecto(float perCosteDirecto) {
		this.perCosteDirecto = perCosteDirecto;
	}

	@Column(name="per_coste_indirecto")
	public float getPerCosteIndirecto() {
		return perCosteIndirecto;
	}

	public void setPerCosteIndirecto(float perCosteIndirecto) {
		this.perCosteIndirecto = perCosteIndirecto;
	}

	@Column(name="tipo_coste_estructural")
	public String getTipoCosteEstructural() {
		return tipoCosteEstructural;
	}

	public void setTipoCosteEstructural(String tipoCosteEstructural) {
		this.tipoCosteEstructural = tipoCosteEstructural;
	}

	/**
	 * @return the vencimiento
	 */
	@Column(name="vencimiento")
	public String getVencimiento() {
		return vencimiento;
	}

	/**
	 * @param vencimiento the vencimiento to set
	 */
	public void setVencimiento(String vencimiento) {
		this.vencimiento = vencimiento;
	}

	/**
	 * @return the cat
	 */
	@Column(name="cat")
	public String getCat() {
		return cat;
	}

	/**
	 * @param cat the cat to set
	 */
	public void setCat(String cat) {
		this.cat = cat;
	}

	@Column(name="centro_coste")
	public String getCentroCoste() {
		return centroCoste;
	}

	public void setCentroCoste(String centroCoste) {
		this.centroCoste = centroCoste;
	}

	@Column(name="forma_pago")
	public String getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}

	@Column(name="banco")
	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
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
	 * @return the iva2
	 */
	@Column(name="iva2")
	public float getIva2() {
		return iva2;
	}

	/**
	 * @param iva2 the iva2 to set
	 */
	public void setIva2(float iva2) {
		this.iva2 = iva2;
	}

	/**
	 * @return the perIva2
	 */
	@Column(name="per_iva2")
	public float getPerIva2() {
		return perIva2;
	}

	/**
	 * @param perIva2 the perIva2 to set
	 */
	public void setPerIva2(float perIva2) {
		this.perIva2 = perIva2;
	}

	/**
	 * @return the fechaRecepcionFactura
	 */
	@Column(name="fecha_recepcion_factura")
	public String getFechaRecepcionFactura() {
		return fechaRecepcionFactura;
	}

	/**
	 * @param fechaRecepcionFactura the fechaRecepcionFactura to set
	 */
	public void setFechaRecepcionFactura(String fechaRecepcionFactura) {
		this.fechaRecepcionFactura = fechaRecepcionFactura;
	}


	
}
