/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uoc.tfc.exafarm.entitats;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author franzz2000
 */
@Entity
@Table(name="bloques")
@NamedQueries({
    @NamedQuery(name = "bloques.findAll", query = "SELECT b FROM Bloque b"),
    @NamedQuery(name = "bloques.findById", query = "SELECT b FROM Bloque b WHERE b.id = :id"),
    @NamedQuery(name = "bloques.findActivos", query = "SELECT b FROM Bloque b WHERE b.isActivo = true")
        
})
@ManagedBean
public class Bloque implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @NotNull
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    private Long id;
    @Column(name="descripcion")
    private String descripcion;
    @Column(name="is_activo")
    private Boolean isActivo;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Bloque (String descripcion, Boolean isActivo) {
        this.setDescripcion(descripcion);
        this.setIsActivo(isActivo);
    }
    public Bloque() {
        id = 0L;
    }

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
