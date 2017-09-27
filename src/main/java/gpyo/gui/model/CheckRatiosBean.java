package gpyo.gui.model;

import gpyo.persistence.entity.admin.CheckRatios;
import gpyo.persistence.entity.admin.Usuario;
import gpyo.service.businesslogic.IObraService;
import gpyo.service.businesslogic.IUserService;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.joda.time.DateTime;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

@ManagedBean
@ViewScoped
@Controller
public class CheckRatiosBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9018738353302002736L;

	private List<Usuario> usuarios = new ArrayList<Usuario>();
	private Usuario usuario = new Usuario();
	private Date fechaInicio;
	private Date fechaFin;
	@Autowired
	private IUserService userService;
	@Autowired
	private IObraService obraService;
	private String nombreUsuario;
	private Calendar end = null;
	private Calendar start = null;
	private List<CheckRatios> diasSinRatios = new ArrayList<CheckRatios>();
	
	/**
	 * Carga una lista con todos los usuarios de la aplicación.
	 */
	public void loadUsers(){
		usuarios=userService.allUsers();
	}
	
	/**
	 * Comprueba los ratios que faltan por meter entre dos fechas determinadas.
	 */
	public void checkRatios(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String fechaInicio = sdf.format(this.fechaInicio);
		DateTime start = DateTime.parse(fechaInicio);
		String fechaFin = sdf.format(this.fechaFin);
		DateTime end = DateTime.parse(fechaFin);
		List<DateTime> between = getDateRange(start, end);
		if(nombreUsuario == null)
			nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		long idUsuario=userService.getUserByName(nombreUsuario).getId();
		List<String> diasSinRatiosAux = new ArrayList<String>();
		diasSinRatios = new ArrayList<CheckRatios>();
		List<Float> horasHechas = new ArrayList<Float>();
		List<Integer> dia = new ArrayList<Integer>();
		for (DateTime d : between) {
			float horas=0;
			horas = (float) obraService.horasDia(idUsuario, d.toString().substring(0, 10));
			if((d.dayOfWeek().get()==1 || d.dayOfWeek().get()==2 || d.dayOfWeek().get()==3 || 
					d.dayOfWeek().get()==4) && horas<8.5){
				diasSinRatiosAux.add(d.toString().substring(0, 10));
				horasHechas.add(horas);
				dia.add(d.dayOfWeek().get());
			}
			else{
				if(d.dayOfWeek().get()==5 && horas<6.5){
					diasSinRatiosAux.add(d.toString().substring(0, 10));
					horasHechas.add(horas);
					dia.add(d.dayOfWeek().get());
				}
			}
	    }
		for(int i=0;i<diasSinRatiosAux.size();i++){
			CheckRatios checkRatio = new CheckRatios();
			checkRatio.setFecha(new StringBuilder("").append(diasSinRatiosAux.get(i).charAt(8)).append(diasSinRatiosAux.get(i).charAt(9)).append(diasSinRatiosAux.get(i).charAt(7)).append(diasSinRatiosAux.get(i).charAt(5)).append(diasSinRatiosAux.get(i).charAt(6)).append(diasSinRatiosAux.get(i).charAt(4)).append(diasSinRatiosAux.get(i).charAt(0)).append(diasSinRatiosAux.get(i).charAt(1)).append(diasSinRatiosAux.get(i).charAt(2)).append(diasSinRatiosAux.get(i).charAt(3)).toString());
			if(dia.get(i)==1)
				checkRatio.setDia("Lunes");
			else
				if(dia.get(i)==2)
					checkRatio.setDia("Martes");
				else
					if(dia.get(i)==3)
						checkRatio.setDia("Miércoles");
					else
						if(dia.get(i)==4)
							checkRatio.setDia("Jueves");
						else
							if(dia.get(i)==5)
								checkRatio.setDia("Viernes");
			if(dia.get(i)==1 || dia.get(i)==2 || dia.get(i)==3 || dia.get(i)==4){
				checkRatio.setHoras(horasHechas.get(i));
				checkRatio.setHorasFaltantes((float) (8.5-horasHechas.get(i)));
			}
			else{
				checkRatio.setHoras(horasHechas.get(i));
				checkRatio.setHorasFaltantes((float) (6.5-horasHechas.get(i)));
			}
			checkRatio.setUser(nombreUsuario);
			diasSinRatios.add(checkRatio);
		}
	}
	
	public void checkAllRatios(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String fechaInicio = sdf.format(this.fechaInicio);
		DateTime start = DateTime.parse(fechaInicio);
		String fechaFin = sdf.format(this.fechaFin);
		DateTime end = DateTime.parse(fechaFin);
		List<DateTime> between = getDateRange(start, end);
		List<String> diasSinRatiosAux = new ArrayList<String>();
		diasSinRatios = new ArrayList<CheckRatios>();
		List<Float> horasHechas = new ArrayList<Float>();
		List<Integer> dia = new ArrayList<Integer>();
		List<Usuario> allUsers = new ArrayList<Usuario>();
		allUsers = userService.allUsers();
		for(int j=0;j<allUsers.size();j++){
			int count=0;
			List<CheckRatios> diasSinRatiosMail = new ArrayList<CheckRatios>();
			if(userService.getUserByName(allUsers.get(j).getNombreUsuario()).isActivo()){
				long idUsuario=userService.getUserByName(allUsers.get(j).getNombreUsuario()).getId();
				diasSinRatiosAux = new ArrayList<String>();
				horasHechas = new ArrayList<Float>();
				for (DateTime d : between) {
					float horas=0;
					horas = (float) obraService.horasDia(idUsuario, d.toString().substring(0, 10));
					if((d.dayOfWeek().get()==1 || d.dayOfWeek().get()==2 || d.dayOfWeek().get()==3 || 
							d.dayOfWeek().get()==4) && horas<8.5){
						diasSinRatiosAux.add(d.toString().substring(0, 10));
						horasHechas.add(horas);
						dia.add(d.dayOfWeek().get());
					}
					else{
						if(d.dayOfWeek().get()==5 && horas<6.5){
							diasSinRatiosAux.add(d.toString().substring(0, 10));
							horasHechas.add(horas);
							dia.add(d.dayOfWeek().get());
						}
					}
			    }
				for(int i=0;i<diasSinRatiosAux.size();i++){
					CheckRatios checkRatio = new CheckRatios();
					checkRatio.setFecha(new StringBuilder("").append(diasSinRatiosAux.get(i).charAt(8)).append(diasSinRatiosAux.get(i).charAt(9)).append(diasSinRatiosAux.get(i).charAt(7)).append(diasSinRatiosAux.get(i).charAt(5)).append(diasSinRatiosAux.get(i).charAt(6)).append(diasSinRatiosAux.get(i).charAt(4)).append(diasSinRatiosAux.get(i).charAt(0)).append(diasSinRatiosAux.get(i).charAt(1)).append(diasSinRatiosAux.get(i).charAt(2)).append(diasSinRatiosAux.get(i).charAt(3)).toString());
					if(dia.get(i)==1)
						checkRatio.setDia("Lunes");
					else
						if(dia.get(i)==2)
							checkRatio.setDia("Martes");
						else
							if(dia.get(i)==3)
								checkRatio.setDia("Miércoles");
							else
								if(dia.get(i)==4)
									checkRatio.setDia("Jueves");
								else
									if(dia.get(i)==5)
										checkRatio.setDia("Viernes");
					if(dia.get(i)==1 || dia.get(i)==2 || dia.get(i)==3 || dia.get(i)==4){
						checkRatio.setHoras(horasHechas.get(i));
						checkRatio.setHorasFaltantes((float) (8.5-horasHechas.get(i)));
					}
					else{
						checkRatio.setHoras(horasHechas.get(i));
						checkRatio.setHorasFaltantes((float) (6.5-horasHechas.get(i)));
					}
					checkRatio.setUser(allUsers.get(j).getNombreUsuario());
					count++;
					diasSinRatios.add(checkRatio);
					diasSinRatiosMail.add(checkRatio);
				}
			}
			if(count>3)
				sendMail(diasSinRatiosMail, allUsers.get(j));
		}
	}
	
	public void sendMail(List<CheckRatios> diasSinRatiosMail, Usuario user){
		 String host = "smtp.gmail.com";
		    String from = "ratios.gpyo@gmail.com";
		    String pass = "gpyoratios2016";
		    Properties props = System.getProperties();
		    props.put("mail.smtp.starttls.enable", "true"); // added this line
		    props.put("mail.smtp.host", host);
		    props.put("mail.smtp.user", from);
		    props.put("mail.smtp.password", pass);
		    props.put("mail.smtp.port", "587");
		    props.put("mail.smtp.auth", "true");
		    props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

		    String[] to = {user.getMail()}; // added this line

		    Session session = Session.getDefaultInstance(props, null);
		    MimeMessage message = new MimeMessage(session);
		    try {
				message.setFrom(new InternetAddress(from));

		    InternetAddress[] toAddress = new InternetAddress[to.length];

		    // To get the array of addresses
		    for( int i=0; i < to.length; i++ ) { // changed from a while loop
		        toAddress[i] = new InternetAddress(to[i]);
		    }

		    for( int i=0; i < toAddress.length; i++) { // changed from a while loop
		        message.addRecipient(Message.RecipientType.TO, toAddress[i]);
		    }
		    message.setSubject("Comprobación de ratios");
		    message.setText("Se ha detectado que el mes pasado no has rellenado todas las horas en los ratios. " +
		    		"Podrás comprobar los días que faltan en la pestaña de comprobar ratios en la aplicación. Ten " +
		    		"en cuenta que la aplicación no distingue festivos, por lo que puede que marque como no completados" +
		    		" días en los que no se ha trabajado. Completa las horas que te falten lo antes posible para" +
		    		" facilitarnos la creación de informes. Un saludo.");
		    Transport transport = session.getTransport("smtp");
		    transport.connect(host, from, pass);
		    transport.sendMessage(message, message.getAllRecipients());
		    transport.close();
		    } catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
	
	/**
	 * Crea una lista con las fechas comprendidas entre dos fechas determinadas.
	 * @param start Fecha de inicio.
	 * @param end Fecha final.
	 * @return Lista con fechas.
	 */
	public static List<DateTime> getDateRange(DateTime start, DateTime end) {
        List<DateTime> ret = new ArrayList<DateTime>();
        DateTime tmp = start;
        while(tmp.isBefore(end) || tmp.equals(end)) {
            ret.add(tmp);
            tmp = tmp.plusDays(1);
        }
        return ret;
    }
	
	public List<Usuario> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
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

	public Calendar getEnd() {
		return end;
	}

	public void setEnd(Calendar end) {
		this.end = end;
	}

	public Calendar getStart() {
		return start;
	}

	public void setStart(Calendar start) {
		this.start = start;
	}

	/**
	 * @return the diasSinRatios
	 */
	public List<CheckRatios> getDiasSinRatios() {
		return diasSinRatios;
	}

	/**
	 * @param diasSinRatios the diasSinRatios to set
	 */
	public void setDiasSinRatios(List<CheckRatios> diasSinRatios) {
		this.diasSinRatios = diasSinRatios;
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
	
	
}
