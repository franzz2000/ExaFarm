/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.uoc.tfc.exafarm.backing;

import edu.uoc.tfc.exafarm.entitats.Examen;
import edu.uoc.tfc.exafarm.entitats.Usuario;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;


@RequestScoped
public abstract class AbstractBacking {

    @ManagedProperty(value="#{facesContext}")
    private FacesContext facesContext;

    @ManagedProperty(value="#{requestScope}")
    private Map<String, Object> requestMap;

    @ManagedProperty(value="#{sessionScope}")
    private Map<String, Object> sessionMap;
    
    @ManagedProperty(value="#{viewScope}")
    private Map<String, Object> viewScope;

    public Usuario getCurrentUser() {
        return (Usuario) getSessionMap().get("currentUser");
    }

    public void setCurrentUsuario(Usuario currentUser) {
        getSessionMap().remove("currentUser");
        if (null != currentUser) {
            getSessionMap().put("currentUser", currentUser);
        }
    }
    
    public Examen getCurrentExamen() {
        return(Examen) getSessionMap().get("currentExamen");
    }
    
    public void setCurrentExamen(Examen currentExamen) {
        getSessionMap().remove("currentExamen");
        if (null != currentExamen) {
            getSessionMap().put("currentExamen", currentExamen);
        }
    }

    public boolean isUsuarioLoggedIn() {
        return getSessionMap().containsKey("currentUser");
    }

    // The compiler can optimizes calls such as this into plain field accesses.
    // Therefore, there is no performance penalty for always using the
    // accessor.
    public FacesContext getFacesContext() {
        return facesContext;
    }

    public void setFacesContext(FacesContext context) {
        this.facesContext = context;
    }

    public Map<String, Object> getSessionMap() {
        return sessionMap;
    }

    public void setSessionMap(Map<String, Object> sessionMap) {
        this.sessionMap = sessionMap;
    }

    public Map<String, Object> getRequestMap() {
        return requestMap;
    }

    public void setRequestMap(Map<String, Object> requestMap) {
        this.requestMap = requestMap;
    }

    public Map<String, Object> getViewScope() {
        return viewScope;
    }

    public void setViewScope(Map<String, Object> viewScope) {
        this.viewScope = viewScope;
    }

    public Flash getFlash() {
        return getFacesContext().getExternalContext().getFlash();
    }
    
    public void addMessage(String message) {
        getFacesContext().getCurrentInstance().addMessage(null, new FacesMessage(message));
    }

}
