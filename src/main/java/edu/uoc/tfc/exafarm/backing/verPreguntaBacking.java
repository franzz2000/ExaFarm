/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uoc.tfc.exafarm.backing;

import edu.uoc.tfc.exafarm.entitats.Pregunta;
import edu.uoc.tfc.exafarm.entitats.Respuesta;
import edu.uoc.tfc.exafarm.entitats.accessor.EntityAccessorException;
import edu.uoc.tfc.exafarm.entitats.accessor.ExamenRegistry;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author franzz2000
 */
@ManagedBean
@ViewScoped
public class verPreguntaBacking implements Serializable{
    Pregunta pregunta;
    String preguntaId;
    String titulo;
    List<Respuesta> respuestas;

    public verPreguntaBacking() {
        preguntaId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pregunta");
        pregunta = ExamenRegistry.getCurrentInstance().getPreguntaById(preguntaId);
        titulo = "Modificar pregunta " + pregunta.getId();
        respuestas = pregunta.getRespuestas();
    }

    public Pregunta getPregunta() {
        return pregunta;
    }

    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }

    public String getTitulo() {
        return titulo;
    }
    
    public List<Respuesta> getRespuestas(){
        return respuestas;
    }
    
    public void setRespuestas(List<Respuesta> respuestas){
        this.respuestas = respuestas;
    }
    
    public String agregaRespuesta() {
        respuestas.add(new Respuesta());
        return "verPregunta";
    }
    
    public void modifica() {
        try {
            ExamenRegistry.getCurrentInstance().updatePregunta(pregunta);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("La pregunta se ha modificado correctamente."));
        } catch (EntityAccessorException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error al actualizar la pregunta."));
            Logger.getLogger(ExamenRegistry.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
}
