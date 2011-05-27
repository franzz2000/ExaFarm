/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uoc.tfc.exafarm.backing;

import edu.uoc.tfc.exafarm.entitats.Pregunta;
import edu.uoc.tfc.exafarm.entitats.Respuesta;
import edu.uoc.tfc.exafarm.entitats.accessor.EntityAccessorException;
import edu.uoc.tfc.exafarm.entitats.accessor.ExamenRegistry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;

/**
 *
 * @author franzz2000
 */
@ManagedBean
@ViewScoped
public class agregaPreguntaBacking extends AbstractBacking {
    Pregunta newPregunta;
    
    Respuesta respuesta;
    
    public Pregunta getNewPregunta() { 
        return newPregunta;
    }
    
    public void setNewPregunta(Pregunta newPregunta) {
        this.newPregunta = newPregunta;
    }
    
    public void agregaPregunta() {
        ExternalContext extContext = getFacesContext().getExternalContext();

        newPregunta = (Pregunta) extContext.getRequestMap().get("pregunta");
        newPregunta.setId(Long.MIN_VALUE);
        try {
            ExamenRegistry.getCurrentInstance().addPregunta(newPregunta);
        } catch (EntityAccessorException ex) {
            addMessage("Error al añadir la pregunta.");
            Logger.getLogger(ExamenRegistry.class.getName()).log(Level.SEVERE, null, ex);
        }
        addMessage("Se ha añadido la pregunta.");
    }
    
    
    public String enlargeList() {
        return null;
    }
    
    @PostConstruct
    public void construct(){
        ExternalContext extContext = getFacesContext().getExternalContext();
        newPregunta = (Pregunta) extContext.getRequestMap().get("pregunta");

    }
}
