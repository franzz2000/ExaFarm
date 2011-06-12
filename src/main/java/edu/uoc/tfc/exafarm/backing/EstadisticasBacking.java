/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uoc.tfc.exafarm.backing;

import edu.uoc.tfc.exafarm.entitats.Tema;
import edu.uoc.tfc.exafarm.entitats.Usuario;
import edu.uoc.tfc.exafarm.entitats.accessor.ExamenRegistry;
import java.util.List;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Franz
 */
@ManagedBean
public class EstadisticasBacking extends AbstractBacking {
    Usuario usuario;
    
    public String getNumPreguntas(){
        return Integer.toString(getCurrentUser().getPreguntas().size());
    }
    
    public String getNumTemas(){
        List<Tema> temas = ExamenRegistry.getCurrentInstance().getTemaList();
        Integer distribucion = ExamenRegistry.getCurrentInstance().getCountTemas(temas.get(1));
        return distribucion.toString();
    }
}
