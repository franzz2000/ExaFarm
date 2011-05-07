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
import javax.persistence.Table;

/**
 *
 * @author franzz2000
 */
@Entity
@Table(name="grupos")
@ManagedBean
@RequestScoped
public class Grupo extends AbstractEntity implements Serializable {
/*
     * Definici—n de variables
     */
    @Column(name="id_grupo")
    private String idGrupo;


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

    @Override
    public boolean equals(Object object) {
        if (object == null) return false;
        if (getClass() != object.getClass()) return false;
        final Grupo otro = (Grupo) object;
        if ((this.idGrupo == null)? (otro.idGrupo != null) : !this.idGrupo.equals(otro.idGrupo)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
