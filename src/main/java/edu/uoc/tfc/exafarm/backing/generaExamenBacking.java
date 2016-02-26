/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uoc.tfc.exafarm.backing;

import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import edu.uoc.tfc.exafarm.entitats.Examen;
import edu.uoc.tfc.exafarm.entitats.Pregunta;
import edu.uoc.tfc.exafarm.entitats.accessor.ExamenRegistry;
import edu.uoc.tfc.exafarm.extras.DocumentoPDF;
import edu.uoc.tfc.exafarm.extras.Version;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author franzz2000
 */
@ManagedBean
@ViewScoped
public class generaExamenBacking implements Serializable{
    public static final Font NORMAL = new Font(FontFamily.TIMES_ROMAN, 22);
    List<Version> versiones;
    String examen;
    Examen currentExamen;
    
    public generaExamenBacking() {
        versiones = new ArrayList();
    }
    
    public List<Version> getVersiones() {
        return versiones;
    }
    
    public void setVersiones(List<Version> versiones){
        this.versiones = versiones;
    }
    
    public void createPDF() {
        DocumentoPDF documento = new DocumentoPDF();
        documento.generaPDF();
    }
    
    @PostConstruct
    public void construct() {
        versiones = new ArrayList();
        examen = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("examen");
        currentExamen = ExamenRegistry.getCurrentInstance().getExamenById(examen);
        for (Long i=0L; i<currentExamen.getNumVersiones(); i++) {
            Version version = new Version(currentExamen.getFechaConvocatoria(), new ArrayList<Pregunta>(currentExamen.getPreguntasList()), i+1, currentExamen.getNumVersiones());
            version.mezcla();
            version.ordena();
            versiones.add(version);
        }
    }
}
