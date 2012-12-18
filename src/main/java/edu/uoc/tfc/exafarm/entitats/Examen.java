/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uoc.tfc.exafarm.entitats;

import java.io.Serializable;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.List;
import java.util.Locale;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
    @NamedQuery(name = "examenes.findById", query = "SELECT e FROM Examen e WHERE e.id = :id"),
    @NamedQuery(name = "examenes.findByActivo", query = "SELECT e FROM Examen e WHERE e.isActivo = TRUE"),
    @NamedQuery(name = "examenes.findByNoCerrado", query = "SELECT e FROM Examen e WHERE e.isCerrado = FALSE"),
    @NamedQuery(name = "examenes.countByTema", query = "SELECT p.tema, COUNT(p) FROM Examen e JOIN e.preguntasList p WHERE e.id = :idExamen GROUP BY p.tema")
})
@ManagedBean
public class Examen extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Column(name="fecha_convocatoria")
    @Temporal(TemporalType.DATE)
    private Date fechaConvocatoria;
    @Column(name="num_preguntas")
    private Integer numPreguntas;
    @Column(name="num_versiones")
    private Integer numVersiones;
    @Column(name="is_activo")
    private Boolean isActivo;
    @Column(name="is_cerrado")
    private Boolean isCerrado;
    @ManyToMany(cascade= CascadeType.ALL)
    @JoinTable(name="examen_pregunta",
            joinColumns= {@JoinColumn(name="id_examen", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="id_pregunta", referencedColumnName="id")
    })
    private List<Pregunta> preguntasList;

    public Date getFechaConvocatoria() {
        return fechaConvocatoria;
    }

    public void setFechaConvocatoria(Date fechaConvocatoria) {
        this.fechaConvocatoria = fechaConvocatoria;
    }

    public Boolean getIsActivo() {
        return isActivo;
    }

    public void setIsActivo(Boolean isActivo) {
        this.isActivo = isActivo;
    }

    public Boolean getIsCerrado() {
        return isCerrado;
    }

    public void setIsCerrado(Boolean isCerrado) {
        this.isCerrado = isCerrado;
    }

    public Integer getNumPreguntas() {
        return numPreguntas;
    }

    public void setNumPreguntas(Integer numPreguntas) {
        this.numPreguntas = numPreguntas;
    }

    public Integer getNumVersiones() {
        return numVersiones;
    }

    public void setNumVersiones(Integer numVersiones) {
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
        Format formatter;
        Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        formatter = new SimpleDateFormat("MMMM yyyy", locale);
        return formatter.format(fechaConvocatoria);
    }
    
}
