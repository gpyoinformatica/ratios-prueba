package gpyo.service.businesslogic;

import gpyo.persistence.entity.admin.Empresa;

import java.util.List;

public interface IEmpresaService {
	
	public List<Empresa> getEmpresas();
	
	public Empresa getEmpresa(String nombre);
	
	public void saveEmpresa(Empresa empresa);

}
