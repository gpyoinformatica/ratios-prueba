package gpyo.service.businesslogic;

import gpyo.persistence.entity.admin.Banco;

import java.util.List;

public interface IBancoService {
	
	public List<Banco> getBancos();
	
	public Banco getBanco(String nombreBanco);
	
	public void updateFondos(Banco banco);

}
