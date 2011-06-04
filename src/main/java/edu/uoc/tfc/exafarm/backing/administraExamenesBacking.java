package edu.uoc.tfc.exafarm.backing;


import edu.uoc.tfc.exafarm.entitats.Examen;
import edu.uoc.tfc.exafarm.entitats.accessor.EntityAccessorException;
import edu.uoc.tfc.exafarm.entitats.accessor.ExamenRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author franzz2000
 */

@ManagedBean
public class administraExamenesBacking extends AbstractBacking {
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
            ExamenRegistry.getCurrentInstance().updateExamen(obj);
        } catch (EntityAccessorException ex) {
            addMessage("Error al actualizar el examen.");
            Logger.getLogger(ExamenRegistry.class.getName()).log(Level.SEVERE, null, ex);
        }
        addMessage("El examen se ha modificado correctamente.");
    }
    
    public void agregaExamen() {
        ExternalContext extContext = getFacesContext().getExternalContext();

        Examen newExamen = (Examen) extContext.getRequestMap().get("examen");
        newExamen.setId(Long.MIN_VALUE);
        try {
            ExamenRegistry.getCurrentInstance().addExamen(newExamen);
        } catch (EntityAccessorException ex) {
            addMessage("Error al añadir el examen.");
            Logger.getLogger(ExamenRegistry.class.getName()).log(Level.SEVERE, null, ex);
        }
        addMessage("Se ha añadido el examen correctamente.");
    }
    
//    public String preguntas() {
//        return "/faces/profesor/administrarPreguntas";
//    }
    
    @PostConstruct
    public void construct() {
    lista = new ArrayList<Examen>();
        if(getCurrentUser().isUsuarioIsAdministrador()||getCurrentUser().isUsuarioIsCoordinador()) {
            lista = ExamenRegistry.getCurrentInstance().getExamenList();
        } else {
            lista = ExamenRegistry.getCurrentInstance().getExamenByActivo();
        }
        selectedExamen = null;    
    }
}
