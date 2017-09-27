package gpyo.service.businesslogic.impl;

import java.util.List;


import gpyo.persistence.dao.ITesoreriaDao;
import gpyo.persistence.entity.admin.Tesoreria;
import gpyo.service.businesslogic.ITesoreriaService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TesoreriaService implements ITesoreriaService{
	

	ITesoreriaDao tesoreriaDao;
	
	public List<Tesoreria> getTesorerias(){
		return tesoreriaDao.getTesorerias();
	}
	
	public void deleteTesoreria(Tesoreria tesoreria){
		tesoreriaDao.deleteTesoreria(tesoreria);
	}
	
	public List<Tesoreria> getTesorerias(long idObra, int year){
		return tesoreriaDao.getTesorerias(idObra, year);
	}

	public List<Tesoreria> getTesoreriasPorTitulo(long idTitulo, int year){
		return tesoreriaDao.getTesoreriasPorTitulo(idTitulo, year);
	}

	
	public List<Tesoreria> getTesoreriaAnterior(int year, long idObra){
		return tesoreriaDao.getTesoreriaAnterior(year, idObra);
	}

	
/*	public void saveGastoAcumulado(GastoAcumulado gastoAcumulado){
		gastoAcumuladoDao.save(gastoAcumulado);
	}
	
	public void saveIngresoAcumulado(IngresoAcumulado ingresoAcumulado){
		ingresoAcumuladoDao.save(ingresoAcumulado);
	}*/
	

	
	public void saveTesoreria(Tesoreria tesoreria){
		tesoreriaDao.saveTesoreria(tesoreria);
	}
	
	@Transactional	
	public void updateTesoreria(Tesoreria tesoreria){
		tesoreriaDao.updateTesoreria(tesoreria);
	}

	
	public List<Tesoreria> getFlujoCaja(long idObra, int year){
		return tesoreriaDao.getFlujoCaja(idObra, year);
	}
/*	
	public List<GastoAcumulado> getGastosAcumulados(long idObra){
		return gastoAcumuladoDao.getGastosAcumulados(idObra);
	}
	
	public List<IngresoAcumulado> getIngresosAcumulados(long idObra){
		return ingresoAcumuladoDao.getIngresosAcumulados(idObra);
	}*/

	public List<Tesoreria> getTesoreriaPorTitulo(long idTitulo, String concepto, int year){
		return tesoreriaDao.getTesoreriaPorTitulo(idTitulo, concepto, year);
	}
	
	public Tesoreria getTesoreriaPorTituloObra(long idTitulo, String concepto, String tituloObra){
		return tesoreriaDao.getTesoreriaPorTitulo(idTitulo, concepto, tituloObra);
	}
	
	public Tesoreria getTesoreriaParaTitulo(long idTesoreria){
		return tesoreriaDao.getTesoreriaParaTitulo(idTesoreria);
	}
	

	
	public ITesoreriaDao getTesoreriaDao() {
		return tesoreriaDao;
	}

	public void setTesoreriaDao(ITesoreriaDao tesoreriaDao) {
		this.tesoreriaDao = tesoreriaDao;
	}


}
