package gpyo.service.businesslogic;

import java.util.List;

import gpyo.persistence.entity.admin.Tesoreria;

public interface ITesoreriaService {
	
	
/*	public List<String> getTitulos();
	public void saveGastoAcumulado(GastoAcumulado gastoAcumulado);
	public void saveIngresoAcumulado(IngresoAcumulado ingresoAcumulado);
	public List<GastoAcumulado> getGastosAcumulados(long idObra);
	public List<IngresoAcumulado> getIngresosAcumulados(long idObra);*/
	public void saveTesoreria(Tesoreria tesoreria);
	public void updateTesoreria(Tesoreria tesoreria);
	public List<Tesoreria> getTesoreriaPorTitulo(long idTitulo, String concepto, int year);
	public Tesoreria getTesoreriaPorTituloObra(long idTitulo, String concepto, String tituloObra);
	public List<Tesoreria> getTesorerias();
	public List<Tesoreria> getTesorerias(long idObra, int year);
	public List<Tesoreria> getTesoreriasPorTitulo(long idTitulo, int year);
	public Tesoreria getTesoreriaParaTitulo(long idTesoreria);
	public List<Tesoreria> getFlujoCaja(long idObra, int year);
	public List<Tesoreria> getTesoreriaAnterior(int year, long idObra);
//	public List<Long> getIdObras();
	public void deleteTesoreria(Tesoreria tesoreria);
}
