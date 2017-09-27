package gpyo.service.businesslogic.impl;

import java.util.List;

import gpyo.persistence.dao.IBancoDao;
import gpyo.persistence.entity.admin.Banco;
import gpyo.service.businesslogic.IBancoService;

import org.springframework.stereotype.Service;

@Service
public class BancoService implements IBancoService{
	
	private IBancoDao bancoDao;
	
	public List<Banco> getBancos(){
		return bancoDao.getBancos();
	}
	
	public Banco getBanco(String nombreBanco){
		return bancoDao.getBanco(nombreBanco);
	}
	
	public void updateFondos(Banco banco){
		bancoDao.updateFondos(banco);
	}

	/**
	 * @return the bancoDao
	 */
	public IBancoDao getBancoDao() {
		return bancoDao;
	}

	/**
	 * @param bancoDao the bancoDao to set
	 */
	public void setBancoDao(IBancoDao bancoDao) {
		this.bancoDao = bancoDao;
	}

}
