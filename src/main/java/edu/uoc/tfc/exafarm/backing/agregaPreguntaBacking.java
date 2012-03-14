package edu.uoc.tfc.exafarm.backing;

import edu.uoc.tfc.exafarm.entitats.Pregunta;
import edu.uoc.tfc.exafarm.entitats.Respuesta;
import edu.uoc.tfc.exafarm.entitats.Tema;
import edu.uoc.tfc.exafarm.entitats.Usuario;
import edu.uoc.tfc.exafarm.entitats.accessor.EntityAccessorException;
import edu.uoc.tfc.exafarm.entitats.accessor.ExamenRegistry;
import edu.uoc.tfc.exafarm.extras.Utils;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author franzz2000
 */
@ManagedBean
@RequestScoped
public class agregaPreguntaBacking extends AbstractBacking {
    List<Tema> temas;
    Boolean correctaSeleccionada = false;
    SelectItem[] temasOptions;
    
    Pregunta newPregunta = new Pregunta();
    
    Respuesta respuesta1 = new Respuesta();
    Respuesta respuesta2 = new Respuesta();
    Respuesta respuesta3 = new Respuesta();
    Respuesta respuesta4 = new Respuesta();
    Respuesta respuesta5 = new Respuesta();
    
    public agregaPreguntaBacking() {
        temas = new ArrayList<Tema>();
        temas = ExamenRegistry.getCurrentInstance().getTemaListActivo();
        temasOptions = createTemasOptions(temas);
    }

    public Pregunta getNewPregunta() {
        return newPregunta;
    }

    public void setNewPregunta(Pregunta newPregunta) {
        this.newPregunta = newPregunta;
    }
    
    

    public Respuesta getRespuesta1() {
        return respuesta1;
    }

    public void setRespuesta1(Respuesta respuesta1) {
        this.respuesta1 = respuesta1;
    }

    public Respuesta getRespuesta2() {
        return respuesta2;
    }

    public void setRespuesta2(Respuesta respuesta2) {
        this.respuesta2 = respuesta2;
    }

    public Respuesta getRespuesta3() {
        return respuesta3;
    }

    public void setRespuesta3(Respuesta respuesta3) {
        this.respuesta3 = respuesta3;
    }

    public Respuesta getRespuesta4() {
        return respuesta4;
    }

    public void setRespuesta4(Respuesta respuesta4) {
        this.respuesta4 = respuesta4;
    }

    public Respuesta getRespuesta5() {
        return respuesta5;
    }

    public void setRespuesta5(Respuesta respuesta5) {
        this.respuesta5 = respuesta5;
    }

    public SelectItem[] getTemasOptions() {
        return temasOptions;
    }

    public void setTemasOptions(SelectItem[] temasOptions) {
        this.temasOptions = temasOptions;
    }
    
    private SelectItem[] createTemasOptions(List<Tema> temas) {
        SelectItem[] options=new SelectItem[temas.size()+1];
        options[0] = new SelectItem(0L, Utils.getMessageResourceString("bundle", "AgregarPreguntaSeleccioneTema"));
        for (int i = 0; i < temas.size(); i++) {
            options[i+1] = new SelectItem(temas.get(i), temas.get(i).getDescripcionCorta());
        }
        
        return options;
    }
    
    public String agregaPregunta() {
        if (respuesta1.getIsCorrecta()||respuesta2.getIsCorrecta()||respuesta3.getIsCorrecta()||respuesta4.getIsCorrecta()||respuesta5.getIsCorrecta()) {
                newPregunta.setId(Long.MIN_VALUE);

                List<Respuesta> respuestas = new ArrayList<Respuesta>();
                respuesta1.setPregunta(newPregunta);
                respuesta2.setPregunta(newPregunta);
                respuesta3.setPregunta(newPregunta);
                respuesta4.setPregunta(newPregunta);
                respuesta5.setPregunta(newPregunta);
                respuestas.add(respuesta1);
                respuestas.add(respuesta2);
                respuestas.add(respuesta3);
                respuestas.add(respuesta4);
                respuestas.add(respuesta5);
                
                
                
                Usuario usuario = getCurrentUser();
                newPregunta.setUsuario(usuario);
                newPregunta.setRespuestas(respuestas);
            try {
                ExamenRegistry.getCurrentInstance().addPregunta(newPregunta);
//                newPregunta.setRespuestas(respuestas);
//                ExamenRegistry.getCurrentInstance().updatePregunta(newPregunta);
                Utils.addMessage(FacesMessage.SEVERITY_INFO, Utils.getMessageResourceString("bundle", "AgregarPreguntaOKAnadir"));
            } catch (EntityAccessorException ex) {
                Utils.addMessage(FacesMessage.SEVERITY_ERROR, Utils.getMessageResourceString("bundle", "AgregarPreguntaErrorAnadir"));
                Logger.getLogger(ExamenRegistry.class.getName()).log(Level.SEVERE, null, ex);
            }    
        } else {
            Utils.addMessage(FacesMessage.SEVERITY_ERROR, Utils.getMessageResourceString("bundle", "AgregarPreguntaErrorSeleccionCorrecta"));
            return "";
        } 
        return "principal.xhtml?faces-redirect=true";
    }
    
    @PostConstruct
    public void construct() {
        String idPregunta = getFacesContext().getExternalContext().getRequestParameterMap().get("pregunta");
        if (idPregunta !=null) {
            Pregunta pregunta = ExamenRegistry.getCurrentInstance().getPreguntaById(idPregunta);
            if(pregunta != null) {
                newPregunta.setTexto(pregunta.getTexto());
                newPregunta.setIsActiva(pregunta.getIsActiva());
                newPregunta.setIsCorta(pregunta.getIsCorta());
                newPregunta.setIsMezclable(pregunta.getIsMezclable());
                respuesta1.setTexto(pregunta.getRespuestas().get(0).getTexto());
                respuesta2.setTexto(pregunta.getRespuestas().get(1).getTexto());
                respuesta3.setTexto(pregunta.getRespuestas().get(2).getTexto());
                respuesta4.setTexto(pregunta.getRespuestas().get(3).getTexto());
                respuesta5.setTexto(pregunta.getRespuestas().get(4).getTexto());

            }
        }
    }
}
