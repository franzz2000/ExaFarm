package edu.uoc.tfc.exafarm.backing;

import edu.uoc.tfc.exafarm.entitats.Bloque;
import edu.uoc.tfc.exafarm.entitats.accessor.EntityAccessorException;
import edu.uoc.tfc.exafarm.entitats.accessor.ExamenRegistry;
import edu.uoc.tfc.exafarm.extras.Utils;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

@ManagedBean(name="administraBloquesBacking")
@ViewScoped
public class administraBloquesBacking implements Serializable{
    List<Bloque> lista;
    Bloque selectedBloque;

    /** Creates a new instance of administraUsuarios */
    public administraBloquesBacking() {
        lista = ExamenRegistry.getCurrentInstance().getBloqueList();
    }

    public List<Bloque> getLista() {
        return lista;
    }

//    public Bloque getSelectedBloque() {
//        return (Bloque) getSessionMap().get("selectedBloque");
//    }
//
//    public void setSelectedBloque(Bloque selectedBloque) {
//        getSessionMap().remove("selectedBloque");
//        if (null != selectedBloque) {
//            getSessionMap().put("selectedBloque", selectedBloque);
//        }
//    }
    
    public boolean isPaginator() {
        return lista.size()>10;
    }
    
    public void modifica(Bloque bloque) {
        Bloque obj = null;
        try {
            obj = bloque;
            ExamenRegistry.getCurrentInstance().updateBloque(obj);
        } catch (EntityAccessorException ex) {
            Utils.addMessage(FacesMessage.SEVERITY_ERROR, Utils.getMessageResourceString("bundle", "AdministrarBloquesErrorModificar"));
            Logger.getLogger(administraBloquesBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
        Utils.addMessage(FacesMessage.SEVERITY_INFO, Utils.getMessageResourceString("bundle", "AdministrarBloquesOKModificar"));
    }
    
    public void agregaBloque() {
        ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();

        ExamenRegistry eventRegistry = ExamenRegistry.getCurrentInstance();
        Bloque newBloque = (Bloque) extContext.getRequestMap().get("bloque");
        newBloque.setId(Long.MIN_VALUE);
        try {
            ExamenRegistry.getCurrentInstance().addBloque(newBloque);
            Utils.addMessage(FacesMessage.SEVERITY_INFO, Utils.getMessageResourceString("bundle", "AdministrarBloquesOKAnadir"));
        } catch (EntityAccessorException ex) {
            Utils.addMessage(FacesMessage.SEVERITY_ERROR, Utils.getMessageResourceString("bundle", "AdministrarBloquesErrorAnadir"));
            Logger.getLogger(administraBloquesBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
