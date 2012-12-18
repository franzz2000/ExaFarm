/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.uoc.tfc.exafarm.backing;

import edu.uoc.tfc.exafarm.entitats.accessor.UsuarioRegistry;
import edu.uoc.tfc.exafarm.extras.Utils;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author franzz2000
 */
@ManagedBean
@RequestScoped
public class LoginBacking extends AbstractBacking {
 
	private String username = null;
	private String password = null;
        

        /**
         * Performs authentication via HttpServletRequest API
         */
	public String authenticate() {
		if (!isAuthenticated()) {
			try {
				getRequest().login(username, password);
			} catch (ServletException e) {
                            Utils.addMessage(FacesMessage.SEVERITY_ERROR, Utils.getMessageResourceString("bundle","LoginErrorAcceso"));
                            return null;
                        }
		}
                setCurrentUsuario(UsuarioRegistry.getCurrentInstance().getUsuarioByIdUsuario(username));
		
                if (isUserInRole("ADMINISTRADOR")) {
                    return "/faces/administrador/principal?faces-redirect=true";
                }
                if (isUserInRole("COORDINADOR")) {
                    return "/faces/coordinador/principal?faces-redirect=true";
                }
                if (isUserInRole("NINGUNO")) {
                    Utils.addMessage(FacesMessage.SEVERITY_ERROR, Utils.getMessageResourceString("bundle", "LoginErrorUsuarioBaja"));
                    try {
                        getRequest().logout();
                    } catch (ServletException ex) {
                        Logger.getLogger(LoginBacking.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return null;
                }
                return "/faces/profesor/principal?faces-redirect=true";
	}
        
        
        public void forwardToGroup(ComponentSystemEvent cse) {
            Object request = FacesContext.getCurrentInstance().getExternalContext().getRequest();

            if (!isAuthenticated()) {
                getFacesContext().getApplication().getNavigationHandler().handleNavigation(getFacesContext(), null, "/login?faces-redirect=true");
            } else {
                if(((HttpServletRequest)request).isUserInRole("ADMINISTRADOR")) {
                    getFacesContext().getApplication().getNavigationHandler().handleNavigation(getFacesContext(), null, "/administrador/principal?faces-redirect=true");
                }
                if(((HttpServletRequest)request).isUserInRole("COORDINADOR")) {
                    getFacesContext().getApplication().getNavigationHandler().handleNavigation(getFacesContext(), null, "/coordinador/principal?faces-redirect=true");
                }
                if(((HttpServletRequest)request).isUserInRole("PROFESOR")) {
                    getFacesContext().getApplication().getNavigationHandler().handleNavigation(getFacesContext(), null, "/profesor/principal?faces-redirect=true");
                }
            }
        }
        
 
        /**
         * Logs out using HttpServletRequest API
         */
	public String logout() {
		if (isAuthenticated())
                    try {
                        getRequest().logout();
                    } catch (ServletException ex) {
                        Logger.getLogger(LoginBacking.class.getName()).log(Level.SEVERE, null, ex);
                    }
                return "/faces/login";
	}
 
	public boolean isAuthenticated() {
		return getRequest().getUserPrincipal() != null;
	}
 
	public boolean isUserInRole(String role) {
		return getRequest().isUserInRole(role);
	}
 
	public Principal getPrincipal() {
		return getRequest().getUserPrincipal();
	}
 
	private HttpServletRequest getRequest() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.
                         getExternalContext();
		Object request = externalContext.getRequest();
		return request instanceof HttpServletRequest ? 
                        (HttpServletRequest) request : null;
	}
 
	public String getUsername() {
		return username;
	}
 
	public void setUsername(String username) {
		this.username = username;
	}
 
	public String getPassword() {
		return password;
	}
 
	public void setPassword(String password) {
		this.password = password;
	}
 
}