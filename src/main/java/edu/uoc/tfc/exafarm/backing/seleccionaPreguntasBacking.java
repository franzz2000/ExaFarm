/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uoc.tfc.exafarm.backing;

import edu.uoc.tfc.exafarm.entitats.Examen;
import edu.uoc.tfc.exafarm.entitats.Pregunta;
import edu.uoc.tfc.exafarm.entitats.Tema;
import edu.uoc.tfc.exafarm.entitats.Usuario;
import edu.uoc.tfc.exafarm.entitats.accessor.EntityAccessorException;
import edu.uoc.tfc.exafarm.entitats.accessor.ExamenRegistry;
import edu.uoc.tfc.exafarm.entitats.accessor.UsuarioRegistry;
import java.io.Serializable;
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


@ManagedBean
@ViewScoped
public class seleccionaPreguntasBacking implements Serializable{
    List<Tema> temas;
    List<Usuario> profesores;
    Examen examen;
    List <Pregunta> listaPreguntas;
    List <Seleccion> listaSeleccion;
    Pregunta preguntaSeleccionada;
    SelectItem[] temasOptions;
    SelectItem[] profesoresOptions;
    String examenId;
    Integer numeroPreguntas;
    
    String titulo = "Preguntas seleccionadas para el examen de ";

    /** Creates a new instance of  */
    public seleccionaPreguntasBacking() {
        temas = ExamenRegistry.getCurrentInstance().getTemaList();
        temasOptions = createTemaOptions(temas);
        profesores = new ArrayList<Usuario>();
        profesores = UsuarioRegistry.getCurrentInstance().getUsuarioListActivo();
        profesoresOptions = createProfesorOptions(profesores);
        numeroPreguntas=0;
    }
    
    public SelectItem[] getTemasOptions(){
        return temasOptions;
    } 
    
    public SelectItem[] getProfesoresOptions() {
        return profesoresOptions;
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
    
    public Integer getNumeroPreguntas() {
        return numeroPreguntas;
    }
    
    public boolean isPaginator() {
        return listaPreguntas.size()>10;
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
        List<Pregunta> preguntasExamen = new ArrayList<Pregunta>();
        for(Seleccion seleccion:listaSeleccion) {
            if (seleccion.getPregunta().getExamenes().contains(examen)&&!seleccion.getSeleccion()) {
                seleccion.getPregunta().getExamenes().remove(examen);
            }
            if (!seleccion.getPregunta().getExamenes().contains(examen)&&seleccion.getSeleccion()) {
                seleccion.getPregunta().getExamenes().add(examen);
            }
            if (seleccion.getSeleccion()) {
                preguntasExamen.add(seleccion.getPregunta());
            }
        }
        examen.setPreguntasList(preguntasExamen);
        try {
            ExamenRegistry.getCurrentInstance().updateExamen(examen);            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha actualizado la selección de preguntas", null));
        } catch (EntityAccessorException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al modificar el examen.", null));
            Logger.getLogger(ExamenRegistry.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void cambioSeleccion(){
        Seleccion currentSeleccion = (Seleccion)FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("seleccion");
        if(currentSeleccion.getSeleccion()) {
            numeroPreguntas++;
        } else {
            numeroPreguntas--;
        }
    }
    
    @PostConstruct
    public void construct(){
        examenId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("examen");
        if(examenId==null) {
            List<Examen> examenes = ExamenRegistry.getCurrentInstance().getExamenByActivo();
            examen = examenes.get(0);
        } else {
            examen = ExamenRegistry.getCurrentInstance().getExamenById(examenId);
        }
        Usuario usuario = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
        if (usuario.isUsuarioIsAdministrador()||usuario.isUsuarioIsCoordinador()) {
            listaPreguntas = ExamenRegistry.getCurrentInstance().getPreguntaList();
        } else {
            listaPreguntas = ExamenRegistry.getCurrentInstance().getPreguntaByUsuario(usuario);
        }

        listaSeleccion = new ArrayList<Seleccion>();
        for (Pregunta pregunta:listaPreguntas) {
            Boolean seleccionada = pregunta.getExamenes().contains(examen);
            listaSeleccion.add(new Seleccion(pregunta, seleccionada));
            if(seleccionada)
                numeroPreguntas++;
            
        }
        titulo += examen.getDescripcion();
    }
}
