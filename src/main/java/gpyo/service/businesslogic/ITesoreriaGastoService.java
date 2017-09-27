package gpyo.service.businesslogic;

import java.util.List;

import gpyo.persistence.entity.admin.Gasto;

public interface ITesoreriaGastoService {

	public void saveGasto(Gasto gasto);
	public List<Gasto> getGastos(long idUsuario);
	public List<Gasto> getGastos1();
	public List<Gasto> getGastosPorTitulo(long idTitulo);
	public void updateGasto(Gasto gasto);
	public void deleteGasto(Gasto gasto);
	public float pendientePago(String nFactura);
	public List<Gasto> getGastoYear(String year);
	public List<Gasto> getGastoYearObraCategoria(String year, String obra, String categoria);
	public List<Gasto> getGastoYearCategoria(String year, String categoria);
	public List<Gasto> getGastosPorObra(long idObra);
	public List<Gasto> getGastoYearMonthObraCategoria(String year, String obra, String categoria, String month);
	public List<Gasto> getGastoYearMonthCategoria(String year, String categoria, String month);
	
	public List<Gasto> getGastoYearObra(String year, String obra);
	//public List<Gasto> getGastoYear(String year);
	public List<Gasto> getGastoYearMonthObra(String year, String obra, String month);
	public List<Gasto> getGastoYearMonth(String year, String month);
}
