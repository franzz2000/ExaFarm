/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uoc.tfc.exafarm.backing;

import edu.uoc.tfc.exafarm.entitats.Examen;
import edu.uoc.tfc.exafarm.entitats.Pregunta;
import edu.uoc.tfc.exafarm.entitats.accessor.ExamenRegistry;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 *
 * @author franzz2000
 */
@ManagedBean
public class administraExamenBacking extends AbstractBacking {
    String examenId;
    Examen examen;
    List<Pregunta> preguntas;
    
    public administraExamenBacking() {      
        examenId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("examen");
        examen = ExamenRegistry.getCurrentInstance().getExamenById(examenId);
        preguntas = examen.getPreguntasList();
    }
    
    public List<Pregunta> getPreguntas(){
        return preguntas;
    }
    
    public void setPreguntas(List<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }

    public Examen getExamen() {
        return examen;
    }

    public void setExamen(Examen examen) {
        this.examen = examen;
    }
    
    
}
