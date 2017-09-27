package gpyo.persistence.entity.admin;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Entity;


/**
 * POJO de la clase Usuario.
 * @author Alberto Revilla Gil
 * @version 1.0
 *
 */
@Entity
@Table(name="usuario")
public class Usuario implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Identificador del usuario.
	 */
	private long id;
	/**
	 * Nombre de usuario.
	 */
	private String nombreUsuario;
	/**
	 * Nombre del usuario.
	 */
	private String nombre;
	/**
	 * Apellidos del usuario.
	 */
	private String apellido;
	/**
	 * Contraseña del usuario.
	 */
	private String password;
	/**
	 * Rol del usuario.
	 */
	private String role1;
	/**
	 * Horas técnicas.
	 */
	private float horasTecnicasObra=0;
	/**
	 * Horas de administración.
	 */
	private float horasAdminObra=0;
	/**
	 * Horas de visita.
	 */
	private float visita=0;
	/**
	 * Horas totales.
	 */
	private float horasTotal=0;
	
	private String lastAccess;
	/**
	 * Set para relacionar al usuario con la obra.
	 */
	private Set<ObraDeUsuario> obraDeUsuario = new HashSet<ObraDeUsuario>(0);
	

    private Role role; 
    
	private boolean activo;
    
    private String mail;
	
	
	public Usuario(){
		
	}

	public Usuario(String nombreUsuario, String apellido, String nombre) {
		super();
		this.setNombreUsuario(nombreUsuario);
		this.setApellido(apellido);
		this.setNombre(nombre);
	}

	/**
	 * @return the idUsuario
	 */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name="id", unique = true, nullable = false)
	public long getId() {
		return id;
	}

	/**
	 * @param idUsuario the idUsuario to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the nombreUsuario
	 */
	@Column(name="nombre_usuario", nullable = false)
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	/**
	 * @param nombreUsuario the nombreUsuario to set
	 */
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	/**
	 * @return the apellidoUsuario
	 */
	@Column(name="apellido")
	public String getApellido() {
		return apellido;
	}

	/**
	 * @param apellidoUsuario the apellidoUsuario to set
	 */
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	/**
	 * @return the obraDeUsuarios
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "idObraDeUsuario.usuario", cascade=CascadeType.ALL)
	public Set<ObraDeUsuario> getObraDeUsuario() {
		return obraDeUsuario;
	}

	/**
	 * @param obraDeUsuarios the obraDeUsuarios to set
	 */
	public void setObraDeUsuario(Set<ObraDeUsuario> obraDeUsuario) {
		this.obraDeUsuario = obraDeUsuario;
	}

	/**
	 * @return the password
	 */
	@Column(name="password", nullable = false)
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the role
	 */
	@Column(name="role", nullable = false)
	public String getRole1() {
		return role1;
	}

	/**
	 * @param role the role to set
	*/
	public void setRole1(String role1) {
		this.role1 = role1;
	}

	/**
	 * @return the nombre
	 */
	@Column(name="nombre", nullable=false)
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the horasTecnicasObra
	 */
	@Column(name="horas_tecnicas", nullable = true)
	public float getHorasTecnicasObra() {
		return horasTecnicasObra;
	}

	/**
	 * @param horasTecnicasObra the horasTecnicasObra to set
	 */
	public void setHorasTecnicasObra(float horasTecnicasObra) {
		this.horasTecnicasObra = horasTecnicasObra;
	}

	/**
	 * @return the horasAdminObra
	 */
	@Column(name="horas_admin", nullable = true)
	public float getHorasAdminObra() {
		return horasAdminObra;
	}

	/**
	 * @param horasAdminObra the horasAdminObra to set
	 */
	public void setHorasAdminObra(float horasAdminObra) {
		this.horasAdminObra = horasAdminObra;
	}

	/**
	 * @return the horasTotal
	 */
	@Column(name="horas_total", nullable = true)
	public float getHorasTotal() {
		return horasTotal;
	}

	/**
	 * @param horasTotal the horasTotal to set
	 */
	public void setHorasTotal(float horasTotal) {
		this.horasTotal = horasTotal;
	}

	/**
	 * @return the visita
	 */
	@Column(name="visita")
	public float getVisita() {
		return visita;
	}

	/**
	 * @param visita the visita to set
	 */
	public void setVisita(float visita) {
		this.visita = visita;
	}

	/**
	 * @return the lastAccess
	 */
	@Column(name="last_access")
	public String getLastAccess() {
		return lastAccess;
	}

	/**
	 * @param lastAccess the lastAccess to set
	 */
	public void setLastAccess(String lastAccess) {
		this.lastAccess = lastAccess;
	}
	
	@OneToOne(cascade=CascadeType.ALL)
    @JoinTable(name="user_roles",
        joinColumns = {@JoinColumn(name="user_id", referencedColumnName="id")},
        inverseJoinColumns = {@JoinColumn(name="role_id", referencedColumnName="id")}
    )
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * @return the activo
	 */
	@Column(name="activo")
	public boolean isActivo() {
		return activo;
	}

	/**
	 * @param activo the activo to set
	 */
	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	/**
	 * @return the mail
	 */
	@Column(name="mail")
	public String getMail() {
		return mail;
	}

	/**
	 * @param mail the mail to set
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

}
