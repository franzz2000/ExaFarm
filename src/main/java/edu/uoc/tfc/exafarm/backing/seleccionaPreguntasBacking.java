/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uoc.tfc.exafarm.backing;

import edu.uoc.tfc.exafarm.entitats.Examen;
import edu.uoc.tfc.exafarm.entitats.Pregunta;
import edu.uoc.tfc.exafarm.entitats.Usuario;
import edu.uoc.tfc.exafarm.entitats.accessor.ExamenRegistry;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;


@ManagedBean
@ViewScoped
public class seleccionaPreguntasBacking extends AbstractBacking {
    Examen examen;
    List <Pregunta> lista;
    Pregunta[] preguntasSeleccionadas;
    Pregunta preguntaSeleccionada;
    
    String titulo = "Lista de preguntas";

    /** Creates a new instance of  */
    public seleccionaPreguntasBacking() {
        
    }

    public Pregunta getPreguntaSeleccionada() {
        return preguntaSeleccionada;
    }

    public void setPreguntaSeleccionada(Pregunta preguntaSeleccionada) {
        this.preguntaSeleccionada = preguntaSeleccionada;
    }

    public Pregunta[] getPreguntasSeleccionadas() {
        return preguntasSeleccionadas;
    }

    public void setPreguntasSeleccionadas(Pregunta[] preguntasSeleccionadas) {
        this.preguntasSeleccionadas = preguntasSeleccionadas;
    }

    
    public List<Pregunta> getLista() {
        return lista;
    }
    
    public void setLista(List<Pregunta> lista) {
        this.lista = lista;
    } 
    
    public String getTitulo() {
        return titulo;
    }
    
    public boolean isPaginator() {
        return lista.size()>10;
    }
    
    @PostConstruct
    public void construct(){
        String examenId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("examen");
        examen = ExamenRegistry.getCurrentInstance().getExamenById(examenId);
        Usuario usuario = getCurrentUser();
        lista = ExamenRegistry.getCurrentInstance().getPreguntaByUsuario(usuario);
        titulo += " del Examen de " + examen.getDescripcion();
    }
}
