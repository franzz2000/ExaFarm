package edu.uoc.tfc.exafarm.backing;

import edu.uoc.tfc.exafarm.entitats.Bloque;
import edu.uoc.tfc.exafarm.entitats.accessor.ExamenRegistry;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author franzz2000
 */

@ManagedBean
public class administraBloquesBacking extends AbstractBacking {
    List <Bloque> lista;
    
    Bloque selectedBloque;

    /** Creates a new instance of administraUsuarios */
    public administraBloquesBacking() {
        lista = new ArrayList<Bloque>();
        lista = ExamenRegistry.getCurrentInstance().getBloqueList();
        selectedBloque = null;
    }

    public List<Bloque> getLista() {
        return lista;
    }

    public Bloque getSelectedBloque() {
        return selectedBloque;
    }

    public void setSelectedBloque(Bloque selectedBloque) {
        this.selectedBloque = selectedBloque;
    }
    
    public boolean isPaginator() {
        return lista.size()>10;
    }
    
    public void modifica() {
        addMessage("oruga");
    }
}
