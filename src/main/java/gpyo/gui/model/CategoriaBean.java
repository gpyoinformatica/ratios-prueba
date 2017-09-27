package gpyo.gui.model;

import gpyo.persistence.entity.admin.Banco;
import gpyo.persistence.entity.admin.Empresa;
import gpyo.persistence.entity.admin.Gasto;
import gpyo.persistence.entity.admin.Ingreso;
import gpyo.persistence.entity.admin.Obra;
import gpyo.persistence.entity.admin.Usuario;
import gpyo.persistence.entity.constants.Months;
import gpyo.service.businesslogic.IBancoService;
import gpyo.service.businesslogic.IEmpresaService;
import gpyo.service.businesslogic.IObraService;
import gpyo.service.businesslogic.ITesoreriaGastoService;
import gpyo.service.businesslogic.ITesoreriaIngresoService;
import gpyo.service.businesslogic.ITesoreriaService;
import gpyo.service.businesslogic.IUserService;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

@ManagedBean
@ViewScoped
@Controller
public class CategoriaBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7098087093702702471L;
	
	private String titulo;
	private List<String> titulos = new ArrayList<String>();
	private boolean otroTitulo;
	private String categoriaGasto;
	private String categoriaIngreso;
	private List<String> categoriaIngresos = new ArrayList<String>();
	private List<String> obras = new ArrayList<String>();
	@Autowired
	private ITesoreriaService tesoreriaService;
	@Autowired
	private IObraService obraService;
	@Autowired
	private IUserService userService;
	Date currentDate;
	Date fecha;
	private Date fechaCobro;
	private Date fechaVencimiento;
	private Date fechaPago;
	private Date fechaRecepcion;
	private Date vencimiento;
	private Gasto gasto = new Gasto();
	private Ingreso ingreso = new Ingreso();
	private String nombreObra;
	private String newNombreObra;
	private String newTitulo;
	@Autowired
	private ITesoreriaGastoService tesoreriaGastoService;
	@Autowired
	private ITesoreriaIngresoService tesoreriaIngresoService;
	@Autowired
	private IBancoService bancoService;
	@Autowired
	private IEmpresaService empresaService;
	private float cantidadGasto;
	private String conceptoGasto;
	private float cantidadIngreso;
	private String conceptoIngreso;
	private Usuario usuario = new Usuario();
	private List<Gasto> gastos = new ArrayList<Gasto>();
	private List<Ingreso> ingresos = new ArrayList<Ingreso>();
	private float iva = 21;
	private float iva2 = 0;
	private float irpfSpGasto;
	private float irpfSpIngreso;
	private float irpfAlquilerGasto;
	private List<String> catGastos = new ArrayList<String>();
	private List<String> tipoCostesEstructurales = new ArrayList<String>();
	private List<String> centroCostes = new ArrayList<String>();
	private List<String> formaPagos = new ArrayList<String>();
	private List<String> prevision = new ArrayList<String>();
	private List<String> estados = new ArrayList<String>();
	private List<String> meses = new ArrayList<String>();
	private List<String> trimestres = new ArrayList<String>();
	private Date fechaMinima;
	private Date fechaMaxima;
	private Date fechaMinimaFiltroFactura;
	private Date fechaMaximaFiltroFactura;
	private Date fechaMinimaFiltroVencimiento;
	private Date fechaMaximaFiltroVencimiento;
	private String filtroFormaPago;
	private String filtroEstado;
	private String filtroCategoria;
	private String filtroEmpresa;
	private String filtroObra;
	private String filtroConcepto;
	private String filtroBanco;
	private String filtroMes;
	private String filtroMesFactura;
	private String filtroTrimestre;
	private float irpfAlquilerIngreso;
	private int tiempoCobro;
	private int tiempoPago;
	private List<String> bancos = new ArrayList<String>();
	private List<Banco> infoBancos = new ArrayList<Banco>();
	private String empresa;
	private List<String> empresas = new ArrayList<String>();
	private boolean existeGasto = false;
	private List<Gasto> gastosComprobar = new ArrayList<Gasto>(); 
	
	public void showBancos(){
		infoBancos =  new ArrayList<Banco>();
		infoBancos = bancoService.getBancos();
	}
	
	public void checkGasto(){
		List<Gasto> gastosCheckCif = new ArrayList<Gasto>();
		List<Gasto> gastosCheckMes = new ArrayList<Gasto>();
		gastosCheckCif = tesoreriaGastoService.getGastos1();
		Calendar fechaPrueba = Calendar.getInstance();
		fechaPrueba.setTime(fecha);
		String fechaGasto = "";
		if(fechaPrueba.get(Calendar.MONTH)<10)
			fechaGasto = fechaGasto+"0"+String.valueOf(fechaPrueba.get(Calendar.MONTH)+1)+"-"+String.valueOf(fechaPrueba.get(Calendar.YEAR));
		else
			fechaGasto = fechaGasto+String.valueOf(fechaPrueba.get(Calendar.MONTH)+1)+"-"+String.valueOf(fechaPrueba.get(Calendar.YEAR));
		for(Gasto g : gastosCheckCif)
			if(g.getCif().equals(gasto.getCif()))
				gastosCheckMes.add(g);
		for(Gasto g : gastosCheckMes){
			String mesLista = ""; 
			mesLista = mesLista+g.getFecha().charAt(3)+g.getFecha().charAt(4)+"-"+g.getFecha().charAt(6)+g.getFecha().charAt(7)+g.getFecha().charAt(8)+g.getFecha().charAt(9);
			if(mesLista.equals(fechaGasto) && g.getPrevision().equals("Previsión"))
				gastosComprobar.add(g);
		}
		if(gastosComprobar.size()>0)
			existeGasto = true;
	}
	
	public void viewCarsCustomized() {
		checkGasto();
		if(gastosComprobar.size()>0)
			RequestContext.getCurrentInstance().execute("dialogWidget.show()");
		else
			saveGasto();
    }
	
	public void corregirGastos() throws ParseException{
		List<Gasto> corregir = new ArrayList<Gasto>();
		corregir = tesoreriaGastoService.getGastos1();
		for(int i=0;i<corregir.size();i++){
			if(corregir.get(i).getFecha()!=null){
				String fecha2="";
				fecha2=fecha2+corregir.get(i).getFecha().charAt(6)+corregir.get(i).getFecha().charAt(7)+corregir.get(i).getFecha().charAt(8)
						+corregir.get(i).getFecha().charAt(9)+corregir.get(i).getFecha().charAt(5)+corregir.get(i).getFecha().charAt(3)
						+corregir.get(i).getFecha().charAt(4)+corregir.get(i).getFecha().charAt(2)+corregir.get(i).getFecha().charAt(0)+corregir.get(i).getFecha().charAt(1);
				corregir.get(i).setFechaSort(fecha2);
			}
			if(corregir.get(i).getFechaPago()!=null){
				String fecha2="";
				fecha2=fecha2+corregir.get(i).getFechaPago().charAt(6)+corregir.get(i).getFechaPago().charAt(7)+corregir.get(i).getFechaPago().charAt(8)
						+corregir.get(i).getFechaPago().charAt(9)+corregir.get(i).getFechaPago().charAt(5)+corregir.get(i).getFechaPago().charAt(3)
						+corregir.get(i).getFechaPago().charAt(4)+corregir.get(i).getFechaPago().charAt(2)+corregir.get(i).getFechaPago().charAt(0)+corregir.get(i).getFechaPago().charAt(1);
				corregir.get(i).setFechaPagoSort(fecha2);
			}
			corregir.get(i).setPerIva((corregir.get(i).getIva()*100)/corregir.get(i).getCantidad());
			corregir.get(i).setPerIva2((corregir.get(i).getIva2()*100)/corregir.get(i).getCantidad());
			corregir.get(i).setPerIrpfAlquiler((corregir.get(i).getIrpfAlquiler()*100)/corregir.get(i).getCantidad());
			corregir.get(i).setPerIrpfSp((corregir.get(i).getIrpfSp()*100)/corregir.get(i).getCantidad());
			if(corregir.get(i).getObra()!=null)
				tesoreriaGastoService.updateGasto(corregir.get(i));
		}
	}
	
	public void corregirIngresos() throws ParseException{
		List<Ingreso> corregir = new ArrayList<Ingreso>();
		corregir = tesoreriaIngresoService.getIngresos();
		for(int i=0;i<corregir.size();i++){
			if(corregir.get(i).getFecha()!=null){
				String fecha2="";
				fecha2=fecha2+corregir.get(i).getFecha().charAt(6)+corregir.get(i).getFecha().charAt(7)+corregir.get(i).getFecha().charAt(8)
						+corregir.get(i).getFecha().charAt(9)+corregir.get(i).getFecha().charAt(5)+corregir.get(i).getFecha().charAt(3)
						+corregir.get(i).getFecha().charAt(4)+corregir.get(i).getFecha().charAt(2)+corregir.get(i).getFecha().charAt(0)+corregir.get(i).getFecha().charAt(1);
				corregir.get(i).setFechaSort(fecha2);
			}
			if(corregir.get(i).getFechaCobro()!=null){
				String fecha2="";
				fecha2=fecha2+corregir.get(i).getFechaCobro().charAt(6)+corregir.get(i).getFechaCobro().charAt(7)+corregir.get(i).getFechaCobro().charAt(8)
						+corregir.get(i).getFechaCobro().charAt(9)+corregir.get(i).getFechaCobro().charAt(5)+corregir.get(i).getFechaCobro().charAt(3)
						+corregir.get(i).getFechaCobro().charAt(4)+corregir.get(i).getFechaCobro().charAt(2)+corregir.get(i).getFechaCobro().charAt(0)+corregir.get(i).getFechaCobro().charAt(1);
				corregir.get(i).setFechaCobroSort(fecha2);
			}
			if(corregir.get(i).getIva()!=0)
				corregir.get(i).setPerIva((corregir.get(i).getIva()*100)/corregir.get(i).getTotalIngreso());
			if(corregir.get(i).getIrpfAlquiler()!=0)
				corregir.get(i).setPerIrpfAlquiler((corregir.get(i).getIrpfAlquiler()*100)/corregir.get(i).getTotalIngreso());
			if(corregir.get(i).getIrpfSp()!=0)
				corregir.get(i).setPerIrpfSp((corregir.get(i).getIrpfSp()*100)/corregir.get(i).getTotalIngreso());
			tesoreriaIngresoService.updateIngreso(corregir.get(i));
		}
	}
	
	public void cancelarGuardado(){
		FacesMessage msg = new FacesMessage("El gasto no se ha guardado");
        FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public void saveGasto(){
		Usuario usuario=new Usuario();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		usuario = userService.getUserByName(nombreUsuario);
		gasto.setFecha(sdf.format(fecha));
		gasto.setFechaSort(sdf2.format(fecha));
		if(fechaPago!=null){
			gasto.setFechaPago(sdf.format(fechaPago));
			gasto.setFechaPagoSort(sdf2.format(fechaPago));
		}
		if(fechaRecepcion!=null)
			gasto.setFechaRecepcionFactura(sdf.format(fechaRecepcion));
		if(vencimiento!=null)
			gasto.setVencimiento(sdf.format(vencimiento)); 
		if(nombreObra.length()>0){
			Obra obra = new Obra();
			obra = obraService.getObraByNombre(nombreObra);
			gasto.setObra(obra);
			gasto.setCategoria(nombreObra);
			gasto.setUsuario(usuario);
			gasto.setPerIva(iva);
			gasto.setPerIva2(iva2);
			gasto.setPerIrpfAlquiler(irpfAlquilerGasto);
			gasto.setPerIrpfSp(irpfSpGasto);
			gasto.setIva((iva*gasto.getBaseImponible())/100);
			gasto.setIva2((iva2*gasto.getBaseImponible())/100);
			gasto.setIrpfAlquiler((irpfAlquilerGasto*gasto.getBaseImponible())/100);
			gasto.setIrpfSp((irpfSpGasto*gasto.getBaseImponible())/100);
			float yaPagado = tesoreriaGastoService.pendientePago(gasto.getFactura());
			gasto.setCantidad(gasto.getBaseImponible()+gasto.getIva()+gasto.getIva2()-(gasto.getIrpfAlquiler()+gasto.getIrpfSp()));
			gasto.setPendiente(gasto.getCantidad()-gasto.getPagado()-yaPagado);
			if(gasto.getPendiente()==0)
				gasto.setEstado("PAGADO");
			else
				gasto.setEstado("PENDIENTE");
			tesoreriaGastoService.saveGasto(gasto);
			Banco banco = new Banco();
			banco = bancoService.getBanco(gasto.getBanco());
			banco.setFondos(banco.getFondos()-gasto.getPagado());
		//	if(gasto.getPrevision().equals("Real"))
				bancoService.updateFondos(banco);
			showGastos();
			FacesMessage msg = new FacesMessage("Gasto guardado");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		else{
			FacesMessage msg = new FacesMessage("El gasto no se ha podido guardar.");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public void showGastos(){
		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		usuario = userService.getUserByName(nombreUsuario);
		setGastos(new ArrayList<Gasto>());
		setGastos(tesoreriaGastoService.getGastos(usuario.getId()));
	}
	
	public void showIngresos(){
		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		usuario = userService.getUserByName(nombreUsuario);
		setIngresos(new ArrayList<Ingreso>());
		setIngresos(tesoreriaIngresoService.getIngresos(usuario.getId()));
	}
	
	public void saveIngreso(){
		Usuario usuario=new Usuario();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		usuario = userService.getUserByName(nombreUsuario);
		ingreso.setFecha(sdf.format(fecha));
		ingreso.setFechaSort(sdf2.format(fecha));
		if(fechaCobro!=null){
			ingreso.setFechaCobro(sdf.format(fechaCobro));
			ingreso.setFechaCobroSort(sdf2.format(fechaCobro));
		}
		if(fechaVencimiento!=null)
			ingreso.setFechaVencimiento(sdf.format(fechaVencimiento));
		if(nombreObra.length()>0){
			Obra obra = new Obra();
			obra = obraService.getObraByNombre(nombreObra);
			ingreso.setObra(obra);
			ingreso.setUsuario(usuario);
			ingreso.setPerIva(iva);
			ingreso.setPerIrpfAlquiler(irpfAlquilerIngreso);
			ingreso.setPerIrpfSp(irpfSpIngreso);
			ingreso.setIrpfAlquiler((irpfAlquilerIngreso*ingreso.getBaseImponible())/100);
			ingreso.setIrpfSp((irpfSpIngreso*ingreso.getBaseImponible())/100);
			ingreso.setIva((iva*ingreso.getBaseImponible())/100);
			float yaCobrado = tesoreriaIngresoService.pendientePago(ingreso.getnFactura());
			ingreso.setTotalIngreso(ingreso.getBaseImponible()-(ingreso.getIrpfAlquiler()+ingreso.getIrpfSp())+ingreso.getIva());
			ingreso.setPendiente(ingreso.getTotalIngreso()-ingreso.getIngreso()-yaCobrado);
			if(ingreso.getPendiente()==0)
				ingreso.setEstado("PAGADO");
			else
				ingreso.setEstado("PENDIENTE");
			tesoreriaIngresoService.saveIngreso(ingreso);
			Banco banco = new Banco();
			banco = bancoService.getBanco(ingreso.getBanco());
			banco.setFondos(banco.getFondos()+ingreso.getTotalIngreso());
		//	if(ingreso.getPrevision().equals("Real"))
				bancoService.updateFondos(banco);
			showIngresos();
			FacesMessage msg = new FacesMessage("Gasto guardado");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		else{
			FacesMessage msg = new FacesMessage("El gasto no se ha podido guardar.");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
    public void deleteGasto(Gasto gasto){
    	tesoreriaGastoService.deleteGasto(gasto);
		Banco banco = new Banco();
		banco = bancoService.getBanco(gasto.getBanco());
		banco.setFondos(banco.getFondos()+gasto.getPagado());
		if(gasto.getPrevision().equals("Real"))
			bancoService.updateFondos(banco);
    	showGastos();
    	
    	FacesMessage msg = new FacesMessage("Gasto eliminado", (gasto).getConcepto());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void deleteIngreso(Ingreso tesoreriaIngreso){
    	tesoreriaIngresoService.deleteIngreso(tesoreriaIngreso);
		Banco banco = new Banco();
		banco = bancoService.getBanco(tesoreriaIngreso.getBanco());
		banco.setFondos(banco.getFondos()-tesoreriaIngreso.getTotalIngreso());
		if(tesoreriaIngreso.getPrevision().equals("Real"))
			bancoService.updateFondos(banco);
    	showIngresos();
    	
    	FacesMessage msg = new FacesMessage("Ingreso eliminado", (tesoreriaIngreso).getOperacion());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
	
	
	public void onEditGasto(RowEditEvent event) {
		Gasto tesoreriaGasto=(Gasto) event.getObject();
		String fecha=tesoreriaGasto.getFecha();
		String fecha2="";
		fecha2=fecha2+fecha.charAt(6)+fecha.charAt(7)+fecha.charAt(8)+fecha.charAt(9)+fecha.charAt(5)+fecha.charAt(3)+fecha.charAt(4)+fecha.charAt(2)+fecha.charAt(0)+fecha.charAt(1);
		tesoreriaGasto.setIva((tesoreriaGasto.getBaseImponible()*tesoreriaGasto.getPerIva())/100);
		tesoreriaGasto.setIva2((tesoreriaGasto.getBaseImponible()*tesoreriaGasto.getPerIva2())/100);
		tesoreriaGasto.setIrpfAlquiler((tesoreriaGasto.getBaseImponible()*tesoreriaGasto.getPerIrpfAlquiler())/100);
		tesoreriaGasto.setIrpfSp((tesoreriaGasto.getBaseImponible()*tesoreriaGasto.getPerIrpfSp())/100);
		tesoreriaGasto.setFechaSort(fecha2);
		tesoreriaGasto.setCantidad(tesoreriaGasto.getBaseImponible()+tesoreriaGasto.getIva()+tesoreriaGasto.getIva2()-(tesoreriaGasto.getIrpfAlquiler()+tesoreriaGasto.getIrpfSp()));
		tesoreriaGasto.setPendiente(tesoreriaGasto.getCantidad()-tesoreriaGasto.getPagado()/*-yaPagado*/);
		if(tesoreriaGasto.getPendiente()==0)
			tesoreriaGasto.setEstado("PAGADO");
		else
			tesoreriaGasto.setEstado("PENDIENTE");
	 	tesoreriaGastoService.updateGasto(tesoreriaGasto);
	 	
        FacesMessage msg = new FacesMessage("Gasto editado", ((Gasto) event.getObject()).getConcepto());
        FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public void onEditIngreso(RowEditEvent event) {
		Ingreso tesoreriaIngreso=(Ingreso) event.getObject();
		String fecha=tesoreriaIngreso.getFecha();
		String fecha2="";
		fecha2=fecha2+fecha.charAt(6)+fecha.charAt(7)+fecha.charAt(8)+fecha.charAt(9)+fecha.charAt(5)+fecha.charAt(3)+fecha.charAt(4)+fecha.charAt(2)+fecha.charAt(0)+fecha.charAt(1);
		tesoreriaIngreso.setFechaSort(fecha2);
		tesoreriaIngreso.setIva((tesoreriaIngreso.getBaseImponible()*tesoreriaIngreso.getPerIva())/100);
		tesoreriaIngreso.setIrpfAlquiler((tesoreriaIngreso.getBaseImponible()*tesoreriaIngreso.getPerIrpfAlquiler())/100);
		tesoreriaIngreso.setIrpfSp((tesoreriaIngreso.getBaseImponible()*tesoreriaIngreso.getPerIrpfSp())/100);
		tesoreriaIngreso.setTotalIngreso(tesoreriaIngreso.getBaseImponible()-(tesoreriaIngreso.getIrpfAlquiler()+tesoreriaIngreso.getIrpfSp())+tesoreriaIngreso.getIva());
		tesoreriaIngreso.setPendiente(tesoreriaIngreso.getTotalIngreso()-tesoreriaIngreso.getIngreso()/*-yaCobrado*/);
		if(tesoreriaIngreso.getPendiente()==0)
			tesoreriaIngreso.setEstado("PAGADO");
		else
			tesoreriaIngreso.setEstado("PENDIENTE");
	 	tesoreriaIngresoService.updateIngreso(tesoreriaIngreso);
	 	
        FacesMessage msg = new FacesMessage("Ingreso editado", ((Ingreso) event.getObject()).getOperacion());
        FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	 public void onCancelGasto(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edición cancelada", ((Gasto) event.getObject()).getConcepto());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
	    
    public void onCancelIngreso(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edición cancelada", ((Ingreso) event.getObject()).getOperacion());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
	
	public void filtroPorFechasFactura() throws ParseException{
		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		usuario = userService.getUserByName(nombreUsuario);
		List<Gasto> gastoSinFiltro = new ArrayList<Gasto>();
 		gastoSinFiltro = tesoreriaGastoService.getGastos(usuario.getId());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
 		for(int i=0;i<gastoSinFiltro.size();i++){
 			Date fechaFactura = sdf.parse(gastoSinFiltro.get(i).getFecha());
 			if(fechaFactura.compareTo(fechaMinimaFiltroFactura)<0 || fechaMaximaFiltroFactura.compareTo(fechaFactura)<0){
 				gastoSinFiltro.remove(i);
 				i--;
 			}
 		}
 		setGastos(gastoSinFiltro);
	}
	
	public void filtroIngresoPorFechasFactura() throws ParseException{
		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		usuario = userService.getUserByName(nombreUsuario);
		List<Ingreso> ingresoSinFiltro = new ArrayList<Ingreso>();
 		ingresoSinFiltro = tesoreriaIngresoService.getIngresos(usuario.getId());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
 		for(int i=0;i<ingresoSinFiltro.size();i++){
 			Date fechaFactura = sdf.parse(ingresoSinFiltro.get(i).getFechaCobro());
 			if(fechaFactura.compareTo(fechaMinimaFiltroFactura)<0 || fechaMaximaFiltroFactura.compareTo(fechaFactura)<0){
 				ingresoSinFiltro.remove(i);
 				i--;
 			}
 		}
 		setIngresos(ingresoSinFiltro);
	}
	
	public void filtroPorFechasVencimiento() throws ParseException{
		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		usuario = userService.getUserByName(nombreUsuario);
		List<Gasto> gastoSinFiltro = new ArrayList<Gasto>();
 		gastoSinFiltro = tesoreriaGastoService.getGastos(usuario.getId());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
 		for(int i=0;i<gastoSinFiltro.size();i++){
 			Date fechaFactura = sdf.parse(gastoSinFiltro.get(i).getFecha());
 			if(fechaFactura.compareTo(fechaMinimaFiltroFactura)<0 || fechaMaximaFiltroFactura.compareTo(fechaFactura)<0){
 				gastoSinFiltro.remove(i);
 				i--;
 			}
 		}
 		setGastos(gastoSinFiltro);
	}
	
	public void filtroIngresoPorFechasVencimiento() throws ParseException{
		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		usuario = userService.getUserByName(nombreUsuario);
		List<Ingreso> ingresoSinFiltro = new ArrayList<Ingreso>();
 		ingresoSinFiltro = tesoreriaIngresoService.getIngresos(usuario.getId());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
 		for(int i=0;i<ingresoSinFiltro.size();i++){
 			Date fechaFactura = sdf.parse(ingresoSinFiltro.get(i).getFecha());
 			if(fechaFactura.compareTo(fechaMinimaFiltroFactura)<0 || fechaMaximaFiltroFactura.compareTo(fechaFactura)<0){
 				ingresoSinFiltro.remove(i);
 				i--;
 			}
 		}
 		setIngresos(ingresoSinFiltro);
	}
	
	public void filtroPorMesFactura() throws ParseException{
		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		usuario = userService.getUserByName(nombreUsuario);
		List<Gasto> gastoSinFiltro = new ArrayList<Gasto>();
 		gastoSinFiltro = tesoreriaGastoService.getGastos1();
 		for(int i=0;i<gastoSinFiltro.size();i++){
 			Months month = new Months();
 			String mesFiltro = month.getMonthNumber(filtroMesFactura);
 			String mesGasto = new StringBuilder().append(gastoSinFiltro.get(i).getFecha().charAt(3)).append(gastoSinFiltro.get(i).getFecha().charAt(4)).toString();
 			if(!mesFiltro.equals(mesGasto)){
 				gastoSinFiltro.remove(i);
 				i--;
 			}
 		}
 		setGastos(gastoSinFiltro);
	}
	
	public void filtroIngresoPorMesFactura() throws ParseException{
		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		usuario = userService.getUserByName(nombreUsuario);
		List<Ingreso> ingresoSinFiltro = new ArrayList<Ingreso>();
 		ingresoSinFiltro = tesoreriaIngresoService.getIngresos(usuario.getId());
 		for(int i=0;i<ingresoSinFiltro.size();i++){
 			Months month = new Months();
 			String mesFiltro = month.getMonthNumber(filtroMesFactura);
 			String mesGasto = new StringBuilder().append(ingresoSinFiltro.get(i).getFechaCobro().charAt(3)).append(ingresoSinFiltro.get(i).getFechaCobro().charAt(4)).toString();
 			if(!mesFiltro.equals(mesGasto)){
 				ingresoSinFiltro.remove(i);
 				i--;
 			}
 		}
 		setIngresos(ingresoSinFiltro);
	}
	
	public void filtroPorMesVencimiento() throws ParseException{
		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		usuario = userService.getUserByName(nombreUsuario);
		List<Gasto> gastoSinFiltro = new ArrayList<Gasto>();
 		gastoSinFiltro = tesoreriaGastoService.getGastos(usuario.getId());
 		for(int i=0;i<gastoSinFiltro.size();i++){
 			Months month = new Months();
 			String mesFiltro = month.getMonthNumber(filtroMes);
 			String mesGasto = new StringBuilder().append(gastoSinFiltro.get(i).getFecha().charAt(3)).append(gastoSinFiltro.get(i).getFecha().charAt(4)).toString();
 			if(!mesFiltro.equals(mesGasto)){
 				gastoSinFiltro.remove(i);
 				i--;
 			}
 		}
 		setGastos(gastoSinFiltro);
	}
	
	public void filtroIngresoPorMesVencimiento() throws ParseException{
		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		usuario = userService.getUserByName(nombreUsuario);
		List<Ingreso> ingresoSinFiltro = new ArrayList<Ingreso>();
 		ingresoSinFiltro = tesoreriaIngresoService.getIngresos(usuario.getId());
 		for(int i=0;i<ingresoSinFiltro.size();i++){
 			Months month = new Months();
 			String mesFiltro = month.getMonthNumber(filtroMes);
 			String mesGasto = new StringBuilder().append(ingresoSinFiltro.get(i).getFecha().charAt(3)).append(ingresoSinFiltro.get(i).getFecha().charAt(4)).toString();
 			if(!mesFiltro.equals(mesGasto)){
 				ingresoSinFiltro.remove(i);
 				i--;
 			}
 		}
 		setIngresos(ingresoSinFiltro);
	}
	
	public void filtroPorTrimestre() throws ParseException{
		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		usuario = userService.getUserByName(nombreUsuario);
		List<Gasto> gastoSinFiltro = new ArrayList<Gasto>();
 		gastoSinFiltro = tesoreriaGastoService.getGastos(usuario.getId());
 		for(int i=0;i<gastoSinFiltro.size();i++){
 			String mesGasto = new StringBuilder().append(gastoSinFiltro.get(i).getFechaPago().charAt(3)).append(gastoSinFiltro.get(i).getFechaPago().charAt(4)).toString();
 			if((!filtroTrimestre.equals("Primer trimestre") && (mesGasto.equals("01") || mesGasto.equals("02") || mesGasto.equals("03")))){
 				gastoSinFiltro.remove(i);
 				i--;
 			}
 			else 
 				if((!filtroTrimestre.equals("Segundo trimestre") && (mesGasto.equals("04") || mesGasto.equals("05") || mesGasto.equals("06")))){
	 				gastoSinFiltro.remove(i);
	 				i--;
	 			}
 				else
 					if((!filtroTrimestre.equals("Tercer trimestre") && (mesGasto.equals("07") || mesGasto.equals("08") || mesGasto.equals("09")))){
 		 				gastoSinFiltro.remove(i);
 		 				i--;
 		 			}
 					else
 						if((!filtroTrimestre.equals("Cuarto trimestre") && (mesGasto.equals("10") || mesGasto.equals("11") || mesGasto.equals("12")))){
 			 				gastoSinFiltro.remove(i);
 			 				i--;
 			 			}
 		}
 		setGastos(gastoSinFiltro);
	}
	
	public void filtroIngresoPorTrimestre() throws ParseException{
		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		usuario = userService.getUserByName(nombreUsuario);
		List<Ingreso> ingresoSinFiltro = new ArrayList<Ingreso>();
 		ingresoSinFiltro = tesoreriaIngresoService.getIngresos(usuario.getId());
 		for(int i=0;i<ingresoSinFiltro.size();i++){
 			String mesGasto = new StringBuilder().append(ingresoSinFiltro.get(i).getFechaCobro().charAt(3)).append(ingresoSinFiltro.get(i).getFechaCobro().charAt(4)).toString();
 			if((!filtroTrimestre.equals("Primer trimestre") && (mesGasto.equals("01") || mesGasto.equals("02") || mesGasto.equals("03")))){
 				ingresoSinFiltro.remove(i);
 				i--;
 			}
 			else 
 				if((!filtroTrimestre.equals("Segundo trimestre") && (mesGasto.equals("04") || mesGasto.equals("05") || mesGasto.equals("06")))){
	 				ingresoSinFiltro.remove(i);
	 				i--;
	 			}
 				else
 					if((!filtroTrimestre.equals("Tercer trimestre") && (mesGasto.equals("07") || mesGasto.equals("08") || mesGasto.equals("09")))){
 		 				ingresoSinFiltro.remove(i);
 		 				i--;
 		 			}
 					else
 						if((!filtroTrimestre.equals("Cuarto trimestre") && (mesGasto.equals("10") || mesGasto.equals("11") || mesGasto.equals("12")))){
 			 				ingresoSinFiltro.remove(i);
 			 				i--;
 			 			}
 		}
 		setIngresos(ingresoSinFiltro);
	}
	
	public void filtroPorFormaPago(){
		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		usuario = userService.getUserByName(nombreUsuario);
		List<Gasto> gastoSinFiltro = new ArrayList<Gasto>();
 		gastoSinFiltro = tesoreriaGastoService.getGastos(usuario.getId());
 		for(int i=0;i<gastoSinFiltro.size();i++){
 			if(!gastoSinFiltro.get(i).getFormaPago().equals(filtroFormaPago)){
 				gastoSinFiltro.remove(i);
 				i--;
 			}
 		}
 		setGastos(gastoSinFiltro);
	}
	
	public void filtroPorEstado(){
		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		usuario = userService.getUserByName(nombreUsuario);
		List<Gasto> gastoSinFiltro = new ArrayList<Gasto>();
 		gastoSinFiltro = tesoreriaGastoService.getGastos(usuario.getId());
 		for(int i=0;i<gastoSinFiltro.size();i++){
 			if(!gastoSinFiltro.get(i).getEstado().equals(filtroEstado)){
 				gastoSinFiltro.remove(i);
 				i--;
 			}
 		}
 		setGastos(gastoSinFiltro);
	}
	
	public void filtroIngresoPorEstado(){
		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		usuario = userService.getUserByName(nombreUsuario);
		List<Ingreso> ingresoSinFiltro = new ArrayList<Ingreso>();
 		ingresoSinFiltro = tesoreriaIngresoService.getIngresos(usuario.getId());
 		for(int i=0;i<ingresoSinFiltro.size();i++){
 			if(!ingresoSinFiltro.get(i).getEstado().equals(filtroEstado)){
 				ingresoSinFiltro.remove(i);
 				i--;
 			}
 		}
 		setIngresos(ingresoSinFiltro);
	}
	
	public void filtroPorCategoria(){
		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		usuario = userService.getUserByName(nombreUsuario);
		List<Gasto> gastoSinFiltro = new ArrayList<Gasto>();
 		gastoSinFiltro = tesoreriaGastoService.getGastos(usuario.getId());
 		for(int i=0;i<gastoSinFiltro.size();i++){
 			if(!gastoSinFiltro.get(i).getCat().equals(filtroCategoria)){
 				gastoSinFiltro.remove(i);
 				i--;
 			}
 		}
 		setGastos(gastoSinFiltro);
	}
	
	public void filtroPorEmpresa(){
		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		usuario = userService.getUserByName(nombreUsuario);
		List<Gasto> gastoSinFiltro = new ArrayList<Gasto>();
 		gastoSinFiltro = tesoreriaGastoService.getGastos(usuario.getId());
 		for(int i=0;i<gastoSinFiltro.size();i++){
 			if(!gastoSinFiltro.get(i).getEmpresa().equals(filtroEmpresa)){
 				gastoSinFiltro.remove(i);
 				i--;
 			}
 		}
 		setGastos(gastoSinFiltro);
	}
	
	public void filtroPorCliente(){
		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		usuario = userService.getUserByName(nombreUsuario);
		List<Ingreso> ingresoSinFiltro = new ArrayList<Ingreso>();
 		ingresoSinFiltro = tesoreriaIngresoService.getIngresos(usuario.getId());
 		for(int i=0;i<ingresoSinFiltro.size();i++){
 			if(!ingresoSinFiltro.get(i).getCliente().equals(filtroEmpresa)){
 				ingresoSinFiltro.remove(i);
 				i--;
 			}
 		}
 		setIngresos(ingresoSinFiltro);
	}
	
	public void filtroPorObra(){
		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		usuario = userService.getUserByName(nombreUsuario);
		List<Gasto> gastoSinFiltro = new ArrayList<Gasto>();
 		gastoSinFiltro = tesoreriaGastoService.getGastos(usuario.getId());
 		for(int i=0;i<gastoSinFiltro.size();i++){
 			if(!gastoSinFiltro.get(i).getObra().getNombreObra().equals(filtroObra)){
 				gastoSinFiltro.remove(i);
 				i--;
 			}
 		}
 		setGastos(gastoSinFiltro);
	}
	
	public void filtroIngresoPorObra(){
		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		usuario = userService.getUserByName(nombreUsuario);
		List<Ingreso> ingresoSinFiltro = new ArrayList<Ingreso>();
 		ingresoSinFiltro = tesoreriaIngresoService.getIngresos(usuario.getId());
 		for(int i=0;i<ingresoSinFiltro.size();i++){
 			if(!ingresoSinFiltro.get(i).getObra().getNombreObra().equals(filtroObra)){
 				ingresoSinFiltro.remove(i);
 				i--;
 			}
 		}
 		setIngresos(ingresoSinFiltro);
	}
	
	public void filtroPorConcepto(){
		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		usuario = userService.getUserByName(nombreUsuario);
		List<Gasto> gastoSinFiltro = new ArrayList<Gasto>();
 		gastoSinFiltro = tesoreriaGastoService.getGastos(usuario.getId());
 		for(int i=0;i<gastoSinFiltro.size();i++){
 			if(!gastoSinFiltro.get(i).getConcepto().equals(filtroConcepto)){
 				gastoSinFiltro.remove(i);
 				i--;
 			}
 		}
 		setGastos(gastoSinFiltro);
	}
	
	public void filtroIngresoPorConcepto(){
		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		usuario = userService.getUserByName(nombreUsuario);
		List<Ingreso> ingresoSinFiltro = new ArrayList<Ingreso>();
 		ingresoSinFiltro = tesoreriaIngresoService.getIngresos(usuario.getId());
 		for(int i=0;i<ingresoSinFiltro.size();i++){
 			if(!ingresoSinFiltro.get(i).getOperacion().equals(filtroConcepto)){
 				ingresoSinFiltro.remove(i);
 				i--;
 			}
 		}
 		setIngresos(ingresoSinFiltro);
	}
	
	public void filtroPorBanco(){
		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		usuario = userService.getUserByName(nombreUsuario);
		List<Gasto> gastoSinFiltro = new ArrayList<Gasto>();
 		gastoSinFiltro = tesoreriaGastoService.getGastos(usuario.getId());
 		for(int i=0;i<gastoSinFiltro.size();i++){
 			if(!gastoSinFiltro.get(i).getBanco().equals(filtroBanco)){
 				gastoSinFiltro.remove(i);
 				i--;
 			}
 		}
 		setGastos(gastoSinFiltro);
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
		if(titulo.equals("Otro"))
			otroTitulo=true;
		else
			otroTitulo=false;
	}
	
	public List<String> getTitulos() {
		titulos = new ArrayList<String>();
		titulos.add("Otro");
		return titulos;
	}
	
	public void setTitulos(List<String> titulos) {
		this.titulos = titulos;
	}
	
	public boolean isOtroTitulo() {
		return otroTitulo;
	}
	
	public void setOtroTitulo(boolean otroTitulo) {
		this.otroTitulo = otroTitulo;
	}
	
	public String getCategoriaGasto() {
		return categoriaGasto;
	}
	
	public void setCategoriaGasto(String categoriaGasto) {
		this.categoriaGasto = categoriaGasto;
	}
	
	public String getCategoriaIngreso() {
		return categoriaIngreso;
	}
	
	public void setCategoriaIngreso(String categoriaIngreso) {
		this.categoriaIngreso = categoriaIngreso;
	}

	public ITesoreriaService getTesoreriaService() {
		return tesoreriaService;
	}

	public void setTesoreriaService(ITesoreriaService tesoreriaService) {
		this.tesoreriaService = tesoreriaService;
	}

	public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	public Gasto getGasto() {
		return gasto;
	}

	public void setGasto(Gasto gasto) {
		this.gasto = gasto;
	}

	public String getNombreObra() {
		return nombreObra;
	}
	
	@SuppressWarnings("deprecation")
	public void fechaLimites(){
		Obra obraTesoreria = new Obra();
		if(nombreObra!=null){
			obraTesoreria = obraService.getObraByNombre(nombreObra);
			SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
			try {
				fechaMinima=formatoDelTexto.parse(obraTesoreria.getFecha());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if(obraTesoreria.getFechaFin()!=null)
					fechaMaxima=formatoDelTexto.parse(obraTesoreria.getFechaFin());
				else{
					fechaMaxima=fechaMinima;
					fechaMaxima.setYear(fechaMinima.getYear()+5);
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void setNombreObra(String nombreObra) {
		this.nombreObra = nombreObra;
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

	public float getCantidadGasto() {
		return cantidadGasto;
	}

	public void setCantidadGasto(float cantidadGasto) {
		this.cantidadGasto = cantidadGasto;
	}

	public String getConceptoGasto() {
		return conceptoGasto;
	}

	public void setConceptoGasto(String conceptoGasto) {
		this.conceptoGasto = conceptoGasto;
	}

	public float getCantidadIngreso() {
		return cantidadIngreso;
	}

	public void setCantidadIngreso(float cantidadIngreso) {
		this.cantidadIngreso = cantidadIngreso;
	}

	public String getConceptoIngreso() {
		return conceptoIngreso;
	}

	public void setConceptoIngreso(String conceptoIngreso) {
		this.conceptoIngreso = conceptoIngreso;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Gasto> getGastos() {
		return gastos;
	}

	public void setGastos(List<Gasto> gastos) {
		this.gastos = gastos;
	}

	public List<Ingreso> getIngresos() {
		return ingresos;
	}

	public void setIngresos(List<Ingreso> ingresos) {
		this.ingresos = ingresos;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Ingreso getIngreso() {
		return ingreso;
	}

	public void setIngreso(Ingreso ingreso) {
		this.ingreso = ingreso;
	}

	/**
	 * @return the categoriaIngresos
	 */
	public List<String> getCategoriaIngresos() {
		List<Obra> obrasList = new ArrayList<Obra>();
		obrasList = obraService.getObras();
		categoriaIngresos = new ArrayList<String>();
		for(int i=0;i<obrasList.size();i++)
			categoriaIngresos.add(obrasList.get(i).getNombreObra());
		return categoriaIngresos;
	}

	/**
	 * @param categoriaIngresos the categoriaIngresos to set
	 */
	public void setCategoriaIngresos(List<String> categoriaIngresos) {
		this.categoriaIngresos = categoriaIngresos;
	}

	public IObraService getObraService() {
		return obraService;
	}

	public void setObraService(IObraService obraService) {
		this.obraService = obraService;
	}

	/**
	 * @return the iva
	 */
	public float getIva() {
		return iva;
	}

	/**
	 * @param iva the iva to set
	 */
	public void setIva(float iva) {
		this.iva = iva;
	}

	/**
	 * @return the tiempoCobro
	 */
	public int getTiempoCobro() {
		return tiempoCobro;
	}

	/**
	 * @param tiempoCobro the tiempoCobro to set
	 */
	public void setTiempoCobro(int tiempoCobro) {
		this.tiempoCobro = tiempoCobro;
	}

	/**
	 * @return the tiempoPago
	 */
	public int getTiempoPago() {
		return tiempoPago;
	}

	/**
	 * @param tiempoPago the tiempoPago to set
	 */
	public void setTiempoPago(int tiempoPago) {
		this.tiempoPago = tiempoPago;
	}

	/**
	 * @return the obras
	 */
	public List<String> getObras() {
		obras = new ArrayList<String>();
		List<Obra> obraList = new ArrayList<Obra>();
		obraList = obraService.getObras();
		for(int i=0; i<obraList.size();i++)
			obras.add(obraList.get(i).getNombreObra());
		return obras;
	}

	/**
	 * @param obras the obras to set
	 */
	public void setObras(List<String> obras) {
		this.obras = obras;
	}

	/**
	 * @return the newNombreObra
	 */
	public String getNewNombreObra() {
		return newNombreObra;
	}

	/**
	 * @param newNombreObra the newNombreObra to set
	 */
	public void setNewNombreObra(String newNombreObra) {
		this.newNombreObra = newNombreObra;
	}

	/**
	 * @return the newTitulo
	 */
	public String getNewTitulo() {
		return newTitulo;
	}

	/**
	 * @param newTitulo the newTitulo to set
	 */
	public void setNewTitulo(String newTitulo) {
		this.newTitulo = newTitulo;
	}

	/**
	 * @return the irpfSpGasto
	 */
	public float getIrpfSpGasto() {
		return irpfSpGasto;
	}

	/**
	 * @param irpfSpGasto the irpfSpGasto to set
	 */
	public void setIrpfSpGasto(float irpfSpGasto) {
		this.irpfSpGasto = irpfSpGasto;
	}

	/**
	 * @return the irpfAlquilerIngreso
	 */
	public float getIrpfAlquilerIngreso() {
		return irpfAlquilerIngreso;
	}

	/**
	 * @param irpfAlquilerIngreso the irpfAlquilerIngreso to set
	 */
	public void setIrpfAlquilerIngreso(float irpfAlquilerIngreso) {
		this.irpfAlquilerIngreso = irpfAlquilerIngreso;
	}

	/**
	 * @return the fechaCobro
	 */
	public Date getFechaCobro() {
		return fechaCobro;
	}

	/**
	 * @param fechaCobro the fechaCobro to set
	 */
	public void setFechaCobro(Date fechaCobro) {
		this.fechaCobro = fechaCobro;
	}

	/**
	 * @return the fechaPago
	 */
	public Date getFechaPago() {
		return fechaPago;
	}

	/**
	 * @param fechaPago the fechaPago to set
	 */
	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	/**
	 * @return the vencimiento
	 */
	public Date getVencimiento() {
		return vencimiento;
	}

	/**
	 * @param vencimiento the vencimiento to set
	 */
	public void setVencimiento(Date vencimiento) {
		this.vencimiento = vencimiento;
	}
	
	/**
	 * @return the catGastos
	 */
	public List<String> getCatGastos() {
		catGastos = new ArrayList<String>();
		catGastos.add("PERS");
		catGastos.add("GTOS");
		catGastos.add("SUM");
		catGastos.add("VEH");
		catGastos.add("TFNO");
		catGastos.add("MENS");
		catGastos.add("SERV.PROF");
		catGastos.add("MAT OF");
		catGastos.add("MOB");
		catGastos.add("INFOR");
		catGastos.add("SEG");
		catGastos.add("ASES");
		catGastos.add("UBU");
		catGastos.add("BCOS");
		catGastos.add("VARIOS");
		catGastos.add("ALQ");
		catGastos.add("PTAMO");
		catGastos.add("LIBR");
		catGastos.add("ASOC");
		catGastos.add("REPR");
		catGastos.add("FORM");
		catGastos.add("LIMP");
		catGastos.add("RESUMEN");
		catGastos.add("GLOBAL");
		catGastos.add("FACTURACION");
		catGastos.add("GTOS PERU");
		return catGastos;
	}


	/**
	 * @param catGastos the catGastos to set
	 */
	public void setCatGastos(List<String> catGastos) {
		this.catGastos = catGastos;
	}

	public List<String> getTipoCostesEstructurales() {
		tipoCostesEstructurales = new ArrayList<String>();
		tipoCostesEstructurales.add("Estudios");
		tipoCostesEstructurales.add("Calidad");
		tipoCostesEstructurales.add("Comercial");
		tipoCostesEstructurales.add("SG");
		tipoCostesEstructurales.add("Internacional");
		tipoCostesEstructurales.add("CC115");
		tipoCostesEstructurales.add("I+D");
		tipoCostesEstructurales.add("Vinoteca");
		return tipoCostesEstructurales;
	}

	public void setTipoCostesEstructurales(List<String> tipoCostesEstructurales) {
		this.tipoCostesEstructurales = tipoCostesEstructurales;
	}

	/**
	 * @return the fechaMinima
	 */
	public Date getFechaMinima() {
		return fechaMinima;
	}

	/**
	 * @param fechaMinima the fechaMinima to set
	 */
	public void setFechaMinima(Date fechaMinima) {
		this.fechaMinima = fechaMinima;
	}

	/**
	 * @return the fechaMaxima
	 */
	public Date getFechaMaxima() {
		return fechaMaxima;
	}

	/**
	 * @param fechaMaxima the fechaMaxima to set
	 */
	public void setFechaMaxima(Date fechaMaxima) {
		this.fechaMaxima = fechaMaxima;
	}

	/**
	 * @return the centroCostes
	 */
	public List<String> getCentroCostes() {
		centroCostes = new ArrayList<String>();
		centroCostes.add("Burgos");
		centroCostes.add("Barcelona");
		centroCostes.add("Central");
		return centroCostes;
	}

	/**
	 * @param centroCostes the centroCostes to set
	 */
	public void setCentroCostes(List<String> centroCostes) {
		this.centroCostes = centroCostes;
	}

	/**
	 * @return the formaPagos
	 */
	public List<String> getFormaPagos() {
		formaPagos = new ArrayList<String>();
		formaPagos.add("Efectivo");
		formaPagos.add("Caja");
		formaPagos.add("Transf.");
		formaPagos.add("Domic.");
		formaPagos.add("Cheque");
		formaPagos.add("Visa");
		return formaPagos;
	}

	/**
	 * @param formaPagos the formaPagos to set
	 */
	public void setFormaPagos(List<String> formaPagos) {
		this.formaPagos = formaPagos;
	}

	/**
	 * @return the prevision
	 */
	public List<String> getPrevision() {
		prevision = new ArrayList<String>();
		prevision.add("Previsión");
		prevision.add("Real");
		return prevision;
	}

	/**
	 * @param prevision the prevision to set
	 */
	public void setPrevision(List<String> prevision) {
		this.prevision = prevision;
	}

	public Date getFechaMinimaFiltroFactura() {
		return fechaMinimaFiltroFactura;
	}

	public void setFechaMinimaFiltroFactura(Date fechaMinimaFiltroFactura) {
		this.fechaMinimaFiltroFactura = fechaMinimaFiltroFactura;
	}

	public Date getFechaMaximaFiltroFactura() {
		return fechaMaximaFiltroFactura;
	}

	public void setFechaMaximaFiltroFactura(Date fechaMaximaFiltroFactura) {
		this.fechaMaximaFiltroFactura = fechaMaximaFiltroFactura;
	}

	public Date getFechaMinimaFiltroVencimiento() {
		return fechaMinimaFiltroVencimiento;
	}

	public void setFechaMinimaFiltroVencimiento(Date fechaMinimaFiltroVencimiento) {
		this.fechaMinimaFiltroVencimiento = fechaMinimaFiltroVencimiento;
	}

	public Date getFechaMaximaFiltroVencimiento() {
		return fechaMaximaFiltroVencimiento;
	}

	public void setFechaMaximaFiltroVencimiento(Date fechaMaximaFiltroVencimiento) {
		this.fechaMaximaFiltroVencimiento = fechaMaximaFiltroVencimiento;
	}

	public String getFiltroFormaPago() {
		return filtroFormaPago;
	}

	public void setFiltroFormaPago(String filtroFormaPago) {
		this.filtroFormaPago = filtroFormaPago;
	}

	public String getFiltroEstado() {
		return filtroEstado;
	}

	public void setFiltroEstado(String filtroEstado) {
		this.filtroEstado = filtroEstado;
	}

	public String getFiltroCategoria() {
		return filtroCategoria;
	}

	public void setFiltroCategoria(String filtroCategoria) {
		this.filtroCategoria = filtroCategoria;
	}

	public String getFiltroEmpresa() {
		return filtroEmpresa;
	}

	public void setFiltroEmpresa(String filtroEmpresa) {
		this.filtroEmpresa = filtroEmpresa;
	}

	public String getFiltroObra() {
		return filtroObra;
	}

	public void setFiltroObra(String filtroObra) {
		this.filtroObra = filtroObra;
	}

	public String getFiltroConcepto() {
		return filtroConcepto;
	}

	public void setFiltroConcepto(String filtroConcepto) {
		this.filtroConcepto = filtroConcepto;
	}

	public String getFiltroBanco() {
		return filtroBanco;
	}

	public void setFiltroBanco(String filtroBanco) {
		this.filtroBanco = filtroBanco;
	}

	public String getFiltroMes() {
		return filtroMes;
	}

	public void setFiltroMes(String filtroMes) {
		this.filtroMes = filtroMes;
	}

	public String getFiltroTrimestre() {
		return filtroTrimestre;
	}

	public void setFiltroTrimestre(String filtroTrimestre) {
		this.filtroTrimestre = filtroTrimestre;
	}

	/**
	 * @return the estados
	 */
	public List<String> getEstados() {
		estados = new ArrayList<String>();
		estados.add("PENDIENTE");
		estados.add("PAGADO");
		return estados;
	}

	/**
	 * @param estados the estados to set
	 */
	public void setEstados(List<String> estados) {
		this.estados = estados;
	}
	
	public float getIrpfSpIngreso() {
		return irpfSpIngreso;
	}

	public void setIrpfSpIngreso(float irpfSpIngreso) {
		this.irpfSpIngreso = irpfSpIngreso;
	}

	public float getIrpfAlquilerGasto() {
		return irpfAlquilerGasto;
	}

	public void setIrpfAlquilerGasto(float irpfAlquilerGasto) {
		this.irpfAlquilerGasto = irpfAlquilerGasto;
	}

	public List<String> getMeses() {
		meses = new ArrayList<String>();
		Months month = new Months();
		List<Months> months = new ArrayList<Months>();
		months = month.getMonths();
		for(int i=0;i<months.size();i++)
			meses.add(months.get(i).getMonth4View(months.get(i).getMonth()));
		return meses;
	}

	public void setMeses(List<String> meses) {
		this.meses = meses;
	}

	public List<String> getTrimestres() {
		trimestres = new ArrayList<String>();
		trimestres.add("Primer trimestre");
		trimestres.add("Segundo trimestre");
		trimestres.add("Tercer trimestre");
		trimestres.add("Cuarto trimestre");
		return trimestres;
	}

	public void setTrimestres(List<String> trimestres) {
		this.trimestres = trimestres;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	/**
	 * @return the iva2
	 */
	public float getIva2() {
		return iva2;
	}

	/**
	 * @param iva2 the iva2 to set
	 */
	public void setIva2(float iva2) {
		this.iva2 = iva2;
	}

	/**
	 * @return the bancos
	 */
	public List<String> getBancos() {
		bancos = new ArrayList<String>();
		List<Banco> listaBancos = new ArrayList<Banco>();
		listaBancos = bancoService.getBancos();
		for(Banco b : listaBancos)
			bancos.add(b.getNombre());
		return bancos;
	}

	/**
	 * @param bancos the bancos to set
	 */
	public void setBancos(List<String> bancos) {
		this.bancos = bancos;
	}

	public IBancoService getBancoService() {
		return bancoService;
	}

	public void setBancoService(IBancoService bancoService) {
		this.bancoService = bancoService;
	}

	/**
	 * @return the infoBancos
	 */
	public List<Banco> getInfoBancos() {
		return infoBancos;
	}

	/**
	 * @param infoBancos the infoBancos to set
	 */
	public void setInfoBancos(List<Banco> infoBancos) {
		this.infoBancos = infoBancos;
	}

	/**
	 * @return the fechaVencimiento
	 */
	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	/**
	 * @param fechaVencimiento the fechaVencimiento to set
	 */
	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public String getFiltroMesFactura() {
		return filtroMesFactura;
	}

	public void setFiltroMesFactura(String filtroMesFactura) {
		this.filtroMesFactura = filtroMesFactura;
	}

	/**
	 * @return the fechaRecepcion
	 */
	public Date getFechaRecepcion() {
		return fechaRecepcion;
	}

	/**
	 * @param fechaRecepcion the fechaRecepcion to set
	 */
	public void setFechaRecepcion(Date fechaRecepcion) {
		this.fechaRecepcion = fechaRecepcion;
		Empresa empresaSeleccionada = new Empresa();
		empresaSeleccionada = empresaService.getEmpresa(empresa);
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(fechaRecepcion); // Configuramos la fecha que se recibe
	    calendar.add(Calendar.DAY_OF_YEAR, empresaSeleccionada.getPlazoPago());  // numero de días a añadir, o restar en caso de días<0
	    this.vencimiento=calendar.getTime();
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
		this.gasto.setEmpresa(empresa);
		Empresa empresaSeleccionada = new Empresa();
		empresaSeleccionada = empresaService.getEmpresa(empresa);
		this.gasto.setCif(empresaSeleccionada.getCif());
		this.gasto.setFormaPago(empresaSeleccionada.getMetodoPago());
	}

	public List<String> getEmpresas() {
		empresas = new ArrayList<String>();
		List<Empresa> listaEmpresas = new ArrayList<Empresa>();
		listaEmpresas = empresaService.getEmpresas();
		for(Empresa e : listaEmpresas)
			empresas.add(e.getNombre());
		return empresas;
	}

	public void setEmpresas(List<String> empresas) {
		this.empresas = empresas;
	}

	public IEmpresaService getEmpresaService() {
		return empresaService;
	}

	public void setEmpresaService(IEmpresaService empresaService) {
		this.empresaService = empresaService;
	}

	/**
	 * @return the existeGasto
	 */
	public boolean isExisteGasto() {
		return existeGasto;
	}

	/**
	 * @param existeGasto the existeGasto to set
	 */
	public void setExisteGasto(boolean existeGasto) {
		this.existeGasto = existeGasto;
	}

	/**
	 * @return the gastosComprabar
	 */
	public List<Gasto> getGastosComprobar() {
		return gastosComprobar;
	}

	/**
	 * @param gastosComprabar the gastosComprabar to set
	 */
	public void setGastosComprobar(List<Gasto> gastosComprobar) {
		this.gastosComprobar = gastosComprobar;
	}


}
