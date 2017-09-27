package gpyo.service.businesslogic;

import java.util.List;

import gpyo.persistence.entity.admin.Ingreso;

public interface ITesoreriaIngresoService {

	public void saveIngreso(Ingreso ingreso);
	public List<Ingreso> getIngresos(long idUsuario);
	public List<Ingreso> getIngresos();
	public List<Ingreso> getIngresosPorObra(long idObra);
	public List<Ingreso> getIngresosPorMes(String mes, long obra);
	public void updateIngreso(Ingreso tesoreriaIngreso);
	public void deleteIngreso(Ingreso tesoreriaIngreso);
	public float pendientePago(String nFactura);
}
