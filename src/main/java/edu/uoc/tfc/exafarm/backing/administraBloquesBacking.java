package edu.uoc.tfc.exafarm.backing;

import edu.uoc.tfc.exafarm.entitats.Bloque;
import edu.uoc.tfc.exafarm.entitats.accessor.EntityAccessorException;
import edu.uoc.tfc.exafarm.entitats.accessor.ExamenRegistry;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author franzz2000
 */

@ManagedBean
@SessionScoped
public class administraBloquesBacking extends AbstractBacking {
    List<Bloque> lista;
    Bloque selectedBloque;

    /** Creates a new instance of administraUsuarios */
    public administraBloquesBacking() {
        lista = ExamenRegistry.getCurrentInstance().getBloqueList();
    }

    public List<Bloque> getLista() {
        return lista;
    }

    public Bloque getSelectedBloque() {
        return (Bloque) getSessionMap().get("selectedBloque");
    }

    public void setSelectedBloque(Bloque selectedBloque) {
        getSessionMap().remove("selectedBloque");
        if (null != selectedBloque) {
            getSessionMap().put("selectedBloque", selectedBloque);
        }
    }
    
    public boolean isPaginator() {
        return lista.size()>10;
    }
    
    public void modifica(RowEditEvent ev) {
        Bloque obj = null;
        try {
            obj = (Bloque) ev.getObject();
            ExamenRegistry.getCurrentInstance().updateBloque(obj);
        } catch (EntityAccessorException ex) {
            addMessage("Error al actualizar el bloque.");
            Logger.getLogger(ExamenRegistry.class.getName()).log(Level.SEVERE, null, ex);
        }
        addMessage("El bloque se ha modificado correctamente.");
    }
    
    public void agregaBloque() {
        ExternalContext extContext = getFacesContext().getExternalContext();

        ExamenRegistry eventRegistry = ExamenRegistry.getCurrentInstance();
        Bloque newBloque = (Bloque) extContext.getRequestMap().get("bloque");
        newBloque.setId(Long.MIN_VALUE);
        try {
            ExamenRegistry.getCurrentInstance().addBloque(newBloque);
            addMessage("Se ha añadido el bloque correctamente.");
        } catch (EntityAccessorException ex) {
            addMessage("Error al añadir el bloque.");
            Logger.getLogger(ExamenRegistry.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
