package gpyo.persistence.dao;

import java.util.List;

import gpyo.persistence.entity.admin.Ingreso;

public interface IIngresoDao extends IGenericDao<Ingreso, Long>{

	public void saveIngreso(Ingreso ingreso);
	public void deleteIngreso(Ingreso ingreso);
	public void updateIngreso(Ingreso ingreso);
	public List<Ingreso> getIngresos();
	public List<Ingreso> getIngresosPorObra(long idObra);
	public List<Ingreso> getIngresosPorMes(String mes, long obra);
	public float pendientePago(String nFactura);
}
