/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uoc.tfc.exafarm.entitats;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author franzz2000
 */
@Entity
@Table(name="bloques")
@NamedQueries({
    @NamedQuery(name = "bloques.findAll", query = "SELECT b FROM Bloque b"),
    @NamedQuery(name = "bloques.findById", query = "SELECT b FROM Bloque b WHERE b.id = :id")
})
public class Bloque extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String descripcion;
    @Column(name="is_activo")
    private Boolean isActivo;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        hash += (descripcion != null ? descripcion.hashCode() : 0);
        hash += (isActivo != null ? isActivo.hashCode():0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bloque)) {
            return false;
        }
        Bloque other = (Bloque) object;
        if ((this.descripcion == null && other.descripcion != null) || (this.descripcion != null && !this.descripcion.equals(other.descripcion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
