/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uoc.tfc.exafarm.entitats;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author franzz2000
 */
@Entity
@Table(name = "respuestas")
@NamedQueries({
    @NamedQuery(name = "respuestas.findAll", query = "SELECT r FROM Respuesta r"),
    @NamedQuery(name = "respuestas.findById", query = "SELECT r FROM Respuesta r WHERE r.id = :id")
})
public class Respuesta extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Column(name="texto")
    private String texto;
    
    @Column(name="is_correcta")
    private Boolean isCorrecta;
    
    @Column(name="is_activa")
    private Boolean isActiva;
    
    @JoinColumn(name = "id_pregunta", referencedColumnName = "id")
    @ManyToOne
    private Pregunta pregunta;
    
    public Boolean getIsActiva() {
        return isActiva;
    }

    public void setIsActiva(Boolean isActiva) {
        this.isActiva = isActiva;
    }

    public Pregunta getPregunta() {
        return pregunta;
    }

    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }

    
    public Boolean getIsCorrecta() {
        return isCorrecta;
    }

    public void setIsCorrecta(Boolean isCorrecta) {
        this.isCorrecta = isCorrecta;
    }

    public Pregunta getPreguntas() {
        return pregunta;
    }

    public void setPreguntas(Pregunta preguntas) {
        this.pregunta = preguntas;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (texto != null ? texto.hashCode() : 0);
        hash += (isCorrecta != null ? isCorrecta.hashCode() : 0);
//        hash += (pregunta != null ? pregunta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Respuesta)) {
            return false;
        }
        Respuesta other = (Respuesta) object;
        if ((this.texto == null && other.texto != null) || (this.texto != null && !this.texto.equals(other.texto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.uoc.tfc.exafarm.entitats.Respuesta[ id=" + texto + " ]";
    }
    
}
