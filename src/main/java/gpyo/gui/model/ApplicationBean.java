package gpyo.gui.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

/**
 * Recoge de forma din�mica el nombre de la aplicaci�n.
 * 
 * @author Alberto Revilla
 * @version 1.0
 */
@Component
public class ApplicationBean extends org.ploin.web.faces.BaseBean implements Serializable {

	private static final long serialVersionUID = -8603773638140988924L;

	/**
	 * Devuelve el nombre de la aplicaci�n.
	 * 
	 * @return Nombre de la aplicaci�n.
	 */
	public String getAppname(){		
	 	String[] strings = getRequest().getRequestURI().split("/");
		return strings[1];
	}

}