/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uoc.tfc.exafarm.backing;

import edu.uoc.tfc.exafarm.backing.Seleccion;
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
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;


@ManagedBean
@RequestScoped
public class seleccionaPreguntasBacking extends AbstractBacking {
    List<Tema> temas;
    List<Usuario> profesores;
    Examen examen;
    List <Pregunta> lista;
    List <Seleccion> listaSeleccion;
    Pregunta[] preguntasSeleccionadas;
    Pregunta preguntaSeleccionada;
    SelectItem[] temasOptions;
    SelectItem[] profesoresOptions;
    String examenId;
    
    String titulo = "Preguntas seleccionadas para el examen de ";

    /** Creates a new instance of  */
    public seleccionaPreguntasBacking() {
        temas = ExamenRegistry.getCurrentInstance().getTemaList();
        temasOptions = createTemaOptions(temas);
        profesores = new ArrayList<Usuario>();
        profesores = UsuarioRegistry.getCurrentInstance().getUsuarioListActivo();
        profesoresOptions = createProfesorOptions(profesores);
    }
    
    public SelectItem[] getTemasOptions(){
        return temasOptions;
    } 
    
    public SelectItem[] getProfesoresOptions() {
        return profesoresOptions;
    }

    public Pregunta getPreguntaSeleccionada() {
        return preguntaSeleccionada;
    }

    public void setPreguntaSeleccionada(Pregunta preguntaSeleccionada) {
        this.preguntaSeleccionada = preguntaSeleccionada;
    }

    public Pregunta[] getPreguntasSeleccionadas() {
        return preguntasSeleccionadas;
    }

    public void setPreguntasSeleccionadas(Pregunta[] preguntasSeleccionadas) {
        this.preguntasSeleccionadas = preguntasSeleccionadas;
    }

    
    public List<Seleccion> getListaSeleccion() {
        return listaSeleccion;
    }
    
    public void setListaSeleccion(List<Seleccion> listaSeleccion) {
        this.listaSeleccion = listaSeleccion;
    } 
    
    public String getTitulo() {
        return titulo;
    }
    
    public boolean isPaginator() {
        return lista.size()>10;
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
    
    public void guardar() {
        lista = new ArrayList<Pregunta>();
        for(Seleccion seleccion:listaSeleccion) {
            if (seleccion.getSeleccion()) {
                lista.add(seleccion.getPregunta());
            }
        }
        examen = getCurrentExamen();
        examen.setPreguntasList(lista);
        try {
            ExamenRegistry.getCurrentInstance().updateExamen(examen);
            addMessage("Se ha actualizado la selección de preguntas");
        } catch (EntityAccessorException ex) {
            addMessage("Error al modificar el examen.");
            Logger.getLogger(ExamenRegistry.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @PostConstruct
    public void construct(){
        listaSeleccion = (List<Seleccion>) getViewMap().get("listaSeleccion");
        if (listaSeleccion==null) {
            examenId = getFacesContext().getExternalContext().getRequestParameterMap().get("examen");
            if(examenId==null) {
                examen = getCurrentExamen();
            } else {
                examen = ExamenRegistry.getCurrentInstance().getExamenById(examenId);
                setCurrentExamen(examen);
            }
            Usuario usuario = getCurrentUser();
            if (getCurrentUser().isUsuarioIsAdministrador()||getCurrentUser().isUsuarioIsCoordinador()) {
                lista = ExamenRegistry.getCurrentInstance().getPreguntaList();
            } else {
                lista = ExamenRegistry.getCurrentInstance().getPreguntaByUsuario(usuario);
            }

            listaSeleccion = new ArrayList();
            for (Pregunta pregunta:lista) {
                listaSeleccion.add(new Seleccion(pregunta, pregunta.getExamenes().contains(examen)));
            }
            getViewMap().put("listaSeleccion", listaSeleccion);
            titulo += examen.getDescripcion();
        }
        
    }
}
