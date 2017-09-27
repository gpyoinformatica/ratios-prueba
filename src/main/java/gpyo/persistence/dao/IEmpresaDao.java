package gpyo.persistence.dao;

import java.util.List;

import gpyo.persistence.entity.admin.Empresa;


public interface IEmpresaDao extends IGenericDao<Empresa, Long>{
	
	public List<Empresa> getEmpresas();
	
	public Empresa getEmpresa(String nombre);
	
	public void saveEmpresa(Empresa empresa);

}
