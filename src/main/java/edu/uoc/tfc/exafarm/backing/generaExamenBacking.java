/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uoc.tfc.exafarm.backing;

import edu.uoc.tfc.exafarm.entitats.Examen;
import edu.uoc.tfc.exafarm.entitats.Pregunta;
import edu.uoc.tfc.exafarm.entitats.accessor.ExamenRegistry;
import edu.uoc.tfc.exafarm.extras.Version;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 *
 * @author franzz2000
 */
@ManagedBean
@RequestScoped
public class generaExamenBacking extends AbstractBacking {
    List<Version> versiones;
    String examen;
    Examen currentExamen;
    
    public generaExamenBacking() {
        versiones = new ArrayList();
        examen = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("examen");
        currentExamen = ExamenRegistry.getCurrentInstance().getExamenById(examen);
        for (Long i=0L; i<currentExamen.getNumVersiones(); i++) {
            Version version = new Version("Versión "+(i+1), new ArrayList<Pregunta>(currentExamen.getPreguntasList()));
            version.mezcla(i);
            version.ordena();
            versiones.add(version);
        } 
    }
    
    public List<Version> getVersiones() {
        return versiones;
    }
    
    public void setVersiones(List<Version> versiones){
        this.versiones = versiones;
    }
}
