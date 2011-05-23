/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uoc.tfc.exafarm.entitats;

import java.io.Serializable;

/**
 *
 * @author franzz2000
 */
public class ExamenPreguntaPK implements Serializable{
    public Long idExamen;
    public Long idPregunta;
    
    public ExamenPreguntaPK() {
        
    }
        
    public ExamenPreguntaPK(Long idExamen, Long idPregunta) {
        this.idExamen = idExamen;
        this.idPregunta = idPregunta;
    }
    
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ExamenPreguntaPK)) {
            return false;
        }
        
        ExamenPreguntaPK other = (ExamenPreguntaPK) object;
        if ((this.idExamen == null && other.idExamen != null) || (this.idExamen != null && !this.idExamen.equals(other.idExamen))) {
            return false;
        }
        if ((this.idPregunta == null && other.idPregunta != null) || (this.idPregunta != null && !this.idPregunta.equals(other.idPregunta))) {
            return false;
        }
        return true;
        
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idExamen != null ? idExamen.hashCode() : 0);
        hash += (idPregunta != null ? idPregunta.hashCode() : 0);
        return hash;
    }
}
