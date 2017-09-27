package gpyo.service.businesslogic.impl;

import java.util.List;

import gpyo.persistence.dao.IIngresoDao;
import gpyo.persistence.entity.admin.Ingreso;
import gpyo.service.businesslogic.ITesoreriaIngresoService;

import org.springframework.stereotype.Service;

@Service
public class TesoreriaIngresoService implements ITesoreriaIngresoService{

	private IIngresoDao ingresoDao;
	

	public void saveIngreso(Ingreso ingreso){
		ingresoDao.saveIngreso(ingreso);
	}
	
	public List<Ingreso> getIngresos(long idUsuario){
		return ingresoDao.getIngresos();
	}
	
	public List<Ingreso> getIngresos(){
		return ingresoDao.getIngresos();
	}
	
	public List<Ingreso> getIngresosPorObra(long idObra){
		return ingresoDao.getIngresosPorObra(idObra);
	}
	
	public List<Ingreso> getIngresosPorMes(String mes, long obra){
		return ingresoDao.getIngresosPorMes(mes, obra);
	}
	
	public void updateIngreso(Ingreso ingreso){
		ingresoDao.updateIngreso(ingreso);
	}
	
	public void deleteIngreso(Ingreso ingreso){
		ingresoDao.deleteIngreso(ingreso);
	}
	
	public float pendientePago(String nFactura){
		return ingresoDao.pendientePago(nFactura);
	}

	public IIngresoDao getIngresoDao() {
		return ingresoDao;
	}

	public void setIngresoDao(IIngresoDao ingresoDao) {
		this.ingresoDao = ingresoDao;
	}
}
