/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uoc.tfc.exafarm.backing;

import edu.uoc.tfc.exafarm.entitats.Examen;
import edu.uoc.tfc.exafarm.entitats.Pregunta;
import edu.uoc.tfc.exafarm.entitats.Tema;
import edu.uoc.tfc.exafarm.entitats.Usuario;
import edu.uoc.tfc.exafarm.entitats.accessor.ExamenRegistry;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

/**
 *
 * @author Franz
 */
@ManagedBean
public class EstadisticasBacking extends AbstractBacking {
    Usuario usuario;
    Long max=0L;
    
    public String numPreguntas(Long id){
        Examen examen = ExamenRegistry.getCurrentInstance().getExamenById(id+"");
        return examen.getPreguntasList().size()+"";
    }
    
    public String numPreguntasBloque(Long idExamen, Long idBloque) {
        Examen examen = ExamenRegistry.getCurrentInstance().getExamenById(idExamen+"");
        List<Pregunta> preguntas = examen.getPreguntasList();
        Iterator it = preguntas.iterator();
        Integer suma = 0;
        while (it.hasNext()) {
            Pregunta element = (Pregunta)it.next();
            Long id = element.getTema().getBloque().getId();
            if(id.equals(idBloque))
                suma++;
        }
        return suma+"";
    }
    
    public List<Examen> getExamenes(){
        return ExamenRegistry.getCurrentInstance().getExamenList();
    }
    
    public CartesianChartModel estadisticaTemas(Long id) {
        CartesianChartModel temasModel = new CartesianChartModel();
        ChartSeries teoria = new ChartSeries();
        ChartSeries seminarios = new ChartSeries();
        teoria.setLabel("Teoria");
        seminarios.setLabel("Seminarios");
        
        List<Object[]> resultado = ExamenRegistry.getCurrentInstance().getEstadisticasByExamen(id+"");
        
        Iterator it = resultado.iterator();
        max=0L;
        while (it.hasNext()) {
            Object[] element = (Object[])it.next();
            Tema tema = (Tema)element[0];
            String temaTxt = tema.getDescripcionCorta();
            Long cantidad = (Long)element[1];
            if (cantidad>max)
                max=cantidad;
            
            if(tema.getBloque().getId()==1L) {
                teoria.set(temaTxt, cantidad);
                seminarios.set(temaTxt, 0);
            }
            else {
                teoria.set(temaTxt, 0);
                seminarios.set(temaTxt, cantidad);
            }
                
        }
        temasModel.addSeries(seminarios);
        temasModel.addSeries(teoria);
        return temasModel;
    }
    
    public String getMaxX(){
        return max+"";
    }
    
    public String altura(Long id) { 
        return (ExamenRegistry.getCurrentInstance().getEstadisticasByExamen(id+"").size()*40+52)+"";
    }
    
    public String numTemas(Long id) { 
        return (ExamenRegistry.getCurrentInstance().getEstadisticasByExamen(id+"").size())+"";
    }
}
