package edu.uoc.tfc.exafarm.backing;

import edu.uoc.tfc.exafarm.entitats.Grupo;
import edu.uoc.tfc.exafarm.entitats.Usuario;
import edu.uoc.tfc.exafarm.entitats.accessor.EntityAccessorException;
import edu.uoc.tfc.exafarm.entitats.accessor.UsuarioRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author franzz2000
 */

@ManagedBean
public class administraUsuariosBacking extends AbstractBacking {
    List <Usuario> lista;
    List <Grupo> listaGrupo;
    
    Usuario selectedUser;

    /** Creates a new instance of administraUsuarios */
    public administraUsuariosBacking() {
        lista = new ArrayList<Usuario>();
        lista = UsuarioRegistry.getCurrentInstance().getUserList();
        listaGrupo = UsuarioRegistry.getCurrentInstance().getGrupoList();
        selectedUser = new Usuario();
    }    

    public List<Usuario> getLista() {
        return lista;
    }
    
    public void setLista(List<Usuario> lista) {
        this.lista = lista;
    }
    
    public List<Grupo> getListaGrupo(){
        return listaGrupo;
    }
    
    public Usuario getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(Usuario selectedUser) {
        this.selectedUser = selectedUser;
    }
    
    public boolean isPaginator() {
        return lista.size()>10;
    }
    
    public void modifica(RowEditEvent ev) {
        Usuario obj = null;
        try {
            obj = (Usuario) ev.getObject();
            if(!obj.getGrupo().equals(getCurrentUser().getGrupo())&&obj.getIdUsuario().equals(getCurrentUser().getIdUsuario())) {
                addMessage(FacesMessage.SEVERITY_ERROR, "No es posible modificar el grupo del usuario activo.");
                obj.setGrupo(getCurrentUser().getGrupo());
            } else {
                UsuarioRegistry.getCurrentInstance().updateUsuario(obj);
                addMessage("El usuario se ha modificado correctamente.");
            }
        } catch (EntityAccessorException ex) {
            addMessage("Error al actualizar el usuario.");
            Logger.getLogger(UsuarioRegistry.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void agregaUsuario() {
        ExternalContext extContext = getFacesContext().getExternalContext();

        UsuarioRegistry eventRegistry = UsuarioRegistry.getCurrentInstance();
        Usuario newUsuario = (Usuario) extContext.getRequestMap().get("usuario");
        newUsuario.setId(Long.MIN_VALUE);
        try {
            UsuarioRegistry.getCurrentInstance().addUsuario(newUsuario);
        } catch (EntityAccessorException ex) {
            addMessage("Error al añadir el usuario.");
            Logger.getLogger(UsuarioRegistry.class.getName()).log(Level.SEVERE, null, ex);
        }
        addMessage("Se ha añadido el usuario correctamente.");
    }
}
