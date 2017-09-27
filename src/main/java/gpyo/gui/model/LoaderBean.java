package gpyo.gui.model;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.stereotype.Component;

/**
 * Abre en la vista la p�gina almacenada en su atributo page. 
 * 
 * @author Alberto Revilla
 * @version 1.0
 */
@ManagedBean
@RequestScoped
@Component
public class LoaderBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5329706353068126800L;
	private String page="welcome.xhtml";
	
	
	public LoaderBean() {
		super();
	}

	/**
	 * Asigna la p�gina pasada por par�metro.
	 * @param page
	 */
	public void openPage(String page){
		this.page=page;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}
	
	/**
	 * Indica la p�gina de bienvenida para la aplicaci�n.
	 * @deprecated
	 * @return
	 */
	public String getWelcomePage(){
		return "newIndex.xhtml";
	}
	
	/**
	 * Inicializa la p�gina de bienvenida despu�s del logeo para evitar acceder a la p�gina de otro usuario.
	 */
	public void firstPage(){
		setPage("welcome.xhtml");
	}
}