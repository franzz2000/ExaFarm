/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.uoc.tfc.exafarm.backing;

import edu.uoc.tfc.exafarm.entitats.Usuario;
import edu.uoc.tfc.exafarm.entitats.accessor.EntityAccessorException;
import edu.uoc.tfc.exafarm.entitats.accessor.UsuarioRegistry;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author franzz2000
 */
@ManagedBean
public class EditUsuarioBacking extends AbstractBacking {
    public void updateUsuario() {
        Usuario newUsuario = (Usuario) getSessionMap().get("currentUsuario");
        try {
            UsuarioRegistry.getCurrentInstance().updateUsuario(newUsuario);
        } catch (EntityAccessorException ex) {
            getFacesContext().addMessage(null, new FacesMessage("Error al añadir el usuario" + ((null != newUsuario)? "" + newUsuario.toString():"")+"."));
        }
    }
}
