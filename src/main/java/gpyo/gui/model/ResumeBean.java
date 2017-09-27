package gpyo.gui.model;

import gpyo.persistence.entity.admin.InformeObraUsuario;
import gpyo.persistence.entity.admin.Obra;
import gpyo.persistence.entity.admin.ObraDeUsuario;
import gpyo.persistence.entity.admin.Usuario;
import gpyo.service.businesslogic.IObraService;
import gpyo.service.businesslogic.IUserService;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Bean que sirve para hacer los resúmenes de los ratios.
 * 
 * @author Alberto Revilla Gil
 * @version 1.0
 */
@ManagedBean
@ViewScoped
@Controller
public class ResumeBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1098169701871127814L;
	
	private List<Usuario> todosUsuarios=new ArrayList<Usuario>();
	private Usuario usuario=new Usuario();
	private List<ObraDeUsuario> obraUsuario=new ArrayList<ObraDeUsuario>();
	private List<InformeObraUsuario> informeObraUsuarios=new ArrayList<InformeObraUsuario>();
	private String mesFilter;
	private List<Obra> obras=new ArrayList<Obra>();
	private List<String> meses=new ArrayList<String>();
	private Date date2;
	@Autowired
	private IObraService obraService;
	private String nombreObraFilter;
	private String codigoFilter;
	private String usuarioFilter;
	private float percentageMes;
	private float percentageTotal;
	@Autowired
	private IUserService userService;
	private float totalHorasTecnicas=0;
	private float totalHorasAdmin=0;
	private int totalVisitas=0;
	private int yearFilter;
	
	
	public ResumeBean() {
	}
	
	/**
	 * Muestra las obras y los usuarios de la base de datos.
	 */
	public void showObras(){
		obras=obraService.getObras();
		todosUsuarios=userService.allUsers();
	}

	/**
	 * Selecciona las obras de un usuario por fecha.
	 */
	public void updateObrasDeUsuarioPorFecha(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		if(date2!=null){
			obraUsuario=obraService.getObrasPorFecha(sdf.format(date2));
			creaInforme();
		}
		else{
			FacesMessage msg = new FacesMessage("Introduzca una fecha");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public void updateObrasDeUsuarioPorYearMesUsuario(){
		int code=0;
		if(mesFilter.equals("Enero"))
			code=01;
		if(mesFilter.equals("Febrero"))
			code=02;
		if(mesFilter.equals("Marzo"))
			code=03;
		if(mesFilter.equals("Abril"))
			code=04;
		if(mesFilter.equals("Mayo"))
			code=05;
		if(mesFilter.equals("Junio"))
			code=06;
		if(mesFilter.equals("Julio"))
			code=07;
		if(mesFilter.equals("Agosto"))
			code=10;
		if(mesFilter.equals("Septiembre"))
			code=11;
		if(mesFilter.equals("Octubre"))
			code=12;
		if(mesFilter.equals("Noviembre"))
			code=13;
		if(mesFilter.equals("Diciembre"))
			code=14;
		if(code!=0 && usuarioFilter.length()>0){
			obraUsuario=obraService.getObrasPorUsuarioYearMesObra(usuarioFilter, code, yearFilter);
			float horasMes=0;
			float horasTotal=0;
			List<ObraDeUsuario> obraUsuarioAux=new ArrayList<ObraDeUsuario>();
			obraUsuarioAux=obraService.getObrasPorUsuarioObra(usuarioFilter, nombreObraFilter);
			for(int i=0;i<obraUsuario.size();i++){
				horasMes+=obraUsuario.get(i).getHorasAdmin()+obraUsuario.get(i).getHorasTecnico();
			}
			for(int i=0;i<obraUsuarioAux.size();i++){
				horasTotal+=obraUsuarioAux.get(i).getHorasAdmin()+obraUsuarioAux.get(i).getHorasTecnico();
			}
			percentageMes=(horasMes*100)/horasTotal;
			obraUsuarioAux=new ArrayList<ObraDeUsuario>();
			obraUsuarioAux=obraService.getObrasPorUsuario(usuarioFilter);
			
			float horasTotalAux=0;
			for(int i=0;i<obraUsuarioAux.size();i++){
				horasTotalAux+=obraUsuarioAux.get(i).getHorasAdmin()+obraUsuarioAux.get(i).getHorasTecnico();
			}
			percentageTotal=(horasTotal*100)/horasTotalAux;
			creaInforme();
		}
		else{
			FacesMessage msg = new FacesMessage("Introduzca un usuario, un mes y una obra");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	/**
	 * Selecciona las obras de un usuario filtrando por obra.
	 */
	public void updateObrasDeUsuarioPorObra(){
		if(nombreObraFilter.length()>0){
			obraUsuario=obraService.getObrasPorNombre(nombreObraFilter);
			creaInforme();
		}
		else{
			FacesMessage msg = new FacesMessage("Introduzca el nombre de una obra");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	/**
	 * Selecciona las obras de un usuario filtrando por código.
	 */
	public void updateObrasDeUsuarioPorCodigo(){
		if(codigoFilter.length()>0){
			obraUsuario=obraService.getObrasPorCodigo(codigoFilter);
			creaInforme();
		}
		else{
			FacesMessage msg = new FacesMessage("Introduzca el código de una obra");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	/**
	 * Selecciona las obras de un usuario.
	 */
	public void updateObrasDeUsuarioPorUsuario(){
		if(usuarioFilter.length()>0){
			obraUsuario=obraService.getObrasPorUsuario(usuarioFilter);
			creaInforme();
		}
		else{
			FacesMessage msg = new FacesMessage("Introduzca un usuario");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public void creaInforme(){
		informeObraUsuarios=new ArrayList<InformeObraUsuario>();
		totalHorasAdmin=0;
		totalHorasTecnicas=0;
		totalVisitas=0;
		float totalHoras=0;
		for(int i=0;i<obraUsuario.size();i++){
			InformeObraUsuario informeObraUsuario=new InformeObraUsuario();
			informeObraUsuario.setTitulo(obraUsuario.get(i).getObra().getTitulo());
			informeObraUsuario.setNombreObra(obraUsuario.get(i).getObra().getNombreObra());
			informeObraUsuario.setHorasAdmin(obraUsuario.get(i).getHorasAdmin());
			informeObraUsuario.setHorasTecnicas(obraUsuario.get(i).getHorasTecnico());
			if(obraUsuario.get(i).getVisita().equals("Si") || obraUsuario.get(i).getVisita().equals("SI"))
				informeObraUsuario.setVisitas(1);
			else
				informeObraUsuario.setVisitas(0);
			totalHoras+=obraUsuario.get(i).getHorasAdmin()+obraUsuario.get(i).getHorasTecnico();
			informeObraUsuarios.add(informeObraUsuario);
		}
		for(int i=0;i<informeObraUsuarios.size();i++){
			for(int j=informeObraUsuarios.size()-1;j>0;j--){
				if(i!=j)
				if(informeObraUsuarios.get(i).getNombreObra().equals(informeObraUsuarios.get(j).getNombreObra())){
					informeObraUsuarios.get(i).setHorasAdmin(informeObraUsuarios.get(i).getHorasAdmin()+informeObraUsuarios.get(j).getHorasAdmin());
					informeObraUsuarios.get(i).setHorasTecnicas(informeObraUsuarios.get(i).getHorasTecnicas()+informeObraUsuarios.get(j).getHorasTecnicas());
					if(informeObraUsuarios.get(j).getVisitas()>0)
						informeObraUsuarios.get(i).setVisitas(informeObraUsuarios.get(i).getVisitas()+informeObraUsuarios.get(j).getVisitas());
					informeObraUsuarios.remove(j);
				}
			}
			informeObraUsuarios.get(i).setPorcentaje(((informeObraUsuarios.get(i).getHorasAdmin()+informeObraUsuarios.get(i).getHorasTecnicas())*100)/totalHoras);
		}
		for(int i=0;i<informeObraUsuarios.size();i++){
			totalHorasAdmin+=informeObraUsuarios.get(i).getHorasAdmin();
			totalHorasTecnicas+=informeObraUsuarios.get(i).getHorasTecnicas();
			totalVisitas+=informeObraUsuarios.get(i).getVisitas();
		}
	}
	
	/**
	 * Selecciona las obras de un usuario filtrando por mes.
	 */
	public void updateObrasDeUsuarioPorMes(){
		int code=0;
		if(mesFilter.equals("Enero"))
			code=01;
		if(mesFilter.equals("Febrero"))
			code=02;
		if(mesFilter.equals("Marzo"))
			code=03;
		if(mesFilter.equals("Abril"))
			code=04;
		if(mesFilter.equals("Mayo"))
			code=05;
		if(mesFilter.equals("Junio"))
			code=06;
		if(mesFilter.equals("Julio"))
			code=07;
		if(mesFilter.equals("Agosto"))
			code=10;
		if(mesFilter.equals("Septiembre"))
			code=11;
		if(mesFilter.equals("Octubre"))
			code=12;
		if(mesFilter.equals("Noviembre"))
			code=13;
		if(mesFilter.equals("Diciembre"))
			code=14;
		if(code!=0){
			obraUsuario=obraService.getObrasPorMes(code);
			creaInforme();
		}
		else{
			FacesMessage msg = new FacesMessage("Introduzca un mes");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	/**
	 * Selecciona las obras por año.
	 */
	public void updateObrasDeUsuarioPorYear(){
		obraUsuario=obraService.getObrasPorYear(yearFilter);
		creaInforme();
		if(yearFilter==0){
			FacesMessage msg = new FacesMessage("Introduzca un año");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	/**
	 * Selecciona las obras de un usuario en un año.
	 */
	public void updateObrasDeUsuarioPorYearUsuario(){
		obraUsuario=obraService.getObrasPorYearUsuario(yearFilter, usuarioFilter);
		creaInforme();
		if(yearFilter==0 && usuarioFilter.length()<=0){
			FacesMessage msg = new FacesMessage("Introduzca un año y un usuario");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	/**
	 * Selecciona los ratios de un usuario, en un año y una obra determinada.
	 */
	public void updateObrasDeUsuarioPorYearObraUsuario(){
		obraUsuario=obraService.getObrasPorYearObraUsuario(yearFilter, usuarioFilter, nombreObraFilter);
		creaInforme();
		if(yearFilter==0 && usuarioFilter.length()<=0 && nombreObraFilter.length()<=0){
			FacesMessage msg = new FacesMessage("Introduzca un año, una obra y un usuario");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	/**
	 * Selecciona las obras de usuario filtrando por mes y obra.
	 */
	public void updateObrasDeUsuarioPorObraMes(){
		int code=0;
		if(mesFilter.equals("Enero"))
			code=01;
		if(mesFilter.equals("Febrero"))
			code=02;
		if(mesFilter.equals("Marzo"))
			code=03;
		if(mesFilter.equals("Abril"))
			code=04;
		if(mesFilter.equals("Mayo"))
			code=05;
		if(mesFilter.equals("Junio"))
			code=06;
		if(mesFilter.equals("Julio"))
			code=07;
		if(mesFilter.equals("Agosto"))
			code=10;
		if(mesFilter.equals("Septiembre"))
			code=11;
		if(mesFilter.equals("Octubre"))
			code=12;
		if(mesFilter.equals("Noviembre"))
			code=13;
		if(mesFilter.equals("Diciembre"))
			code=14;
		if(code!=0 && nombreObraFilter.length()>0){
			obraUsuario=obraService.getObrasPorMesObra(code, nombreObraFilter);
			creaInforme();
		}
		else{
			FacesMessage msg = new FacesMessage("Introduzca un mes y una obra");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	/**
	 * Selecciona las obras de usuario filtrando por obra y nombre de usuario.
	 */
	public void updateObrasDeUsuarioPorUsuarioObra(){
		if(yearFilter!=0 && usuarioFilter.length()>0 && nombreObraFilter.length()>0){
			obraUsuario=obraService.getObrasPorYearObraUsuario(yearFilter, usuarioFilter, nombreObraFilter);
			float horasMes=0;
			float horasTotal=0;
			List<ObraDeUsuario> obraUsuarioAux=new ArrayList<ObraDeUsuario>();
			obraUsuarioAux=obraService.getObrasPorUsuarioObra(usuarioFilter, nombreObraFilter);
			for(int i=0;i<obraUsuario.size();i++){
				horasMes+=obraUsuario.get(i).getHorasAdmin()+obraUsuario.get(i).getHorasTecnico();
			}
			for(int i=0;i<obraUsuarioAux.size();i++){
				horasTotal+=obraUsuarioAux.get(i).getHorasAdmin()+obraUsuarioAux.get(i).getHorasTecnico();
			}
			percentageMes=(horasMes*100)/horasTotal;
			obraUsuarioAux=new ArrayList<ObraDeUsuario>();
			obraUsuarioAux=obraService.getObrasPorUsuario(usuarioFilter);
			
			float horasTotalAux=0;
			for(int i=0;i<obraUsuarioAux.size();i++){
				horasTotalAux+=obraUsuarioAux.get(i).getHorasAdmin()+obraUsuarioAux.get(i).getHorasTecnico();
			}
			percentageTotal=(horasTotal*100)/horasTotalAux;
			creaInforme();
		}
		else{
			FacesMessage msg = new FacesMessage("Introduzca un usuario y una obra");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	/**
	 * Selecciona las obras de usuario filtrando por mes y usuario.
	 */
	public void updateObrasDeUsuarioPorUsuarioMes(){
		int code=0;
		if(mesFilter.equals("Enero"))
			code=01;
		if(mesFilter.equals("Febrero"))
			code=02;
		if(mesFilter.equals("Marzo"))
			code=03;
		if(mesFilter.equals("Abril"))
			code=04;
		if(mesFilter.equals("Mayo"))
			code=05;
		if(mesFilter.equals("Junio"))
			code=06;
		if(mesFilter.equals("Julio"))
			code=07;
		if(mesFilter.equals("Agosto"))
			code=10;
		if(mesFilter.equals("Septiembre"))
			code=11;
		if(mesFilter.equals("Octubre"))
			code=12;
		if(mesFilter.equals("Noviembre"))
			code=13;
		if(mesFilter.equals("Diciembre"))
			code=14;
		if(code!=0 && usuarioFilter.length()>0){
			obraUsuario=obraService.getObrasPorUsuarioMes(usuarioFilter, code);
			creaInforme();
		}
		else{
			FacesMessage msg = new FacesMessage("Introduzca un usuario y un mes");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	/**
	 * Selecciona las obras de usuario filtrando por usuario, mes y nombre de la obra.
	 */
	public void updateObrasDeUsuarioPorUsuarioMesObra(){
		int code=0;
		if(mesFilter.equals("Enero"))
			code=01;
		if(mesFilter.equals("Febrero"))
			code=02;
		if(mesFilter.equals("Marzo"))
			code=03;
		if(mesFilter.equals("Abril"))
			code=04;
		if(mesFilter.equals("Mayo"))
			code=05;
		if(mesFilter.equals("Junio"))
			code=06;
		if(mesFilter.equals("Julio"))
			code=07;
		if(mesFilter.equals("Agosto"))
			code=10;
		if(mesFilter.equals("Septiembre"))
			code=11;
		if(mesFilter.equals("Octubre"))
			code=12;
		if(mesFilter.equals("Noviembre"))
			code=13;
		if(mesFilter.equals("Diciembre"))
			code=14;
		if(code!=0 && usuarioFilter.length()>0 && nombreObraFilter.length()>0){
			obraUsuario=obraService.getObrasPorUsuarioMesObra(usuarioFilter, code, nombreObraFilter);
			float horasMes=0;
			float horasTotal=0;
			List<ObraDeUsuario> obraUsuarioAux=new ArrayList<ObraDeUsuario>();
			obraUsuarioAux=obraService.getObrasPorUsuarioObra(usuarioFilter, nombreObraFilter);
			for(int i=0;i<obraUsuario.size();i++){
				horasMes+=obraUsuario.get(i).getHorasAdmin()+obraUsuario.get(i).getHorasTecnico();
			}
			for(int i=0;i<obraUsuarioAux.size();i++){
				horasTotal+=obraUsuarioAux.get(i).getHorasAdmin()+obraUsuarioAux.get(i).getHorasTecnico();
			}
			percentageMes=(horasMes*100)/horasTotal;
			obraUsuarioAux=new ArrayList<ObraDeUsuario>();
			obraUsuarioAux=obraService.getObrasPorUsuario(usuarioFilter);
			
			float horasTotalAux=0;
			for(int i=0;i<obraUsuarioAux.size();i++){
				horasTotalAux+=obraUsuarioAux.get(i).getHorasAdmin()+obraUsuarioAux.get(i).getHorasTecnico();
			}
			percentageTotal=(horasTotal*100)/horasTotalAux;
			creaInforme();
		}
		else{
			FacesMessage msg = new FacesMessage("Introduzca un usuario, un mes y una obra");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	/**
	 * Actualiza las obras eliminando las que no tienen ratios asociados.
	 */
	public void updateObras(){
		obraUsuario=obraService.getObrasDeUsuario();
		for(int i=0;i<obraUsuario.size();i++){
			if(obraUsuario.get(i).getHorasTecnico()==0 && obraUsuario.get(i).getHorasAdmin()==0)
				obraUsuario.remove(i);
		}
		creaInforme();
	}
	
	/**
	 * Selecciona el nombre de la obra asociada a un código.
	 */
	public void updateObra(){
		this.nombreObraFilter=obraService.getObraByCodigo(codigoFilter).getNombreObra();
		creaInforme();
	}
	
	/**
	 * Selecciona el código asociado a un nombre de obra.
	 */
	public void updateCodigo(){
		this.codigoFilter=obraService.getObraByNombre(nombreObraFilter).getCodigoObra();
		creaInforme();
	}

	public List<Usuario> getTodosUsuarios() {
		return todosUsuarios;
	}


	public void setTodosUsuarios(List<Usuario> todosUsuarios) {
		this.todosUsuarios = todosUsuarios;
	}


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public List<ObraDeUsuario> getObraUsuario() {
		return obraUsuario;
	}


	public void setObraUsuario(List<ObraDeUsuario> obraUsuario) {
		this.obraUsuario = obraUsuario;
	}


	public String getMesFilter() {
		return mesFilter;
	}


	public void setMesFilter(String mesFilter) {
		this.mesFilter = mesFilter;
	}


	public List<Obra> getObras() {
		return obras;
	}


	public void setObras(List<Obra> obras) {
		this.obras = obras;
	}


	/**
	 * Lista con todos los meses del año.
	 * @return lista de meses.
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


	public void setMeses(List<String> meses) {
		this.meses = meses;
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

	public IObraService getObraService() {
		return obraService;
	}

	public void setObraService(IObraService obraService) {
		this.obraService = obraService;
	}

	public String getNombreObraFilter() {
		return nombreObraFilter;
	}

	public void setNombreObraFilter(String nombreObraFilter) {
		this.nombreObraFilter = nombreObraFilter;
	}

	/**
	 * @return the codigoFilter
	 */
	public String getCodigoFilter() {
		return codigoFilter;
	}

	/**
	 * @param codigoFilter the codigoFilter to set
	 */
	public void setCodigoFilter(String codigoFilter) {
		this.codigoFilter = codigoFilter;
	}

	/**
	 * @return the usuarioFilter
	 */
	public String getUsuarioFilter() {
		return usuarioFilter;
	}

	/**
	 * @param usuarioFilter the usuarioFilter to set
	 */
	public void setUsuarioFilter(String usuarioFilter) {
		this.usuarioFilter = usuarioFilter;
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

	/**
	 * @return the percentageMes
	 */
	public float getPercentageMes() {
		return percentageMes;
	}

	/**
	 * @param percentageMes the percentageMes to set
	 */
	public void setPercentageMes(float percentageMes) {
		this.percentageMes = percentageMes;
	}

	/**
	 * @return the percentageTotal
	 */
	public float getPercentageTotal() {
		return percentageTotal;
	}

	/**
	 * @param percentageTotal the percentageTotal to set
	 */
	public void setPercentageTotal(float percentageTotal) {
		this.percentageTotal = percentageTotal;
	}

	/**
	 * @return the informeObraUsuario
	 */
	public List<InformeObraUsuario> getInformeObraUsuarios() {
		return informeObraUsuarios;
	}

	/**
	 * @param informeObraUsuario the informeObraUsuario to set
	 */
	public void setInformeObraUsuarios(List<InformeObraUsuario> informeObraUsuarios) {
		this.informeObraUsuarios = informeObraUsuarios;
	}

	public float getTotalHorasTecnicas() {
		return totalHorasTecnicas;
	}

	public void setTotalHorasTecnicas(float totalHorasTecnicas) {
		this.totalHorasTecnicas = totalHorasTecnicas;
	}

	public float getTotalHorasAdmin() {
		return totalHorasAdmin;
	}

	public void setTotalHorasAdmin(float totalHorasAdmin) {
		this.totalHorasAdmin = totalHorasAdmin;
	}

	public int getTotalVisitas() {
		return totalVisitas;
	}

	public void setTotalVisitas(int totalVisitas) {
		this.totalVisitas = totalVisitas;
	}

	/**
	 * @return the yearFilter
	 */
	public int getYearFilter() {
		return yearFilter;
	}

	/**
	 * @param yearFilter the yearFilter to set
	 */
	public void setYearFilter(int yearFilter) {
		this.yearFilter = yearFilter;
	}

}
