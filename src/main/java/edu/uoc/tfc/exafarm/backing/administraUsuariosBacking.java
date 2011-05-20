package edu.uoc.tfc.exafarm.backing;

import edu.uoc.tfc.exafarm.entitats.Usuario;
import edu.uoc.tfc.exafarm.entitats.accessor.UsuarioRegistry;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author franzz2000
 */

@ManagedBean
public class administraUsuariosBacking extends AbstractBacking {
    List <Usuario> lista;
    
    Usuario selectedUser;

    /** Creates a new instance of administraUsuarios */
    public administraUsuariosBacking() {
        lista = new ArrayList<Usuario>();
        lista = UsuarioRegistry.getCurrentInstance().getUserList();
        selectedUser = null;
    }

    public List<Usuario> getLista() {
        return lista;
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
    
    public void modifica() {
        addMessage("oruga");
    }
}
