package gpyo.gui.model;

import gpyo.persistence.entity.admin.Gasto;
import gpyo.persistence.entity.admin.Obra;
import gpyo.service.businesslogic.IObraService;
import gpyo.service.businesslogic.ITesoreriaGastoService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author usuario
 *
 */
@ManagedBean
@ViewScoped
@Controller
public class FacturasBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5455917415814513628L;
	
	private List<String> obras = new ArrayList<String>();
	private String obra;
	private List<String> categorias = new ArrayList<String>();
	private String categoria;
	private int year;
	private String month;
	private String monthCode;
	private List<String> months = new ArrayList<String>();
	@Autowired
	private IObraService obraService;
	@Autowired
	private ITesoreriaGastoService tesoreriaGastoService;
	private List<Gasto> facturas = new ArrayList<Gasto>();
	

	public void verFacturas(){
		setFacturas(new ArrayList<Gasto>());
		setMonthCode();
		if(obra==""){
			if(categoria=="" || categoria.equals("Todos")){
				if(monthCode.equals("00"))
					setFacturas(tesoreriaGastoService.getGastoYear(Integer.toString(year)));
				else
					setFacturas(tesoreriaGastoService.getGastoYearMonth(Integer.toString(year), monthCode));
			}
			else{
				if(monthCode.equals("00"))
					setFacturas(tesoreriaGastoService.getGastoYearCategoria(Integer.toString(year), categoria));
				else
					setFacturas(tesoreriaGastoService.getGastoYearMonthCategoria(Integer.toString(year), categoria, monthCode));
			}
		}
		else{
			if(categoria!="" && !categoria.equals("Todos")){
				if(monthCode.equals("00"))
					setFacturas(tesoreriaGastoService.getGastoYearObraCategoria(Integer.toString(year), obra, categoria));
				else
					setFacturas(tesoreriaGastoService.getGastoYearMonthObraCategoria(Integer.toString(year), obra, categoria, monthCode));
			}
			else{
				if(monthCode.equals("00"))
					setFacturas(tesoreriaGastoService.getGastoYearObra(Integer.toString(year), obra));
				else
					setFacturas(tesoreriaGastoService.getGastoYearMonthObra(Integer.toString(year), obra, monthCode));
			}
		}
	}
	
	public void setMonthCode(){
		if(month.equals("Enero"))
			monthCode = "01";
		else{
			if(month.equals("Febrero"))
				monthCode = "02";
			else{
				if(month.equals("Marzo"))
					monthCode = "03";
				else{
					if(month.equals("Abril"))
						monthCode = "04";
					else{
						if(month.equals("Mayo"))
							monthCode = "05";
						else{
							if(month.equals("Junio"))
								monthCode = "06";
							else{
								if(month.equals("Julio"))
									monthCode = "07";
								else{
									if(month.equals("Agosto"))
										monthCode = "08";
									else{
										if(month.equals("Septiembre"))
											monthCode = "09";
										else{
											if(month.equals("Octubre"))
												monthCode = "10";
											else{
												if(month.equals("Noviembre"))
													monthCode = "11";
												else{
													if(month.equals("Diciembre"))
														monthCode = "12";
													else
														monthCode = "00";
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	public List<String> getObras() {
		obras = new ArrayList<String>();
		List<Obra> o = new ArrayList<Obra>();
		o = obraService.getObras();
		for(Obra ob : o)
			obras.add(ob.getNombreObra());
		return obras;
	}
	
	public void setObras(List<String> obras) {
		this.obras = obras;
	}
	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public List<String> getMonths() {
		this.months = new ArrayList<String>();
		this.months.add("Enero");
		this.months.add("Febrero");
		this.months.add("Marzo");
		this.months.add("Abril");
		this.months.add("Mayo");
		this.months.add("Junio");
		this.months.add("Julio");
		this.months.add("Agosto");
		this.months.add("Septiembre");
		this.months.add("Octubre");
		this.months.add("Noviembre");
		this.months.add("Diciembre");
		return months;
	}

	public void setMonths(List<String> months) {
		this.months = months;
	}

	public String getObra() {
		return obra;
	}
	public void setObra(String obra) {
		this.obra = obra;
	}
	public List<String> getCategorias() {
		categorias = new ArrayList<String>();
		categorias.add("Todos");
		categorias.add("PERS");
		categorias.add("GTOS");
		categorias.add("SUM");
		categorias.add("VEH");
		categorias.add("TFNO");
		categorias.add("MENS");
		categorias.add("SERV.PROF");
		categorias.add("MAT OF");
		categorias.add("MOB");
		categorias.add("INFOR");
		categorias.add("SEG");
		categorias.add("ASES");
		categorias.add("UBU");
		categorias.add("BCOS");
		categorias.add("VARIOS");
		categorias.add("ALQ");
		categorias.add("PTAMO");
		categorias.add("LIBR");
		categorias.add("ACAL");
		categorias.add("REPR");
		categorias.add("FORM");
		categorias.add("LIMP");
		categorias.add("RESUMEN");
		categorias.add("GLOBAL");
		categorias.add("FACTURACION");
		categorias.add("GTOS PERU");
		return categorias;
	}
	public void setCategorias(List<String> categorias) {
		this.categorias = categorias;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	/**
	 * @return the obraService
	 */
	public IObraService getObraService() {
		return obraService;
	}
	/**
	 * @param obraService the obraService to set
	 */
	public void setObraService(IObraService obraService) {
		this.obraService = obraService;
	}
	/**
	 * @return the tesoreriaGastoService
	 */
	public ITesoreriaGastoService getTesoreriaGastoService() {
		return tesoreriaGastoService;
	}
	/**
	 * @param tesoreriaGastoService the tesoreriaGastoService to set
	 */
	public void setTesoreriaGastoService(ITesoreriaGastoService tesoreriaGastoService) {
		this.tesoreriaGastoService = tesoreriaGastoService;
	}


	/**
	 * @return the facturas
	 */
	public List<Gasto> getFacturas() {
		return facturas;
	}


	/**
	 * @param facturas the facturas to set
	 */
	public void setFacturas(List<Gasto> facturas) {
		this.facturas = facturas;
	}

	/**
	 * @return the monthCode
	 */
	public String getMonthCode() {
		return monthCode;
	}

	/**
	 * @param monthCode the monthCode to set
	 */
	public void setMonthCode(String monthCode) {
		this.monthCode = monthCode;
	}

}
