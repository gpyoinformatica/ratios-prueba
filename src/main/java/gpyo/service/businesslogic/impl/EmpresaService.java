package gpyo.service.businesslogic.impl;

import java.util.List;

import gpyo.persistence.dao.IEmpresaDao;
import gpyo.persistence.entity.admin.Empresa;
import gpyo.service.businesslogic.IEmpresaService;

import org.springframework.stereotype.Service;

@Service
public class EmpresaService implements IEmpresaService{
	
	private IEmpresaDao empresaDao;
	
	public List<Empresa> getEmpresas(){
		return empresaDao.getEmpresas();
	}
	
	public Empresa getEmpresa(String nombre){
		return empresaDao.getEmpresa(nombre);
	}
	
	public void saveEmpresa(Empresa empresa){
		empresaDao.saveEmpresa(empresa);
	}

	public IEmpresaDao getEmpresaDao() {
		return empresaDao;
	}

	public void setEmpresaDao(IEmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}

}
