/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uoc.tfc.exafarm.backing;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import edu.uoc.tfc.exafarm.entitats.Examen;
import edu.uoc.tfc.exafarm.entitats.Pregunta;
import edu.uoc.tfc.exafarm.entitats.accessor.ExamenRegistry;
import edu.uoc.tfc.exafarm.extras.Utils;
import edu.uoc.tfc.exafarm.extras.Version;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author franzz2000
 */
@ManagedBean
@ViewScoped
public class generaExamenBacking implements Serializable{
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
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        Version version = (Version) ec.getRequestMap().get("lista");
        String nombreFichero = currentExamen.getConvocatoria()+"-v"+version.getVersion();
        ec.setResponseHeader("Content-Type", "application/pdf");
        ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + nombreFichero + ".pdf" + "\"");
        try {
            Document document = new Document();
            try {
                PdfWriter writer = PdfWriter.getInstance(document, ec.getResponseOutputStream());
                document.open();
                Paragraph texto = new Paragraph(Utils.getMessageResourceString("bundle", "GeneraExamenTituloExamen")+" "+currentExamen.getDescripcion());
                texto.setLeading(-18F);
                document.add(texto); 
                version.buildPDFContent(document, writer);
            document.close();
            FacesContext.getCurrentInstance().responseComplete();
            } catch (DocumentException ex) {
                Logger.getLogger(generaExamenBacking.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(generaExamenBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @PostConstruct
    public void construct() {
        versiones = new ArrayList();
        examen = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("examen");
        currentExamen = ExamenRegistry.getCurrentInstance().getExamenById(examen);
        for (Long i=0L; i<currentExamen.getNumVersiones(); i++) {
            Version version = new Version(Utils.getMessageResourceString("bundle", "GeneraExamenVersion")+" "+(i+1), new ArrayList<Pregunta>(currentExamen.getPreguntasList()), i+1);
            version.mezcla(i);
            version.ordena();
            versiones.add(version);
        }
    }
}
