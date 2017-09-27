package gpyo.persistence.dao;

import java.util.List;

import gpyo.persistence.entity.admin.Tesoreria;

public interface ITesoreriaDao extends IGenericDao<Tesoreria, Long>{
	
	public void saveTesoreria(Tesoreria tesoreria);
	public void deleteTesoreria(Tesoreria tesoreria);
	public List<Tesoreria> getTesorerias();
	public List<Tesoreria> getTesorerias(long idObra, int year);
	public List<Tesoreria> getTesoreriasPorTitulo(long idTitulo, int year);
	public void updateTesoreria(Tesoreria tesoreria);
	public List<Tesoreria> getTesoreriaPorTitulo(long idTitulo, String concepto, int year);
	public Tesoreria getTesoreriaPorTitulo(long idTitulo, String concepto, String tituloObra);
	public Tesoreria getTesoreriaParaTitulo(long idTesoreria);
	public List<Tesoreria> getFlujoCaja(long idObra, int year);
	public List<Tesoreria> getTesoreriaAnterior(int year, long idObra);
}
