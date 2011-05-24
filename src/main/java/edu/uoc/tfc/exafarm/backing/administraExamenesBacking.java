package edu.uoc.tfc.exafarm.backing;


import edu.uoc.tfc.exafarm.entitats.Examen;
import edu.uoc.tfc.exafarm.entitats.Pregunta;
import edu.uoc.tfc.exafarm.entitats.Tema;
import edu.uoc.tfc.exafarm.entitats.Usuario;
import edu.uoc.tfc.exafarm.entitats.accessor.ExamenRegistry;
import edu.uoc.tfc.exafarm.entitats.accessor.UsuarioRegistry;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;

/**
 *
 * @author franzz2000
 */

@ManagedBean
public class administraExamenesBacking extends AbstractBacking {
    List <Examen> lista;
    
    Examen selectedExamen;

    /** Creates a new instance of administraUsuarios */
    public administraExamenesBacking() {
        lista = new ArrayList<Examen>();
        lista = ExamenRegistry.getCurrentInstance().getExamenList();
        selectedExamen = null;
    }
    

    public List<Examen> getLista() {
        return lista;
    }

    public Examen getSelectedExamen() {
        return selectedExamen;
    }

    public void setSelectedPregunta(Examen selectedExamen) {
        this.selectedExamen = selectedExamen;
    }
    
    public boolean isPaginator() {
        return lista.size()>10;
    }
    
    public void modifica() {
        addMessage("oruga");
    }
}
