/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uoc.tfc.exafarm.backing;

import edu.uoc.tfc.exafarm.entitats.Usuario;
import edu.uoc.tfc.exafarm.entitats.accessor.EntityAccessorException;
import edu.uoc.tfc.exafarm.entitats.accessor.UsuarioRegistry;
import edu.uoc.tfc.exafarm.extras.Utils;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author franzz2000
 */
@ManagedBean
@RequestScoped
public class administraDatosBacking extends AbstractBacking {
    Usuario usuario;
    
    public administraDatosBacking(){}
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario=usuario;
    }
    
    public void modifica() {
        try {
            UsuarioRegistry.getCurrentInstance().updateUsuario(usuario);
            Utils.addMessage(FacesMessage.SEVERITY_INFO, "Se ha modificado el usuario correctamente.");
        } catch (EntityAccessorException ex) {
            Utils.addMessage(FacesMessage.SEVERITY_ERROR, "Error al modificar el usuario.");
            Logger.getLogger(administraDatosBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @PostConstruct
    public void construct() {
        usuario = getCurrentUser();
    }
}
