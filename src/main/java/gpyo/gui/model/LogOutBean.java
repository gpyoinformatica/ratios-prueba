package gpyo.gui.model;


import gpyo.util.FacesUtil;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * Cierra la sesión.
 * 
 * @author Alberto Revilla Gil
 * @version 1.0
 *
 */
@Component
@Scope("request")
public class LogOutBean implements Serializable {
	
	private static final long serialVersionUID = 1977779L;

    private SessionBean sessionBean;
    private LoaderBean loaderBean;
    
    /**
     * Cierra la sesión.
     * @return Ruta de la página principal.
     */
    public String doLogout(boolean todo){
    	sessionBean.setUser(null); 
    	FacesUtil.setSessionMapValue("usuario", null);
    	ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
    	FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    	String bean = "";
		Set<String> claves = new HashSet<String>();
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().remove(sessionBean);
		claves = context.getExternalContext().getSessionMap().keySet();
		Iterator<String> it = claves.iterator();
		while (it.hasNext()) {
			bean = it.next();
			if(todo == true){
				context.getExternalContext().getSessionMap().remove(bean);
			}
		}
    	try {
			ec.redirect(ec.getRequestContextPath() + "/page/firstPage.jsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	claves = context.getExternalContext().getSessionMap().keySet();
    	FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		//loaderBean.setPage("welcome.xhtml");
    	return "/page/firstPage";
    }
    
    public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}


	/**
	 * @return the loaderBean
	 */
	public LoaderBean getLoaderBean() {
		return loaderBean;
	}

	/**
	 * @param loaderBean the loaderBean to set
	 */
	public void setLoaderBean(LoaderBean loaderBean) {
		this.loaderBean = loaderBean;
	}

	
}