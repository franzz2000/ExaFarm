/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uoc.tfc.exafarm.entitats;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 *
 * @author franzz2000
 */
@Entity
@Table(name="examen_pregunta")
@IdClass(value = ExamenPreguntaPK.class)
public class ExamenPregunta {
    @Id
    @Column(name="id_examen")
    private Long idExamen;
    
    @Id
    @Column(name="id_pregunta")
    private Long idPregunta;

    public Long getIdExamen() {
        return idExamen;
    }

    public void setIdExamen(Long idExamen) {
        this.idExamen = idExamen;
    }

    public Long getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(Long idPregunta) {
        this.idPregunta = idPregunta;
    }
}
