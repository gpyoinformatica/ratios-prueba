package gpyo.persistence.dao;

import gpyo.persistence.entity.admin.ObraDeUsuario;

import java.util.List;

/**
 * Realiza las operaciones básicas del DAO.
 * 
 * @author Alberto Revilla Gil
 *
 * @param <T> Tipo de Objeto con las que se van a realizar las operaciones del DAO. 
 * @param <ID> Identificador del objeto.
 */
public interface IObraDeUsuarioDao extends IGenericDao<ObraDeUsuario, Long>{
	
	public void borrarObra(ObraDeUsuario obra);
	public ObraDeUsuario getObra(String nombreObra);
	public List<ObraDeUsuario> getObras(long idUsuario, String fecha);
	public List<ObraDeUsuario> getObrasPorFecha(String fecha);
	public List<ObraDeUsuario> getObrasPorNombre(String nombre);
	public List<ObraDeUsuario> getObrasPorMes(int mes);
	public List<ObraDeUsuario> getObrasPorMesObra(int mes, String nombreObra);
	public List<ObraDeUsuario> getObrasPorUsuarioObra(String usuario, String nombreObra);
	public List<ObraDeUsuario> getObrasPorUsuarioMes(String usuario, int code);
	public List<ObraDeUsuario> getObrasPorUsuarioMesObra(String usuario, int code, String nombreObra);
	public List<ObraDeUsuario> getObrasPorCodigo(String codigo);
	public List<ObraDeUsuario> getObrasPorUsuario(String usuario);
	public List<ObraDeUsuario> getObras(long idUsuario, String fecha, String obra);
	public List<ObraDeUsuario> getObras2(long idUsuario, String obra);
	public List<ObraDeUsuario> getObrasMes(long idUsuario, int code);
	public List<ObraDeUsuario> getObrasMesObra(long idUsuario, int code, String obra);
	public void insertObraDeUsuario(ObraDeUsuario obra);
	public void deleteObraUsuario(ObraDeUsuario obra);
	public void updateRatio(ObraDeUsuario obraNueva);
	public List<ObraDeUsuario> getTodasObras(long idUsuario);
	public List<ObraDeUsuario> getObrasDeUsuario();
	public List<ObraDeUsuario> getObrasDeUsuarioPorTitulo(String titulo);
	public List<ObraDeUsuario> getObrasPorYear(int yearFilter);
	public List<ObraDeUsuario> getObrasPorYearUsuario(int yearFilter, String idUsuario);
	public List<ObraDeUsuario> getObrasPorYearObraUsuario(int yearFilter, String idUsuario, String obra);
	public List<ObraDeUsuario> getObrasPorYearObra(int yearFilter, String nombreObraFilter);
	public double horasDia(long idUsuario, String fecha);
	public List<ObraDeUsuario> getObrasPorUsuarioYearMesObra(String usuario, int code, int year);
}