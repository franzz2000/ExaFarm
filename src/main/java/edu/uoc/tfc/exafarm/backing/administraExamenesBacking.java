package edu.uoc.tfc.exafarm.backing;


import edu.uoc.tfc.exafarm.entitats.Examen;
import edu.uoc.tfc.exafarm.entitats.Usuario;
import edu.uoc.tfc.exafarm.entitats.accessor.EntityAccessorException;
import edu.uoc.tfc.exafarm.entitats.accessor.ExamenRegistry;
import edu.uoc.tfc.exafarm.extras.Utils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author franzz2000
 */

@ManagedBean(name="administraExamenesBacking")
@ViewScoped
public class administraExamenesBacking implements Serializable{
    List <Examen> lista;
    
    Examen selectedExamen;

    /** Creates a new instance of administraUsuarios */
    public administraExamenesBacking() {}
    

    public List<Examen> getLista() {
        return lista;
    }

    public Examen getSelectedExamen() {
        return selectedExamen;
    }

    public void setSelectedExamen(Examen selectedExamen) {
        this.selectedExamen = selectedExamen;
    }
    
    public boolean isPaginator() {
        return lista.size()>10;
    }
    
    
    public void modifica(RowEditEvent ev) {
        Examen obj = null;
        try {
            obj = (Examen) ev.getObject();
            if(obj.getIsCerrado()&&obj.getIsActivo()) {
                obj.setIsActivo(Boolean.FALSE);
                Utils.addMessage(FacesMessage.SEVERITY_WARN, Utils.getMessageResourceString("bundle", "AdministrarExamenesAvisoDesactivo"));
            }
            ExamenRegistry.getCurrentInstance().updateExamen(obj);
        } catch (EntityAccessorException ex) {
            Utils.addMessage(FacesMessage.SEVERITY_ERROR, Utils.getMessageResourceString("bundle", "AdministrarExamenesErrorModificar"));
            Logger.getLogger(ExamenRegistry.class.getName()).log(Level.SEVERE, null, ex);
        }
        Utils.addMessage(FacesMessage.SEVERITY_INFO, Utils.getMessageResourceString("bundle", "AdministrarExamenesOKModificar"));
    }
    
    public void agregaExamen() {
        ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();

        Examen newExamen = (Examen) extContext.getRequestMap().get("examen");
        newExamen.setId(Long.MIN_VALUE);
        try {
            ExamenRegistry.getCurrentInstance().addExamen(newExamen);
            Utils.addMessage(FacesMessage.SEVERITY_INFO, Utils.getMessageResourceString("bundle", "AdministrarExamenesOKAnadir"));
        } catch (EntityAccessorException ex) {
            Utils.addMessage(FacesMessage.SEVERITY_ERROR, Utils.getMessageResourceString("bundle", "AdministrarExamenesErrorAnadir"));
            Logger.getLogger(ExamenRegistry.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @PostConstruct
    public void construct() {
        FacesContext fc = FacesContext.getCurrentInstance();
        String pagina = fc.getViewRoot().getViewId();
        Usuario currentUser = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
        if(pagina.contains("seleccionarExamen")) {
            lista = ExamenRegistry.getCurrentInstance().getExamenByNoCerrado(); 
        } else {
            lista = new ArrayList<Examen>();
            
            if(currentUser.isUsuarioIsAdministrador()||currentUser.isUsuarioIsCoordinador()) {
                lista = ExamenRegistry.getCurrentInstance().getExamenList();
            } else {
                lista = ExamenRegistry.getCurrentInstance().getExamenByActivo();
            }
            selectedExamen = null;
        }
    }
}
