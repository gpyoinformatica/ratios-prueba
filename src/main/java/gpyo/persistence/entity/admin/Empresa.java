package gpyo.persistence.entity.admin;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="empresa")
public class Empresa implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8334548099860119088L;
	
	private long idEmpresa;
	private String cif;
	private String nombre;
	private int plazoPago;
	private String metodoPago;
	
	
	public Empresa() {
		super();
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name="id_empresa", unique = true)
	public long getIdEmpresa() {
		return idEmpresa;
	}


	public void setIdEmpresa(long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}


	@Column(name="cif")
	public String getCif() {
		return cif;
	}


	public void setCif(String cif) {
		this.cif = cif.toUpperCase();
	}


	@Column(name="nombre")
	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	@Column(name="plazo_pago")
	public int getPlazoPago() {
		return plazoPago;
	}


	public void setPlazoPago(int plazoPago) {
		this.plazoPago = plazoPago;
	}


	@Column(name="metodo_pago")
	public String getMetodoPago() {
		return metodoPago;
	}


	public void setMetodoPago(String metodoPago) {
		this.metodoPago = metodoPago;
	}
	
	
	
}