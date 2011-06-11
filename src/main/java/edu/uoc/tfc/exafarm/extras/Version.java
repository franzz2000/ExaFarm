/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uoc.tfc.exafarm.extras;

import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import edu.uoc.tfc.exafarm.entitats.Pregunta;
import edu.uoc.tfc.exafarm.entitats.Respuesta;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
public class Version implements Serializable {
    public static final float[][] COLUMNS = {
        { 36, 36, 296, 806 } , { 299, 36, 559, 806 }
    };
    public static final Font NORMAL = new Font(FontFamily.HELVETICA, 22);
    private List<Pregunta> preguntas;
    private Date fechaExamen;
    private Long numVersion;
    private String fichero;
    /**
     * Constructor vacío
     */
    public Version() {}
    /**
     * Constructor que se inicializa con los parámetros enviados
     * 
     * @param texto Texto de cabecera del examen
     * @param preguntas Lista de preguntas a incluir en el examen
     */
    public Version(Date fechaExamen, List<Pregunta> preguntas, Long numVersion) {
        this.fechaExamen = fechaExamen;
        this.preguntas = preguntas;
        this.numVersion = numVersion;
    }
            
    /**
     * Getter de lista de preguntas
     * @return Lista de preguntas del examen
     */
    public List<Pregunta> getPreguntas() {
        return preguntas;
    }
    
    /**
     * Setter de lista de preguntas de examen
     * @param preguntas Lista de preguntas
     */
    public void setPreguntas(List<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }
    
    /**
     * Getter de texto de cabecera de examen
     * 
     * @return Cabecera de examen 
     */
    public Date getFechaExamen() {
        return fechaExamen;
    }

    /**
     * Setter de cabecera de examen
     * 
     * @param version 
     */
    public void setFechaExamen(Date fechaExamen) {
        this.fechaExamen = fechaExamen;
    }
    
    public Long getNumVersion() {
        return numVersion;
    }
    
    public void setNumVersion(Long numVersion){
        this.numVersion = numVersion;
    }

    
    /**
     * Mezcla las preguntas del examen y las respuestas si son mezclables.
     * 
     * @param seed Semilla para la generación de mezcla semialeatoria 
     */
    public void mezcla() {
        Random random = new Random(numVersion);
        Collections.shuffle(preguntas, random);
    }
    
    /**
     * Ordena las preguntas por bloque (Teoría, Seminarios)
     */
    public void ordena() {
        Collections.sort(preguntas);
    }

    
}
