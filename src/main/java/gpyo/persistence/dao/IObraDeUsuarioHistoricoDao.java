package gpyo.persistence.dao;

import java.util.List;

import gpyo.persistence.entity.admin.ObraDeUsuarioHistorica;
/**
 * Realiza las operaciones básicas del DAO.
 * 
 * @author Alberto Revilla Gil
 *
 * @param <T> Tipo de Objeto con las que se van a realizar las operaciones del DAO. 
 * @param <ID> Identificador del objeto.
 */
public interface IObraDeUsuarioHistoricoDao extends IGenericDao<ObraDeUsuarioHistorica, Long>{

	public void saveObraHistorico(ObraDeUsuarioHistorica obraHistorica);
	public List<ObraDeUsuarioHistorica> getObrasHistorico();
	public List<ObraDeUsuarioHistorica> getObrasHistoricoPorObra(String nombreObra);
	public List<ObraDeUsuarioHistorica> getObrasHistoricoPorCodigo(String codigoObra);
	public List<ObraDeUsuarioHistorica> getObrasHistoricoPorTitulo(String titulo);
	public List<ObraDeUsuarioHistorica> getObrasHistoricoPorUsuario(String usuarioObra);
	public List<ObraDeUsuarioHistorica> getObrasHistoricoPorYear(String year);
}
