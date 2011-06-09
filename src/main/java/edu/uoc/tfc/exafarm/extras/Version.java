/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uoc.tfc.exafarm.extras;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfWriter;
import edu.uoc.tfc.exafarm.entitats.Pregunta;
import edu.uoc.tfc.exafarm.entitats.Respuesta;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author franzz2000
 */
@ManagedBean
@RequestScoped
public class Version {
    public static final float[][] COLUMNS = {
        { 36, 36, 296, 806 } , { 299, 36, 559, 806 }
    };
    List<Pregunta> preguntas;
    String texto;
    Long version;
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
    public Version(String texto, List<Pregunta> preguntas, Long version) {
        this.texto = texto;
        this.preguntas = preguntas;
        this.version = version;
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
    public String getTexto() {
        return texto;
    }

    /**
     * Setter de cabecera de examen
     * 
     * @param version 
     */
    public void setTexto(String version) {
        this.texto = version;
    }
    
    public Long getVersion() {
        return version;
    }
    
    public void setVersion(Long version){
        this.version = version;
    }
    
    /**
     * Mezcla las preguntas del examen.
     * 
     * @param seed Semilla para la generación de mezcla semialeatoria 
     */
    public void mezcla(Long seed) {
        Random random = new Random(seed);
        Collections.shuffle(preguntas, random);
    }
    
    /**
     * Ordena las preguntas por bloque (Teoría, Seminarios)
     */
    public void ordena() {
        Collections.sort(preguntas);
    }

    /**
     * Genera y agrega preguntas en documento pdf
     * @param document 
     */
    public void buildPDFContent(Document document, PdfWriter writer) throws DocumentException {
        ColumnText ct = new ColumnText(writer.getDirectContent());
        int column = 0;
        ct.setSimpleColumn(COLUMNS[column][0], COLUMNS[column][1], COLUMNS[column][2], COLUMNS[column][3]);
        int status = ColumnText.START_COLUMN;
        float y;
        com.itextpdf.text.List listaPreguntas = new com.itextpdf.text.List(com.itextpdf.text.List.ORDERED);
        int i = 1;
        for(Pregunta pregunta:preguntas) {
            y = ct.getYLine();
            addPregunta(ct, pregunta, i);
            status = ct.go(true);
            if (ColumnText.hasMoreText(status)) {
                column = (column + 1) % 2;
                if (column==0)
                    document.newPage();
                ct.setSimpleColumn(COLUMNS[column][0], COLUMNS[column][1], COLUMNS[column][2], COLUMNS[column][3]);
                y = COLUMNS[column][3];
            }
            ct.setYLine(y);
            ct.setText(null);
            addPregunta(ct, pregunta, i);
            i++;
            status = ct.go();
        }
    }
    
    private void addPregunta(ColumnText ct, Pregunta pregunta, int i) {
        ct.addElement(new Paragraph(i + ") " + pregunta.getTexto().toUpperCase()));
        com.itextpdf.text.List listaRespuestas = new com.itextpdf.text.List(com.itextpdf.text.List.ORDERED, com.itextpdf.text.List.ALPHABETICAL);
        listaRespuestas.setIndentationLeft(15F);
        for(Respuesta respuesta:pregunta.getRespuestas()) {
            listaRespuestas.add(respuesta.getTexto());
        }
        ct.addElement(listaRespuestas);
        ct.addElement(Chunk.NEWLINE);
    }
}
