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
import javax.faces.model.SelectItem;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author franzz2000
 */

@ManagedBean(name="administraTemasBacking")
@ViewScoped
public class administraTemasBacking implements Serializable{
    List <Tema> lista;
    List <Bloque> listaBloques;
    SelectItem[] bloqueOptions;
    
    Tema selectedTema;

    /** Creates a new instance of administraUsuarios */
    public administraTemasBacking() {
        lista = new ArrayList<Tema>();
        lista = ExamenRegistry.getCurrentInstance().getTemaList();
        listaBloques = ExamenRegistry.getCurrentInstance().getBloqueListActivo();
        bloqueOptions = createBloquesOptions(listaBloques);
        selectedTema = null;
    }

    public List<Tema> getLista() {
        return lista;
    }

    public SelectItem[] getBloqueOptions() {
        return bloqueOptions;
    }
    
    public List<Bloque> getListaBloques() {
        return listaBloques;
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
    
    private SelectItem[] createBloquesOptions(List<Bloque> bloques) {
        SelectItem[] options=new SelectItem[bloques.size()+1];
        options[0] = new SelectItem("", Utils.getMessageResourceString("bundle", "AdministrarTemasSeleccioneBloque"));
        for (int i = 0; i < bloques.size(); i++) {
            options[i+1] = new SelectItem(bloques.get(i), bloques.get(i).getDescripcion());
        }
        return options;
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
    
    public String agregaTema() {
        ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();

//        ExamenRegistry eventRegistry = ExamenRegistry.getCurrentInstance();
        Tema newTema = (Tema) extContext.getRequestMap().get("tema");
        newTema.setId(Long.MIN_VALUE);
        try {
            ExamenRegistry.getCurrentInstance().addTema(newTema);
            Utils.addMessage(FacesMessage.SEVERITY_INFO,Utils.getMessageResourceString("bundle", "AdministrarTemasOKAnadir"));
            return "principal";
        } catch (EntityAccessorException ex) {
            Utils.addMessage(FacesMessage.SEVERITY_ERROR,Utils.getMessageResourceString("bundle", "AdministrarTemasErrorAnadir"));
            Logger.getLogger(administraTemasBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
