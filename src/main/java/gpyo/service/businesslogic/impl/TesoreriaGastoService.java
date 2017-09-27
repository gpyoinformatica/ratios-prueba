package gpyo.service.businesslogic.impl;

import java.util.List;

import gpyo.persistence.dao.IGastoDao;
import gpyo.persistence.entity.admin.Gasto;
import gpyo.service.businesslogic.ITesoreriaGastoService;

import org.springframework.stereotype.Service;

@Service
public class TesoreriaGastoService implements ITesoreriaGastoService{

	private IGastoDao gastoDao;
	
	
	public void saveGasto(Gasto gasto){
		gastoDao.saveGasto(gasto);
	}
	
	public void updateGasto(Gasto gasto){
		gastoDao.updateGasto(gasto);
	}
	
	public void deleteGasto(Gasto gasto){
		gastoDao.deleteGasto(gasto);
	}
	
	public List<Gasto> getGastos(long idUsuario){
		return gastoDao.getGastos();
	}
	
	public List<Gasto> getGastos1(){
		return gastoDao.getGastos();
	}
	
	public List<Gasto> getGastosPorTitulo(long idTitulo){
		return gastoDao.getGastosPorTitulo(idTitulo);
	}

	
	public float pendientePago(String nFactura){
		return gastoDao.pendientePago(nFactura);
	}
	
	public List<Gasto> getGastoYear(String year){
		return gastoDao.getGastoYear(year);
	}
	
	public List<Gasto> getGastoYearObraCategoria(String year, String obra, String categoria){
		return gastoDao.getGastoYearObraCategoria(year, obra, categoria);
	}
	public List<Gasto> getGastoYearCategoria(String year, String categoria){
		return gastoDao.getGastoYearCategoria(year, categoria);
	}
	
	public List<Gasto> getGastosPorObra(long idObra){
		return gastoDao.getGastosPorObra(idObra);
	}

	public List<Gasto> getGastoYearMonthObraCategoria(String year, String obra, String categoria, String month){
		return gastoDao.getGastoYearMonthObraCategoria(year, obra, categoria, month);
	}
	public List<Gasto> getGastoYearMonthCategoria(String year, String categoria, String month){
		return gastoDao.getGastoYearMonthCategoria(year, categoria, month);
	}

	public List<Gasto> getGastoYearObra(String year, String obra){
		return gastoDao.getGastoYearObra(year, obra);
	}

	public List<Gasto> getGastoYearMonthObra(String year, String obra, String month){
		return gastoDao.getGastoYearMonthObra(year, obra, month);
	}
	public List<Gasto> getGastoYearMonth(String year, String month){
		return gastoDao.getGastoYearMonth(year, month);
	}
	
	/**
	 * @return the gastoDao
	 */
	public IGastoDao getGastoDao() {
		return gastoDao;
	}


	/**
	 * @param gastoDao the gastoDao to set
	 */
	public void setGastoDao(IGastoDao gastoDao) {
		this.gastoDao = gastoDao;
	}


	
}
