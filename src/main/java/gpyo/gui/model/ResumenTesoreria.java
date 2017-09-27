package gpyo.gui.model;


import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.springframework.stereotype.Controller;

@ManagedBean
@ViewScoped
@Controller
public class ResumenTesoreria implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -777534421210528504L;

	/**
	 * 
	 *//*
	private static final long serialVersionUID = -777534421210528504L;

	List<Obra> obras = new ArrayList<Obra>();
	private IObraService obraService;
	private ITesoreriaIngresoService tesoreriaIngresoService;
	private ITesoreriaGastoService tesoreriaGastoService;
	private ITesoreriaService tesoreriaService;
	
	private List<Tesoreria> tesoreriasIngresos = new ArrayList<Tesoreria>();
	private List<Tesoreria> tesoreriasIngresos2 = new ArrayList<Tesoreria>();
	private List<Tesoreria> tesoreriasIngresos3 = new ArrayList<Tesoreria>();
	private List<Tesoreria> tesoreriasIngresos4 = new ArrayList<Tesoreria>();
	private List<Tesoreria> tesoreriasIngresos5 = new ArrayList<Tesoreria>();
	
	private List<Tesoreria> tesoreriasGastos = new ArrayList<Tesoreria>();
	private List<Tesoreria> tesoreriasGastos2 = new ArrayList<Tesoreria>();
	private List<Tesoreria> tesoreriasGastos3 = new ArrayList<Tesoreria>();
	private List<Tesoreria> tesoreriasGastos4 = new ArrayList<Tesoreria>();
	private List<Tesoreria> tesoreriasGastos5 = new ArrayList<Tesoreria>();
	
	private List<Tesoreria> flujoCaja = new ArrayList<Tesoreria>();
	private List<Tesoreria> flujoCaja2 = new ArrayList<Tesoreria>();
	private List<Tesoreria> flujoCaja3 = new ArrayList<Tesoreria>();
	private List<Tesoreria> flujoCaja4 = new ArrayList<Tesoreria>();
	private List<Tesoreria> flujoCaja5 = new ArrayList<Tesoreria>();
	
	private int firstYear;
	private int secondYear;
	private int thirdYear;
	private int fourthYear;
	private int fifthYear;
	
	*//**
	 * Crea un resumen de las tesorerías de todas las obras.
	 *//*
	public void createResume(){
		tesoreriasIngresos = new ArrayList<Tesoreria>();
		tesoreriasIngresos2 = new ArrayList<Tesoreria>();
		tesoreriasIngresos3 = new ArrayList<Tesoreria>();
		tesoreriasIngresos4 = new ArrayList<Tesoreria>();
		tesoreriasIngresos5 = new ArrayList<Tesoreria>();
		tesoreriasGastos = new ArrayList<Tesoreria>();
		tesoreriasGastos2 = new ArrayList<Tesoreria>();
		tesoreriasGastos3 = new ArrayList<Tesoreria>();
		tesoreriasGastos4 = new ArrayList<Tesoreria>();
		tesoreriasGastos5 = new ArrayList<Tesoreria>();
		flujoCaja = new ArrayList<Tesoreria>();
		flujoCaja2 = new ArrayList<Tesoreria>();
		flujoCaja3 = new ArrayList<Tesoreria>();
		flujoCaja4 = new ArrayList<Tesoreria>();
		flujoCaja5 = new ArrayList<Tesoreria>();
		obras = obraService.getObras();
		List<Tesoreria> tesorerias = new ArrayList<Tesoreria>();
		List<Tesoreria> tesorerias2 = new ArrayList<Tesoreria>();
		List<Tesoreria> tesorerias3 = new ArrayList<Tesoreria>();
		List<Tesoreria> tesorerias4 = new ArrayList<Tesoreria>();
		List<Tesoreria> tesorerias5 = new ArrayList<Tesoreria>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy");
		Calendar calendar = Calendar.getInstance();
		java.util.Date currentDate1 = calendar.getTime();
		Date currentDate = new java.sql.Date(currentDate1.getTime());
		firstYear=Integer.parseInt(sdf2.format(currentDate));
		secondYear=firstYear+1;
		thirdYear=secondYear+1;
		fourthYear=thirdYear+1;
		fifthYear=fourthYear+1;
		String year1 = sdf.format(currentDate);
		String year="";
		TesoreriaTitulo personal = new TesoreriaTitulo();
		TesoreriaTitulo subcontratas = new TesoreriaTitulo();
		TesoreriaTitulo gastosfactura = new TesoreriaTitulo();
		TesoreriaTitulo gastosSinFactura = new TesoreriaTitulo();
		TesoreriaTitulo gastosFinancieros = new TesoreriaTitulo();
		TesoreriaTitulo gastosEstructura = new TesoreriaTitulo();
		year=year+year1.charAt(year1.length()-4)+year1.charAt(year1.length()-3)+year1.charAt(year1.length()-2)+year1.charAt(year1.length()-1);
		for(int i=0;i<obras.size();i++){
			if(tesoreriaService.getTesoreriaTituloPorTitulo("Personal", obras.get(i).getIdObra()).size()>0){
				personal = new TesoreriaTitulo();
				personal = tesoreriaService.getTesoreriaTituloPorTitulo("Personal", obras.get(i).getIdObra()).get(0);
				personal.setTesorerias(tesoreriaService.getTesoreriasPorTitulo(personal.getIdTesoreriaTitulo(), Integer.parseInt(year)));
				personal.setTesorerias2(tesoreriaService.getTesoreriasPorTitulo(personal.getIdTesoreriaTitulo(), Integer.parseInt(year)+1));
				personal.setTesorerias3(tesoreriaService.getTesoreriasPorTitulo(personal.getIdTesoreriaTitulo(), Integer.parseInt(year)+2));
				personal.setTesorerias4(tesoreriaService.getTesoreriasPorTitulo(personal.getIdTesoreriaTitulo(), Integer.parseInt(year)+3));
				personal.setTesorerias5(tesoreriaService.getTesoreriasPorTitulo(personal.getIdTesoreriaTitulo(), Integer.parseInt(year)+4));
			}
			if(tesoreriaService.getTesoreriaTituloPorTitulo("Subcontratas", obras.get(i).getIdObra()).size()>0){
				subcontratas = new TesoreriaTitulo();
				subcontratas = tesoreriaService.getTesoreriaTituloPorTitulo("Subcontratas", obras.get(i).getIdObra()).get(0);
				subcontratas.setTesorerias(tesoreriaService.getTesoreriasPorTitulo(subcontratas.getIdTesoreriaTitulo(), Integer.parseInt(year)));
				subcontratas.setTesorerias2(tesoreriaService.getTesoreriasPorTitulo(subcontratas.getIdTesoreriaTitulo(), Integer.parseInt(year)+1));
				subcontratas.setTesorerias3(tesoreriaService.getTesoreriasPorTitulo(subcontratas.getIdTesoreriaTitulo(), Integer.parseInt(year)+2));
				subcontratas.setTesorerias4(tesoreriaService.getTesoreriasPorTitulo(subcontratas.getIdTesoreriaTitulo(), Integer.parseInt(year)+3));
				subcontratas.setTesorerias5(tesoreriaService.getTesoreriasPorTitulo(subcontratas.getIdTesoreriaTitulo(), Integer.parseInt(year)+4));
			}
			if(tesoreriaService.getTesoreriaTituloPorTitulo("Gastos con factura", obras.get(i).getIdObra()).size()>0){
				gastosfactura = new TesoreriaTitulo();
				gastosfactura = tesoreriaService.getTesoreriaTituloPorTitulo("Gastos con factura", obras.get(i).getIdObra()).get(0);
				gastosfactura.setTesorerias(tesoreriaService.getTesoreriasPorTitulo(gastosfactura.getIdTesoreriaTitulo(), Integer.parseInt(year)));
				gastosfactura.setTesorerias2(tesoreriaService.getTesoreriasPorTitulo(gastosfactura.getIdTesoreriaTitulo(), Integer.parseInt(year)+1));
				gastosfactura.setTesorerias3(tesoreriaService.getTesoreriasPorTitulo(gastosfactura.getIdTesoreriaTitulo(), Integer.parseInt(year)+2));
				gastosfactura.setTesorerias4(tesoreriaService.getTesoreriasPorTitulo(gastosfactura.getIdTesoreriaTitulo(), Integer.parseInt(year)+3));
				gastosfactura.setTesorerias5(tesoreriaService.getTesoreriasPorTitulo(gastosfactura.getIdTesoreriaTitulo(), Integer.parseInt(year)+4));
			}
			if(tesoreriaService.getTesoreriaTituloPorTitulo("Gastos sin factura", obras.get(i).getIdObra()).size()>0){
				gastosSinFactura = new TesoreriaTitulo();
				gastosSinFactura = tesoreriaService.getTesoreriaTituloPorTitulo("Gastos sin factura", obras.get(i).getIdObra()).get(0);
				gastosSinFactura.setTesorerias(tesoreriaService.getTesoreriasPorTitulo(gastosSinFactura.getIdTesoreriaTitulo(), Integer.parseInt(year)));
				gastosSinFactura.setTesorerias2(tesoreriaService.getTesoreriasPorTitulo(gastosSinFactura.getIdTesoreriaTitulo(), Integer.parseInt(year)+1));
				gastosSinFactura.setTesorerias3(tesoreriaService.getTesoreriasPorTitulo(gastosSinFactura.getIdTesoreriaTitulo(), Integer.parseInt(year)+2));
				gastosSinFactura.setTesorerias4(tesoreriaService.getTesoreriasPorTitulo(gastosSinFactura.getIdTesoreriaTitulo(), Integer.parseInt(year)+3));
				gastosSinFactura.setTesorerias5(tesoreriaService.getTesoreriasPorTitulo(gastosSinFactura.getIdTesoreriaTitulo(), Integer.parseInt(year)+4));
			}
			if(tesoreriaService.getTesoreriaTituloPorTitulo("Gastos financieros", obras.get(i).getIdObra()).size()>0){
				gastosFinancieros = new TesoreriaTitulo();
				gastosFinancieros = tesoreriaService.getTesoreriaTituloPorTitulo("Gastos financieros", obras.get(i).getIdObra()).get(0);
				gastosFinancieros.setTesorerias(tesoreriaService.getTesoreriasPorTitulo(gastosFinancieros.getIdTesoreriaTitulo(), Integer.parseInt(year)));
				gastosFinancieros.setTesorerias2(tesoreriaService.getTesoreriasPorTitulo(gastosFinancieros.getIdTesoreriaTitulo(), Integer.parseInt(year)+1));
				gastosFinancieros.setTesorerias3(tesoreriaService.getTesoreriasPorTitulo(gastosFinancieros.getIdTesoreriaTitulo(), Integer.parseInt(year)+2));
				gastosFinancieros.setTesorerias4(tesoreriaService.getTesoreriasPorTitulo(gastosFinancieros.getIdTesoreriaTitulo(), Integer.parseInt(year)+3));
				gastosFinancieros.setTesorerias5(tesoreriaService.getTesoreriasPorTitulo(gastosFinancieros.getIdTesoreriaTitulo(), Integer.parseInt(year)+4));
			}
			if(tesoreriaService.getTesoreriaTituloPorTitulo("Gastos estructura", obras.get(i).getIdObra()).size()>0){
				gastosEstructura = new TesoreriaTitulo();
				gastosEstructura = tesoreriaService.getTesoreriaTituloPorTitulo("Gastos estructura", obras.get(i).getIdObra()).get(0);
				gastosEstructura.setTesorerias(tesoreriaService.getTesoreriasPorTitulo(gastosEstructura.getIdTesoreriaTitulo(), Integer.parseInt(year)));
				gastosEstructura.setTesorerias2(tesoreriaService.getTesoreriasPorTitulo(gastosEstructura.getIdTesoreriaTitulo(), Integer.parseInt(year)+1));
				gastosEstructura.setTesorerias3(tesoreriaService.getTesoreriasPorTitulo(gastosEstructura.getIdTesoreriaTitulo(), Integer.parseInt(year)+2));
				gastosEstructura.setTesorerias4(tesoreriaService.getTesoreriasPorTitulo(gastosEstructura.getIdTesoreriaTitulo(), Integer.parseInt(year)+3));
				gastosEstructura.setTesorerias5(tesoreriaService.getTesoreriasPorTitulo(gastosEstructura.getIdTesoreriaTitulo(), Integer.parseInt(year)+4));
			}
			Tesoreria tesoreria = new Tesoreria(obras.get(i).getNombreObra());
			Tesoreria tesoreriaGasto = new Tesoreria(obras.get(i).getNombreObra());
			tesoreriasIngresos.add(tesoreria);
			tesoreriasGastos.add(tesoreriaGasto);
			tesorerias = tesoreriaService.getTesorerias(obras.get(i).getIdObra(), Integer.parseInt(year));
			List<Tesoreria> anteriores=new ArrayList<Tesoreria>();
			anteriores=tesoreriaService.getTesoreriaAnterior(Integer.parseInt(year), obras.get(i).getIdObra());
			float anterior=0;
			int contador=0;
			for(int j=anteriores.size()-1;j>=0;j--){
				if(anteriores.get(j).getTesoreriaTitulo().getTitulo().equals("Ingresos")){
					if(contador==0 && anteriores.get(j).getTitulos().equals("Total ingreso líquido en cuenta")){
						anterior += anteriores.get(j).getTotal();
						contador++;
					}
				}
			}
			tesoreriasIngresos.set(i, createTesoreriaIngresos(tesoreriasIngresos.get(i), tesorerias, 0, anterior));
			float anteriorPersonal=0;
			float anteriorSubcontratas=0;
			float anteriorFacturas=0;
			float anteriorSinFactura=0;
			float anteriorFinancieros=0;
			float anteriorEstructura=0;
			contador=0;
			for(int j=anteriores.size()-1;j>=0;j--){
				if(anteriores.get(j).getTesoreriaTitulo().getTitulo().equals("Personal")){
					if(contador==0 && anteriores.get(j).getTitulos().equals("Total (con IVA)")){
						anteriorPersonal += anteriores.get(j).getTotal();
						contador++;
					}
				}
			}
			contador=0;
			for(int j=anteriores.size()-1;j>=0;j--){
				if(anteriores.get(j).getTesoreriaTitulo().getTitulo().equals("Subcontratas")){
					if(contador==0 && anteriores.get(j).getTitulos().equals("Total (con IVA)")){
						anteriorSubcontratas += anteriores.get(j).getTotal();
						contador++;
					}
				}
			}
			contador=0;
			for(int j=anteriores.size()-1;j>=0;j--){
				if(anteriores.get(j).getTesoreriaTitulo().getTitulo().equals("Gastos con factura")){
					if(contador==0 && anteriores.get(j).getTitulos().equals("Total (con IVA)")){
						anteriorFacturas += anteriores.get(j).getTotal();
						contador++;
					}
				}
			}
			contador=0;
			for(int j=anteriores.size()-1;j>=0;j--){
				if(anteriores.get(j).getTesoreriaTitulo().getTitulo().equals("Gastos sin factura")){
					if(contador==0 && anteriores.get(j).getTitulos().equals("Total (con IVA)")){
						anteriorSinFactura += anteriores.get(j).getTotal();
						contador++;
					}
				}
			}
			contador=0;
			for(int j=anteriores.size()-1;j>=0;j--){
				if(anteriores.get(j).getTesoreriaTitulo().getTitulo().equals("Gastos financieros")){
					if(contador==0 && anteriores.get(j).getTitulos().equals("Total (con IVA)")){
						anteriorFinancieros += anteriores.get(j).getTotal();
						contador++;
					}
				}
			}
			contador=0;
			for(int j=anteriores.size()-1;j>=0;j--){
				if(anteriores.get(j).getTesoreriaTitulo().getTitulo().equals("Gastos estructura")){
					if(contador==0 && anteriores.get(j).getTitulos().equals("Total (con IVA)")){
						anteriorEstructura += anteriores.get(j).getTotal();
						contador++;
					}
				}
			}
			tesoreriasGastos.set(i, createTesoreriaGastos(tesoreriasGastos.get(i), personal.getTesorerias(), subcontratas.getTesorerias(), gastosfactura.getTesorerias(), 
					gastosSinFactura.getTesorerias(), gastosFinancieros.getTesorerias(), gastosEstructura.getTesorerias(), 0, tesorerias, anteriorEstructura+anteriorFacturas+
					anteriorFinancieros+anteriorPersonal+anteriorSinFactura+anteriorSubcontratas));

			tesoreria = new Tesoreria(obras.get(i).getNombreObra());
			tesoreriaGasto = new Tesoreria(obras.get(i).getNombreObra());
			tesoreriasIngresos2.add(tesoreria);
			tesoreriasGastos2.add(tesoreriaGasto);
			tesorerias2 = tesoreriaService.getTesorerias(obras.get(i).getIdObra(), Integer.parseInt(year)+1);
			anteriores=new ArrayList<Tesoreria>();
			anteriores=tesoreriaService.getTesoreriaAnterior(Integer.parseInt(year), obras.get(i).getIdObra());
			if(tesorerias2.size()>0){
				tesoreriasIngresos2.set(i, createTesoreriaIngresos(tesoreriasIngresos2.get(i), tesorerias2, tesoreriasIngresos.get(i).getTotal(), tesoreriasIngresos.get(i).getDiciembre()));
				tesoreriasGastos2.set(i, createTesoreriaGastos(tesoreriasGastos2.get(i), personal.getTesorerias2(), subcontratas.getTesorerias2(), gastosfactura.getTesorerias2(), 
					gastosSinFactura.getTesorerias2(), gastosFinancieros.getTesorerias2(), gastosEstructura.getTesorerias2(), tesoreriasGastos.get(i).getTotal(), tesorerias2, 0));
			}
			else{
				tesoreriasIngresos2.set(i, createTesoreriaIngresos(tesoreriasIngresos2.get(i), new ArrayList<Tesoreria>(), tesoreriasIngresos.get(i).getTotal(), tesoreriasIngresos.get(i).getDiciembre()));
				tesoreriasGastos2.set(i, createTesoreriaGastos(tesoreriasGastos2.get(i), personal.getTesorerias2(), subcontratas.getTesorerias2(), gastosfactura.getTesorerias2(), 
					gastosSinFactura.getTesorerias2(), gastosFinancieros.getTesorerias2(), gastosEstructura.getTesorerias2(), tesoreriasGastos.get(i).getTotal(), new ArrayList<Tesoreria>(), 0));
			}
			tesoreria = new Tesoreria(obras.get(i).getNombreObra());
			tesoreriaGasto = new Tesoreria(obras.get(i).getNombreObra());
			tesoreriasIngresos3.add(tesoreria);
			tesoreriasGastos3.add(tesoreriaGasto);
			tesorerias3 = tesoreriaService.getTesorerias(obras.get(i).getIdObra(), Integer.parseInt(year)+2);
			anteriores=new ArrayList<Tesoreria>();
			anteriores=tesoreriaService.getTesoreriaAnterior(Integer.parseInt(year), obras.get(i).getIdObra());
			if(tesorerias3.size()>0){
				tesoreriasIngresos3.set(i, createTesoreriaIngresos(tesoreriasIngresos3.get(i), tesorerias3, tesoreriasIngresos2.get(i).getTotal(), tesoreriasIngresos2.get(i).getDiciembre()));
				tesoreriasGastos3.set(i, createTesoreriaGastos(tesoreriasGastos3.get(i), personal.getTesorerias3(), subcontratas.getTesorerias3(), gastosfactura.getTesorerias3(), 
					gastosSinFactura.getTesorerias3(), gastosFinancieros.getTesorerias3(), gastosEstructura.getTesorerias3(), tesoreriasGastos2.get(i).getTotal(), tesorerias3, 0));
			}
			else{
				tesoreriasIngresos3.set(i, createTesoreriaIngresos(tesoreriasIngresos3.get(i), new ArrayList<Tesoreria>(), tesoreriasIngresos2.get(i).getTotal(), tesoreriasIngresos2.get(i).getDiciembre()));
				tesoreriasGastos3.set(i, createTesoreriaGastos(tesoreriasGastos3.get(i), personal.getTesorerias3(), subcontratas.getTesorerias3(), gastosfactura.getTesorerias3(), 
					gastosSinFactura.getTesorerias3(), gastosFinancieros.getTesorerias3(), gastosEstructura.getTesorerias3(), tesoreriasGastos2.get(i).getTotal(), new ArrayList<Tesoreria>(), 0));
			}
			tesoreria = new Tesoreria(obras.get(i).getNombreObra());
			tesoreriaGasto = new Tesoreria(obras.get(i).getNombreObra());
			tesoreriasIngresos4.add(tesoreria);
			tesoreriasGastos4.add(tesoreriaGasto);
			tesorerias4 = tesoreriaService.getTesorerias(obras.get(i).getIdObra(), Integer.parseInt(year)+3);
			anteriores=new ArrayList<Tesoreria>();
			anteriores=tesoreriaService.getTesoreriaAnterior(Integer.parseInt(year), obras.get(i).getIdObra());
			if(tesorerias4.size()>0){
				tesoreriasIngresos4.set(i, createTesoreriaIngresos(tesoreriasIngresos4.get(i), tesorerias4, tesoreriasIngresos3.get(i).getTotal(), tesoreriasIngresos3.get(i).getDiciembre()));
				tesoreriasGastos4.set(i, createTesoreriaGastos(tesoreriasGastos4.get(i), personal.getTesorerias4(), subcontratas.getTesorerias4(), gastosfactura.getTesorerias4(), 
					gastosSinFactura.getTesorerias4(), gastosFinancieros.getTesorerias4(), gastosEstructura.getTesorerias4(), tesoreriasGastos3.get(i).getTotal(), tesorerias4, 0));
			}
			else{
				tesoreriasIngresos4.set(i, createTesoreriaIngresos(tesoreriasIngresos4.get(i), new ArrayList<Tesoreria>(), tesoreriasIngresos3.get(i).getTotal(), tesoreriasIngresos3.get(i).getDiciembre()));
				tesoreriasGastos4.set(i, createTesoreriaGastos(tesoreriasGastos4.get(i), personal.getTesorerias4(), subcontratas.getTesorerias4(), gastosfactura.getTesorerias4(), 
					gastosSinFactura.getTesorerias4(), gastosFinancieros.getTesorerias4(), gastosEstructura.getTesorerias4(), tesoreriasGastos3.get(i).getTotal(), new ArrayList<Tesoreria>(), 0));
			}
			tesoreria = new Tesoreria(obras.get(i).getNombreObra());
			tesoreriaGasto = new Tesoreria(obras.get(i).getNombreObra());
			tesoreriasIngresos5.add(tesoreria);
			tesoreriasGastos5.add(tesoreriaGasto);
			tesorerias5 = tesoreriaService.getTesorerias(obras.get(i).getIdObra(), Integer.parseInt(year)+4);
			anteriores=new ArrayList<Tesoreria>();
			anteriores=tesoreriaService.getTesoreriaAnterior(Integer.parseInt(year), obras.get(i).getIdObra());
			if(tesorerias5.size()>0){
				tesoreriasIngresos5.set(i, createTesoreriaIngresos(tesoreriasIngresos5.get(i), tesorerias5, tesoreriasIngresos4.get(i).getTotal(), tesoreriasIngresos4.get(i).getDiciembre()));
				tesoreriasGastos5.set(i, createTesoreriaGastos(tesoreriasGastos5.get(i), personal.getTesorerias5(), subcontratas.getTesorerias5(), gastosfactura.getTesorerias5(), 
					gastosSinFactura.getTesorerias5(), gastosFinancieros.getTesorerias5(), gastosEstructura.getTesorerias5(), tesoreriasGastos4.get(i).getTotal(), tesorerias5, 0));
			}
			else{
				tesoreriasIngresos5.set(i, createTesoreriaIngresos(tesoreriasIngresos5.get(i), new ArrayList<Tesoreria>(), tesoreriasIngresos4.get(i).getTotal(), tesoreriasIngresos4.get(i).getDiciembre()));
				tesoreriasGastos5.set(i, createTesoreriaGastos(tesoreriasGastos5.get(i), personal.getTesorerias5(), subcontratas.getTesorerias5(), gastosfactura.getTesorerias5(), 
					gastosSinFactura.getTesorerias5(), gastosFinancieros.getTesorerias5(), gastosEstructura.getTesorerias5(), tesoreriasGastos4.get(i).getTotal(), new ArrayList<Tesoreria>(), 0));
			}
		}
		tesoreriasIngresos.add(createTotales(tesoreriasIngresos, 1, 0));
		tesoreriasIngresos2.add(createTotales(tesoreriasIngresos2, 1, tesoreriasIngresos.get(tesoreriasIngresos.size()-1).getTotal()));
		tesoreriasIngresos3.add(createTotales(tesoreriasIngresos3, 1, tesoreriasIngresos2.get(tesoreriasIngresos2.size()-1).getTotal()));
		tesoreriasIngresos4.add(createTotales(tesoreriasIngresos4, 1, tesoreriasIngresos3.get(tesoreriasIngresos3.size()-1).getTotal()));
		tesoreriasIngresos5.add(createTotales(tesoreriasIngresos5, 1, tesoreriasIngresos4.get(tesoreriasIngresos4.size()-1).getTotal()));
		tesoreriasGastos.add(createTotales(tesoreriasGastos, 0, 0));
		tesoreriasGastos2.add(createTotales(tesoreriasGastos2, 0, tesoreriasGastos.get(tesoreriasGastos.size()-1).getTotal()));
		tesoreriasGastos3.add(createTotales(tesoreriasGastos3, 0, tesoreriasGastos2.get(tesoreriasGastos2.size()-1).getTotal()));
		tesoreriasGastos4.add(createTotales(tesoreriasGastos4, 0, tesoreriasGastos3.get(tesoreriasGastos3.size()-1).getTotal()));
		tesoreriasGastos5.add(createTotales(tesoreriasGastos5, 0, tesoreriasGastos4.get(tesoreriasGastos4.size()-1).getTotal()));
		
		createFlujoCaja();
		
	}
	
	*//**
	 * Crea el flujo de caja del resumen de las obras.
	 *//*
	public void createFlujoCaja(){
		if(flujoCaja.size()==0){
			flujoCaja.add(new Tesoreria());
			flujoCaja.add(new Tesoreria());
			flujoCaja.add(new Tesoreria());
			flujoCaja.add(new Tesoreria());
			flujoCaja.add(new Tesoreria());
			flujoCaja.set(1, tesoreriasIngresos.get(tesoreriasIngresos.size()-1));
			flujoCaja.get(1).setTitulos("Total ingresos");
			flujoCaja2.add(new Tesoreria());
			flujoCaja2.add(new Tesoreria());
			flujoCaja2.add(new Tesoreria());
			flujoCaja2.add(new Tesoreria());
			flujoCaja2.add(new Tesoreria());
			flujoCaja2.set(1, tesoreriasIngresos2.get(tesoreriasIngresos2.size()-1));
			flujoCaja2.get(1).setTitulos("Total ingresos");
			flujoCaja3.add(new Tesoreria());
			flujoCaja3.add(new Tesoreria());
			flujoCaja3.add(new Tesoreria());
			flujoCaja3.add(new Tesoreria());
			flujoCaja3.add(new Tesoreria());
			flujoCaja3.set(1, tesoreriasIngresos3.get(tesoreriasIngresos3.size()-1));
			flujoCaja3.get(1).setTitulos("Total ingresos");
			flujoCaja4.add(new Tesoreria());
			flujoCaja4.add(new Tesoreria());
			flujoCaja4.add(new Tesoreria());
			flujoCaja4.add(new Tesoreria());
			flujoCaja4.add(new Tesoreria());
			flujoCaja4.set(1, tesoreriasIngresos4.get(tesoreriasIngresos4.size()-1));
			flujoCaja4.get(1).setTitulos("Total ingresos");
			flujoCaja5.add(new Tesoreria());
			flujoCaja5.add(new Tesoreria());
			flujoCaja5.add(new Tesoreria());
			flujoCaja5.add(new Tesoreria());
			flujoCaja5.add(new Tesoreria());
			flujoCaja5.set(1, tesoreriasIngresos5.get(tesoreriasIngresos5.size()-1));
			flujoCaja5.get(1).setTitulos("Total ingresos");
		}

		flujoCaja.set(2, tesoreriasGastos.get(tesoreriasGastos.size()-1));
		flujoCaja.get(2).setTitulos("Total gastos");
		flujoCaja2.set(2, tesoreriasGastos2.get(tesoreriasGastos2.size()-1));
		flujoCaja2.get(2).setTitulos("Total gastos");
		flujoCaja3.set(2, tesoreriasGastos3.get(tesoreriasGastos3.size()-1));
		flujoCaja3.get(2).setTitulos("Total gastos");
		flujoCaja4.set(2, tesoreriasGastos4.get(tesoreriasGastos4.size()-1));
		flujoCaja4.get(2).setTitulos("Total gastos");
		flujoCaja5.set(2, tesoreriasGastos5.get(tesoreriasGastos5.size()-1));
		flujoCaja5.get(2).setTitulos("Total gastos");
		
		flujoCaja.set(3, resultadoParcial(tesoreriasGastos.get(tesoreriasGastos.size()-1), tesoreriasIngresos.get(tesoreriasIngresos.size()-1)));
		flujoCaja2.set(3, resultadoParcial(tesoreriasGastos2.get(tesoreriasGastos2.size()-1), tesoreriasIngresos2.get(tesoreriasIngresos2.size()-1)));
		flujoCaja3.set(3, resultadoParcial(tesoreriasGastos3.get(tesoreriasGastos3.size()-1), tesoreriasIngresos3.get(tesoreriasIngresos3.size()-1)));
		flujoCaja4.set(3, resultadoParcial(tesoreriasGastos4.get(tesoreriasGastos4.size()-1), tesoreriasIngresos4.get(tesoreriasIngresos4.size()-1)));
		flujoCaja5.set(3, resultadoParcial(tesoreriasGastos5.get(tesoreriasGastos5.size()-1), tesoreriasIngresos5.get(tesoreriasIngresos5.size()-1)));

		flujoCaja.set(4, creaSaldoResultado(tesoreriasGastos.get(tesoreriasGastos.size()-1), tesoreriasIngresos.get(tesoreriasIngresos.size()-1), null, 0).get(1));
		flujoCaja.set(0, creaSaldoResultado(tesoreriasGastos.get(tesoreriasGastos.size()-1), tesoreriasIngresos.get(tesoreriasIngresos.size()-1), null, 0).get(0));
		flujoCaja2.set(4, creaSaldoResultado(tesoreriasGastos2.get(tesoreriasGastos2.size()-1), tesoreriasIngresos2.get(tesoreriasIngresos2.size()-1), flujoCaja.get(4), 1).get(1));
		flujoCaja2.set(0, creaSaldoResultado(tesoreriasGastos2.get(tesoreriasGastos2.size()-1), tesoreriasIngresos2.get(tesoreriasIngresos2.size()-1), flujoCaja.get(4), 1).get(0));
		flujoCaja3.set(4, creaSaldoResultado(tesoreriasGastos3.get(tesoreriasGastos3.size()-1), tesoreriasIngresos3.get(tesoreriasIngresos3.size()-1), flujoCaja2.get(4), 2).get(1));
		flujoCaja3.set(0, creaSaldoResultado(tesoreriasGastos3.get(tesoreriasGastos3.size()-1), tesoreriasIngresos3.get(tesoreriasIngresos3.size()-1), flujoCaja2.get(4), 2).get(0));
		flujoCaja4.set(4, creaSaldoResultado(tesoreriasGastos4.get(tesoreriasGastos4.size()-1), tesoreriasIngresos4.get(tesoreriasIngresos4.size()-1), flujoCaja3.get(4), 3).get(1));
		flujoCaja4.set(0, creaSaldoResultado(tesoreriasGastos4.get(tesoreriasGastos4.size()-1), tesoreriasIngresos4.get(tesoreriasIngresos4.size()-1), flujoCaja3.get(4), 3).get(0));
		flujoCaja5.set(4, creaSaldoResultado(tesoreriasGastos5.get(tesoreriasGastos5.size()-1), tesoreriasIngresos5.get(tesoreriasIngresos5.size()-1), flujoCaja4.get(4), 4).get(1));
		flujoCaja5.set(0, creaSaldoResultado(tesoreriasGastos5.get(tesoreriasGastos5.size()-1), tesoreriasIngresos5.get(tesoreriasIngresos5.size()-1), flujoCaja4.get(4), 4).get(0));
	}
	
	*//**
	 * Crea el saldo de resultado del resumen de las tesorerías.
	 * @param gasto Tesorería con el gasto.
	 * @param ingreso Tesorería con el ingreso.
	 * @param anterior Tesorería acumulada de años anteriores.
	 * @param year Año al que corresponde la tesorería.
	 * @return Lista con el saldo de resultados.
	 *//*
	public List<Tesoreria> creaSaldoResultado(Tesoreria gasto, Tesoreria ingreso, Tesoreria anterior, int year){
		List<Tesoreria> tesorerias = new ArrayList<Tesoreria>();
		Tesoreria saldo = new Tesoreria();
		Tesoreria resultado = new Tesoreria();
		saldo.setTitulos("Saldo mes anterior");
		resultado.setTitulos("Resultado a origen");
		if(year==0){
			resultado.setAnteriores(ingreso.getAnteriores()-gasto.getAnteriores());
			saldo.setAnteriores(0);
		}
		else{
			saldo.setAnteriores(anterior.getDiciembre());
			resultado.setAnteriores(anterior.getDiciembre()+ingreso.getAnteriores()-gasto.getAnteriores());
		}
		saldo.setEnero(resultado.getAnteriores());
		resultado.setEnero(saldo.getEnero()+ingreso.getEnero()-gasto.getEnero());
		saldo.setFebrero(resultado.getEnero());
		resultado.setFebrero(saldo.getFebrero()+ingreso.getFebrero()-gasto.getFebrero());
		saldo.setMarzo(resultado.getFebrero());
		resultado.setMarzo(saldo.getMarzo()+ingreso.getMarzo()-gasto.getMarzo());
		saldo.setAbril(resultado.getMarzo());
		resultado.setAbril(saldo.getAbril()+ingreso.getAbril()-gasto.getAbril());
		saldo.setMayo(resultado.getAbril());
		resultado.setMayo(saldo.getMayo()+ingreso.getMayo()-gasto.getMayo());
		saldo.setJunio(resultado.getMayo());
		resultado.setJunio(saldo.getJunio()+ingreso.getJunio()-gasto.getJunio());
		saldo.setJulio(resultado.getJunio());
		resultado.setJulio(saldo.getJulio()+ingreso.getJulio()-gasto.getJulio());
		saldo.setAgosto(resultado.getJulio());
		resultado.setAgosto(saldo.getAgosto()+ingreso.getAgosto()-gasto.getAgosto());
		saldo.setSeptiembre(resultado.getAgosto());
		resultado.setSeptiembre(saldo.getSeptiembre()+ingreso.getSeptiembre()-gasto.getSeptiembre());
		saldo.setOctubre(resultado.getSeptiembre());
		resultado.setOctubre(saldo.getOctubre()+ingreso.getOctubre()-gasto.getOctubre());
		saldo.setNoviembre(resultado.getOctubre());
		resultado.setNoviembre(saldo.getNoviembre()+ingreso.getNoviembre()-gasto.getNoviembre());
		saldo.setDiciembre(resultado.getNoviembre());
		resultado.setDiciembre(saldo.getDiciembre()+ingreso.getDiciembre()-gasto.getDiciembre());
		tesorerias.add(saldo);
		tesorerias.add(resultado);
		return tesorerias;
	}
	
	*//**
	 * Crea el resultado parcial del resumen.
	 * @param gasto Gasto del resumen.
	 * @param ingreso Ingreso del resumen.
	 * @return Tesorería con el resultado parcial.
	 *//*
	public Tesoreria resultadoParcial(Tesoreria gasto, Tesoreria ingreso){
		Tesoreria tesoreria = new Tesoreria();
		tesoreria.setTitulos("Resultado parcial mes");
		tesoreria.setAnteriores(gasto.getAnteriores()-ingreso.getAnteriores());
		tesoreria.setEnero(gasto.getEnero()-ingreso.getEnero());
		tesoreria.setFebrero(gasto.getFebrero()-ingreso.getFebrero());
		tesoreria.setMarzo(gasto.getMarzo()-ingreso.getMarzo());
		tesoreria.setAbril(gasto.getAbril()-ingreso.getAbril());
		tesoreria.setMayo(gasto.getMayo()-ingreso.getMayo());
		tesoreria.setJunio(gasto.getJunio()-ingreso.getJunio());
		tesoreria.setJulio(gasto.getJulio()-ingreso.getJulio());
		tesoreria.setAgosto(gasto.getAgosto()-ingreso.getAgosto());
		tesoreria.setSeptiembre(gasto.getSeptiembre()-ingreso.getSeptiembre());
		tesoreria.setOctubre(gasto.getOctubre()-ingreso.getOctubre());
		tesoreria.setNoviembre(gasto.getNoviembre()-ingreso.getNoviembre());
		tesoreria.setDiciembre(gasto.getDiciembre()-ingreso.getDiciembre());
		tesoreria.setTotal(gasto.getTotal()-ingreso.getTotal());
		return tesoreria;
	}
	
	*//**
	 * Crea los totales para el resumen.
	 * @param tesorerias Lista de tesorerías para hacer el total.
	 * @param ingreso Indica si se tiene que hacer el total de ingresos o de gastos.
	 * @param totalAcumulado Acumulado de años anteriores.
	 * @return El total de la tesorería.
	 *//*
	public Tesoreria createTotales(List<Tesoreria> tesorerias, int ingreso, float totalAcumulado){
		Tesoreria tesoreria = new Tesoreria();
		if(ingreso==1)
			tesoreria.setTitulos("Total ingreso obras");
		else
			tesoreria.setTitulos("Total gasto obras");
		for(int i=0;i<tesorerias.size();i++){
			tesoreria.setAnteriores(tesoreria.getAnteriores()+tesorerias.get(i).getAnteriores());
			tesoreria.setEnero(tesoreria.getEnero()+tesorerias.get(i).getEnero());
			tesoreria.setFebrero(tesoreria.getFebrero()+tesorerias.get(i).getFebrero());
			tesoreria.setMarzo(tesoreria.getMarzo()+tesorerias.get(i).getMarzo());
			tesoreria.setAbril(tesoreria.getAbril()+tesorerias.get(i).getAbril());
			tesoreria.setMayo(tesoreria.getMayo()+tesorerias.get(i).getMayo());
			tesoreria.setJunio(tesoreria.getJunio()+tesorerias.get(i).getJunio());
			tesoreria.setJulio(tesoreria.getJulio()+tesorerias.get(i).getJulio());
			tesoreria.setAgosto(tesoreria.getAgosto()+tesorerias.get(i).getAgosto());
			tesoreria.setSeptiembre(tesoreria.getSeptiembre()+tesorerias.get(i).getSeptiembre());
			tesoreria.setOctubre(tesoreria.getOctubre()+tesorerias.get(i).getOctubre());
			tesoreria.setNoviembre(tesoreria.getNoviembre()+tesorerias.get(i).getNoviembre());
			tesoreria.setDiciembre(tesoreria.getDiciembre()+tesorerias.get(i).getDiciembre());
			tesoreria.setTotal(tesoreria.getAnteriores()+tesoreria.getEnero()+tesoreria.getFebrero()+tesoreria.getMarzo()+tesoreria.getAbril()+tesoreria.getMayo()+
					tesoreria.getJunio()+tesoreria.getJulio()+tesoreria.getAgosto()+tesoreria.getSeptiembre()+tesoreria.getOctubre()+
					tesoreria.getNoviembre()+tesoreria.getDiciembre()+totalAcumulado);
		}
		return tesoreria;
	}
	
	*//**
	 * Crea los ingresos para el resumen.
	 * @param tesoreria Tesorería donde se almacenarán los ingresos.
	 * @param tesorerias Lista de tesorerías que se utilizarán para calcular los ingresos.
	 * @param totalAcumulado Acumulado de años anteriores para el total.
	 * @param acumulado Acumulado de años anteriores.
	 * @return
	 *//*
	public Tesoreria createTesoreriaIngresos(Tesoreria tesoreria, List<Tesoreria> tesorerias, float totalAcumulado, float acumulado){
		tesoreria.setTitulos(tesoreria.getTitulos());
		tesoreria.setAnteriores(acumulado);
		if(tesorerias.size()>0){
			tesoreria.setYear(tesorerias.get(tesorerias.size()-1).getYear());
			tesoreria.setEnero(tesorerias.get(tesorerias.size()-1).getEnero());
			tesoreria.setFebrero(tesorerias.get(tesorerias.size()-1).getFebrero());
			tesoreria.setMarzo(tesorerias.get(tesorerias.size()-1).getMarzo());
			tesoreria.setAbril(tesorerias.get(tesorerias.size()-1).getAbril());
			tesoreria.setMayo(tesorerias.get(tesorerias.size()-1).getMayo());
			tesoreria.setJunio(tesorerias.get(tesorerias.size()-1).getJunio());
			tesoreria.setJulio(tesorerias.get(tesorerias.size()-1).getJulio());
			tesoreria.setAgosto(tesorerias.get(tesorerias.size()-1).getAgosto());
			tesoreria.setSeptiembre(tesorerias.get(tesorerias.size()-1).getSeptiembre());
			tesoreria.setOctubre(tesorerias.get(tesorerias.size()-1).getOctubre());
			tesoreria.setNoviembre(tesorerias.get(tesorerias.size()-1).getNoviembre());
			tesoreria.setDiciembre(tesorerias.get(tesorerias.size()-1).getDiciembre());
		}
		tesoreria.setTotal(tesoreria.getAnteriores()+tesoreria.getEnero()+tesoreria.getFebrero()+tesoreria.getMarzo()+tesoreria.getAbril()+tesoreria.getMayo()+
				tesoreria.getJunio()+tesoreria.getJulio()+tesoreria.getAgosto()+tesoreria.getSeptiembre()+tesoreria.getOctubre()+
				tesoreria.getNoviembre()+tesoreria.getDiciembre()+totalAcumulado);
		return tesoreria;
	}
	
	*//**
	 * Calcula el resumen para los gastos.
	 * @param tesoreria Tesorería donde se almacenarán los gastos calculados.
	 * @param personal Gastos de personal.
	 * @param subcontratas Gastos de subcontratas.
	 * @param gastosFactura Gastos con factura.
	 * @param gastosSinFactura Gastos sin factura.
	 * @param gastosFinancieros Gastos financieros.
	 * @param gastosEstructura Gastos de estructura.
	 * @param totalAcumulado Total acumulado de años anteriores para calcular el total.
	 * @param tesorerias 
	 * @param anteriores Acumulado de años anteriores.
	 * @return
	 *//*
	public Tesoreria createTesoreriaGastos(Tesoreria tesoreria, List<Tesoreria> personal, List<Tesoreria> subcontratas, List<Tesoreria> gastosFactura, 
			List<Tesoreria> gastosSinFactura, List<Tesoreria> gastosFinancieros, List<Tesoreria> gastosEstructura, float totalAcumulado, List<Tesoreria> tesorerias, float anteriores){
		tesoreria.setTitulos(tesoreria.getTitulos()); 
		tesoreria.setAnteriores(anteriores);
		if(tesorerias.size()>0){
		tesoreria.setEnero(personal.get(personal.size()-3).getEnero()+subcontratas.get(subcontratas.size()-3).getEnero()+gastosFactura.get(gastosFactura.size()-3).getEnero()+
				gastosSinFactura.get(gastosSinFactura.size()-3).getEnero()+gastosFinancieros.get(gastosFinancieros.size()-3).getEnero()+gastosEstructura.get(gastosEstructura.size()-3).getEnero());
		tesoreria.setFebrero(personal.get(personal.size()-3).getFebrero()+subcontratas.get(subcontratas.size()-3).getFebrero()+gastosFactura.get(gastosFactura.size()-3).getFebrero()+
				gastosSinFactura.get(gastosSinFactura.size()-3).getFebrero()+gastosFinancieros.get(gastosFinancieros.size()-3).getFebrero()+gastosEstructura.get(gastosEstructura.size()-3).getFebrero());
		tesoreria.setMarzo(personal.get(personal.size()-3).getMarzo()+subcontratas.get(subcontratas.size()-3).getMarzo()+gastosFactura.get(gastosFactura.size()-3).getMarzo()+
				gastosSinFactura.get(gastosSinFactura.size()-3).getMarzo()+gastosFinancieros.get(gastosFinancieros.size()-3).getMarzo()+gastosEstructura.get(gastosEstructura.size()-3).getMarzo());
		tesoreria.setAbril(personal.get(personal.size()-3).getAbril()+subcontratas.get(subcontratas.size()-3).getAbril()+gastosFactura.get(gastosFactura.size()-3).getAbril()+
				gastosSinFactura.get(gastosSinFactura.size()-3).getAbril()+gastosFinancieros.get(gastosFinancieros.size()-3).getAbril()+gastosEstructura.get(gastosEstructura.size()-3).getAbril());
		tesoreria.setMayo(personal.get(personal.size()-3).getMayo()+subcontratas.get(subcontratas.size()-3).getMayo()+gastosFactura.get(gastosFactura.size()-3).getMayo()+
				gastosSinFactura.get(gastosSinFactura.size()-3).getMayo()+gastosFinancieros.get(gastosFinancieros.size()-3).getMayo()+gastosEstructura.get(gastosEstructura.size()-3).getMayo());
		tesoreria.setJunio(personal.get(personal.size()-3).getJunio()+subcontratas.get(subcontratas.size()-3).getJunio()+gastosFactura.get(gastosFactura.size()-3).getJunio()+
				gastosSinFactura.get(gastosSinFactura.size()-3).getJunio()+gastosFinancieros.get(gastosFinancieros.size()-3).getJunio()+gastosEstructura.get(gastosEstructura.size()-3).getJunio());
		tesoreria.setJulio(personal.get(personal.size()-3).getJulio()+subcontratas.get(subcontratas.size()-3).getJulio()+gastosFactura.get(gastosFactura.size()-3).getJulio()+
				gastosSinFactura.get(gastosSinFactura.size()-3).getJulio()+gastosFinancieros.get(gastosFinancieros.size()-3).getJulio()+gastosEstructura.get(gastosEstructura.size()-3).getJulio());
		tesoreria.setAgosto(personal.get(personal.size()-3).getAgosto()+subcontratas.get(subcontratas.size()-3).getAgosto()+gastosFactura.get(gastosFactura.size()-3).getAgosto()+
				gastosSinFactura.get(gastosSinFactura.size()-3).getAgosto()+gastosFinancieros.get(gastosFinancieros.size()-3).getAgosto()+gastosEstructura.get(gastosEstructura.size()-3).getAgosto());
		tesoreria.setSeptiembre(personal.get(personal.size()-3).getSeptiembre()+subcontratas.get(subcontratas.size()-3).getSeptiembre()+gastosFactura.get(gastosFactura.size()-3).getSeptiembre()+
				gastosSinFactura.get(gastosSinFactura.size()-3).getSeptiembre()+gastosFinancieros.get(gastosFinancieros.size()-3).getSeptiembre()+gastosEstructura.get(gastosEstructura.size()-3).getSeptiembre());
		tesoreria.setOctubre(personal.get(personal.size()-3).getOctubre()+subcontratas.get(subcontratas.size()-3).getOctubre()+gastosFactura.get(gastosFactura.size()-3).getOctubre()+
				gastosSinFactura.get(gastosSinFactura.size()-3).getOctubre()+gastosFinancieros.get(gastosFinancieros.size()-3).getOctubre()+gastosEstructura.get(gastosEstructura.size()-3).getOctubre());
		tesoreria.setNoviembre(personal.get(personal.size()-3).getNoviembre()+subcontratas.get(subcontratas.size()-3).getNoviembre()+gastosFactura.get(gastosFactura.size()-3).getNoviembre()+
				gastosSinFactura.get(gastosSinFactura.size()-3).getNoviembre()+gastosFinancieros.get(gastosFinancieros.size()-3).getNoviembre()+gastosEstructura.get(gastosEstructura.size()-3).getNoviembre());
		tesoreria.setDiciembre(personal.get(personal.size()-3).getDiciembre()+subcontratas.get(subcontratas.size()-3).getDiciembre()+gastosFactura.get(gastosFactura.size()-3).getDiciembre()+
				gastosSinFactura.get(gastosSinFactura.size()-3).getDiciembre()+gastosFinancieros.get(gastosFinancieros.size()-3).getDiciembre()+gastosEstructura.get(gastosEstructura.size()-3).getDiciembre());
		}
		tesoreria.setTotal(tesoreria.getAnteriores()+tesoreria.getEnero()+tesoreria.getFebrero()+tesoreria.getMarzo()+tesoreria.getAbril()+tesoreria.getMayo()+
				tesoreria.getJunio()+tesoreria.getJulio()+tesoreria.getAgosto()+tesoreria.getSeptiembre()+tesoreria.getOctubre()+
				tesoreria.getNoviembre()+tesoreria.getDiciembre()+totalAcumulado);
		return tesoreria;
	}
	
	public List<Obra> getObras() {
		return obras;
	}
	public void setObras(List<Obra> obras) {
		this.obras = obras;
	}
	public IObraService getObraService() {
		return obraService;
	}
	public void setObraService(IObraService obraService) {
		this.obraService = obraService;
	}
	public ITesoreriaIngresoService getTesoreriaIngresoService() {
		return tesoreriaIngresoService;
	}
	public void setTesoreriaIngresoService(
			ITesoreriaIngresoService tesoreriaIngresoService) {
		this.tesoreriaIngresoService = tesoreriaIngresoService;
	}
	public ITesoreriaGastoService getTesoreriaGastoService() {
		return tesoreriaGastoService;
	}
	public void setTesoreriaGastoService(
			ITesoreriaGastoService tesoreriaGastoService) {
		this.tesoreriaGastoService = tesoreriaGastoService;
	}
	public ITesoreriaService getTesoreriaService() {
		return tesoreriaService;
	}
	public void setTesoreriaService(ITesoreriaService tesoreriaService) {
		this.tesoreriaService = tesoreriaService;
	}
	public List<Tesoreria> getTesoreriasIngresos() {
		return tesoreriasIngresos;
	}
	public void setTesoreriasIngresos(List<Tesoreria> tesoreriasIngresos) {
		this.tesoreriasIngresos = tesoreriasIngresos;
	}
	public List<Tesoreria> getTesoreriasIngresos2() {
		return tesoreriasIngresos2;
	}
	public void setTesoreriasIngresos2(List<Tesoreria> tesoreriasIngresos2) {
		this.tesoreriasIngresos2 = tesoreriasIngresos2;
	}
	public List<Tesoreria> getTesoreriasIngresos3() {
		return tesoreriasIngresos3;
	}
	public void setTesoreriasIngresos3(List<Tesoreria> tesoreriasIngresos3) {
		this.tesoreriasIngresos3 = tesoreriasIngresos3;
	}
	public List<Tesoreria> getTesoreriasIngresos4() {
		return tesoreriasIngresos4;
	}
	public void setTesoreriasIngresos4(List<Tesoreria> tesoreriasIngresos4) {
		this.tesoreriasIngresos4 = tesoreriasIngresos4;
	}
	public List<Tesoreria> getTesoreriasIngresos5() {
		return tesoreriasIngresos5;
	}
	public void setTesoreriasIngresos5(List<Tesoreria> tesoreriasIngresos5) {
		this.tesoreriasIngresos5 = tesoreriasIngresos5;
	}
	public List<Tesoreria> getTesoreriasGastos() {
		return tesoreriasGastos;
	}
	public void setTesoreriasGastos(List<Tesoreria> tesoreriasGastos) {
		this.tesoreriasGastos = tesoreriasGastos;
	}
	public List<Tesoreria> getTesoreriasGastos2() {
		return tesoreriasGastos2;
	}
	public void setTesoreriasGastos2(List<Tesoreria> tesoreriasGastos2) {
		this.tesoreriasGastos2 = tesoreriasGastos2;
	}
	public List<Tesoreria> getTesoreriasGastos3() {
		return tesoreriasGastos3;
	}
	public void setTesoreriasGastos3(List<Tesoreria> tesoreriasGastos3) {
		this.tesoreriasGastos3 = tesoreriasGastos3;
	}
	public List<Tesoreria> getTesoreriasGastos4() {
		return tesoreriasGastos4;
	}
	public void setTesoreriasGastos4(List<Tesoreria> tesoreriasGastos4) {
		this.tesoreriasGastos4 = tesoreriasGastos4;
	}
	public List<Tesoreria> getTesoreriasGastos5() {
		return tesoreriasGastos5;
	}
	public void setTesoreriasGastos5(List<Tesoreria> tesoreriasGastos5) {
		this.tesoreriasGastos5 = tesoreriasGastos5;
	}

	public List<Tesoreria> getFlujoCaja() {
		return flujoCaja;
	}

	public void setFlujoCaja(List<Tesoreria> flujoCaja) {
		this.flujoCaja = flujoCaja;
	}

	public List<Tesoreria> getFlujoCaja2() {
		return flujoCaja2;
	}

	public void setFlujoCaja2(List<Tesoreria> flujoCaja2) {
		this.flujoCaja2 = flujoCaja2;
	}

	public List<Tesoreria> getFlujoCaja3() {
		return flujoCaja3;
	}

	public void setFlujoCaja3(List<Tesoreria> flujoCaja3) {
		this.flujoCaja3 = flujoCaja3;
	}

	public List<Tesoreria> getFlujoCaja4() {
		return flujoCaja4;
	}

	public void setFlujoCaja4(List<Tesoreria> flujoCaja4) {
		this.flujoCaja4 = flujoCaja4;
	}

	public List<Tesoreria> getFlujoCaja5() {
		return flujoCaja5;
	}

	public void setFlujoCaja5(List<Tesoreria> flujoCaja5) {
		this.flujoCaja5 = flujoCaja5;
	}

	public int getFirstYear() {
		return firstYear;
	}

	public void setFirstYear(int firstYear) {
		this.firstYear = firstYear;
	}

	public int getSecondYear() {
		return secondYear;
	}

	public void setSecondYear(int secondYear) {
		this.secondYear = secondYear;
	}

	public int getThirdYear() {
		return thirdYear;
	}

	public void setThirdYear(int thirdYear) {
		this.thirdYear = thirdYear;
	}

	public int getFourthYear() {
		return fourthYear;
	}

	public void setFourthYear(int fourthYear) {
		this.fourthYear = fourthYear;
	}

	public int getFifthYear() {
		return fifthYear;
	}

	public void setFifthYear(int fifthYear) {
		this.fifthYear = fifthYear;
	}*/
}
