package edu.uoc.tfc.exafarm.backing;

import edu.uoc.tfc.exafarm.entitats.Bloque;
import edu.uoc.tfc.exafarm.entitats.Tema;
import edu.uoc.tfc.exafarm.entitats.accessor.EntityAccessorException;
import edu.uoc.tfc.exafarm.entitats.accessor.ExamenRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author franzz2000
 */

@ManagedBean
public class administraTemasBacking extends AbstractBacking {
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
            addMessage("Error al actualizar el tema.");
            Logger.getLogger(ExamenRegistry.class.getName()).log(Level.SEVERE, null, ex);
        }
        addMessage("El tema se ha modificado correctamente.");
    }
    
    public void agregaTema() {
        ExternalContext extContext = getFacesContext().getExternalContext();

        ExamenRegistry eventRegistry = ExamenRegistry.getCurrentInstance();
        Tema newTema = (Tema) extContext.getRequestMap().get("tema");
        newTema.setId(Long.MIN_VALUE);
        try {
            ExamenRegistry.getCurrentInstance().addTema(newTema);
        } catch (EntityAccessorException ex) {
            addMessage("Error al añadir el tema.");
            Logger.getLogger(ExamenRegistry.class.getName()).log(Level.SEVERE, null, ex);
        }
        addMessage("Se ha añadido el tema correctamente.");
    }
}
