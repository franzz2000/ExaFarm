/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uoc.tfc.exafarm.entitats;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
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
@Table(name="temas")
@NamedQueries({
    @NamedQuery(name="temas.findAll", query = "SELECT t FROM Tema t"),
    @NamedQuery(name="temas.findById", query = "SELECT t FROM Tema t WHERE t.id = :id")
})
@ManagedBean
public class Tema extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Column(name="descripcion")
    private String descripcion;
    @Column(name="descripcion_corta")
    private String descripcionCorta;
    @Column(name="is_activo")
    private Boolean isActivo;
    @ManyToOne
    @JoinColumn(name="id_bloque")
    private Bloque bloque;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcionCorta() {
        return descripcionCorta;
    }

    public void setDescripcionCorta(String descripcionCorta) {
        this.descripcionCorta = descripcionCorta;
    }

    public Bloque getBloque() {
        return bloque;
    }

    public void setBloque(Bloque bloque) {
        this.bloque = bloque;
    }

    public Boolean getIsActivo() {
        return isActivo;
    }

    public void setIsActivo(Boolean isActivo) {
        this.isActivo = isActivo;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode(): 0);
        hash += (descripcion != null ? descripcion.hashCode() : 0);
        hash += (descripcionCorta != null ? descripcionCorta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Tema)) {
            return false;
        }
        Tema other = (Tema) object;
        if ((this.descripcion == null && other.descripcion != null) || (this.descripcion != null && !this.descripcion.equals(other.descripcion))) {
            return false;
        }
        if ((this.descripcionCorta == null && other.descripcionCorta != null) || (this.descripcionCorta != null && !this.descripcionCorta.equals(other.descripcionCorta))) {
            return false;
        }
        
        return true;
    }

    @Override
    public String toString() {
        return descripcionCorta;
    }
    
}
