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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
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
    SelectItem[] grupoOptions;
    
    Usuario selectedUser;

    /** Creates a new instance of administraUsuarios */
    public administraUsuariosBacking() {
        lista = new ArrayList<Usuario>();
        lista = UsuarioRegistry.getCurrentInstance().getUserList();
        listaGrupo = UsuarioRegistry.getCurrentInstance().getGrupoList();
        grupoOptions = createGrupoOptions(listaGrupo);
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
    
    public SelectItem[] getGrupoOptions() {
        return grupoOptions;
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
                Utils.addMessage(FacesMessage.SEVERITY_INFO,Utils.getMessageResourceString("bundle", "AdministrarUsuariosOKModificar"));
            }
        } catch (EntityAccessorException ex) {
            Utils.addMessage(FacesMessage.SEVERITY_ERROR, Utils.getMessageResourceString("bundle", "AdministrarUsuariosErrorModificar", null));
            Logger.getLogger(UsuarioRegistry.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private SelectItem[] createGrupoOptions(List<Grupo> grupos) {
        SelectItem[] options=new SelectItem[grupos.size()+1];
        options[0] = new SelectItem(null, Utils.getMessageResourceString("bundle", "AdministrarUsuariosSeleccioneGrupo"));
        for (int i = 0; i < grupos.size(); i++) {
            options[i+1] = new SelectItem(grupos.get(i), grupos.get(i).getDescripcion());
        }
        return options;
    }
    
    public String agregaUsuario() {
        ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
        
        Usuario newUsuario = (Usuario) extContext.getRequestMap().get("usuario");
        Usuario buscaUsuario = UsuarioRegistry.getCurrentInstance().getUsuarioByIdUsuario(newUsuario.getIdUsuario());
        if(buscaUsuario!=null){
            Utils.addMessage(FacesMessage.SEVERITY_ERROR,Utils.getMessageResourceString("bundle", "AdministrarUsuariosErrorIdUsuarioNoRepite"));
        } else {
            newUsuario.setId(Long.MIN_VALUE);
            try {
                UsuarioRegistry.getCurrentInstance().addUsuario(newUsuario);
                Utils.addMessage(FacesMessage.SEVERITY_INFO,Utils.getMessageResourceString("bundle", "AdministrarUsuariosOKAnadir"));
                return "principal";
            } catch (EntityAccessorException ex) {
                Utils.addMessage(FacesMessage.SEVERITY_ERROR, Utils.getMessageResourceString("bundle", "AdministrarUsuariosErrorAnadir", null));
                Logger.getLogger(UsuarioRegistry.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
}
