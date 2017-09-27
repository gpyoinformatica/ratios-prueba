package gpyo.gui.model;

import gpyo.persistence.entity.admin.Gasto;
import gpyo.persistence.entity.admin.Impuestos;
import gpyo.persistence.entity.admin.Ingreso;
import gpyo.service.businesslogic.ITesoreriaGastoService;
import gpyo.service.businesslogic.ITesoreriaIngresoService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@ManagedBean
@ViewScoped
@Controller
public class ImpuestosBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3261010669588485316L;
	
	private Impuestos impuesto;
	private List<Impuestos> impuestosIVA = new ArrayList<Impuestos>();
	private List<Impuestos> impuestosIRPF = new ArrayList<Impuestos>();
	private List<Impuestos> impuestosSS = new ArrayList<Impuestos>();
	private String year;
	@Autowired
	private ITesoreriaGastoService tesoreriaGastoService;
	@Autowired
	private ITesoreriaIngresoService tesoreriaIngresoService;
	
	public void generarImpuestos(){
		List<Gasto> gastos = new ArrayList<Gasto>();
		List<Ingreso> ingresos = new ArrayList<Ingreso>();
		gastos = tesoreriaGastoService.getGastos1();
		ingresos = tesoreriaIngresoService.getIngresos();
		generarIVA(gastos, ingresos);
		generarIRPF(gastos, ingresos);
		generarSS(gastos, ingresos);
	}
	
	public void generarIVA(List<Gasto> gastos, List<Ingreso> ingresos){
		Impuestos emitidasIVA = new Impuestos();
		Impuestos recibidasIVA = new Impuestos();
		Impuestos resultadoIVA = new Impuestos();
		emitidasIVA.setTitulo("INGRESOS");
		recibidasIVA.setTitulo("GASTOS");
		resultadoIVA.setTitulo("RESULTADO (INGRESOS-GASTOS)");
		for(Gasto g : gastos){
			String mes = " ";
			mes = new StringBuilder().append(g.getFecha().charAt(3)).append(g.getFecha().charAt(4)).toString();
			if(mes.equals("01") || mes.equals("02") || mes.equals("03"))
				recibidasIVA.setPrimerTrimestre(recibidasIVA.getPrimerTrimestre()+(/*g.getCantidad()-*/g.getIva())+g.getIva2());
			else
				if(mes.equals("04") || mes.equals("05") || mes.equals("06"))
					recibidasIVA.setSegundoTrimestre(recibidasIVA.getSegundoTrimestre()+(/*g.getCantidad()-*/g.getIva())+g.getIva2());
				else
					if(mes.equals("07") || mes.equals("08") || mes.equals("09"))
						recibidasIVA.setTercerTrimestre(recibidasIVA.getTercerTrimestre()+(/*g.getCantidad()-*/g.getIva())+g.getIva2());
					else
						recibidasIVA.setCuartoTrimestre(recibidasIVA.getCuartoTrimestre()+(/*g.getCantidad()-*/g.getIva())+g.getIva2());
		}
		for(Ingreso i : ingresos){
			String mes = " ";
			mes = new StringBuilder().append(i.getFecha().charAt(3)).append(i.getFecha().charAt(4)).toString();
			if(mes.equals("01") || mes.equals("02") || mes.equals("03"))
				emitidasIVA.setPrimerTrimestre(emitidasIVA.getPrimerTrimestre()+(/*i.getIngreso()-*/i.getIva()));
			else
				if(mes.equals("04") || mes.equals("05") || mes.equals("06"))
					emitidasIVA.setSegundoTrimestre(emitidasIVA.getSegundoTrimestre()+(/*i.getIngreso()-*/i.getIva()));
				else
					if(mes.equals("07") || mes.equals("08") || mes.equals("09"))
						emitidasIVA.setTercerTrimestre(emitidasIVA.getTercerTrimestre()+(/*i.getIngreso()-*/i.getIva()));
					else
						emitidasIVA.setCuartoTrimestre(emitidasIVA.getCuartoTrimestre()+(/*i.getIngreso()-*/i.getIva()));
		}
		recibidasIVA.setTotal(recibidasIVA.getPrimerTrimestre()+recibidasIVA.getSegundoTrimestre()+recibidasIVA.getTercerTrimestre()+recibidasIVA.getCuartoTrimestre());
		emitidasIVA.setTotal(emitidasIVA.getPrimerTrimestre()+emitidasIVA.getSegundoTrimestre()+emitidasIVA.getTercerTrimestre()+emitidasIVA.getCuartoTrimestre());
		
		resultadoIVA.setPrimerTrimestre(recibidasIVA.getPrimerTrimestre()-emitidasIVA.getPrimerTrimestre());
		resultadoIVA.setSegundoTrimestre(recibidasIVA.getSegundoTrimestre()-emitidasIVA.getSegundoTrimestre());
		resultadoIVA.setTercerTrimestre(recibidasIVA.getTercerTrimestre()-emitidasIVA.getTercerTrimestre());
		resultadoIVA.setCuartoTrimestre(recibidasIVA.getCuartoTrimestre()-emitidasIVA.getCuartoTrimestre());
		resultadoIVA.setTotal(recibidasIVA.getTotal()-emitidasIVA.getTotal());
		
		impuestosIVA.add(emitidasIVA);
		impuestosIVA.add(recibidasIVA);
		impuestosIVA.add(resultadoIVA);
	}
	
	public void generarIRPF(List<Gasto> gastos, List<Ingreso> ingresos){
		Impuestos emitidasIRPF = new Impuestos();
		Impuestos recibidasIRPF = new Impuestos();
		Impuestos resultadoIRPF = new Impuestos();
		emitidasIRPF.setTitulo("INGRESOS");
		recibidasIRPF.setTitulo("GASTOS");
		resultadoIRPF.setTitulo("RESULTADO (GASTOS-INGRESOS)");
		for(Gasto g : gastos){
			String mes = " ";
			mes = new StringBuilder().append(g.getFecha().charAt(3)).append(g.getFecha().charAt(4)).toString();
			if(mes.equals("01") || mes.equals("02") || mes.equals("03"))
				recibidasIRPF.setPrimerTrimestre(recibidasIRPF.getPrimerTrimestre()+(/*g.getCantidad()-*/g.getIrpfAlquiler()+g.getIrpfSp()));
			else
				if(mes.equals("04") || mes.equals("05") || mes.equals("06"))
					recibidasIRPF.setSegundoTrimestre(recibidasIRPF.getSegundoTrimestre()+(/*g.getCantidad()-*/g.getIrpfAlquiler()+g.getIrpfSp()));
				else
					if(mes.equals("07") || mes.equals("08") || mes.equals("09"))
						recibidasIRPF.setTercerTrimestre(recibidasIRPF.getTercerTrimestre()+(/*g.getCantidad()-*/g.getIrpfAlquiler()+g.getIrpfSp()));
					else
						recibidasIRPF.setCuartoTrimestre(recibidasIRPF.getCuartoTrimestre()+(/*g.getCantidad()-*/g.getIrpfAlquiler()+g.getIrpfSp()));
		}
		for(Ingreso i : ingresos){
			String mes = " ";
			mes = new StringBuilder().append(i.getFecha().charAt(3)).append(i.getFecha().charAt(4)).toString();
			if(mes.equals("01") || mes.equals("02") || mes.equals("03"))
				emitidasIRPF.setPrimerTrimestre(emitidasIRPF.getPrimerTrimestre()+(/*i.getIngreso()-*/i.getIrpfAlquiler()+i.getIrpfSp()));
			else
				if(mes.equals("04") || mes.equals("05") || mes.equals("06"))
					emitidasIRPF.setSegundoTrimestre(emitidasIRPF.getSegundoTrimestre()+(/*i.getIngreso()-*/i.getIrpfAlquiler()+i.getIrpfSp()));
				else
					if(mes.equals("07") || mes.equals("08") || mes.equals("09"))
						emitidasIRPF.setTercerTrimestre(emitidasIRPF.getTercerTrimestre()+(/*i.getIngreso()-*/i.getIrpfAlquiler()+i.getIrpfSp()));
					else
						emitidasIRPF.setCuartoTrimestre(emitidasIRPF.getCuartoTrimestre()+(/*i.getIngreso()-*/i.getIrpfAlquiler()+i.getIrpfSp()));
		}
		recibidasIRPF.setTotal(recibidasIRPF.getPrimerTrimestre()+recibidasIRPF.getSegundoTrimestre()+recibidasIRPF.getTercerTrimestre()+recibidasIRPF.getCuartoTrimestre());
		emitidasIRPF.setTotal(emitidasIRPF.getPrimerTrimestre()+emitidasIRPF.getSegundoTrimestre()+emitidasIRPF.getTercerTrimestre()+emitidasIRPF.getCuartoTrimestre());
		
		resultadoIRPF.setPrimerTrimestre(recibidasIRPF.getPrimerTrimestre()-emitidasIRPF.getPrimerTrimestre());
		resultadoIRPF.setSegundoTrimestre(recibidasIRPF.getSegundoTrimestre()-emitidasIRPF.getSegundoTrimestre());
		resultadoIRPF.setTercerTrimestre(recibidasIRPF.getTercerTrimestre()-emitidasIRPF.getTercerTrimestre());
		resultadoIRPF.setCuartoTrimestre(recibidasIRPF.getCuartoTrimestre()-emitidasIRPF.getCuartoTrimestre());
		resultadoIRPF.setTotal(recibidasIRPF.getTotal()-emitidasIRPF.getTotal());
		
		impuestosIRPF.add(emitidasIRPF);
		impuestosIRPF.add(recibidasIRPF);
		impuestosIRPF.add(resultadoIRPF);
	}
	
	public void generarSS(List<Gasto> gastos, List<Ingreso> ingresos){
		Impuestos emitidasSS = new Impuestos();
		Impuestos recibidasSS = new Impuestos();
		Impuestos resultadoSS = new Impuestos();
		emitidasSS.setTitulo("INGRESOS");
		recibidasSS.setTitulo("GASTOS");
		resultadoSS.setTitulo("RESULTADO (GASTOS-INGRESOS)");
		for(Gasto g : gastos){
			if(g.getEmpresa().equals("Seguridad Social")){
			String mes = " ";
			mes = new StringBuilder().append(g.getFecha().charAt(3)).append(g.getFecha().charAt(4)).toString();
			if(mes.equals("01") || mes.equals("02") || mes.equals("03"))
				recibidasSS.setPrimerTrimestre(recibidasSS.getPrimerTrimestre()+g.getCantidad());
			else
				if(mes.equals("04") || mes.equals("05") || mes.equals("06"))
					recibidasSS.setSegundoTrimestre(recibidasSS.getSegundoTrimestre()+g.getCantidad());
				else
					if(mes.equals("07") || mes.equals("08") || mes.equals("09"))
						recibidasSS.setTercerTrimestre(recibidasSS.getTercerTrimestre()+g.getCantidad());
					else
						recibidasSS.setCuartoTrimestre(recibidasSS.getCuartoTrimestre()+g.getCantidad());
			}
		}
		/**
		 * La seguridad social se saca por los gastos de salarios, por lo que no hay ingresos.
		 */
		/*for(Ingreso i : ingresos){
			String mes = " ";
			mes = new StringBuilder().append(i.getFecha().charAt(3)).append(i.getFecha().charAt(4)).toString();
			if(mes.equals("01") || mes.equals("02") || mes.equals("03"))
				emitidasSS.setPrimerTrimestre(emitidasSS.getPrimerTrimestre()+(i.getIngreso()-i.getIrpfAlquiler()));
			else
				if(mes.equals("04") || mes.equals("05") || mes.equals("06"))
					emitidasSS.setSegundoTrimestre(emitidasSS.getSegundoTrimestre()+(i.getIngreso()-i.getIrpfAlquiler()));
				else
					if(mes.equals("07") || mes.equals("08") || mes.equals("09"))
						emitidasSS.setTercerTrimestre(emitidasSS.getTercerTrimestre()+(i.getIngreso()-i.getIrpfAlquiler()));
					else
						emitidasSS.setCuartoTrimestre(emitidasSS.getCuartoTrimestre()+(i.getIngreso()-i.getIrpfAlquiler()));
		}*/
		recibidasSS.setTotal(recibidasSS.getPrimerTrimestre()+recibidasSS.getSegundoTrimestre()+recibidasSS.getTercerTrimestre()+recibidasSS.getCuartoTrimestre());
		emitidasSS.setTotal(emitidasSS.getPrimerTrimestre()+emitidasSS.getSegundoTrimestre()+emitidasSS.getTercerTrimestre()+emitidasSS.getCuartoTrimestre());
		
		resultadoSS.setPrimerTrimestre(recibidasSS.getPrimerTrimestre()-emitidasSS.getPrimerTrimestre());
		resultadoSS.setSegundoTrimestre(recibidasSS.getSegundoTrimestre()-emitidasSS.getSegundoTrimestre());
		resultadoSS.setTercerTrimestre(recibidasSS.getTercerTrimestre()-emitidasSS.getTercerTrimestre());
		resultadoSS.setCuartoTrimestre(recibidasSS.getCuartoTrimestre()-emitidasSS.getCuartoTrimestre());
		resultadoSS.setTotal(recibidasSS.getTotal()-emitidasSS.getTotal());
		
		impuestosSS.add(emitidasSS);
		impuestosSS.add(recibidasSS);
		impuestosSS.add(resultadoSS);
	}
	
	public Impuestos getImpuesto() {
		return impuesto;
	}
	
	public void setImpuesto(Impuestos impuesto) {
		this.impuesto = impuesto;
	}
	
	public String getYear() {
		return year;
	}
	
	public void setYear(String year) {
		this.year = year;
	}

	public List<Impuestos> getImpuestosIVA() {
		return impuestosIVA;
	}

	public void setImpuestosIVA(List<Impuestos> impuestosIVA) {
		this.impuestosIVA = impuestosIVA;
	}

	public List<Impuestos> getImpuestosIRPF() {
		return impuestosIRPF;
	}

	public void setImpuestosIRPF(List<Impuestos> impuestosIRPF) {
		this.impuestosIRPF = impuestosIRPF;
	}

	public ITesoreriaGastoService getTesoreriaGastoService() {
		return tesoreriaGastoService;
	}

	public void setTesoreriaGastoService(
			ITesoreriaGastoService tesoreriaGastoService) {
		this.tesoreriaGastoService = tesoreriaGastoService;
	}

	public ITesoreriaIngresoService getTesoreriaIngresoService() {
		return tesoreriaIngresoService;
	}

	public void setTesoreriaIngresoService(
			ITesoreriaIngresoService tesoreriaIngresoService) {
		this.tesoreriaIngresoService = tesoreriaIngresoService;
	}


	/**
	 * @return the impuestosSS
	 */
	public List<Impuestos> getImpuestosSS() {
		return impuestosSS;
	}


	/**
	 * @param impuestosSS the impuestosSS to set
	 */
	public void setImpuestosSS(List<Impuestos> impuestosSS) {
		this.impuestosSS = impuestosSS;
	}
	
	

}
