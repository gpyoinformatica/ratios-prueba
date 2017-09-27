package gpyo.persistence.entity.admin;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

/**
 * Implementa la clave de las obras de usuario.
 * @author Alberto Revilla Gil
 * @version 1.0
 *
 */
@Embeddable
public class IdObraDeUsuario implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 518170488610712003L;
	
	/**
	 * Identificador de la obra de usuario.
	 */
	private long idObraUsuario;
	/**
	 * Obra asociada al usuario.
	 */
	private Obra obra;
	/**
	 * Usuario asociado a la obra.
	 */
	private Usuario usuario;
	
	public IdObraDeUsuario() {
		super();
	}

	

	public IdObraDeUsuario(long idObraUsuario, Obra obra, Usuario usuario) {
		super();
		this.idObraUsuario = idObraUsuario;
		this.obra = obra;
		this.usuario = usuario;
	}



	@ManyToOne
	public Obra getObra() {
		return obra;
	}

	public void setObra(Obra obra) {
		this.obra = obra;
	}

	@ManyToOne
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
 
        IdObraDeUsuario that = (IdObraDeUsuario) o;
 
        if (obra != null ? !obra.equals(that.obra) : that.obra != null) return false;
        if (usuario != null ? !usuario.equals(that.usuario) : that.usuario != null)
            return false;
 
        return true;
    }
 
    public int hashCode() {
        int result;
        result = (obra != null ? obra.hashCode() : 0);
        result = 31 * result + (usuario != null ? usuario.hashCode() : 0);
        return result;
    }

	/**
	 * @return the idObraUsuario
	 */
    @Column(name="id_obra_usuario")
	public long getIdObraUsuario() {
		return idObraUsuario;
	}

	/**
	 * @param idObraUsuario the idObraUsuario to set
	 */
	public void setIdObraUsuario(long idObraUsuario) {
		this.idObraUsuario = idObraUsuario;
	}
	
}
