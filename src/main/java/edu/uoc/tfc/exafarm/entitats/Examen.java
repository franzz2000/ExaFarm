/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uoc.tfc.exafarm.entitats;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author franzz2000
 */
@Entity
@Table(name="examenes")
@NamedQueries({
    @NamedQuery(name = "examenes.findAll", query = "SELECT e FROM Examen e"),
    @NamedQuery(name = "examenes.findById", query = "SELECT e FROM Examen e WHERE e.id = :id")
})
public class Examen extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Column(name="fecha_convocatoria")
    @Temporal(TemporalType.DATE)
    private Date fechaConvocatoria;
    @Column(name="convocatoria")
    private String convocatoria;
    @Column(name="descripcion")
    private String descripcion;
    @Column(name="num_preguntas")
    private Short numPreguntas;
    @Column(name="num_versiones")
    private Short numVersiones;
    @Column(name="is_activo")
    private Short isActivo;
    @Column(name="is_cerrado")
    private Short isCerrado;
    @JoinTable(name="examen_pregunta", joinColumns= {
        @JoinColumn(name="FK_examen")}, inverseJoinColumns={
        @JoinColumn(name="FK_pregunta")
    })
    @ManyToMany
    private List<Pregunta> preguntasList;

    public String getConvocatoria() {
        return convocatoria;
    }

    public void setConvocatoria(String convocatoria) {
        this.convocatoria = convocatoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaConvocatoria() {
        return fechaConvocatoria;
    }

    public void setFechaConvocatoria(Date fechaConvocatoria) {
        this.fechaConvocatoria = fechaConvocatoria;
    }

    public Short getIsActivo() {
        return isActivo;
    }

    public void setIsActivo(Short isActivo) {
        this.isActivo = isActivo;
    }

    public Short getIsCerrado() {
        return isCerrado;
    }

    public void setIsCerrado(Short isCerrado) {
        this.isCerrado = isCerrado;
    }

    public Short getNumPreguntas() {
        return numPreguntas;
    }

    public void setNumPreguntas(Short numPreguntas) {
        this.numPreguntas = numPreguntas;
    }

    public Short getNumVersiones() {
        return numVersiones;
    }

    public void setNumVersiones(Short numVersiones) {
        this.numVersiones = numVersiones;
    }

    public List<Pregunta> getPreguntasList() {
        return preguntasList;
    }

    public void setPreguntasList(List<Pregunta> preguntasList) {
        this.preguntasList = preguntasList;
    }
    
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fechaConvocatoria != null ? fechaConvocatoria.hashCode() : 0);
        hash += (convocatoria != null ? convocatoria.hashCode() : 0);
        hash += (descripcion != null ? descripcion.hashCode() : 0);
        hash += (fechaConvocatoria != null ? fechaConvocatoria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Examen)) {
            return false;
        }
        Examen other = (Examen) object;
        if ((this.fechaConvocatoria == null && other.fechaConvocatoria != null) || (this.fechaConvocatoria != null && !this.fechaConvocatoria.equals(other.fechaConvocatoria))) {
            return false;
        }
        if ((this.convocatoria == null && other.convocatoria != null) || (this.convocatoria != null && !this.convocatoria.equals(other.convocatoria))) {
            return false;
        }
        if ((this.descripcion == null && other.descripcion != null) || (this.descripcion != null && !this.descripcion.equals(other.descripcion))) {
            return false;
        }
        if ((this.numPreguntas == null && other.numPreguntas != null) || (this.numPreguntas != null && !this.numPreguntas.equals(other.numPreguntas))) {
            return false;
        }
        if ((this.numVersiones == null && other.numVersiones != null) || (this.numVersiones != null && !this.numVersiones.equals(other.numVersiones))) {
            return false;
        }
        if ((this.isActivo == null && other.isActivo != null) || (this.isActivo != null && !this.isActivo.equals(other.isActivo))) {
            return false;
        }
        if ((this.isCerrado == null && other.isCerrado != null) || (this.isCerrado != null && !this.isCerrado.equals(other.isCerrado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return convocatoria;
    }
    
}
