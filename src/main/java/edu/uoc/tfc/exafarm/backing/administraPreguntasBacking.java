package edu.uoc.tfc.exafarm.backing;


import edu.uoc.tfc.exafarm.entitats.Pregunta;
import edu.uoc.tfc.exafarm.entitats.Tema;
import edu.uoc.tfc.exafarm.entitats.Usuario;
import edu.uoc.tfc.exafarm.entitats.accessor.ExamenRegistry;
import edu.uoc.tfc.exafarm.entitats.accessor.UsuarioRegistry;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author franzz2000
 */

@ManagedBean
@RequestScoped
public class administraPreguntasBacking extends AbstractBacking {
    List <Pregunta> lista;
    List <Tema> temas;
    List <Usuario> profesores;
    private SelectItem[] temasOptions;
    private SelectItem[] profesoresOptions;
    
    
    Pregunta selectedPregunta;

    /** Creates a new instance of administraUsuarios */
    public administraPreguntasBacking() {
        lista = new ArrayList<Pregunta>();
        lista = ExamenRegistry.getCurrentInstance().getPreguntaList();
        selectedPregunta = null;
        temas = new ArrayList<Tema>();
        temas = ExamenRegistry.getCurrentInstance().getTemaList();
        temasOptions = createTemaOptions(temas);
        profesores = new ArrayList<Usuario>();
        profesores = UsuarioRegistry.getCurrentInstance().getUserList();
        profesoresOptions = createProfesorOptions(profesores);
        
    }
    
    private SelectItem[] createTemaOptions(List<Tema> temas) {
        SelectItem[] options=new SelectItem[temas.size()+1];
        options[0] = new SelectItem("", "Seleccione:");
        for (int i = 0; i < temas.size(); i++) {
            options[i+1] = new SelectItem(temas.get(i).getId(), temas.get(i).getDescripcionCorta());
        }
        
        return options;
    }
    
    private SelectItem[] createProfesorOptions(List<Usuario> temas) {
        SelectItem[] options=new SelectItem[temas.size()+1];
        options[0] = new SelectItem("", "Seleccione:");
        for (int i = 0; i < temas.size(); i++) {
            options[i+1] = new SelectItem(temas.get(i).getId(), temas.get(i).getIdUsuario());
        }
        
        return options;
    }

    public List<Pregunta> getLista() {
        return lista;
    }

    public Pregunta getSelectedPregunta() {
        return selectedPregunta;
    }

    public void setSelectedPregunta(Pregunta selectedPregunta) {
        this.selectedPregunta = selectedPregunta;
    }
    
    public boolean isPaginator() {
        return lista.size()>10;
    }
    
    public void modifica() {
        addMessage("oruga");
    }
    
    public SelectItem[] getTemasOptions(){
        return temasOptions;
    } 
    
    public SelectItem[] getProfesoresOptions() {
        return profesoresOptions;
    }
    
    
}
