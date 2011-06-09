package edu.uoc.tfc.exafarm.backing;

import edu.uoc.tfc.exafarm.entitats.Bloque;
import edu.uoc.tfc.exafarm.entitats.Tema;
import edu.uoc.tfc.exafarm.entitats.accessor.EntityAccessorException;
import edu.uoc.tfc.exafarm.entitats.accessor.ExamenRegistry;
import edu.uoc.tfc.exafarm.extras.Utils;
import java.io.Serializable;
import java.util.ArrayList;
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

@ManagedBean(name="administraTemasBacking")
@ViewScoped
public class administraTemasBacking implements Serializable{
    List <Tema> lista;
    List <Bloque> listaBloque;
    
    Tema selectedTema;

    /** Creates a new instance of administraUsuarios */
    public administraTemasBacking() {
        lista = new ArrayList<Tema>();
        lista = ExamenRegistry.getCurrentInstance().getTemaList();
        listaBloque = ExamenRegistry.getCurrentInstance().getBloqueListActivo();
        selectedTema = null;
    }

    public List<Tema> getLista() {
        return lista;
    }
    
    public List<Bloque> getListaBloque() {
        return listaBloque;
    }

    public Tema getSelectedTema() {
        return selectedTema;
    }

    public void setSelectedTema(Tema selectedTema) {
        this.selectedTema = selectedTema;
    }
    
    public boolean isPaginator() {
        return lista.size()>10;
    }
    
    public void modifica(RowEditEvent ev) {
        Tema obj = null;
        try {
            obj = (Tema) ev.getObject();
            ExamenRegistry.getCurrentInstance().updateTema(obj);
        } catch (EntityAccessorException ex) {
            Utils.addMessage(FacesMessage.SEVERITY_ERROR,Utils.getMessageResourceString("bundle", "AdministrarTemasErrorModificar"));
            Logger.getLogger(administraTemasBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
        Utils.addMessage(FacesMessage.SEVERITY_INFO, Utils.getMessageResourceString("bundle", "AdministrarTemasOKModificar"));
    }
    
    public void agregaTema() {
        ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();

//        ExamenRegistry eventRegistry = ExamenRegistry.getCurrentInstance();
        Tema newTema = (Tema) extContext.getRequestMap().get("tema");
        newTema.setId(Long.MIN_VALUE);
        try {
            ExamenRegistry.getCurrentInstance().addTema(newTema);
        } catch (EntityAccessorException ex) {
            Utils.addMessage(FacesMessage.SEVERITY_ERROR,Utils.getMessageResourceString("bundle", "AdministrarTemasErrorAnadir"));
            Logger.getLogger(administraTemasBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
        Utils.addMessage(FacesMessage.SEVERITY_INFO,Utils.getMessageResourceString("bundle", "AdministrarTemasOKAnadir"));
    }
}
