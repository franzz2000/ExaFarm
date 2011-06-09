/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uoc.tfc.exafarm.backing;

import edu.uoc.tfc.exafarm.entitats.Pregunta;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author franzz2000
 */
@ManagedBean
@ViewScoped
public class Seleccion implements Serializable {
    private Pregunta pregunta;
    private Boolean seleccion;
    
    public Seleccion() {}

    public Seleccion (Pregunta pregunta, Boolean seleccion) {
        this.pregunta = pregunta;
        this.seleccion = seleccion;
    }
    public Pregunta getPregunta() {
        return pregunta;
    }

    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }

    public Boolean getSeleccion() {
        return seleccion;
    }

    public void setSeleccion(Boolean seleccion) {
        this.seleccion = seleccion;
    }
}
