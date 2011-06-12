/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uoc.tfc.exafarm.backing;

import edu.uoc.tfc.exafarm.entitats.Pregunta;
import edu.uoc.tfc.exafarm.entitats.Respuesta;
import edu.uoc.tfc.exafarm.entitats.accessor.EntityAccessorException;
import edu.uoc.tfc.exafarm.entitats.accessor.ExamenRegistry;
import edu.uoc.tfc.exafarm.extras.Utils;
import java.io.Serializable;
import java.text.MessageFormat;
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
        respuestas = pregunta.getRespuestas();
    }

    public Pregunta getPregunta() {
        return pregunta;
    }

    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }

    public String getTitulo() {
        String texto = Utils.getMessageResourceString("bundle", "VerPreguntaTituloPanel");
        Object[] parametros = {pregunta.getId()};
        MessageFormat messageFormat = new MessageFormat(texto);
        return messageFormat.format(parametros);
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
            Utils.addMessage(FacesMessage.SEVERITY_INFO,  Utils.getMessageResourceString("bundle", "VerPreguntaOKModificar"));
        } catch (EntityAccessorException ex) {
            Utils.addMessage(FacesMessage.SEVERITY_ERROR, Utils.getMessageResourceString("bundle", "VerPreguntaErrorModificar"));
            Logger.getLogger(ExamenRegistry.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
}
