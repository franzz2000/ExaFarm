package edu.uoc.tfc.exafarm.backing;


import edu.uoc.tfc.exafarm.entitats.Examen;
import edu.uoc.tfc.exafarm.entitats.Pregunta;
import edu.uoc.tfc.exafarm.entitats.Tema;
import edu.uoc.tfc.exafarm.entitats.Usuario;
import edu.uoc.tfc.exafarm.entitats.accessor.EntityAccessorException;
import edu.uoc.tfc.exafarm.entitats.accessor.ExamenRegistry;
import edu.uoc.tfc.exafarm.entitats.accessor.UsuarioRegistry;
import edu.uoc.tfc.exafarm.extras.Utils;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author franzz2000
 */

@ManagedBean
@ViewScoped
public class administraPreguntasBacking implements Serializable{
    String examenId;
    Examen examen;
    List <Pregunta> lista;
    List <Tema> temas;
    List <Usuario> profesores;
    SelectItem[] temasOptions;
    SelectItem[] profesoresOptions;
    String titulo;

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
        options[0] = new SelectItem("", Utils.getMessageResourceString("bundle", "AdministrarPreguntasTodos"));
        for (int i = 0; i < temas.size(); i++) {
            options[i+1] = new SelectItem(temas.get(i).getId(), temas.get(i).getDescripcionCorta());
        }
        
        return options;
    }
    
    private SelectItem[] createProfesorOptions(List<Usuario> profesor) {
        SelectItem[] options=new SelectItem[profesor.size()+1];
        options[0] = new SelectItem("", Utils.getMessageResourceString("bundle", "AdministrarPreguntasTodos"));
        for (int i = 0; i < profesor.size(); i++) {
            options[i+1] = new SelectItem(profesor.get(i).getId(), profesor.get(i).getIdUsuario());
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
            Utils.addMessage(FacesMessage.SEVERITY_INFO, Utils.getMessageResourceString("bundle", "AdministrarPreguntasOKActualizar"));
        } catch (EntityAccessorException ex) {
            Utils.addMessage(FacesMessage.SEVERITY_ERROR, Utils.getMessageResourceString("bundle", "AdministrarPreguntasErrorActualizar"));
            Logger.getLogger(ExamenRegistry.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        if(examenId==null) {
            Usuario usuario = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
            if (usuario.isUsuarioIsAdministrador()||usuario.isUsuarioIsCoordinador()) {
                lista = ExamenRegistry.getCurrentInstance().getPreguntaList();
            } else {
                lista = ExamenRegistry.getCurrentInstance().getPreguntaByUsuario(usuario);
            }
            titulo=Utils.getMessageResourceString("bundle", "AdministrarPreguntasLista");
        } else {
            examen = ExamenRegistry.getCurrentInstance().getExamenById(examenId);
            lista = ExamenRegistry.getCurrentInstance().getPreguntaByExamen(examen);
            String texto = Utils.getMessageResourceString("bundle", "AdministrarPreguntasListaExamen");
            Object[] params ={examen.toString()};
            MessageFormat messageFormat = new MessageFormat(texto);
            titulo = messageFormat.format(params);
        }
    }
}
