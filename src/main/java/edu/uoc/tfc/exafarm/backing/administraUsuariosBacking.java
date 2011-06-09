package edu.uoc.tfc.exafarm.backing;

import edu.uoc.tfc.exafarm.entitats.Grupo;
import edu.uoc.tfc.exafarm.entitats.Usuario;
import edu.uoc.tfc.exafarm.entitats.accessor.EntityAccessorException;
import edu.uoc.tfc.exafarm.entitats.accessor.UsuarioRegistry;
import edu.uoc.tfc.exafarm.extras.Utils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author franzz2000
 */

@ManagedBean(name="administraUsuariosBacking")
@ViewScoped
public class administraUsuariosBacking implements Serializable{
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
        Usuario currentUser = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
        try {
            obj = (Usuario) ev.getObject();
            if(!obj.getGrupo().equals(currentUser.getGrupo())&&obj.getIdUsuario().equals(currentUser.getIdUsuario())) {
                Utils.addMessage(FacesMessage.SEVERITY_ERROR, Utils.getMessageResourceString("bundle", "AdministrarUsuariosErrorModificarGrupo"));
                obj.setGrupo(currentUser.getGrupo());
            } else {
                UsuarioRegistry.getCurrentInstance().updateUsuario(obj);
                Utils.addMessage(FacesMessage.SEVERITY_INFO,Utils.getMessageResourceString("bundle", "AdministrarUsuarioOKModificar"));
            }
        } catch (EntityAccessorException ex) {
            Utils.addMessage(FacesMessage.SEVERITY_ERROR, Utils.getMessageResourceString("bundle", "AdministrarUsuariosErrorModificar", null));
            Logger.getLogger(UsuarioRegistry.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void agregaUsuario() {
        ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();

        UsuarioRegistry eventRegistry = UsuarioRegistry.getCurrentInstance();
        Usuario newUsuario = (Usuario) extContext.getRequestMap().get("usuario");
        newUsuario.setId(Long.MIN_VALUE);
        try {
            UsuarioRegistry.getCurrentInstance().addUsuario(newUsuario);
        } catch (EntityAccessorException ex) {
            Utils.addMessage(FacesMessage.SEVERITY_ERROR, Utils.getMessageResourceString("bundle", "AdministrarUsuariosErrorAnadir", null));
            Logger.getLogger(UsuarioRegistry.class.getName()).log(Level.SEVERE, null, ex);
        }
        Utils.addMessage(FacesMessage.SEVERITY_INFO,Utils.getMessageResourceString("bundle", "AdministrarUsuarioOKAnadir"));
    }
}
