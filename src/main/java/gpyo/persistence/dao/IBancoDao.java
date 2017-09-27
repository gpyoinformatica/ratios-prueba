package gpyo.persistence.dao;

import java.util.List;

import gpyo.persistence.entity.admin.Banco;


public interface IBancoDao extends IGenericDao<Banco, Long>{
	
	public List<Banco> getBancos();
	
	public Banco getBanco(String nombreBanco);
	
	public void updateFondos(Banco banco);

}
