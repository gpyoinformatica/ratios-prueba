package gpyo.gui.model;

import gpyo.persistence.entity.admin.Empresa;
import gpyo.service.businesslogic.IEmpresaService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@ManagedBean
@ViewScoped
@Controller
public class EmpresaBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 269048827983965589L;
	
	private Empresa empresa = new Empresa();
	@Autowired
	private IEmpresaService empresaService;
	private List<Empresa> empresas = new ArrayList<Empresa>();
	public List<String> metodosPago = new ArrayList<String>();
	
	public void saveEmpresa(){
		/**Chequear que el cif no está repetido */
		List<Empresa> empresas = empresaService.getEmpresas();
		boolean repetida = false;
		for(int i=0;i<empresas.size();i++)
			if(empresas.get(i).getCif().equals(empresa.getCif()))
				repetida = true;
		if(!repetida){
			empresaService.saveEmpresa(empresa);
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Empresa guardada"));
		}
		else{
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Ya existe una empresa con el mismo nif"));
		}
		showEmpresas();
	}
	
	public void showEmpresas(){
		empresas = new ArrayList<Empresa>();
		empresas = empresaService.getEmpresas();
	}

	/**
	 * @return the empresa
	 */
	public Empresa getEmpresa() {
		return empresa;
	}

	/**
	 * @param empresa the empresa to set
	 */
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	/**
	 * @return the empresaServica
	 */
	public IEmpresaService getEmpresaService() {
		return empresaService;
	}

	/**
	 * @param empresaServica the empresaServica to set
	 */
	public void setEmpresaServica(IEmpresaService empresaService) {
		this.empresaService = empresaService;
	}



	/**
	 * @return the empresas
	 */
	public List<Empresa> getEmpresas() {
		return empresas;
	}



	/**
	 * @param empresas the empresas to set
	 */
	public void setEmpresas(List<Empresa> empresas) {
		this.empresas = empresas;
	}
	
	/**
	 * @return the formaPagos
	 */
	public List<String> getMetodosPago() {
		metodosPago = new ArrayList<String>();
		metodosPago.add("Efectivo");
		metodosPago.add("Caja");
		metodosPago.add("Transf.");
		metodosPago.add("Domic.");
		metodosPago.add("Cheque");
		metodosPago.add("Visa");
		return metodosPago;
	}

	/**
	 * @param formaPagos the formaPagos to set
	 */
	public void setMetodosPago(List<String> metodosPago) {
		this.metodosPago = metodosPago;
	}
	
}