/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.uoc.tfc.exafarm.entitats;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author franzz2000
 */
@Entity
@Table(name="grupos")
@NamedQueries ({
    @NamedQuery(name="grupos.findAll", query="SELECT g FROM Grupo AS g")
})
@ManagedBean
@RequestScoped
public class Grupo implements Serializable {
/*
     * Definición de variables
     */
    @Id
    @Column(name="id_grupo")
    private String idGrupo;
    
    @Column(name="descripcion")
    private String descripcion;


    /**
     * Constructor
     */
    public Grupo(String idGrupo) {
        this.idGrupo = idGrupo;
    }

    public Grupo() {}

    /**
     * Getter para idGrupo
     * @return El grupo del usuario
     *
     */
    public String getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(String idGrupo) {
        this.idGrupo = idGrupo;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) return false;
        if (getClass() != object.getClass()) return false;
        final Grupo otro = (Grupo) object;
        if ((this.idGrupo == null)? (otro.idGrupo != null) : !this.idGrupo.equals(otro.idGrupo)) return false;
        if ((this.descripcion == null)? (otro.descripcion != null) : !this.descripcion.equals(otro.descripcion)) return false;
        
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash = 73 * hash + (this.idGrupo != null ? idGrupo.hashCode() : 0);
        hash = 73 * hash + (this.descripcion != null ? descripcion.hashCode():0);
        return hash;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
