package edu.uoc.tfc.exafarm.backing;


import edu.uoc.tfc.exafarm.entitats.Examen;
import edu.uoc.tfc.exafarm.entitats.Pregunta;
import edu.uoc.tfc.exafarm.entitats.Tema;
import edu.uoc.tfc.exafarm.entitats.Usuario;
import edu.uoc.tfc.exafarm.entitats.accessor.EntityAccessorException;
import edu.uoc.tfc.exafarm.entitats.accessor.ExamenRegistry;
import edu.uoc.tfc.exafarm.entitats.accessor.UsuarioRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author franzz2000
 */

@ManagedBean
@RequestScoped
public class administraPreguntasBacking extends AbstractBacking {
    String examenId;
    Examen examen;
    List <Pregunta> lista;
    List <Tema> temas;
    List <Usuario> profesores;
    SelectItem[] temasOptions;
    SelectItem[] profesoresOptions;
    String titulo = "Lista de preguntas";

    /** Creates a new instance of administraUsuarios */
    public administraPreguntasBacking() {
        temas = new ArrayList<Tema>();
        temas = ExamenRegistry.getCurrentInstance().getTemaList();
        temasOptions = createTemaOptions(temas);
        profesores = new ArrayList<Usuario>();
        profesores = UsuarioRegistry.getCurrentInstance().getUsuarioListActivo();
        profesoresOptions = createProfesorOptions(profesores);
    }
    
    private SelectItem[] createTemaOptions(List<Tema> temas) {
        SelectItem[] options=new SelectItem[temas.size()+1];
        options[0] = new SelectItem("", "Todos");
        for (int i = 0; i < temas.size(); i++) {
            options[i+1] = new SelectItem(temas.get(i).getId(), temas.get(i).getDescripcionCorta());
        }
        
        return options;
    }
    
    private SelectItem[] createProfesorOptions(List<Usuario> temas) {
        SelectItem[] options=new SelectItem[temas.size()+1];
        options[0] = new SelectItem("", "Todos");
        for (int i = 0; i < temas.size(); i++) {
            options[i+1] = new SelectItem(temas.get(i).getId(), temas.get(i).getIdUsuario());
        }
        
        return options;
    }

    public List<Pregunta> getLista() {
        return lista;
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public boolean isPaginator() {
        return lista.size()>10;
    }
    
    
    public void modifica(RowEditEvent ev) {
        Pregunta obj = null;
        try {
            obj = (Pregunta) ev.getObject();
            ExamenRegistry.getCurrentInstance().updatePregunta(obj);
        } catch (EntityAccessorException ex) {
            addMessage("Error al actualizar la pregunta.");
            Logger.getLogger(ExamenRegistry.class.getName()).log(Level.SEVERE, null, ex);
        }
        addMessage("La pregunta se ha modificado correctamente.");
    }
    
    
    
    public SelectItem[] getTemasOptions(){
        return temasOptions;
    } 
    
    public SelectItem[] getProfesoresOptions() {
        return profesoresOptions;
    }
    
    public List<Usuario> getListaProfesores() {
        return profesores;
    }

    public List<Usuario> getProfesores() {
        return profesores;
    }

    public void setProfesores(List<Usuario> profesores) {
        this.profesores = profesores;
    }

    public List<Tema> getTemas() {
        return temas;
    }

    public void setTemas(List<Tema> temas) {
        this.temas = temas;
    }
    
    public String getExamenId(){
        return examenId;
    }
    
    @PostConstruct
    public void construct(){
        examenId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("examen");
        if(examenId==null){
            examenId = (String)getViewMap().get("examenId");
        } else {
            getViewMap().put("examenId", examenId);
        }
        if(examenId==null) {
            Usuario usuario = getCurrentUser();
            if (usuario.isUsuarioIsAdministrador()||usuario.isUsuarioIsCoordinador()) {
                lista = ExamenRegistry.getCurrentInstance().getPreguntaList();
            } else {
                lista = ExamenRegistry.getCurrentInstance().getPreguntaByUsuario(usuario);
            }
        } else {
            examen = ExamenRegistry.getCurrentInstance().getExamenById(examenId);
            lista = ExamenRegistry.getCurrentInstance().getPreguntaByExamen(examen);
            titulo += " del Examen de " + examen.getDescripcion();
        }
    }
}
