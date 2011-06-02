/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uoc.tfc.exafarm.extras;

import edu.uoc.tfc.exafarm.entitats.Pregunta;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author franzz2000
 */
@ManagedBean
@RequestScoped
public class Version {
    List<Pregunta> preguntas;
    String texto;
    
    public Version() {}
    
    public Version(String texto, List<Pregunta> preguntas) {
        this.texto = texto;
        this.preguntas = preguntas;
    }
            
    
    public List<Pregunta> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(List<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String version) {
        this.texto = version;
    }
    
    public void mezcla(Long seed) {
        Random random = new Random(seed);
        Collections.shuffle(preguntas, random);
    }
    
    public void ordena() {
        Collections.sort(preguntas);
    }
    
    
}
