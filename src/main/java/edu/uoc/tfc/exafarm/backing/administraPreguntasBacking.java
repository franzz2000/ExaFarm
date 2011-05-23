package edu.uoc.tfc.exafarm.backing;


import edu.uoc.tfc.exafarm.entitats.Pregunta;
import edu.uoc.tfc.exafarm.entitats.accessor.ExamenRegistry;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author franzz2000
 */

@ManagedBean
public class administraPreguntasBacking extends AbstractBacking {
    List <Pregunta> lista;
    
    Pregunta selectedPregunta;

    /** Creates a new instance of administraUsuarios */
    public administraPreguntasBacking() {
        lista = new ArrayList<Pregunta>();
        lista = ExamenRegistry.getCurrentInstance().getPreguntaList();
        selectedPregunta = null;
    }

    public List<Pregunta> getLista() {
        return lista;
    }

    public Pregunta getSelectedPregunta() {
        return selectedPregunta;
    }

    public void setSelectedPregunta(Pregunta selectedBloque) {
        this.selectedPregunta = selectedPregunta;
    }
    
    public boolean isPaginator() {
        return lista.size()>10;
    }
    
    public void modifica() {
        addMessage("oruga");
    }
}
