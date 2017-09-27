package gpyo.gui.model;

import gpyo.persistence.entity.admin.DatosGenerales;
import gpyo.persistence.entity.admin.Obra;
import gpyo.persistence.entity.admin.ObraDeUsuario;
import gpyo.persistence.entity.admin.ObraDeUsuarioHistorica;
import gpyo.persistence.entity.admin.ObraDeUsuarioHistoricaAux;
import gpyo.persistence.entity.admin.Usuario;
import gpyo.service.businesslogic.IObraService;
import gpyo.service.businesslogic.ITesoreriaService;
import gpyo.service.businesslogic.IUserService;

import java.io.Serializable;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Bean que sirve para gestionar las obras.
 * 
 * @author Alberto Revilla Gil
 * @version 1.0
 */
@ManagedBean
@ViewScoped
@Controller
public class ObraBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4754491926788393088L;
	
	private Obra obra=new Obra();
	@Autowired
	private IObraService obraService;
	@Autowired
	private IUserService userService;
	private List<Obra> todasObras=new ArrayList<Obra>();
	private List<ObraDeUsuario> obraDeUsuario=new ArrayList<ObraDeUsuario>();
	private Date date2;
	private List<Obra> obras=new ArrayList<Obra>();
	private List<ObraDeUsuarioHistorica> obrasHistoricas=new ArrayList<ObraDeUsuarioHistorica>();
	/**
	 * Lista con los códigos de todas las obras.
	 */
	private List<Obra> obrasCodigo=new ArrayList<Obra>();
	/**
	 * Lista con todos los títulos de las obras.
	 */
	private List<Obra> obrasTitulo=new ArrayList<Obra>();
	/**
	 * Filtro con el nombre de la obra.
	 */
	private String nombreObraFilter;
	/**
	 * Nombre de la obra que se va a borrar.
	 */
	private String nombreObraDelete;
	/**
	 * Código por el que hará el filtro.
	 */
	private String codigoFilter;
	/**
	 * Título para filtrar los resultados.
	 */
	private String tituloFilter;
	/**
	 * Mes para filtrar los resultados.
	 */
	private String mesFilter;
	/**
	 * Usuario para hacer el filtro.
	 */
	private String usuarioFilter;
	/**
	 * Año para filtrar.
	 */
	private String yearFiltro;
	/**
	 * Lista con los meses del año.
	 */
	private List<String> meses=new ArrayList<String>();
	private float sumTotal=0;
	private float sumTotalTecnicas=0;
	private float sumTotalAdmin=0;
	private float sumTotalTecnicasAnt=0;
	private float sumTotalAdminAnt=0;
	private float totalVisitasAnt=0;
	private Date fecha;
	private Date fechaFin;
	List<Usuario> usuarios=new ArrayList<Usuario>();
	private float total=0;
	private float totalTecnicas=0;
	private float totalVisitas=0;
	private float totalAdmin=0;
	private boolean renderPrevision=false;
	private float horasAnteriores=0;
	private float horasActuales=0;
	private float horasTotalesPrevision=0;
	private List<ObraDeUsuarioHistorica> obrasHistorico=new ArrayList<ObraDeUsuarioHistorica>();
	private ITesoreriaService tesoreriaService;
	private List<DatosGenerales> datosGenerales = new ArrayList<DatosGenerales>();
	private int yearFilter;
	

	public ObraBean() {
	}
	
	/**
	 * Guarda la obra en la base de datos.
	 */
	public void saveObra(){
		if(obra.getNombreObra().length()>0){
			obraService.saveObra(obra);
			todasObras = new ArrayList<Obra>();
			todasObras=obraService.getObras();
			FacesMessage msg = new FacesMessage("Obra guardada", obra.getNombreObra());
		    FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		else{
			FacesMessage msg = new FacesMessage("Faltan datos para guardar la obra.", obra.getNombreObra());
		    FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	/**
	 * Muestra todas las obras.
	 */
	public void showObras(){
		todasObras = new ArrayList<Obra>();
		obraDeUsuario = new ArrayList<ObraDeUsuario>();
		todasObras=obraService.getObras();
		datosGenerales = new ArrayList<DatosGenerales>();
		obraDeUsuario=obraService.getObrasDeUsuario();
		renderPrevision=false;
		float sum=0;
		float sum2=0;
		float sumVisita=0;
		for(int j=0;j<todasObras.size();j++){
			float sumTemp=0;
			float sumTemp2=0;
			float sumTempVisita=0;
			for(int i=0;i<obraDeUsuario.size();i++){
				if(todasObras.get(j).getIdObra()==obraDeUsuario.get(i).getObra().getIdObra()){
					sumTemp+=obraDeUsuario.get(i).getHorasTecnico();
					sum+=obraDeUsuario.get(i).getHorasTecnico();
					sumTemp2+=obraDeUsuario.get(i).getHorasAdmin();
					sum2+=obraDeUsuario.get(i).getHorasAdmin();
					if(obraDeUsuario.get(i).getVisita().equals("Si") || obraDeUsuario.get(i).getVisita().equals("si")){
						sumVisita++;
						sumTempVisita++;
					}
				}
			}
			datosGenerales.add(new DatosGenerales());
			datosGenerales.get(j).setCodigoObra(todasObras.get(j).getCodigoObra());
			datosGenerales.get(j).setTitulo(todasObras.get(j).getTitulo());
			datosGenerales.get(j).setNombreObra(todasObras.get(j).getNombreObra());
			datosGenerales.get(j).setHorasAdmin(sumTemp2);
			datosGenerales.get(j).setHorasTecnico(sumTemp);
			datosGenerales.get(j).setVisitas(sumTempVisita);
			datosGenerales.get(j).setHorasTotal(sumTemp2+sumTemp);
		}
		
		List<ObraDeUsuarioHistorica> obrasHistoricos = new ArrayList<ObraDeUsuarioHistorica>();
		List<ObraDeUsuarioHistoricaAux> obrasHist = new ArrayList<ObraDeUsuarioHistoricaAux>();
		obrasHistoricos=obraService.getObrasHistorico();
		for(int i=0;i<obrasHistoricos.size();i++){
			ObraDeUsuarioHistoricaAux aux = new ObraDeUsuarioHistoricaAux();
			aux.setHorasAdmin(obrasHistoricos.get(i).getHorasAdmin());
			aux.setHorasTecnicas(obrasHistoricos.get(i).getHorasTecnicas());
			aux.setHorasTotal(obrasHistoricos.get(i).getHorasTotal());
			aux.setNombreObra(obrasHistoricos.get(i).getNombreObra());
			aux.setTitulo(obrasHistoricos.get(i).getTitulo());
			aux.setUsuario(obrasHistoricos.get(i).getUsuario());
			aux.setVisita(obrasHistoricos.get(i).getVisita());
			aux.setYearRatio(obrasHistoricos.get(i).getYearRatio());
			obrasHist.add(aux);
		}
		for(int i=0;i<obrasHist.size();i++){
			for(int j=obrasHist.size()-1;j>0;j--){
				if(i!=j){
					if(obrasHist.get(i).getNombreObra().equals(obrasHist.get(j).getNombreObra())){
						obrasHist.get(i).setHorasAdmin(obrasHist.get(i).getHorasAdmin()+obrasHist.get(j).getHorasAdmin());
						obrasHist.get(i).setHorasTecnicas(obrasHist.get(i).getHorasTecnicas()+obrasHist.get(j).getHorasTecnicas());
						obrasHist.get(i).setHorasTotal(obrasHist.get(i).getHorasTotal()+obrasHist.get(j).getHorasTotal());
						obrasHist.remove(j);
					}
				}
			}
		}
		setSumTotal(sum+sum2);
		setSumTotalAdmin(sum2);
		setSumTotalTecnicas(sum);
		setTotalVisitas(sumVisita);
		for(int j=0;j<obrasHist.size();j++){
			for(int i=0;i<todasObras.size();i++){
				float sumTemp=0;
				float sumTemp2=0;
				float sumTempVisita=0;
				if(todasObras.get(i).getNombreObra().equals(obrasHist.get(j).getNombreObra())){
					sumTemp+=obrasHist.get(j).getHorasTecnicas();
					sum+=obrasHist.get(j).getHorasTecnicas();
					sumTemp2+=obrasHist.get(j).getHorasAdmin();
					sum2+=obrasHist.get(j).getHorasAdmin();
					sumVisita += obrasHist.get(j).getVisita();
					sumTempVisita += obrasHist.get(j).getVisita();
					datosGenerales.get(i).setHorasAdminAnt(sumTemp2);
					datosGenerales.get(i).setHorasTecnicoAnt(sumTemp);
					datosGenerales.get(i).setVisitasAnt(sumTempVisita);
					datosGenerales.get(i).setHorasTotal(datosGenerales.get(i).getHorasTotal()+sumTemp+sumTemp2);
				}
			}
		}
		setSumTotal(getSumTotal()+sum+sum2);
		setSumTotalAdminAnt(sum2);
		setSumTotalTecnicasAnt(sum);
		setTotalVisitasAnt(sumVisita);
	}
	
	/**
	 * Crea un informe de la obra indicando el total de horas invertidas.
	 */
	public void showObrasInforme(){
		todasObras = new ArrayList<Obra>();
		obraDeUsuario = new ArrayList<ObraDeUsuario>();
		todasObras=obraService.getObras();
		obraDeUsuario=obraService.getObrasDeUsuario();
		renderPrevision=false;
		float sum=0;
		float sum2=0;
		@SuppressWarnings("unused")
		float sumVisita=0;
		for(int j=0;j<todasObras.size();j++){
			float sumTemp=0;
			float sumTemp2=0;
			float sumTempVisita=0;
			for(int i=0;i<obraDeUsuario.size();i++){
				if(todasObras.get(j).getIdObra()==obraDeUsuario.get(i).getObra().getIdObra()){
					sumTemp+=obraDeUsuario.get(i).getHorasTecnico();
					sum+=obraDeUsuario.get(i).getHorasTecnico();
					sumTemp2+=obraDeUsuario.get(i).getHorasAdmin();
					sum2+=obraDeUsuario.get(i).getHorasAdmin();
					if(obraDeUsuario.get(i).getVisita().equals("Si") || obraDeUsuario.get(i).getVisita().equals("si")){
						sumVisita++;
						sumTempVisita++;
					}
				}
			}
			todasObras.get(j).setHorasSum(sumTemp+sumTemp2);
			todasObras.get(j).setHorasTecnicoSum(sumTemp);
			todasObras.get(j).setHorasAdminSum(sumTemp2);
			todasObras.get(j).setHorasVisitaSum(sumTempVisita);
		}
		setSumTotal(sum+sum2);
		setSumTotalAdmin(sum2);
		setSumTotalTecnicas(sum);
	}
	
	/**
	 * Guarda todas las obras en la tabla de históricos ántes de borrar la información de la base de datos.
	 */
	public void save(){
		todasObras = new ArrayList<Obra>();
		obraDeUsuario = new ArrayList<ObraDeUsuario>();
		todasObras=obraService.getObras();
		obraDeUsuario=obraService.getObrasDeUsuario();
		float sum=0;
		float sum2=0;
		
		for(int j=0;j<todasObras.size();j++){
			float sumTemp=0;
			float sumTemp2=0;
			for(int i=0;i<obraDeUsuario.size();i++){
				if(todasObras.get(j).getIdObra()==obraDeUsuario.get(i).getObra().getIdObra()){
					sumTemp+=obraDeUsuario.get(i).getHorasTecnico();
					sum+=obraDeUsuario.get(i).getHorasTecnico();
					sumTemp2+=obraDeUsuario.get(i).getHorasAdmin();
					sum2+=obraDeUsuario.get(i).getHorasAdmin();
				}
			}
			todasObras.get(j).setHorasSum(sumTemp+sumTemp2);
			todasObras.get(j).setHorasTecnicoSum(sumTemp);
			todasObras.get(j).setHorasAdminSum(sumTemp2);
		}
		setSumTotal(sum+sum2);
		setSumTotalAdmin(sum2);
		setSumTotalTecnicas(sum);
		obrasHistorico=new ArrayList<ObraDeUsuarioHistorica>();
		for(int i=0;i<obraDeUsuario.size();i++){
			ObraDeUsuarioHistorica obraHist=new ObraDeUsuarioHistorica();
			obraHist.setUsuario(obraDeUsuario.get(i).getIdObraDeUsuario().getUsuario().getNombreUsuario());
			obraHist.setCodigoObra(obraDeUsuario.get(i).getIdObraDeUsuario().getObra().getCodigoObra());
			obraHist.setTitulo(obraDeUsuario.get(i).getIdObraDeUsuario().getObra().getTitulo());
			obraHist.setNombreObra(obraDeUsuario.get(i).getIdObraDeUsuario().getObra().getNombreObra());
			obraHist.setHorasTecnicas(obraDeUsuario.get(i).getHorasTecnico());
			obraHist.setHorasAdmin(obraDeUsuario.get(i).getHorasAdmin());
			obraHist.setHorasTotal(obraDeUsuario.get(i).getHorasTecnico()+obraDeUsuario.get(i).getHorasAdmin());
			String year=obraDeUsuario.get(i).getFecha();
			obraHist.setYearRatio(year);
			obrasHistorico.add(obraHist);
		}
		for(int i=0;i<obrasHistorico.size();i++){
			obraService.saveObraHistorico(obrasHistorico.get(i));	
		}
		List<ObraDeUsuarioHistorica> obraHist=new ArrayList<ObraDeUsuarioHistorica>(); 
		obrasHistorico=new ArrayList<ObraDeUsuarioHistorica>();
		obraHist=obraService.getObrasHistorico();
		for(int i=0;i<obraHist.size();i++)
			obrasHistorico.add(obraHist.get(i));
	}
	
	/**
	 * Actualiza en la base de datos la obra editada.
	 * @param La obra que se quiere editar.
	 */
	public void onEdit(RowEditEvent event) {
		obraService.updateObra((Obra)event.getObject());
        FacesMessage msg = new FacesMessage("Obra editada", ((Obra) event.getObject()).getNombreObra());
 
        FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	     
	/**
	 * Cancela los cambios realiados al editar una obra.
	 * @param Obra editada.
	 */
	public void onCancel(RowEditEvent event) {
	    FacesMessage msg = new FacesMessage("Edición cancelada", ((Obra) event.getObject()).getNombreObra());
	
	    FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	/**
	 * Elimina de la base de datos una obra.
	 */
	public void deleteObra(){
		Obra obra=new Obra();
		try{
			obra=obraService.getObraByNombre(nombreObraDelete);
	    	obraService.deleteObra(obra);
	    	todasObras = new ArrayList<Obra>();
	    	todasObras=obraService.getObras();
	    	FacesMessage msg = new FacesMessage("Obra eliminada.", (obra).getNombreObra());
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}catch(Exception e){
			FacesMessage msg = new FacesMessage("No se pudo eliminar la obra.");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
    }
	
	/**
	 * Selecciona todas las obras guardadas en la tabla de históricos.
	 */
	public void updateObrasDeUsuarioHistorico(){
		obrasHistorico = new ArrayList<ObraDeUsuarioHistorica>();
		obrasHistorico=obraService.getObrasHistorico();
		obrasHistoricas=obrasHistorico;
		for(int i=0;i<obrasHistorico.size();i++){
			for(int j=1;j<obrasHistorico.size();j++){
				if(i!=j){
					if(obrasHistorico.get(i).getUsuario().equals(obrasHistorico.get(j).getUsuario()) && obrasHistorico.get(i).getNombreObra().equals(obrasHistorico.get(j).getNombreObra())){
						if(obrasHistorico.get(i).getHorasAdmin()==0)
							obrasHistorico.get(i).setHorasAdmin(obrasHistorico.get(i).getHorasAdmin()+obrasHistorico.get(j).getHorasAdmin());
						if(obrasHistorico.get(i).getHorasTecnicas()==0)
							obrasHistorico.get(i).setHorasTecnicas(obrasHistorico.get(i).getHorasTecnicas()+obrasHistorico.get(j).getHorasTecnicas());
						if(obrasHistorico.get(i).getHorasTotal()==0)
							obrasHistorico.get(i).setHorasTotal(obrasHistorico.get(i).getHorasTotal()+obrasHistorico.get(j).getHorasTotal());
						obrasHistorico.remove(j);
					}
				}
			}
		}
		obrasHistoricas=obrasHistorico;
		usuarios=userService.allUsers();
	}
	
	/**
	 * Filtra las obras históricas por nombre de obra.
	 */
	public void updateObrasDeUsuarioObraHistorica(){
		obrasHistorico = new ArrayList<ObraDeUsuarioHistorica>();
		obrasHistorico=obraService.getObrasHistoricoPorObra(nombreObraFilter);
	}
	
	/**
	 * Filtra las obras históricas por código de obra.
	 */
	public void updateObrasDeUsuarioCodigoHistorica(){
		obrasHistorico = new ArrayList<ObraDeUsuarioHistorica>();
		obrasHistorico=obraService.getObrasHistoricoPorCodigo(codigoFilter);
	}
	
	/**
	 * Selecciona las obras históricas de un usuario.
	 */
	public void updateObrasDeUsuarioPorUsuarioHistorica(){
		obrasHistorico = new ArrayList<ObraDeUsuarioHistorica>();
		obrasHistorico=obraService.getObrasHistoricoPorUsuario(usuarioFilter);
		for(int i=0;i<obrasHistorico.size();i++){
			for(int j=1;j<obrasHistorico.size();j++){
				if(i!=j){
					if(obrasHistorico.get(i).getUsuario().equals(obrasHistorico.get(j).getUsuario()) && obrasHistorico.get(i).getNombreObra().equals(obrasHistorico.get(j).getNombreObra())){
						if(obrasHistorico.get(i).getHorasAdmin()==0)
							obrasHistorico.get(i).setHorasAdmin(obrasHistorico.get(i).getHorasAdmin()+obrasHistorico.get(j).getHorasAdmin());
						if(obrasHistorico.get(i).getHorasTecnicas()==0)
							obrasHistorico.get(i).setHorasTecnicas(obrasHistorico.get(i).getHorasTecnicas()+obrasHistorico.get(j).getHorasTecnicas());
						if(obrasHistorico.get(i).getHorasTotal()==0)
							obrasHistorico.get(i).setHorasTotal(obrasHistorico.get(i).getHorasTotal()+obrasHistorico.get(j).getHorasTotal());
						obrasHistorico.remove(j);
					}
				}
			}
		}
	}
	
	/**
	 * Selecciona las obras históricas del año seleccionado.
	 */
	public void updateObrasDeUsuarioYearHistorica(){
		obrasHistorico = new ArrayList<ObraDeUsuarioHistorica>();
		obrasHistorico=obraService.getObrasHistoricoPorYear(yearFiltro);
		for(int i=0;i<obrasHistorico.size();i++){
			for(int j=1;j<obrasHistorico.size();j++){
				if(i!=j){
					if(obrasHistorico.get(i).getUsuario().equals(obrasHistorico.get(j).getUsuario()) && obrasHistorico.get(i).getNombreObra().equals(obrasHistorico.get(j).getNombreObra())){
						if(obrasHistorico.get(i).getHorasAdmin()==0)
							obrasHistorico.get(i).setHorasAdmin(obrasHistorico.get(i).getHorasAdmin()+obrasHistorico.get(j).getHorasAdmin());
						if(obrasHistorico.get(i).getHorasTecnicas()==0)
							obrasHistorico.get(i).setHorasTecnicas(obrasHistorico.get(i).getHorasTecnicas()+obrasHistorico.get(j).getHorasTecnicas());
						if(obrasHistorico.get(i).getHorasTotal()==0)
							obrasHistorico.get(i).setHorasTotal(obrasHistorico.get(i).getHorasTotal()+obrasHistorico.get(j).getHorasTotal());
						obrasHistorico.remove(j);
					}
				}
			}
		}
	}
	
	/**
	 * Elimina una obra y los ratios asociados a ella.
	 */
	public void borrar(){
		List<ObraDeUsuario> deleteObras=new ArrayList<ObraDeUsuario>();
		deleteObras=obraService.getObrasDeUsuario();
		save();
		for(int i=0;i<deleteObras.size();i++)
			obraService.deleteRatio(deleteObras.get(i));
	}

    /**
     * Selecciona las obras de usuario filtrando por nombre de obra.
     */
	public void updateObrasDeUsuarioPorObra(){
		if(nombreObraFilter.length()>0){
			todasObras = new ArrayList<Obra>();
			obraDeUsuario = new ArrayList<ObraDeUsuario>();
			usuarios = new ArrayList<Usuario>();
			todasObras=obraService.getObras2(nombreObraFilter);
			obraDeUsuario=obraService.getObrasPorNombre(nombreObraFilter);
			usuarios=userService.allUsers();
			total=0;
			totalTecnicas=0;
			totalAdmin=0;
			horasActuales=0;
			horasAnteriores=0;
			horasTotalesPrevision=0;
			for(int i=0;i<usuarios.size();i++){
				usuarios.get(i).setHorasAdminObra(0);
				usuarios.get(i).setHorasTecnicasObra(0);
				usuarios.get(i).setVisita(0);
			}
			float sum=0;
			float sum2=0;
			for(int j=0;j<todasObras.size();j++){
				float sumTemp=0;
				float sumTemp2=0;
				float sumVisitas=0;
				for(int i=0;i<obraDeUsuario.size();i++){
					float sumTempVisita=0;
					if(todasObras.get(j).getIdObra()==obraDeUsuario.get(i).getObra().getIdObra()){
						sumTemp+=obraDeUsuario.get(i).getHorasTecnico();
						sum+=obraDeUsuario.get(i).getHorasTecnico();
						sumTemp2+=obraDeUsuario.get(i).getHorasAdmin();
						sum2+=obraDeUsuario.get(i).getHorasAdmin();
						if(obraDeUsuario.get(i).getVisita().equals("Si") || obraDeUsuario.get(i).getVisita().equals("si") || obraDeUsuario.get(i).getVisita().equals("SI"))
							sumTempVisita++;
					}
					for(int k=0;k<usuarios.size();k++){
						if(usuarios.get(k).getId()==obraDeUsuario.get(i).getIdObraDeUsuario().getUsuario().getId()){
							usuarios.get(k).setHorasAdminObra(usuarios.get(k).getHorasAdminObra()+obraDeUsuario.get(i).getHorasAdmin());
							usuarios.get(k).setHorasTecnicasObra(usuarios.get(k).getHorasTecnicasObra()+obraDeUsuario.get(i).getHorasTecnico());
							usuarios.get(k).setVisita(sumTempVisita);
							sumVisitas += sumTempVisita;
						}
					}
				}
				todasObras.get(j).setHorasSum(sumTemp+sumTemp2);
				todasObras.get(j).setHorasTecnicoSum(sumTemp);
				todasObras.get(j).setHorasAdminSum(sumTemp2);
				setTotalVisitas(sumVisitas);
			}
			setSumTotal(sum+sum2);
			setSumTotalAdmin(sum2);
			setSumTotalTecnicas(sum);
			
		
			for(int i=0;i<obraService.getObrasHistoricoPorObra(nombreObraFilter).size();i++)
				horasAnteriores+=obraService.getObrasHistoricoPorObra(nombreObraFilter).get(i).getHorasAdmin()+obraService.getObrasHistoricoPorObra(nombreObraFilter).get(i).getHorasTecnicas();
			for(int i=0;i<obraService.getObrasPorNombre(nombreObraFilter).size();i++)
				horasActuales+=obraService.getObrasPorNombre(nombreObraFilter).get(i).getHorasAdmin()+obraService.getObrasPorNombre(nombreObraFilter).get(i).getHorasTecnico();
			
			horasTotalesPrevision=horasAnteriores+horasActuales;
			
			for(int i=0;i<usuarios.size();i++){
				usuarios.get(i).setHorasTotal(usuarios.get(i).getHorasAdminObra()+usuarios.get(i).getHorasTecnicasObra());
				total+=usuarios.get(i).getHorasTotal();
				totalTecnicas+=usuarios.get(i).getHorasTecnicasObra();
				totalAdmin+=usuarios.get(i).getHorasAdminObra();
				if(usuarios.get(i).getHorasAdminObra()==0 && usuarios.get(i).getHorasTecnicasObra()==0){
					usuarios.remove(i);
					i--;
				}
			}
			renderPrevision=true;
		}
		else{
			FacesMessage msg = new FacesMessage("Introduzca el nombre de una obra");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	/**
	 * Selecciona las obras de usuario filtradas por código de obra.
	 */
	public void updateObrasDeUsuarioPorCodigo(){
		if(codigoFilter.length()>0){
			todasObras = new ArrayList<Obra>();
			obraDeUsuario = new ArrayList<ObraDeUsuario>();
			todasObras=obraService.getObras(codigoFilter);
			obraDeUsuario=obraService.getObrasPorCodigo(codigoFilter);
			datosGenerales = new ArrayList<DatosGenerales>();
			float sum=0;
			float sum2=0;
			float sumVisita=0;
			for(int j=0;j<todasObras.size();j++){
				float sumTemp=0;
				float sumTemp2=0;
				for(int i=0;i<obraDeUsuario.size();i++){
					if(todasObras.get(j).getIdObra()==obraDeUsuario.get(i).getObra().getIdObra()){
						sumTemp+=obraDeUsuario.get(i).getHorasTecnico();
						sum+=obraDeUsuario.get(i).getHorasTecnico();
						sumTemp2+=obraDeUsuario.get(i).getHorasAdmin();
						sum2+=obraDeUsuario.get(i).getHorasAdmin();
					}
				}
				todasObras.get(j).setHorasSum(sumTemp+sumTemp2);
				todasObras.get(j).setHorasTecnicoSum(sumTemp);
				todasObras.get(j).setHorasAdminSum(sumTemp2);
				datosGenerales.add(new DatosGenerales());
				datosGenerales.get(j).setCodigoObra(todasObras.get(j).getCodigoObra());
				datosGenerales.get(j).setTitulo(todasObras.get(j).getTitulo());
				datosGenerales.get(j).setNombreObra(todasObras.get(j).getNombreObra());
				datosGenerales.get(j).setHorasAdmin(sumTemp2);
				datosGenerales.get(j).setHorasTecnico(sumTemp);
				datosGenerales.get(j).setHorasTotal(sumTemp2+sumTemp);
			}
			setSumTotal(sum+sum2);
			setSumTotalAdmin(sum2);
			setSumTotalTecnicas(sum);
			setTotalVisitas(sumVisita);

			List<ObraDeUsuarioHistorica> obrasHistoricos = new ArrayList<ObraDeUsuarioHistorica>();
			List<ObraDeUsuarioHistoricaAux> obrasHist = new ArrayList<ObraDeUsuarioHistoricaAux>();
			obrasHistoricos=obraService.getObrasHistorico();
			for(int i=0;i<obrasHistoricos.size();i++){
				ObraDeUsuarioHistoricaAux aux = new ObraDeUsuarioHistoricaAux();
				aux.setHorasAdmin(obrasHistoricos.get(i).getHorasAdmin());
				aux.setHorasTecnicas(obrasHistoricos.get(i).getHorasTecnicas());
				aux.setHorasTotal(obrasHistoricos.get(i).getHorasTotal());
				aux.setNombreObra(obrasHistoricos.get(i).getNombreObra());
				aux.setTitulo(obrasHistoricos.get(i).getTitulo());
				aux.setUsuario(obrasHistoricos.get(i).getUsuario());
				aux.setVisita(obrasHistoricos.get(i).getVisita());
				aux.setYearRatio(obrasHistoricos.get(i).getYearRatio());
				obrasHist.add(aux);
			}
			for(int i=0;i<obrasHist.size();i++){
				for(int j=obrasHist.size()-1;j>0;j--){
					if(i!=j){
						if(obrasHist.get(i).getNombreObra().equals(obrasHist.get(j).getNombreObra())){
							obrasHist.get(i).setHorasAdmin(obrasHist.get(i).getHorasAdmin()+obrasHist.get(j).getHorasAdmin());
							obrasHist.get(i).setHorasTecnicas(obrasHist.get(i).getHorasTecnicas()+obrasHist.get(j).getHorasTecnicas());
							obrasHist.get(i).setHorasTotal(obrasHist.get(i).getHorasTotal()+obrasHist.get(j).getHorasTotal());
							obrasHist.remove(j);
						}
					}
				}
			}
			setSumTotal(sum+sum2);
			setSumTotalAdmin(sum2);
			setSumTotalTecnicas(sum);
			setTotalVisitas(sumVisita);
			for(int j=0;j<obrasHist.size();j++){
				for(int i=0;i<todasObras.size();i++){
					float sumTemp=0;
					float sumTemp2=0;
					float sumTempVisita=0;
					if(todasObras.get(i).getNombreObra().equals(obrasHist.get(j).getNombreObra())){
						sumTemp+=obrasHist.get(j).getHorasTecnicas();
						sum+=obrasHist.get(j).getHorasTecnicas();
						sumTemp2+=obrasHist.get(j).getHorasAdmin();
						sum2+=obrasHist.get(j).getHorasAdmin();
						sumVisita += obrasHist.get(j).getVisita();
						sumTempVisita += obrasHist.get(j).getVisita();
						datosGenerales.get(i).setHorasAdminAnt(sumTemp2);
						datosGenerales.get(i).setHorasTecnicoAnt(sumTemp);
						datosGenerales.get(i).setVisitasAnt(sumTempVisita);
						datosGenerales.get(i).setHorasTotal(datosGenerales.get(i).getHorasTotal()+sumTemp+sumTemp2);
					}
				}
			}
			setSumTotal(getSumTotal()+sum+sum2);
			setSumTotalAdminAnt(sum2);
			setSumTotalTecnicasAnt(sum);
			setTotalVisitasAnt(sumVisita);
		}
		else{
			FacesMessage msg = new FacesMessage("Introduzca el código de la obra");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	/**
	 * Selecciona las obras de usuario filtradas por título.
	 */
	public void updateObrasDeUsuarioPorTitulo(){
		if(tituloFilter.length()>0){
			todasObras = new ArrayList<Obra>();
			obraDeUsuario = new ArrayList<ObraDeUsuario>();
			todasObras=obraService.getObrasPorTitulo(tituloFilter);
			obraDeUsuario=obraService.getObrasDeUsuarioPorTitulo(tituloFilter);
			datosGenerales = new ArrayList<DatosGenerales>();
			float sum=0;
			float sum2=0;
			float sumVisita=0;
			for(int j=0;j<todasObras.size();j++){
				float sumTemp=0;
				float sumTemp2=0;
				for(int i=0;i<obraDeUsuario.size();i++){
					if(todasObras.get(j).getIdObra()==obraDeUsuario.get(i).getObra().getIdObra()){
						sumTemp+=obraDeUsuario.get(i).getHorasTecnico();
						sum+=obraDeUsuario.get(i).getHorasTecnico();
						sumTemp2+=obraDeUsuario.get(i).getHorasAdmin();
						sum2+=obraDeUsuario.get(i).getHorasAdmin();
					}
				}
				todasObras.get(j).setHorasSum(sumTemp+sumTemp2);
				todasObras.get(j).setHorasTecnicoSum(sumTemp);
				todasObras.get(j).setHorasAdminSum(sumTemp2);
				datosGenerales.add(new DatosGenerales());
				datosGenerales.get(j).setCodigoObra(todasObras.get(j).getCodigoObra());
				datosGenerales.get(j).setTitulo(todasObras.get(j).getTitulo());
				datosGenerales.get(j).setNombreObra(todasObras.get(j).getNombreObra());
				datosGenerales.get(j).setHorasAdmin(sumTemp2);
				datosGenerales.get(j).setHorasTecnico(sumTemp);
				datosGenerales.get(j).setHorasTotal(sumTemp2+sumTemp);
			}
			setSumTotal(sum+sum2);
			setSumTotalAdmin(sum2);
			setSumTotalTecnicas(sum);
			setTotalVisitas(sumVisita);


			List<ObraDeUsuarioHistorica> obrasHistoricos = new ArrayList<ObraDeUsuarioHistorica>();
			List<ObraDeUsuarioHistoricaAux> obrasHist = new ArrayList<ObraDeUsuarioHistoricaAux>();
			obrasHistoricos=obraService.getObrasHistoricoPorTitulo(tituloFilter);
			for(int i=0;i<obrasHistoricos.size();i++){
				ObraDeUsuarioHistoricaAux aux = new ObraDeUsuarioHistoricaAux();
				aux.setHorasAdmin(obrasHistoricos.get(i).getHorasAdmin());
				aux.setHorasTecnicas(obrasHistoricos.get(i).getHorasTecnicas());
				aux.setHorasTotal(obrasHistoricos.get(i).getHorasTotal());
				aux.setNombreObra(obrasHistoricos.get(i).getNombreObra());
				aux.setTitulo(obrasHistoricos.get(i).getTitulo());
				aux.setUsuario(obrasHistoricos.get(i).getUsuario());
				aux.setVisita(obrasHistoricos.get(i).getVisita());
				aux.setYearRatio(obrasHistoricos.get(i).getYearRatio());
				obrasHist.add(aux);
			}
			for(int i=0;i<obrasHist.size();i++){
				for(int j=obrasHist.size()-1;j>0;j--){
					if(i!=j){
						if(obrasHist.get(i).getNombreObra().equals(obrasHist.get(j).getNombreObra())){
							obrasHist.get(i).setHorasAdmin(obrasHist.get(i).getHorasAdmin()+obrasHist.get(j).getHorasAdmin());
							obrasHist.get(i).setHorasTecnicas(obrasHist.get(i).getHorasTecnicas()+obrasHist.get(j).getHorasTecnicas());
							obrasHist.get(i).setHorasTotal(obrasHist.get(i).getHorasTotal()+obrasHist.get(j).getHorasTotal());
							obrasHist.remove(j);
						}
					}
				}
			}
			setSumTotal(sum+sum2);
			setSumTotalAdmin(sum2);
			setSumTotalTecnicas(sum);
			setTotalVisitas(sumVisita);
			for(int j=0;j<obrasHist.size();j++){
				for(int i=0;i<todasObras.size();i++){
					float sumTemp=0;
					float sumTemp2=0;
					float sumTempVisita=0;
					if(todasObras.get(i).getNombreObra().equals(obrasHist.get(j).getNombreObra())){
						sumTemp+=obrasHist.get(j).getHorasTecnicas();
						sum+=obrasHist.get(j).getHorasTecnicas();
						sumTemp2+=obrasHist.get(j).getHorasAdmin();
						sum2+=obrasHist.get(j).getHorasAdmin();
						sumVisita += obrasHist.get(j).getVisita();
						sumTempVisita += obrasHist.get(j).getVisita();
						datosGenerales.get(i).setHorasAdminAnt(sumTemp2);
						datosGenerales.get(i).setHorasTecnicoAnt(sumTemp);
						datosGenerales.get(i).setVisitasAnt(sumTempVisita);
						datosGenerales.get(i).setHorasTotal(datosGenerales.get(i).getHorasTotal()+sumTemp+sumTemp2);
					}
				}
			}
			setSumTotal(getSumTotal()+sum+sum2);
			setSumTotalAdminAnt(sum2);
			setSumTotalTecnicasAnt(sum);
			setTotalVisitasAnt(sumVisita);
		}
		else{
			FacesMessage msg = new FacesMessage("Introduzca el título de la obra");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	/**
	 * Selecciona el nombre de la obra a través del código de obra.
	 */
	public void updateObra(){
		this.nombreObraFilter=obraService.getObraByCodigo(codigoFilter).getNombreObra();
	}
	
	/**
	 * Selecciona el código de la obra a través del nombre de obra.
	 */
	public void updateCodigo(){
		this.codigoFilter=obraService.getObraByNombre(nombreObraFilter).getCodigoObra();
	}
	
	/**
	 * Selecciona las obras de usuario en el mes seleccionado.
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
			float sum=0;
			float sum2=0;
			todasObras = new ArrayList<Obra>();
			obraDeUsuario = new ArrayList<ObraDeUsuario>();
			obraDeUsuario=obraService.getObrasPorMes(code);
			todasObras=obraService.getObras();
			datosGenerales = new ArrayList<DatosGenerales>();
			float sumVisita=0;
			for(int j=0;j<todasObras.size();j++){
				float sumTemp=0;
				float sumTemp2=0;
				
				for(int i=0;i<obraDeUsuario.size();i++){
					if(todasObras.get(j).getIdObra()==obraDeUsuario.get(i).getObra().getIdObra()){
						sumTemp+=obraDeUsuario.get(i).getHorasTecnico();
						sum+=obraDeUsuario.get(i).getHorasTecnico();
						sumTemp2+=obraDeUsuario.get(i).getHorasAdmin();
						sum2+=obraDeUsuario.get(i).getHorasAdmin();
						
					}
				}
				todasObras.get(j).setHorasSum(sumTemp+sumTemp2);
				todasObras.get(j).setHorasTecnicoSum(sumTemp);
				todasObras.get(j).setHorasAdminSum(sumTemp2);
				datosGenerales.add(new DatosGenerales());
				datosGenerales.get(j).setCodigoObra(todasObras.get(j).getCodigoObra());
				datosGenerales.get(j).setTitulo(todasObras.get(j).getTitulo());
				datosGenerales.get(j).setNombreObra(todasObras.get(j).getNombreObra());
				datosGenerales.get(j).setHorasAdmin(sumTemp2);
				datosGenerales.get(j).setHorasTecnico(sumTemp);
				datosGenerales.get(j).setHorasTotal(sumTemp2+sumTemp);
			}
			setSumTotal(sum+sum2);
			setSumTotalAdmin(sum2);
			setSumTotalTecnicas(sum);
			setTotalVisitas(sumVisita);

			List<ObraDeUsuarioHistorica> obrasHistoricos = new ArrayList<ObraDeUsuarioHistorica>();
			List<ObraDeUsuarioHistoricaAux> obrasHist = new ArrayList<ObraDeUsuarioHistoricaAux>();
			obrasHistoricos=obraService.getObrasHistorico();
			for(int i=0;i<obrasHistoricos.size();i++){
				ObraDeUsuarioHistoricaAux aux = new ObraDeUsuarioHistoricaAux();
				aux.setHorasAdmin(obrasHistoricos.get(i).getHorasAdmin());
				aux.setHorasTecnicas(obrasHistoricos.get(i).getHorasTecnicas());
				aux.setHorasTotal(obrasHistoricos.get(i).getHorasTotal());
				aux.setNombreObra(obrasHistoricos.get(i).getNombreObra());
				aux.setTitulo(obrasHistoricos.get(i).getTitulo());
				aux.setUsuario(obrasHistoricos.get(i).getUsuario());
				aux.setVisita(obrasHistoricos.get(i).getVisita());
				aux.setYearRatio(obrasHistoricos.get(i).getYearRatio());
				obrasHist.add(aux);
			}
			for(int i=0;i<obrasHist.size();i++){
				for(int j=obrasHist.size()-1;j>0;j--){
					if(i!=j){
						if(obrasHist.get(i).getNombreObra().equals(obrasHist.get(j).getNombreObra())){
							obrasHist.get(i).setHorasAdmin(obrasHist.get(i).getHorasAdmin()+obrasHist.get(j).getHorasAdmin());
							obrasHist.get(i).setHorasTecnicas(obrasHist.get(i).getHorasTecnicas()+obrasHist.get(j).getHorasTecnicas());
							obrasHist.get(i).setHorasTotal(obrasHist.get(i).getHorasTotal()+obrasHist.get(j).getHorasTotal());
							obrasHist.remove(j);
						}
					}
				}
			}
			setSumTotal(sum+sum2);
			setSumTotalAdmin(sum2);
			setSumTotalTecnicas(sum);
			setTotalVisitas(sumVisita);
			for(int j=0;j<obrasHist.size();j++){
				for(int i=0;i<todasObras.size();i++){
					float sumTemp=0;
					float sumTemp2=0;
					float sumTempVisita=0;
					if(todasObras.get(i).getNombreObra().equals(obrasHist.get(j).getNombreObra())){
						sumTemp+=obrasHist.get(j).getHorasTecnicas();
						sum+=obrasHist.get(j).getHorasTecnicas();
						sumTemp2+=obrasHist.get(j).getHorasAdmin();
						sum2+=obrasHist.get(j).getHorasAdmin();
						sumVisita += obrasHist.get(j).getVisita();
						sumTempVisita += obrasHist.get(j).getVisita();
						datosGenerales.get(i).setHorasAdminAnt(sumTemp2);
						datosGenerales.get(i).setHorasTecnicoAnt(sumTemp);
						datosGenerales.get(i).setVisitasAnt(sumTempVisita);
						datosGenerales.get(i).setHorasTotal(datosGenerales.get(i).getHorasTotal()+sumTemp+sumTemp2);
					}
				}
			}
			setSumTotal(getSumTotal()+sum+sum2);
			setSumTotalAdminAnt(sum2);
			setSumTotalTecnicasAnt(sum);
			setTotalVisitasAnt(sumVisita);
			
		}
		else{
			FacesMessage msg = new FacesMessage("Introduzca un mes");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	/**
	 * Actualiza los ratios filtrando por el año.
	 */
	public void updateObrasDeUsuarioPorYear(){
		if(yearFilter!=0){
			float sum=0;
			float sum2=0;
			todasObras = new ArrayList<Obra>();
			obraDeUsuario = new ArrayList<ObraDeUsuario>();
			obraDeUsuario=obraService.getObrasPorYear(yearFilter);
			todasObras=obraService.getObras();
			datosGenerales = new ArrayList<DatosGenerales>();
			float sumVisita=0;
			for(int j=0;j<todasObras.size();j++){
				float sumTemp=0;
				float sumTemp2=0;
				
				for(int i=0;i<obraDeUsuario.size();i++){
					if(todasObras.get(j).getIdObra()==obraDeUsuario.get(i).getObra().getIdObra()){
						sumTemp+=obraDeUsuario.get(i).getHorasTecnico();
						sum+=obraDeUsuario.get(i).getHorasTecnico();
						sumTemp2+=obraDeUsuario.get(i).getHorasAdmin();
						sum2+=obraDeUsuario.get(i).getHorasAdmin();
						
					}
				}
				todasObras.get(j).setHorasSum(sumTemp+sumTemp2);
				todasObras.get(j).setHorasTecnicoSum(sumTemp);
				todasObras.get(j).setHorasAdminSum(sumTemp2);
				datosGenerales.add(new DatosGenerales());
				datosGenerales.get(j).setCodigoObra(todasObras.get(j).getCodigoObra());
				datosGenerales.get(j).setTitulo(todasObras.get(j).getTitulo());
				datosGenerales.get(j).setNombreObra(todasObras.get(j).getNombreObra());
				datosGenerales.get(j).setHorasAdmin(sumTemp2);
				datosGenerales.get(j).setHorasTecnico(sumTemp);
				datosGenerales.get(j).setHorasTotal(sumTemp2+sumTemp);
			}
			setSumTotal(sum+sum2);
			setSumTotalAdmin(sum2);
			setSumTotalTecnicas(sum);
			setTotalVisitas(sumVisita);

			List<ObraDeUsuarioHistorica> obrasHistoricos = new ArrayList<ObraDeUsuarioHistorica>();
			List<ObraDeUsuarioHistoricaAux> obrasHist = new ArrayList<ObraDeUsuarioHistoricaAux>();
			obrasHistoricos=obraService.getObrasHistorico();
			for(int i=0;i<obrasHistoricos.size();i++){
				ObraDeUsuarioHistoricaAux aux = new ObraDeUsuarioHistoricaAux();
				aux.setHorasAdmin(obrasHistoricos.get(i).getHorasAdmin());
				aux.setHorasTecnicas(obrasHistoricos.get(i).getHorasTecnicas());
				aux.setHorasTotal(obrasHistoricos.get(i).getHorasTotal());
				aux.setNombreObra(obrasHistoricos.get(i).getNombreObra());
				aux.setTitulo(obrasHistoricos.get(i).getTitulo());
				aux.setUsuario(obrasHistoricos.get(i).getUsuario());
				aux.setVisita(obrasHistoricos.get(i).getVisita());
				aux.setYearRatio(obrasHistoricos.get(i).getYearRatio());
				obrasHist.add(aux);
			}
			for(int i=0;i<obrasHist.size();i++){
				for(int j=obrasHist.size()-1;j>0;j--){
					if(i!=j){
						if(obrasHist.get(i).getNombreObra().equals(obrasHist.get(j).getNombreObra())){
							obrasHist.get(i).setHorasAdmin(obrasHist.get(i).getHorasAdmin()+obrasHist.get(j).getHorasAdmin());
							obrasHist.get(i).setHorasTecnicas(obrasHist.get(i).getHorasTecnicas()+obrasHist.get(j).getHorasTecnicas());
							obrasHist.get(i).setHorasTotal(obrasHist.get(i).getHorasTotal()+obrasHist.get(j).getHorasTotal());
							obrasHist.remove(j);
						}
					}
				}
			}
			setSumTotal(sum+sum2);
			setSumTotalAdmin(sum2);
			setSumTotalTecnicas(sum);
			setTotalVisitas(sumVisita);
			for(int j=0;j<obrasHist.size();j++){
				for(int i=0;i<todasObras.size();i++){
					float sumTemp=0;
					float sumTemp2=0;
					float sumTempVisita=0;
					if(todasObras.get(i).getNombreObra().equals(obrasHist.get(j).getNombreObra())){
						sumTemp+=obrasHist.get(j).getHorasTecnicas();
						sum+=obrasHist.get(j).getHorasTecnicas();
						sumTemp2+=obrasHist.get(j).getHorasAdmin();
						sum2+=obrasHist.get(j).getHorasAdmin();
						sumVisita += obrasHist.get(j).getVisita();
						sumTempVisita += obrasHist.get(j).getVisita();
						datosGenerales.get(i).setHorasAdminAnt(sumTemp2);
						datosGenerales.get(i).setHorasTecnicoAnt(sumTemp);
						datosGenerales.get(i).setVisitasAnt(sumTempVisita);
						datosGenerales.get(i).setHorasTotal(datosGenerales.get(i).getHorasTotal()+sumTemp+sumTemp2);
					}
				}
			}
			setSumTotal(getSumTotal()+sum+sum2);
			setSumTotalAdminAnt(sum2);
			setSumTotalTecnicasAnt(sum);
			setTotalVisitasAnt(sumVisita);
			
		}
		else{
			FacesMessage msg = new FacesMessage("Introduzca un mes");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	
	/**
	 * Selecciona las obras de usuario por mes en la obra seleccionada.
	 */
	public void updateObrasDeUsuarioPorMesObra(){
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
		if(code!=0 || nombreObraFilter.length()>0){
			float sum=0;
			float sum2=0;
			total=0;
			totalTecnicas=0;
			totalAdmin=0;
			setTotalVisitas(0);
			usuarios = new ArrayList<Usuario>();
			todasObras = new ArrayList<Obra>();
			obraDeUsuario = new ArrayList<ObraDeUsuario>();
			usuarios=userService.allUsers();
			todasObras=obraService.getObras2(nombreObraFilter);
			for(int i=0;i<usuarios.size();i++){
				usuarios.get(i).setHorasAdminObra(0);
				usuarios.get(i).setHorasTecnicasObra(0);
				usuarios.get(i).setVisita(0);
			}
			float sumVisitas=0;
			obraDeUsuario=obraService.getObrasPorMesObra(code, nombreObraFilter);
			for(int j=0;j<todasObras.size();j++){
				float sumTemp=0;
				float sumTemp2=0;
				for(int i=0;i<obraDeUsuario.size();i++){
					float sumTempVisita=0;
					if(todasObras.get(j).getIdObra()==obraDeUsuario.get(i).getObra().getIdObra()){
						sumTemp+=obraDeUsuario.get(i).getHorasTecnico();
						sum+=obraDeUsuario.get(i).getHorasTecnico();
						sumTemp2+=obraDeUsuario.get(i).getHorasAdmin();
						sum2+=obraDeUsuario.get(i).getHorasAdmin();
						if(obraDeUsuario.get(i).getVisita().equals("Si") || obraDeUsuario.get(i).getVisita().equals("si") || obraDeUsuario.get(i).getVisita().equals("SI"))
							sumTempVisita++;
						for(int k=0;k<usuarios.size();k++){
							if(usuarios.get(k).getId()==obraDeUsuario.get(i).getIdObraDeUsuario().getUsuario().getId()){
								usuarios.get(k).setHorasAdminObra(usuarios.get(k).getHorasAdminObra()+obraDeUsuario.get(i).getHorasAdmin());
								usuarios.get(k).setHorasTecnicasObra(usuarios.get(k).getHorasTecnicasObra()+obraDeUsuario.get(i).getHorasTecnico());
								usuarios.get(k).setVisita(sumTempVisita);
								sumVisitas += sumTempVisita;
							}
						}
					}
				}
				todasObras.get(j).setHorasSum(sumTemp+sumTemp2);
				todasObras.get(j).setHorasTecnicoSum(sumTemp);
				todasObras.get(j).setHorasAdminSum(sumTemp2);
				sum+=todasObras.get(j).getHorasTecnicoSum();
				sum2+=todasObras.get(j).getHorasAdminSum();
				setTotalVisitas(sumVisitas);
			}
			setSumTotal(sum+sum2);
			setSumTotalAdmin(sum2);
			setSumTotalTecnicas(sum);
			horasAnteriores = 0;
			horasActuales = 0;
			horasTotalesPrevision = 0;
			for(int i=0;i<obraService.getObrasHistoricoPorObra(nombreObraFilter).size();i++)
				horasAnteriores+=obraService.getObrasHistoricoPorObra(nombreObraFilter).get(i).getHorasAdmin()+obraService.getObrasHistoricoPorObra(nombreObraFilter).get(i).getHorasTecnicas();
			for(int i=0;i<obraService.getObrasPorNombre(nombreObraFilter).size();i++)
				horasActuales+=obraService.getObrasPorNombre(nombreObraFilter).get(i).getHorasAdmin()+obraService.getObrasPorNombre(nombreObraFilter).get(i).getHorasTecnico();
			
			horasTotalesPrevision=horasAnteriores+horasActuales;
			for(int i=0;i<usuarios.size();i++){
				usuarios.get(i).setHorasTotal(usuarios.get(i).getHorasAdminObra()+usuarios.get(i).getHorasTecnicasObra());
				total+=usuarios.get(i).getHorasTotal();
				totalTecnicas+=usuarios.get(i).getHorasTecnicasObra();
				totalAdmin+=usuarios.get(i).getHorasAdminObra();
				if(usuarios.get(i).getHorasAdminObra()==0 && usuarios.get(i).getHorasTecnicasObra()==0){
					usuarios.remove(i);
					i--;
				}
			}
			renderPrevision=true;
		}
		else{
			FacesMessage msg = new FacesMessage("Introduzca un mes y el nombre de una obra");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	/**
	 * Actualiza los ratios filtrando por año y obra.
	 */
	public void updateObrasDeUsuarioPorYearObra(){
		if(yearFilter!=0 || nombreObraFilter.length()>0){
			float sum=0;
			float sum2=0;
			total=0;
			totalTecnicas=0;
			totalAdmin=0;
			setTotalVisitas(0);
			usuarios = new ArrayList<Usuario>();
			todasObras = new ArrayList<Obra>();
			obraDeUsuario = new ArrayList<ObraDeUsuario>();
			usuarios=userService.allUsers();
			todasObras=obraService.getObras2(nombreObraFilter);
			for(int i=0;i<usuarios.size();i++){
				usuarios.get(i).setHorasAdminObra(0);
				usuarios.get(i).setHorasTecnicasObra(0);
				usuarios.get(i).setVisita(0);
			}
			float sumVisitas=0;
			obraDeUsuario=obraService.getObrasPorYearObra(yearFilter, nombreObraFilter);
			for(int j=0;j<todasObras.size();j++){
				float sumTemp=0;
				float sumTemp2=0;
				for(int i=0;i<obraDeUsuario.size();i++){
					float sumTempVisita=0;
					if(todasObras.get(j).getIdObra()==obraDeUsuario.get(i).getObra().getIdObra()){
						sumTemp+=obraDeUsuario.get(i).getHorasTecnico();
						sum+=obraDeUsuario.get(i).getHorasTecnico();
						sumTemp2+=obraDeUsuario.get(i).getHorasAdmin();
						sum2+=obraDeUsuario.get(i).getHorasAdmin();
						if(obraDeUsuario.get(i).getVisita().equals("Si") || obraDeUsuario.get(i).getVisita().equals("si") || obraDeUsuario.get(i).getVisita().equals("SI"))
							sumTempVisita++;
						for(int k=0;k<usuarios.size();k++){
							if(usuarios.get(k).getId()==obraDeUsuario.get(i).getIdObraDeUsuario().getUsuario().getId()){
								usuarios.get(k).setHorasAdminObra(usuarios.get(k).getHorasAdminObra()+obraDeUsuario.get(i).getHorasAdmin());
								usuarios.get(k).setHorasTecnicasObra(usuarios.get(k).getHorasTecnicasObra()+obraDeUsuario.get(i).getHorasTecnico());
								usuarios.get(k).setVisita(sumTempVisita);
								sumVisitas += sumTempVisita;
							}
						}
					}
				}
				todasObras.get(j).setHorasSum(sumTemp+sumTemp2);
				todasObras.get(j).setHorasTecnicoSum(sumTemp);
				todasObras.get(j).setHorasAdminSum(sumTemp2);
				sum+=todasObras.get(j).getHorasTecnicoSum();
				sum2+=todasObras.get(j).getHorasAdminSum();
				setTotalVisitas(sumVisitas);
			}
			setSumTotal(sum+sum2);
			setSumTotalAdmin(sum2);
			setSumTotalTecnicas(sum);
			horasAnteriores = 0;
			horasActuales = 0;
			horasTotalesPrevision = 0;
			for(int i=0;i<obraService.getObrasHistoricoPorObra(nombreObraFilter).size();i++)
				horasAnteriores+=obraService.getObrasHistoricoPorObra(nombreObraFilter).get(i).getHorasAdmin()+obraService.getObrasHistoricoPorObra(nombreObraFilter).get(i).getHorasTecnicas();
			for(int i=0;i<obraService.getObrasPorNombre(nombreObraFilter).size();i++)
				horasActuales+=obraService.getObrasPorNombre(nombreObraFilter).get(i).getHorasAdmin()+obraService.getObrasPorNombre(nombreObraFilter).get(i).getHorasTecnico();
			
			horasTotalesPrevision=horasAnteriores+horasActuales;
			for(int i=0;i<usuarios.size();i++){
				usuarios.get(i).setHorasTotal(usuarios.get(i).getHorasAdminObra()+usuarios.get(i).getHorasTecnicasObra());
				total+=usuarios.get(i).getHorasTotal();
				totalTecnicas+=usuarios.get(i).getHorasTecnicasObra();
				totalAdmin+=usuarios.get(i).getHorasAdminObra();
				if(usuarios.get(i).getHorasAdminObra()==0 && usuarios.get(i).getHorasTecnicasObra()==0){
					usuarios.remove(i);
					i--;
				}
			}
			renderPrevision=true;
		}
		else{
			FacesMessage msg = new FacesMessage("Introduzca un mes y el nombre de una obra");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	/**
	 * Selecciona las horas totales filtrando por obra. Se utiliza en datosGenerales.xhtml.
	 */
	public void updateObrasDeUsuarioPorObraGeneral(){
		if(nombreObraFilter.length()>0){
			todasObras = new ArrayList<Obra>();
			obraDeUsuario = new ArrayList<ObraDeUsuario>();
			todasObras=obraService.getObras2(nombreObraFilter);
			obraDeUsuario=obraService.getObrasPorNombre(nombreObraFilter);
			datosGenerales = new ArrayList<DatosGenerales>();
			float sum=0;
			float sum2=0;
			float sumVisita=0;
			for(int j=0;j<todasObras.size();j++){
				float sumTemp=0;
				float sumTemp2=0;
				for(int i=0;i<obraDeUsuario.size();i++){
					if(todasObras.get(j).getIdObra()==obraDeUsuario.get(i).getObra().getIdObra()){
						sumTemp+=obraDeUsuario.get(i).getHorasTecnico();
						sum+=obraDeUsuario.get(i).getHorasTecnico();
						sumTemp2+=obraDeUsuario.get(i).getHorasAdmin();
						sum2+=obraDeUsuario.get(i).getHorasAdmin();
					}
				}
				todasObras.get(j).setHorasSum(sumTemp+sumTemp2);
				todasObras.get(j).setHorasTecnicoSum(sumTemp);
				todasObras.get(j).setHorasAdminSum(sumTemp2);
				datosGenerales.add(new DatosGenerales());
				datosGenerales.get(j).setCodigoObra(todasObras.get(j).getCodigoObra());
				datosGenerales.get(j).setTitulo(todasObras.get(j).getTitulo());
				datosGenerales.get(j).setNombreObra(todasObras.get(j).getNombreObra());
				datosGenerales.get(j).setHorasAdmin(sumTemp2);
				datosGenerales.get(j).setHorasTecnico(sumTemp);
				datosGenerales.get(j).setHorasTotal(sumTemp2+sumTemp);
			}
			setSumTotal(sum+sum2);
			setSumTotalAdmin(sum2);
			setSumTotalTecnicas(sum);
			setTotalVisitas(sumVisita);

			List<ObraDeUsuarioHistorica> obrasHistoricos = new ArrayList<ObraDeUsuarioHistorica>();
			List<ObraDeUsuarioHistoricaAux> obrasHist = new ArrayList<ObraDeUsuarioHistoricaAux>();
			obrasHistoricos=obraService.getObrasHistorico();
			for(int i=0;i<obrasHistoricos.size();i++){
				ObraDeUsuarioHistoricaAux aux = new ObraDeUsuarioHistoricaAux();
				aux.setHorasAdmin(obrasHistoricos.get(i).getHorasAdmin());
				aux.setHorasTecnicas(obrasHistoricos.get(i).getHorasTecnicas());
				aux.setHorasTotal(obrasHistoricos.get(i).getHorasTotal());
				aux.setNombreObra(obrasHistoricos.get(i).getNombreObra());
				aux.setTitulo(obrasHistoricos.get(i).getTitulo());
				aux.setUsuario(obrasHistoricos.get(i).getUsuario());
				aux.setVisita(obrasHistoricos.get(i).getVisita());
				aux.setYearRatio(obrasHistoricos.get(i).getYearRatio());
				obrasHist.add(aux);
			}
			for(int i=0;i<obrasHist.size();i++){
				for(int j=obrasHist.size()-1;j>0;j--){
					if(i!=j){
						if(obrasHist.get(i).getNombreObra().equals(obrasHist.get(j).getNombreObra())){
							obrasHist.get(i).setHorasAdmin(obrasHist.get(i).getHorasAdmin()+obrasHist.get(j).getHorasAdmin());
							obrasHist.get(i).setHorasTecnicas(obrasHist.get(i).getHorasTecnicas()+obrasHist.get(j).getHorasTecnicas());
							obrasHist.get(i).setHorasTotal(obrasHist.get(i).getHorasTotal()+obrasHist.get(j).getHorasTotal());
							obrasHist.remove(j);
						}
					}
				}
			}
			setSumTotal(sum+sum2);
			setSumTotalAdmin(sum2);
			setSumTotalTecnicas(sum);
			setTotalVisitas(sumVisita);
			for(int j=0;j<obrasHist.size();j++){
				for(int i=0;i<todasObras.size();i++){
					float sumTemp=0;
					float sumTemp2=0;
					float sumTempVisita=0;
					if(todasObras.get(i).getNombreObra().equals(obrasHist.get(j).getNombreObra())){
						sumTemp+=obrasHist.get(j).getHorasTecnicas();
						sum+=obrasHist.get(j).getHorasTecnicas();
						sumTemp2+=obrasHist.get(j).getHorasAdmin();
						sum2+=obrasHist.get(j).getHorasAdmin();
						sumVisita += obrasHist.get(j).getVisita();
						sumTempVisita += obrasHist.get(j).getVisita();
						datosGenerales.get(i).setHorasAdminAnt(sumTemp2);
						datosGenerales.get(i).setHorasTecnicoAnt(sumTemp);
						datosGenerales.get(i).setVisitasAnt(sumTempVisita);
						datosGenerales.get(i).setHorasTotal(datosGenerales.get(i).getHorasTotal()+sumTemp+sumTemp2);
					}
				}
			}
			setSumTotal(getSumTotal()+sum+sum2);
			setSumTotalAdminAnt(sum2);
			setSumTotalTecnicasAnt(sum);
			setTotalVisitasAnt(sumVisita);
		}
		else{
			FacesMessage msg = new FacesMessage("Introduzca el nombre de la obra");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
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

	public IObraService getObraService() {
		return obraService;
	}

	public void setObraService(IObraService obraService) {
		this.obraService = obraService;
	}

	/**
	 * @return the todasObras
	 */
	public List<Obra> getTodasObras() {
		return todasObras;
	}

	/**
	 * @param todasObras the todasObras to set
	 */
	public void setTodasObras(List<Obra> todasObras) {
		this.todasObras = todasObras;
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

	
	public String getNombreObraFilter() {
		return nombreObraFilter;
	}

	public void setNombreObraFilter(String nombreObraFilter) {
		this.nombreObraFilter = nombreObraFilter;
	}

	public String getCodigoFilter() {
		return codigoFilter;
	}

	public void setCodigoFilter(String codigoFilter) {
		this.codigoFilter = codigoFilter;
	}

	/**
	 * @return the tituloFilter
	 */
	public String getTituloFilter() {
		return tituloFilter;
	}

	/**
	 * @param tituloFilter the tituloFilter to set
	 */
	public void setTituloFilter(String tituloFilter) {
		this.tituloFilter = tituloFilter;
	}

	public List<Obra> getObrasCodigo() {
		obrasCodigo=obraService.getObrasCodigo();
		return obrasCodigo;
	}

	public void setObrasCodigo(List<Obra> obrasCodigo) {
		this.obrasCodigo = obrasCodigo;
	}

	public List<Obra> getObrasTitulo() {
		obrasTitulo=obraService.getObrasTitulo();
		return obrasTitulo;
	}

	public void setObrasTitulo(List<Obra> obrasTitulo) {
		this.obrasTitulo = obrasTitulo;
	}

	/**
	 * @return the obraDeUsuario
	 */
	public List<ObraDeUsuario> getObraDeUsuario() {
		return obraDeUsuario;
	}

	/**
	 * @param obraDeUsuario the obraDeUsuario to set
	 */
	public void setObraDeUsuario(List<ObraDeUsuario> obraDeUsuario) {
		this.obraDeUsuario = obraDeUsuario;
	}

	/**
	 * @return the mesFilter
	 */
	public String getMesFilter() {
		return mesFilter;
	}

	/**
	 * @param mesFilter the mesFilter to set
	 */
	public void setMesFilter(String mesFilter) {
		this.mesFilter = mesFilter;
	}

	/**
	 * Lista inicializada con los meses del año.
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
	 * @return the sumTotal
	 */
	public float getSumTotal() {
		return sumTotal;
	}

	/**
	 * @param sumTotal the sumTotal to set
	 */
	public void setSumTotal(float sumTotal) {
		this.sumTotal = sumTotal;
	}

	/**
	 * @return the sumTotalTecnicas
	 */
	public float getSumTotalTecnicas() {
		return sumTotalTecnicas;
	}

	/**
	 * @param sumTotalTecnicas the sumTotalTecnicas to set
	 */
	public void setSumTotalTecnicas(float sumTotalTecnicas) {
		this.sumTotalTecnicas = sumTotalTecnicas;
	}

	/**
	 * @return the sumTotalAdmin
	 */
	public float getSumTotalAdmin() {
		return sumTotalAdmin;
	}

	/**
	 * @param sumTotalAdmin the sumTotalAdmin to set
	 */
	public void setSumTotalAdmin(float sumTotalAdmin) {
		this.sumTotalAdmin = sumTotalAdmin;
	}

	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		if(fecha!=null)
			obra.setFecha(sdf.format(fecha));
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	/**
	 * @return the total
	 */
	public float getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(float total) {
		this.total = total;
	}

	/**
	 * @return the totalTecnicas
	 */
	public float getTotalTecnicas() {
		return totalTecnicas;
	}

	/**
	 * @param totalTecnicas the totalTecnicas to set
	 */
	public void setTotalTecnicas(float totalTecnicas) {
		this.totalTecnicas = totalTecnicas;
	}

	/**
	 * @return the totalAdmin
	 */
	public float getTotalAdmin() {
		return totalAdmin;
	}

	/**
	 * @param totalAdmin the totalAdmin to set
	 */
	public void setTotalAdmin(float totalAdmin) {
		this.totalAdmin = totalAdmin;
	}

	/**
	 * @return the obrasHistorico
	 */
	public List<ObraDeUsuarioHistorica> getObrasHistorico() {
		return obrasHistorico;
	}

	/**
	 * @param obrasHistorico the obrasHistorico to set
	 */
	public void setObrasHistorico(List<ObraDeUsuarioHistorica> obrasHistorico) {
		this.obrasHistorico = obrasHistorico;
	}

	/**
	 * @return the obrasHistoricas
	 */
	public List<ObraDeUsuarioHistorica> getObrasHistoricas() {
		return obrasHistoricas;
	}

	/**
	 * @param obrasHistoricas the obrasHistoricas to set
	 */
	public void setObrasHistoricas(List<ObraDeUsuarioHistorica> obrasHistoricas) {
		this.obrasHistoricas = obrasHistoricas;
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
	 * @return the yearFiltro
	 */
	public String getYearFiltro() {
		return yearFiltro;
	}

	/**
	 * @param yearFiltro the yearFiltro to set
	 */
	public void setYearFiltro(String yearFiltro) {
		this.yearFiltro = yearFiltro;
	}

	/**
	 * @return the nombreObraDelete
	 */
	public String getNombreObraDelete() {
		return nombreObraDelete;
	}

	/**
	 * @param nombreObraDelete the nombreObraDelete to set
	 */
	public void setNombreObraDelete(String nombreObraDelete) {
		this.nombreObraDelete = nombreObraDelete;
	}

	/**
	 * @return the totalVisitas
	 */
	public float getTotalVisitas() {
		return totalVisitas;
	}

	/**
	 * @param totalVisitas the totalVisitas to set
	 */
	public void setTotalVisitas(float totalVisitas) {
		this.totalVisitas = totalVisitas;
	}

	/**
	 * @return the renderPrevision
	 */
	public boolean isRenderPrevision() {
		return renderPrevision;
	}

	/**
	 * @param renderPrevision the renderPrevision to set
	 */
	public void setRenderPrevision(boolean renderPrevision) {
		this.renderPrevision = renderPrevision;
	}

	/**
	 * @return the horasAnteriores
	 */
	public float getHorasAnteriores() {
		return horasAnteriores;
	}

	/**
	 * @param horasAnteriores the horasAnteriores to set
	 */
	public void setHorasAnteriores(float horasAnteriores) {
		this.horasAnteriores = horasAnteriores;
	}

	/**
	 * @return the horasActuales
	 */
	public float getHorasActuales() {
		return horasActuales;
	}

	/**
	 * @param horasActuales the horasActuales to set
	 */
	public void setHorasActuales(float horasActuales) {
		this.horasActuales = horasActuales;
	}

	/**
	 * @return the horasTotalesPrevision
	 */
	public float getHorasTotalesPrevision() {
		return horasTotalesPrevision;
	}

	/**
	 * @param horasTotalesPrevision the horasTotalesPrevision to set
	 */
	public void setHorasTotalesPrevision(float horasTotalesPrevision) {
		this.horasTotalesPrevision = horasTotalesPrevision;
	}

	/**
	 * @return the tesoreriaService
	 */
	public ITesoreriaService getTesoreriaService() {
		return tesoreriaService;
	}

	/**
	 * @param tesoreriaService the tesoreriaService to set
	 */
	public void setTesoreriaService(ITesoreriaService tesoreriaService) {
		this.tesoreriaService = tesoreriaService;
	}

	/**
	 * @return the fechaFin
	 */
	public Date getFechaFin() {
		return fechaFin;
	}

	/**
	 * @param fechaFin the fechaFin to set
	 */
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		if(fechaFin!=null)
			obra.setFechaFin(sdf.format(fechaFin));
	}

	public float getSumTotalTecnicasAnt() {
		return sumTotalTecnicasAnt;
	}

	public void setSumTotalTecnicasAnt(float sumTotalTecnicasAnt) {
		this.sumTotalTecnicasAnt = sumTotalTecnicasAnt;
	}

	public float getSumTotalAdminAnt() {
		return sumTotalAdminAnt;
	}

	public void setSumTotalAdminAnt(float sumTotalAdminAnt) {
		this.sumTotalAdminAnt = sumTotalAdminAnt;
	}

	public float getTotalVisitasAnt() {
		return totalVisitasAnt;
	}

	public void setTotalVisitasAnt(float totalVisitasAnt) {
		this.totalVisitasAnt = totalVisitasAnt;
	}

	public List<DatosGenerales> getDatosGenerales() {
		return datosGenerales;
	}

	public void setDatosGenerales(List<DatosGenerales> datosGenerales) {
		this.datosGenerales = datosGenerales;
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
