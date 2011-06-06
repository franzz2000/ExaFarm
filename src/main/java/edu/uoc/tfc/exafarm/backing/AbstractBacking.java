/**
 * Clase abstracta recoge valores del entorno para poder utilizarlos en 
 * los diferentes Backing Beans
 */

package edu.uoc.tfc.exafarm.backing;

import edu.uoc.tfc.exafarm.entitats.Examen;
import edu.uoc.tfc.exafarm.entitats.Usuario;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

@ManagedBean
@RequestScoped
public abstract class AbstractBacking {

    @ManagedProperty(value="#{facesContext}")
    private FacesContext facesContext;

    @ManagedProperty(value="#{requestScope}")
    private Map<String, Object> requestMap;

    @ManagedProperty(value="#{sessionScope}")
    private Map<String, Object> sessionMap;
    
    @ManagedProperty(value="#{viewScope}")
    private Map<String, Object> viewMap;

    
    /**
     * Getter para el usuario que ha accedido. Los datos del usuario
     * se encuentran guardados en la sesión.
     * 
     * @return Usuario que ha accedido 
     */
    public Usuario getCurrentUser() {
        return (Usuario) getSessionMap().get("currentUser");
    }

    /**
     * Setter para el usuario que ha accedido.
     * 
     * @param currentUser Usuario que ha accedido.
     */
    public void setCurrentUsuario(Usuario currentUser) {
        getSessionMap().remove("currentUser");
        if (null != currentUser) {
            getSessionMap().put("currentUser", currentUser);
        }
    }
    
    /**
     * Getter para Examen. Permite consultar un examen seleccionado
     * durante la vista.
     * 
     * @return Examen seleccionado
     */
    public Examen getCurrentExamen() {
        return(Examen) getViewMap().get("currentExamen");
    }
    
    /**
     * Setter del Examen. Guarda el examen en el entorno vista.
     * 
     * @param currentExamen Examen a guardar.
     */
    public void setCurrentExamen(Examen currentExamen) {
        getViewMap().remove("currentExamen");
        if (null != currentExamen) {
            getViewMap().put("currentExamen", currentExamen);
        }
    }

    /**
     * Método que comprueba si hay algún usuario registrado.
     * 
     * @return Boolean. True si hay un usuario registrado. 
     */
    public boolean isUsuarioLoggedIn() {
        return getSessionMap().containsKey("currentUser");
    }

    /**
     * Getter para FacesContext
     * 
     * @return FacesContext de la aplicación 
     */
    public FacesContext getFacesContext() {
        return facesContext;
    }

    /**
     * Setter para FacesContext.
     * 
     * @param context
     */
    public void setFacesContext(FacesContext context) {
        this.facesContext = context;
    }

    /**
     * Getter para SessionMap
     * 
     * @return Map<String, Object> actual. 
     */
    public Map<String, Object> getSessionMap() {
        return sessionMap;
    }

    /**
     * Setter para SessionMap.
     * 
     * @param sessionMap Guarda un sessionMap
     */
    public void setSessionMap(Map<String, Object> sessionMap) {
        this.sessionMap = sessionMap;
    }

    /**
     * Getter para RequestMap.
     * 
     * @return RequestMap
     */
    public Map<String, Object> getRequestMap() {
        return requestMap;
    }

    /**
     * Setter para RequestMap.
     * 
     * @param requestMap 
     */
    public void setRequestMap(Map<String, Object> requestMap) {
        this.requestMap = requestMap;
    }

    /**
     * Getter para ViewMap
     * 
     * @return viewMap 
     */
    public Map<String, Object> getViewMap() {
        return viewMap;
    }
    
    /**
     * Setter para ViewMap
     * 
     * @param viewScope 
     */
    public void setViewMap(Map<String, Object> viewMap) {
        this.viewMap = viewMap;
    }

    /**
     * Getter para Flash.
     * 
     * @return flash 
     */
    public Flash getFlash() {
        return getFacesContext().getExternalContext().getFlash();
    }
    
    /**
     * Añade un mensaje general que aparecerá en la visualización
     * 
     * @param message Texto que ha de aparecer
     */
    public void addMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
    }
    
    /**
     * Añade un mensaje general con tipo de severidad.
     * 
     * @param severity severidad del mensaje
     * @param message Texto que aparece
     * 
     */
    public void addMessage(FacesMessage.Severity severity, String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, message, null));
    }

}
