package edu.uoc.tfc.exafarm.backing;

import edu.uoc.tfc.exafarm.entitats.Tema;
import edu.uoc.tfc.exafarm.entitats.accessor.ExamenRegistry;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author franzz2000
 */

@ManagedBean
public class administraTemasBacking extends AbstractBacking {
    List <Tema> lista;
    
    Tema selectedTema;

    /** Creates a new instance of administraUsuarios */
    public administraTemasBacking() {
        lista = new ArrayList<Tema>();
        lista = ExamenRegistry.getCurrentInstance().getTemaList();
        selectedTema = null;
    }

    public List<Tema> getLista() {
        return lista;
    }

    public Tema getSelectedTema() {
        return selectedTema;
    }

    public void setSelectedTema(Tema selectedTema) {
        this.selectedTema = selectedTema;
    }
    
    public boolean isPaginator() {
        return lista.size()>10;
    }
    
    public void modifica() {
        addMessage("oruga");
    }
}
