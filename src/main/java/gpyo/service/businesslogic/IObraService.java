package gpyo.service.businesslogic;

import gpyo.persistence.entity.admin.Obra;
import gpyo.persistence.entity.admin.ObraDeUsuario;
import gpyo.persistence.entity.admin.ObraDeUsuarioHistorica;

import java.util.List;

/**
 * Interfaz para la conexión con el DAO de obras, obras de usuario y obras de usuario históricas.
 * @author Alberto Revilla Gil
 * @version 1.0
 *
 */
public interface IObraService {

	public List<Obra> getObras();
	public List<Obra> getObrasCodigo();
	public List<Obra> getObrasTitulo();
	public List<ObraDeUsuario> getObrasDeUsuario(String fecha);
	public List<ObraDeUsuario> getObrasPorFecha(String fecha);
	public List<ObraDeUsuario> getObrasPorNombre(String nombre);
	public List<ObraDeUsuario> getObrasPorCodigo(String codigo);
	public List<ObraDeUsuario> getObrasPorUsuario(String usuario);
	public List<ObraDeUsuario> getObrasPorMes(int code);
	public List<ObraDeUsuario> getObrasPorMesObra(int code, String nombreObra);
	public List<ObraDeUsuario> getObrasPorUsuarioObra(String usuario, String nombreObra);
	public List<ObraDeUsuario> getObrasPorUsuarioMes(String usuario, int code);
	public List<ObraDeUsuario> getObrasPorUsuarioMesObra(String usuario, int code, String nombreObra);
	public List<Obra> getObras(String codigo);
	public List<ObraDeUsuario> getObrasDeUsuario2(long idUsuario, String obra);
	public List<Obra> getObras2(String obra);
	public List<ObraDeUsuario> getObrasDeUsuario(long idUsuario, String fecha, String obra);
	public List<Obra> getObrasPorTitulo(String titulo);
	public List<ObraDeUsuario> getTodasObrasDeUsuario();
	public void saveObraDeUsuario(ObraDeUsuario obra);
	public void saveObra(Obra obra);
	public Obra getObraByNombre(String nombre);
	public Obra getObraByCodigo(String codigo);
	public void deleteRatio(ObraDeUsuario obra);
	public void updateRatio(ObraDeUsuario obraNueva);
	public List<ObraDeUsuario> getObrasDeUsuarioMes(long idUsuario, int code);
	public List<ObraDeUsuario> getObrasDeUsuarioMesObra(long idUsuario, int code, String obra);
	public void deleteObra(Obra obra);
	public void updateObra(Obra obra);
	public List<ObraDeUsuario> getObrasDeUsuario();
	public List<ObraDeUsuario> getObrasDeUsuarioPorTitulo(String titulo);
	public void saveObraHistorico(ObraDeUsuarioHistorica obraHistorica);
	public List<ObraDeUsuarioHistorica> getObrasHistorico();
	public List<ObraDeUsuarioHistorica> getObrasHistoricoPorObra(String nombreObra);
	public List<ObraDeUsuarioHistorica> getObrasHistoricoPorCodigo(String codigoObra);
	public List<ObraDeUsuarioHistorica> getObrasHistoricoPorTitulo(String titulo);
	public List<ObraDeUsuarioHistorica> getObrasHistoricoPorUsuario(String usuarioObra);
	public List<ObraDeUsuarioHistorica> getObrasHistoricoPorYear(String year);
	public List<ObraDeUsuario> getObrasPorYear(int yearFilter);
	public List<ObraDeUsuario> getObrasPorYearUsuario(int yearFilter, String idUsuario);
	public List<ObraDeUsuario> getObrasPorYearObraUsuario(int yearFilter, String idUsuario, String obra);
	public List<ObraDeUsuario> getObrasPorYearObra(int yearFilter, String nombreObraFilter);
	public double horasDia(long idUsuario, String fecha);
	public List<ObraDeUsuario> getObrasPorUsuarioYearMesObra(String usuario, int code, int year);
}
