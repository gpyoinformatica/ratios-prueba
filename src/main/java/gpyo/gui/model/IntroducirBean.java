package gpyo.gui.model;

import gpyo.persistence.entity.admin.IdObraDeUsuario;
import gpyo.persistence.entity.admin.Obra;
import gpyo.persistence.entity.admin.ObraDeUsuario;
import gpyo.persistence.entity.admin.Usuario;
import gpyo.service.businesslogic.IObraService;
import gpyo.service.businesslogic.IUserService;

import java.io.Serializable;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

/**
 * Bean que sirve para gestionar la introducción de ratios.
 * 
 * @author Alberto Revilla Gil
 * @version 1.0
 */
@ManagedBean
@ViewScoped
@Controller
public class IntroducirBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1182571492130004853L;
	
	private Obra obra=new Obra();
	private Usuario usuario/*=(Usuario) FacesUtil.getSessionMapValue("usuario")*/;
	private ObraDeUsuario obraDeUsuario=new ObraDeUsuario();
	private Date date;
	/**
	 * Fecha para filtrar los datos a mostrar.
	 */
	private Date date2;
	/**
	 * Contraseña para confirmar cuando se quiera cambiar.
	 */
	private String password2;
	
	private List<Obra> obras=new ArrayList<Obra>();
	@Autowired
	private IObraService obraService;
	@Autowired
	private IUserService userService;
	/**
	 * Fecha actual.
	 */
	Date currentDate;
	/**
	 * Nombre de la obra.
	 */
	private String nombreObra;
	/**
	 * Código de la obra.
	 */
	private String codigoObra;
	/**
	 * String con el nombre de la obra por la que se quiera hacer el filtro.
	 */
	private String nombreObraFilter;
	/**
	 * Mes con el que se quiera hacer el filtro.
	 */
	private String mesObraFilter;
	private List<ObraDeUsuario> obrasUsuario=new ArrayList<ObraDeUsuario>();
	private List<ObraDeUsuario> todasObrasUsuario=new ArrayList<ObraDeUsuario>();
	private String nombreUsuario;
	private List<String> visitas = new ArrayList<String>();
	
	private float totalHorasTecnico = 0;
	private float totalHorasAdmin = 0;
	private boolean showTodas=true;
	private boolean filtrado = true;
	
	/**
	 * Lista con los meses del año.
	 */
	private List<String> meses=new ArrayList<String>();
	
	public IntroducirBean(){
		
	}
	
	public IntroducirBean(Obra obra, Usuario usuario) {
		super();
		this.setObra(obra);
		this.setUsuario(usuario);
	}
	/**
	 * @return the obra
	 */
	public Obra getObra() {
		return obra;
	}

	/**
	 * @param obra the obra to set
	 */
	public void setObra(Obra obra) {
		this.obra = obra;
	}

	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		usuario = userService.getUserByName(nombreUsuario);
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		usuario = userService.getUserByName(nombreUsuario);
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the obraDeUsuario
	 */
	public ObraDeUsuario getObraDeUsuario() {
		return obraDeUsuario;
	}

	/**
	 * @param obraDeUsuario the obraDeUsuario to set
	 */
	public void setObraDeUsuario(ObraDeUsuario obraDeUsuario) {
		this.obraDeUsuario = obraDeUsuario;
	}
	
	/**
	 * Devuelve la fecha actual.
	 * 
	 * @return Fecha actual.
	 */
	public Date getCurrentDate() {
		return currentDate;
	}

	/**
	 * @param Fecha actual.
	 */
	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	/**
	 * @return the obras
	 */
	public List<Obra> getObras() {
		obras=obraService.getObras();
		for(int i=0;i<obras.size();i++)
			if(!obras.get(i).isActiva())
				obras.remove(i);
		return obras;
	}

	/**
	 * @param obras the obras to set
	 */
	public void setObras(List<Obra> obras) {
		this.obras = obras;
	}

	public IObraService getObraService() {
		return obraService;
	}

	public void setObraService(IObraService obraService) {
		this.obraService = obraService;
	}
	
	public String getNombreObra() {
		return nombreObra;
	}

	public void setNombreObra(String nombreObra) {
		this.nombreObra = nombreObra;
	}

	/**
	 * Guarda el ratio introducido en la base de datos.
	 */
	public void saveRatio(){
		Usuario usuario=new Usuario();
		totalHorasAdmin=0;
		totalHorasTecnico=0;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		usuario = userService.getUserByName(nombreUsuario);
		Calendar calendar = Calendar.getInstance();
		java.util.Date currentDate1 = calendar.getTime();
		currentDate = new java.sql.Date(currentDate1.getTime());
		obraDeUsuario.setFecha(sdf.format(currentDate));
		obraDeUsuario.setFechaSort(sdf2.format(currentDate));
		if(nombreObra.length()>0){
			obra.setIdObra(obraService.getObraByNombre(nombreObra).getIdObra());
			obraDeUsuario.setIdObraDeUsuario(new IdObraDeUsuario());
			obraDeUsuario.getIdObraDeUsuario().setUsuario(usuario);
			obraDeUsuario.getIdObraDeUsuario().setObra(obra);
			obraService.saveObraDeUsuario(obraDeUsuario);
			obrasUsuario=obraService.getObrasDeUsuario(sdf.format(currentDate));
			for(int i=0;i<obrasUsuario.size();i++){
				totalHorasAdmin += obrasUsuario.get(i).getHorasAdmin();
				totalHorasTecnico += obrasUsuario.get(i).getHorasTecnico();
			}
			List<ObraDeUsuario> todasAux = new ArrayList<ObraDeUsuario>();
			if(todasObrasUsuario.size()>20){
				showTodas = false;
				for(int i=0;i<todasObrasUsuario.size()-(todasObrasUsuario.size()-20);i++){
					todasAux.add(todasObrasUsuario.get(i));
				}
				todasObrasUsuario = new ArrayList<ObraDeUsuario>();
				todasObrasUsuario = todasAux;
			}
			FacesMessage msg = new FacesMessage("Ratio guardado");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		else{
			FacesMessage msg = new FacesMessage("El ratio no se ha podido guardar.");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	/**
	 * Guarda un ratio antiguo en la base de datos.
	 */
	public void saveOldRatio(){
	//	Usuario usuario=new Usuario();
		//usuario=(Usuario)FacesUtil.getSessionMapValue("usuario");
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		totalHorasAdmin=0;
		totalHorasTecnico=0;
		if(nombreObra.length()>0 && date!=null){
			obraDeUsuario.setFecha(sdf.format(date));
			String fecha=sdf.format(date);
			String fecha2="";
			fecha2=fecha2+fecha.charAt(6)+fecha.charAt(7)+fecha.charAt(8)+fecha.charAt(9)+fecha.charAt(5)+fecha.charAt(3)+fecha.charAt(4)+fecha.charAt(2)+fecha.charAt(0)+fecha.charAt(1);
			obraDeUsuario.setFechaSort(fecha2);
			obra.setIdObra(obraService.getObraByNombre(nombreObra).getIdObra());
			obraDeUsuario.setIdObraDeUsuario(new IdObraDeUsuario());
	//		obraDeUsuario.getIdObraDeUsuario().setUsuario(usuario);
			obraDeUsuario.getIdObraDeUsuario().setObra(obra);
			obraService.saveObraDeUsuario(obraDeUsuario);
			obrasUsuario=obraService.getObrasDeUsuario(sdf.format(date));
			todasObrasUsuario=obraService.getTodasObrasDeUsuario();
			for(int i=0;i<obrasUsuario.size();i++){
				totalHorasAdmin += obrasUsuario.get(i).getHorasAdmin();
				totalHorasTecnico += obrasUsuario.get(i).getHorasTecnico();
			}
			List<ObraDeUsuario> todasAux = new ArrayList<ObraDeUsuario>();
			if(todasObrasUsuario.size()>20){
				showTodas = false;
				for(int i=0;i<todasObrasUsuario.size()-(todasObrasUsuario.size()-20);i++){
					todasAux.add(todasObrasUsuario.get(i));
				}
				todasObrasUsuario = new ArrayList<ObraDeUsuario>();
				todasObrasUsuario = todasAux;
			}
			FacesMessage msg = new FacesMessage("Ratio guardado");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		else{
			FacesMessage msg = new FacesMessage("Introduzca todos los datos necesarios.");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		obraDeUsuario = new ObraDeUsuario();
		date=new Date();
	}
	
	/**
	 * Muestra los ratios que se han introducido en la fecha actual.
	 */
	public void showTodayRatios(){
		this.nombreObra="   ";
		this.codigoObra=" ";
		this.obraDeUsuario.setHorasTecnico(0);
		this.obraDeUsuario.setHorasAdmin(0);
		this.obraDeUsuario.setVisita("  ");
		this.obraDeUsuario.setTarea("  ");
		totalHorasAdmin=0;
		totalHorasTecnico=0;
		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		usuario = userService.getUserByName(nombreUsuario);
        Calendar calendar = Calendar.getInstance();
		java.util.Date currentDate = calendar.getTime();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String reportDate = df.format(currentDate);
        usuario.setLastAccess(reportDate);
		userService.updateUsuario(usuario);
		calendar = Calendar.getInstance();
		java.util.Date currentDate1 = calendar.getTime();
		currentDate = new java.sql.Date(currentDate1.getTime());
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		obrasUsuario=obraService.getObrasDeUsuario(sdf.format(currentDate));
		todasObrasUsuario=obraService.getTodasObrasDeUsuario();
		for(int i=0;i<obrasUsuario.size();i++){
			totalHorasAdmin += obrasUsuario.get(i).getHorasAdmin();
			totalHorasTecnico += obrasUsuario.get(i).getHorasTecnico();
		}
		List<ObraDeUsuario> todasAux = new ArrayList<ObraDeUsuario>();
		if(todasObrasUsuario.size()>20){
			showTodas = false;
			for(int i=0;i<todasObrasUsuario.size()-(todasObrasUsuario.size()-20);i++){
				todasAux.add(todasObrasUsuario.get(i));
			}
			todasObrasUsuario = new ArrayList<ObraDeUsuario>();
			todasObrasUsuario = todasAux;
		}
	}
	
	
	/**
	 * Edita el ratio seleccionado y lo actualiza en la base de datos.
	 * 
	 * @param El ratio seleccionado.
	 */
	public void onEdit(RowEditEvent event) {
		ObraDeUsuario obra1=(ObraDeUsuario) event.getObject();
		System.out.println(obra1.getObra().getNombreObra()+"   "+obra1.getFecha()+"   "+obra1.getTarea());
		String fecha=obra1.getFecha();
		String fecha2="";
		fecha2=fecha2+fecha.charAt(6)+fecha.charAt(7)+fecha.charAt(8)+fecha.charAt(9)+fecha.charAt(5)+fecha.charAt(3)+fecha.charAt(4)+fecha.charAt(2)+fecha.charAt(0)+fecha.charAt(1);
		obra1.setFechaSort(fecha2);
	 	obraService.updateRatio(obra1);
	 	setFiltrado(false);
        FacesMessage msg = new FacesMessage("Ratio editado", ((ObraDeUsuario) event.getObject()).getIdObraDeUsuario().getObra().getNombreObra());
        FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	 
	/**
	 * Se cancelan los cambios realizados en el ratio seleccionado.
	 * @param El ratio seleccionado.
	 */
    public void onCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edición cancelada", ((ObraDeUsuario) event.getObject()).getIdObraDeUsuario().getObra().getNombreObra());
 
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    /**
     * Elimina un ratio de la base de datos.
     * @param obra seleccionada para su eliminación.
     */
    public void deleteRatio(ObraDeUsuario obra){
    	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    	obraService.deleteRatio(obra);
    	Calendar calendar = Calendar.getInstance();
		java.util.Date currentDate1 = calendar.getTime();
		currentDate = new java.sql.Date(currentDate1.getTime());
    	obrasUsuario=obraService.getObrasDeUsuario(sdf.format(currentDate));
    	todasObrasUsuario=obraService.getTodasObrasDeUsuario();
    	setFiltrado(false);
    	FacesMessage msg = new FacesMessage("Ratio eliminado", (obra).getIdObraDeUsuario().getObra().getNombreObra());
   	 
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
   
	/**
	 * @return the obrasUsuario
	 */
	public List<ObraDeUsuario> getObrasUsuario() {
		Calendar calendar = Calendar.getInstance();
		java.util.Date currentDate1 = calendar.getTime();
		currentDate = new java.sql.Date(currentDate1.getTime());
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		obrasUsuario=obraService.getObrasDeUsuario(sdf.format(currentDate));
		return obrasUsuario;
	}
	
	/**
	 * @param obrasUsuario the obrasUsuario to set
	 */
	public void setObrasUsuario(List<ObraDeUsuario> obrasUsuario) {
		this.obrasUsuario = obrasUsuario;
	}
	
	/**
	 * Selecciona todos los ratios que están asociados a un usuario.
	 */
	public void updateObrasDeUsuario(){
		todasObrasUsuario=obraService.getTodasObrasDeUsuario();
	}
	
	/**
	 * Selecciona los ratios asociados y un usuario y filtrados por fecha.
	 */
	public void updateObrasDeUsuarioPorFecha(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		showTodas=false;
		if(date2!=null)
			todasObrasUsuario=obraService.getObrasDeUsuario(sdf.format(date2));
		else{
			FacesMessage msg = new FacesMessage("Introduzca una fecha");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	/**
	 * Selecciona los ratios de un usuario en la fecha seleccionada y para una obra en concreto.
	 */
	public void updateObrasDeUsuarioDoble(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Usuario us=new Usuario();
		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		us = userService.getUserByName(nombreUsuario);
		showTodas=false;
		if(date2!=null && nombreObraFilter.length()>0)
			todasObrasUsuario=obraService.getObrasDeUsuario(us.getId(), sdf.format(date2), nombreObraFilter);
		else{
			FacesMessage msg = new FacesMessage("Introduzca una fecha y el nombre de una obra");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	/**
	 * Selecciona los ratios de un usuario para una obra.
	 */
	public void updateObrasDeUsuarioPorObra(){
		Usuario us=new Usuario();
		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		us = userService.getUserByName(nombreUsuario);
		showTodas=false;
		if(nombreObraFilter.length()>0)
			todasObrasUsuario=obraService.getObrasDeUsuario2(us.getId(), nombreObraFilter);
		else{
			FacesMessage msg = new FacesMessage("Introduzca el nombre de una obra");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	/**
	 * Selecciona los ratios de un usuario filtrados por mes.
	 */
	public void updateObrasDeUsuarioPorMes(){
		int code=0;
		if(mesObraFilter.equals("Enero"))
			code=01;
		if(mesObraFilter.equals("Febrero"))
			code=02;
		if(mesObraFilter.equals("Marzo"))
			code=03;
		if(mesObraFilter.equals("Abril"))
			code=04;
		if(mesObraFilter.equals("Mayo"))
			code=05;
		if(mesObraFilter.equals("Junio"))
			code=06;
		if(mesObraFilter.equals("Julio"))
			code=07;
		if(mesObraFilter.equals("Agosto"))
			code=10;
		if(mesObraFilter.equals("Septiembre"))
			code=11;
		if(mesObraFilter.equals("Octubre"))
			code=12;
		if(mesObraFilter.equals("Noviembre"))
			code=13;
		if(mesObraFilter.equals("Diciembre"))
			code=14;
		Usuario us=new Usuario();
		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		us = userService.getUserByName(nombreUsuario);
		showTodas=false;
		if(code!=0)
			todasObrasUsuario=obraService.getObrasDeUsuarioMes(us.getId(), code);
		else{
			FacesMessage msg = new FacesMessage("Introduzca un mes");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	/**
	 * Selecciona los ratios de un usuario filtrados por mes y por obra.
	 */
	public void updateObrasDeUsuarioDoble2(){
		int code=0;
		if(mesObraFilter.equals("Enero"))
			code=01;
		if(mesObraFilter.equals("Febrero"))
			code=02;
		if(mesObraFilter.equals("Marzo"))
			code=03;
		if(mesObraFilter.equals("Abril"))
			code=04;
		if(mesObraFilter.equals("Mayo"))
			code=05;
		if(mesObraFilter.equals("Junio"))
			code=06;
		if(mesObraFilter.equals("Julio"))
			code=07;
		if(mesObraFilter.equals("Agosto"))
			code=10;
		if(mesObraFilter.equals("Septiembre"))
			code=11;
		if(mesObraFilter.equals("Octubre"))
			code=12;
		if(mesObraFilter.equals("Noviembre"))
			code=13;
		if(mesObraFilter.equals("Diciembre"))
			code=14;
		Usuario us=new Usuario();
		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		us = userService.getUserByName(nombreUsuario);
		showTodas=false;
		if(code!=0 && nombreObraFilter.length()>0)
			todasObrasUsuario=obraService.getObrasDeUsuarioMesObra(us.getId(), code, nombreObraFilter);
		else{
			FacesMessage msg = new FacesMessage("Introduzca un mes y el nombre de una obra");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	/**
	 * Devuelve los últimos 20 ratios introducidas por el usuario.
	 * @return the todasObrasUsuario
	 */
	public List<ObraDeUsuario> getTodasObrasUsuario() {
		if(showTodas)
    	todasObrasUsuario=obraService.getTodasObrasDeUsuario();
		if(!filtrado){
			List<ObraDeUsuario> todasAux = new ArrayList<ObraDeUsuario>();
			if(todasObrasUsuario.size()>20){
				showTodas = false;
				for(int i=0;i<todasObrasUsuario.size()-(todasObrasUsuario.size()-20);i++){
					todasAux.add(todasObrasUsuario.get(i));
				}
				todasObrasUsuario = new ArrayList<ObraDeUsuario>();
				todasObrasUsuario = todasAux;
			}
		}
		return todasObrasUsuario;
	}

	/**
	 * @param todasObrasUsuario the todasObrasUsuario to set
	 */
	public void setTodasObrasUsuario(List<ObraDeUsuario> todasObrasUsuario) {
		this.todasObrasUsuario = todasObrasUsuario;
	}

	/**
	 * @return the date2
	 */
	public Date getDate2() {
		return date2;
	}

	/**
	 * @param date2 the date2 to set
	 */
	public void setDate2(Date date2) {
		this.date2 = date2;
	}

	/**
	 * @return the nombreObraFilter
	 */
	public String getNombreObraFilter() {
		return nombreObraFilter;
	}

	/**
	 * @param nombreObraFilter the nombreObraFilter to set
	 */
	public void setNombreObraFilter(String nombreObraFilter) {
		this.nombreObraFilter = nombreObraFilter;
	}

	/**
	 * @return the codigoObra
	 */
	public String getCodigoObra() {
		return codigoObra;
	}

	/**
	 * @param codigoObra the codigoObra to set
	 */
	public void setCodigoObra(String codigoObra) {
		this.codigoObra = codigoObra;
	}
	
	/**
	 * Actualiza la obra en función del código seleccionado.
	 */
	public void updateObra(){
		this.nombreObra=obraService.getObraByCodigo(codigoObra).getNombreObra();
		setFiltrado(false);
	}
	
	/**
	 * Actualiza el código de la obra a partir del nombre seleccionado.
	 */
	public void updateCodigo(){
		this.codigoObra=obraService.getObraByNombre(nombreObra).getCodigoObra();
		setFiltrado(false);
	}

	/**
	 * @return the nombreUsuario
	 */
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	/**
	 * @param nombreUsuario the nombreUsuario to set
	 */
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	/**
	 * Inicializa la lista con los meses del año.
	 * 
	 * @return the meses
	 */
	public List<String> getMeses() {
		meses = new ArrayList<String>();
		meses.add("Enero");
		meses.add("Febrero");
		meses.add("Marzo");
		meses.add("Abril");
		meses.add("Mayo");
		meses.add("Junio");
		meses.add("Julio");
		meses.add("Agosto");
		meses.add("Septiembre");
		meses.add("Octubre");
		meses.add("Noviembre");
		meses.add("Diciembre");
		return meses;
	}

	/**
	 * @param meses the meses to set
	 */
	public void setMeses(List<String> meses) {
		this.meses = meses;
	}

	/**
	 * @return the mesObraFilter
	 */
	public String getMesObraFilter() {
		return mesObraFilter;
	}

	/**
	 * @param mesObraFilter the mesObraFilter to set
	 */
	public void setMesObraFilter(String mesObraFilter) {
		this.mesObraFilter = mesObraFilter;
	}

	/**
	 * @return the userService
	 */
	public IUserService getUserService() {
		return userService;
	}

	/**
	 * @param userService the userService to set
	 */
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	/**
	 * @return the visitas
	 */
	public List<String> getVisitas() {
		visitas = new ArrayList<String>();
		visitas.add("Si");
		visitas.add("No");
		return visitas;
	}

	/**
	 * @param visitas the visitas to set
	 */
	public void setVisitas(List<String> visitas) {
		this.visitas = visitas;
	}

	public float getTotalHorasTecnico() {
		return totalHorasTecnico;
	}

	public void setTotalHorasTecnico(float totalHorasTecnico) {
		this.totalHorasTecnico = totalHorasTecnico;
	}

	public float getTotalHorasAdmin() {
		return totalHorasAdmin;
	}

	public void setTotalHorasAdmin(float totalHorasAdmin) {
		this.totalHorasAdmin = totalHorasAdmin;
	}

	/**
	 * @return the showTodas
	 */
	public boolean isShowTodas() {
		return showTodas;
	}

	/**
	 * @param showTodas the showTodas to set
	 */
	public void setShowTodas(boolean showTodas) {
		this.showTodas = showTodas;
	}

	/**
	 * @return the filtrado
	 */
	public boolean isFiltrado() {
		return filtrado;
	}

	/**
	 * @param filtrado the filtrado to set
	 */
	public void setFiltrado(boolean filtrado) {
		this.filtrado = filtrado;
	}

}
