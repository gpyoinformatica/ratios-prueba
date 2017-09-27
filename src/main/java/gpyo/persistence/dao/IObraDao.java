package gpyo.persistence.dao;

import java.util.List;

import gpyo.persistence.entity.admin.Obra;
/**
 * Realiza las operaciones básicas del DAO.
 * 
 * @author Alberto Revilla Gil
 *
 * @param <T> Tipo de Objeto con las que se van a realizar las operaciones del DAO. 
 * @param <ID> Identificador del objeto.
 */
public interface IObraDao extends IGenericDao<Obra, Long>{
	
	public void borrarObra(Obra obra);
	public Obra getObra(String nombreObra);
	public Obra getObraPorCodigo(String codigoObra);
	public void insertObra(Obra obra);
	public List<Obra> getObras();
	public List<Obra> getObrasCodigo();
	public List<Obra> getObrasTitulo();
	public void updateObra(Obra obra);
	
	public List<Obra> getObras2(String obra);
	public List<Obra> getObras(String codigo);
	public List<Obra> getObrasPorTitulo(String titulo);
	
}
