package gpyo.gui.model;

import gpyo.persistence.entity.admin.Obra;
import gpyo.persistence.entity.admin.ObraDeUsuario;
import gpyo.persistence.entity.admin.Role;
import gpyo.persistence.entity.admin.UserRole;
import gpyo.persistence.entity.admin.Usuario;
import gpyo.service.businesslogic.IObraService;
import gpyo.service.businesslogic.IUserService;
import gpyo.service.businesslogic.RoleService;
import gpyo.util.FacesUtil;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
 * Bean que sirve para gestionar a los usuarios.
 * 
 * @author Alberto Revilla Gil
 * @version 1.0
 */
@ManagedBean
@ViewScoped
@Controller
public class UsuarioBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4301433544739297908L;
	
	private Usuario usuario=new Usuario();
	private Usuario usuario2=new Usuario();
	@Autowired
	private IUserService userService;
	@Autowired
	private IObraService obraService;
	@Autowired
	private RoleService roleService;
	private String password2;
	private List<String> rol=new ArrayList<String>();
	private List<Usuario> todosUsuarios=new ArrayList<Usuario>();
	private List<ObraDeUsuario> todasObrasUsuario=new ArrayList<ObraDeUsuario>();
	private Date date2;
	private String nombreObraFilter;
	private String mesObraFilter;
	private List<Obra> obras=new ArrayList<Obra>();
	private List<String> meses=new ArrayList<String>();
	private Usuario usuario3=new Usuario();
	private float totalHorasAdmin;
	private float totalHorasTecnico;
	private String role;
	
	private float totalVisitas;
	
	public UsuarioBean() {
	}
	
	/**
	 * Actualiza la información de un usuario en la base de datos.
	 */
	public void updateUsuario(){
    	if(usuario.getPassword().equals(password2)){
    		userService.updateUsuario(usuario);
			FacesMessage msg = new FacesMessage("Datos de usuario actualizados", (usuario).getNombreUsuario());
		    FacesContext.getCurrentInstance().addMessage(null, msg);
    	}
    	else{
    		FacesMessage msg2 = new FacesMessage("Las contraseñas deben ser iguales", (usuario).getNombreUsuario());
            FacesContext.getCurrentInstance().addMessage(null, msg2);
    	}
    }
    
	/**
	 * Inserta un usuario en la base de datos.
	 */
    @SuppressWarnings("unused")
	public void insertUsuario(){
    	if(usuario2.getPassword().equals(password2)){	
    		usuario2.setRole(new Role());
    		Role userRole = roleService.getRole(role);
    		usuario2.setRole(userRole);
    		usuario2.setRole1("");
    		userService.createUser(usuario2);
    		UserRole userRoles = new UserRole(usuario2.getId(), userRole.getId());
			FacesMessage msg = new FacesMessage("Usuario insertado", (usuario2).getNombreUsuario());
		    FacesContext.getCurrentInstance().addMessage(null, msg);
    	}
    	else{
    		FacesMessage msg2 = new FacesMessage("Las contraseñas deben ser iguales", (usuario2).getNombreUsuario());
            FacesContext.getCurrentInstance().addMessage(null, msg2);
    	}
    }
    
    /**
     * Muestra la información del usuario logueado.
     */
    public void showUsuario(){
		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		usuario = userService.getUserByName(nombreUsuario);
		usuario=userService.getUserByName(usuario.getNombreUsuario());
	}
    
    /**
     * Elimina a un usuario de la base de datos.
     * @param usuario El usuario que se va a eliminar.
     */
    public void deleteUsuario(Usuario usuario){
    	userService.deleteUser(usuario);
    	FacesMessage msg = new FacesMessage("Usuario eliminado", (usuario).getNombreUsuario());
	    FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    /**
     * Actualiza los cambios realizados al editar a un usuario.
     * @param event
     */
    public void onEdit(RowEditEvent event) {
	 	userService.updateUsuario((Usuario) event.getObject());
        FacesMessage msg = new FacesMessage("Usuario editado", ((Usuario) event.getObject()).getNombreUsuario());
        FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	     
    /**
     * Cancela los cambios realizados al editar un usuario.
     * @param event
     */
	public void onCancel(RowEditEvent event) {
	    FacesMessage msg = new FacesMessage("Edición cancelada", ((Usuario) event.getObject()).getNombreUsuario());
	    FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	/**
	 * Muestra los ratios de un usuario.
	 * @param usuario
	 */
	public void showRatios(Usuario usuario){
		todasObrasUsuario=obraService.getTodasObrasDeUsuario();
		FacesUtil.setSessionMapValue("usuario4view", usuario);
		totalHorasAdmin=0;
		totalHorasTecnico=0;
		totalVisitas=0;
		for(int i=0;i<todasObrasUsuario.size();i++){
			totalHorasAdmin += todasObrasUsuario.get(i).getHorasAdmin();
			totalHorasTecnico += todasObrasUsuario.get(i).getHorasTecnico();
			if(todasObrasUsuario.get(i).getVisita().equals("SI") || todasObrasUsuario.get(i).getVisita().equals("Si") || todasObrasUsuario.get(i).getVisita().equals("si"))
				totalVisitas++;
		}
	}
	
	/**
	 * Actualiza las obras de usuario por fecha. 
	 */
	public void updateObrasDeUsuarioPorFecha(){
		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		usuario2 = userService.getUserByName(nombreUsuario);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		
		if(date2!=null){
			todasObrasUsuario=obraService.getObrasDeUsuario(sdf.format(date2));
			totalHorasAdmin=0;
			totalHorasTecnico=0;
			totalVisitas=0;
			for(int i=0;i<todasObrasUsuario.size();i++){
				totalHorasAdmin += todasObrasUsuario.get(i).getHorasAdmin();
				totalHorasTecnico += todasObrasUsuario.get(i).getHorasTecnico();
				if(todasObrasUsuario.get(i).getVisita().equals("SI") || todasObrasUsuario.get(i).getVisita().equals("Si") || todasObrasUsuario.get(i).getVisita().equals("si"))
					totalVisitas++;
			}
		}
		else{
			FacesMessage msg = new FacesMessage("Introduzca una fecha");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	/**
	 * Selecciona las obras de un usuario por fecha y nombre de obra.
	 */
	public void updateObrasDeUsuarioDoble(){
		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		usuario2 = userService.getUserByName(nombreUsuario);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		if(date2!=null && nombreObraFilter.length()>0){
			todasObrasUsuario=obraService.getObrasDeUsuario(usuario2.getId(),sdf.format(date2), nombreObraFilter);
			totalHorasAdmin=0;
			totalHorasTecnico=0;
			totalVisitas=0;
			for(int i=0;i<todasObrasUsuario.size();i++){
				totalHorasAdmin += todasObrasUsuario.get(i).getHorasAdmin();
				totalHorasTecnico += todasObrasUsuario.get(i).getHorasTecnico();
				if(todasObrasUsuario.get(i).getVisita().equals("SI") || todasObrasUsuario.get(i).getVisita().equals("Si") || todasObrasUsuario.get(i).getVisita().equals("si"))
					totalVisitas++;
			}
		}
		else{
			FacesMessage msg = new FacesMessage("Introduzca una fecha y el nombre de una obra");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
    
	/**
	 * Selecciona las obras de usuario por nombre de obra.
	 */
	public void updateObrasDeUsuarioPorObra(){
		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		usuario2 = userService.getUserByName(nombreUsuario);
		if(nombreObraFilter.length()>0){
			todasObrasUsuario=obraService.getObrasDeUsuario2(usuario2.getId(), nombreObraFilter);
			totalHorasAdmin=0;
			totalHorasTecnico=0;
			totalVisitas=0;
			for(int i=0;i<todasObrasUsuario.size();i++){
				totalHorasAdmin += todasObrasUsuario.get(i).getHorasAdmin();
				totalHorasTecnico += todasObrasUsuario.get(i).getHorasTecnico();
				if(todasObrasUsuario.get(i).getVisita().equals("SI") || todasObrasUsuario.get(i).getVisita().equals("Si") || todasObrasUsuario.get(i).getVisita().equals("si"))
					totalVisitas++;
			}
		}
		else{
			FacesMessage msg = new FacesMessage("Introduzca el nombre de una obra");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	/**
	 * Selecciona las obras de usuario por mes.
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
		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		usuario2 = userService.getUserByName(nombreUsuario);
		if(code!=0){
			todasObrasUsuario=obraService.getObrasDeUsuarioMes(usuario2.getId(), code);
			totalHorasAdmin=0;
			totalHorasTecnico=0;
			totalVisitas=0;
			for(int i=0;i<todasObrasUsuario.size();i++){
				totalHorasAdmin += todasObrasUsuario.get(i).getHorasAdmin();
				totalHorasTecnico += todasObrasUsuario.get(i).getHorasTecnico();
				if(todasObrasUsuario.get(i).getVisita().equals("SI") || todasObrasUsuario.get(i).getVisita().equals("Si") || todasObrasUsuario.get(i).getVisita().equals("si"))
					totalVisitas++;
			}
		}
		else{
			FacesMessage msg = new FacesMessage("Introduzca un mes");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	/**
	 * Selecciona las obras de usuario por mes y obra.
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
		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		usuario2 = userService.getUserByName(nombreUsuario);
		if(code!=0 && nombreObraFilter.length()>0){
			todasObrasUsuario=obraService.getObrasDeUsuarioMesObra(usuario2.getId(), code, nombreObraFilter);
			totalHorasAdmin=0;
			totalHorasTecnico=0;
			totalVisitas=0;
			for(int i=0;i<todasObrasUsuario.size();i++){
				totalHorasAdmin += todasObrasUsuario.get(i).getHorasAdmin();
				totalHorasTecnico += todasObrasUsuario.get(i).getHorasTecnico();
				if(todasObrasUsuario.get(i).getVisita().equals("SI") || todasObrasUsuario.get(i).getVisita().equals("Si") || todasObrasUsuario.get(i).getVisita().equals("si"))
					totalVisitas++;
			}
		}
		else{
			FacesMessage msg = new FacesMessage("Introduzca un mes y el nombre de una obra");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
    
	/**
	 * Selecciona las obras de un usuario.
	 */
	public void updateObrasDeUsuario(){
		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		usuario2 = userService.getUserByName(nombreUsuario);
		todasObrasUsuario=obraService.getTodasObrasDeUsuario();
		totalHorasAdmin=0;
		totalHorasTecnico=0;
		totalVisitas=0;
		for(int i=0;i<todasObrasUsuario.size();i++){
			totalHorasAdmin += todasObrasUsuario.get(i).getHorasAdmin();
			totalHorasTecnico += todasObrasUsuario.get(i).getHorasTecnico();
			if(todasObrasUsuario.get(i).getVisita().equals("SI") || todasObrasUsuario.get(i).getVisita().equals("Si") || todasObrasUsuario.get(i).getVisita().equals("si"))
				totalVisitas++;
		}
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Usuario getUsuario2() {
		return usuario2;
	}
	public void setUsuario2(Usuario usuario2) {
		this.usuario2 = usuario2;
	}

	public IUserService getUserService() {
		return userService;
	}

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
	 * Lista con los posibles roles de usuario.
	 * @return the rol
	 */
	public List<String> getRol() {
		rol=new ArrayList<String>();
		rol.add("admin");
		rol.add("usuario");
		rol.add("tesoreria");
		return rol;
	}

	/**
	 * @param rol the rol to set
	 */
	public void setRol(List<String> rol) {
		this.rol = rol;
	}

	/**
	 * @return the todosUsuarios
	 */
	public List<Usuario> getTodosUsuarios() {
		todosUsuarios=userService.allUsers();
		return todosUsuarios;
	}

	/**
	 * @param todosUsuarios the todosUsuarios to set
	 */
	public void setTodosUsuarios(List<Usuario> todosUsuarios) {
		this.todosUsuarios = todosUsuarios;
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
	 * @return the todasObrasUsuario
	 */
	public List<ObraDeUsuario> getTodasObrasUsuario() {
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

	public String getNombreObraFilter() {
		return nombreObraFilter;
	}

	public void setNombreObraFilter(String nombreObraFilter) {
		this.nombreObraFilter = nombreObraFilter;
	}

	public String getMesObraFilter() {
		return mesObraFilter;
	}

	public void setMesObraFilter(String mesObraFilter) {
		this.mesObraFilter = mesObraFilter;
	}

	/**
	 * @return the obras
	 */
	public List<Obra> getObras() {
		obras=obraService.getObras();
		return obras;
	}

	/**
	 * @param obras the obras to set
	 */
	public void setObras(List<Obra> obras) {
		this.obras = obras;
	}

	/**
	 * Lista con los meses del año.
	 * @return the meses
	 */
	public List<String> getMeses() {
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
	 * @return the usuario3
	 */
	public Usuario getUsuario3() {
		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		usuario3 = userService.getUserByName(nombreUsuario);
		return usuario3;
	}

	/**
	 * @param usuario3 the usuario3 to set
	 */
	public void setUsuario3(Usuario usuario3) {
		this.usuario3 = usuario3;
	}

	public float getTotalHorasAdmin() {
		return totalHorasAdmin;
	}

	public void setTotalHorasAdmin(float totalHorasAdmin) {
		this.totalHorasAdmin = totalHorasAdmin;
	}

	public float getTotalHorasTecnico() {
		return totalHorasTecnico;
	}

	public void setTotalHorasTecnico(float totalHorasTecnico) {
		this.totalHorasTecnico = totalHorasTecnico;
	}

	public float getTotalVisitas() {
		return totalVisitas;
	}

	public void setTotalVisitas(float totalVisitas) {
		this.totalVisitas = totalVisitas;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

}
