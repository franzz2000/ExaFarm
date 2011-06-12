package edu.uoc.tfc.exafarm.extras;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import edu.uoc.tfc.exafarm.entitats.Pregunta;
import edu.uoc.tfc.exafarm.entitats.Respuesta;
import java.io.IOException;
import java.text.Format;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 * Genera un examen a partir de los datos de Versión del examen
 * 
 * @author Franz
 */
@ManagedBean
public class DocumentoPDF {
    /* Definición de las columnas para la página 1 y consecutivas
     * Los valores representan las coordenadas de las esquinas inferior izquierda y superior derecha.
     * 1º valor x para esquina inferior izquierda
     * 2º valor y para esquina inferior izquierda
     * 3º valor x para esquina superior derecha
     * 4º valor y para esquina superior derecha
    */
    public static final float[][] COLUMNS = {
          { 36, 36, 286, 750 } , { 319, 36, 559, 750 }
    }; //Columnas para las preguntas de la primera página
    public static final float[][] COLUMNS2 = {
          { 36, 36, 286, 806 } , { 319, 36, 559, 806 }
    }; //Columnas para las preguntas de las páginas consecutivas
    
    //Definición de tipos de letra utilizados en el documento
    public static final Font NORMAL = new Font(FontFamily.HELVETICA, 9); //Fuente por defecto
    public static final Font NORMAL_BOLD = new Font(FontFamily.HELVETICA, 9, Font.BOLD); //Fuente por defecto
    public static final Font TITULO = new Font(FontFamily.HELVETICA, 12, Font.BOLD);
    
    private Version version; //Datos de la versión
    private Document documento;
    private PdfWriter writer;
    private HeaderFooter event;
    
    
    public void generaPDF() {
        Format formatter;
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        version = (Version) ec.getRequestMap().get("lista");
        
        formatter = new SimpleDateFormat("MMMMMyyyy");
        String fecha = formatter.format(version.getFechaExamen());
        String nombreFichero = fecha+"_"+ version.getNumVersion();
        ec.setResponseHeader("Content-Type", "application/pdf");
        ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + nombreFichero + ".pdf" + "\"");
        try {
            documento = new Document();
            try {
                writer = PdfWriter.getInstance(documento, ec.getResponseOutputStream());
                event = new HeaderFooter();
                writer.setPageEvent(event);
                documento.open();
                documento.add(addTitulo()); 
                addPreguntas();
                documento.newPage();
                addPlantilla();
                documento.newPage();
                addResultados();
            documento.close();
            FacesContext.getCurrentInstance().responseComplete();
            } catch (DocumentException ex) {
                Logger.getLogger(DocumentoPDF.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(DocumentoPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Devuelve un párrafo con el título
     * 
     * @return 
     */
    private Paragraph addTitulo() {
        Paragraph texto;
        Format formatter = new SimpleDateFormat("MMMMM yyyy");
        String titulo = Utils.getMessageResourceString("examen", "Titulo")+ " " + formatter.format(version.getFechaExamen());
        texto = new Paragraph(titulo, TITULO);
        return texto;
    }
    
    /**
     * Genera y agrega preguntas al documento
     * @param document 
     */
    private void addPreguntas() throws DocumentException {
        ColumnText ct = new ColumnText(writer.getDirectContent());
        int column = 0;
        ct.setSimpleColumn(COLUMNS[column][0], COLUMNS[column][1], COLUMNS[column][2], COLUMNS[column][3]);
        int status = ColumnText.START_COLUMN;
        float y;
        int i = 1;
        for(Pregunta pregunta:version.getPreguntas()) {
            y = ct.getYLine();
            addPregunta(ct, pregunta, i);
            status = ct.go(true);
            if (ColumnText.hasMoreText(status)) {
                column = (column + 1) % 2;
                if (column==0) {
                    documento.newPage();
                }
                if (event.getPagenumber()==1) {
                    ct.setSimpleColumn(COLUMNS[column][0], COLUMNS[column][1], COLUMNS[column][2], COLUMNS[column][3]);
                    y = COLUMNS[column][3];
                } else {
                    ct.setSimpleColumn(COLUMNS2[column][0], COLUMNS2[column][1], COLUMNS2[column][2], COLUMNS2[column][3]);
                    y = COLUMNS2[column][3];
                }
            }
            ct.setYLine(y);
            ct.setText(null);
            addPregunta(ct, pregunta, i);
            i++;
            status = ct.go();
        }
    }
    
    /**
     * Añade bloque de pregunta/respuesta al documento PDF
     * 
     * @param ct Column text
     * @param pregunta
     * @param i Número de pregunta 
     */
    private void addPregunta(ColumnText ct, Pregunta pregunta, int i) {
        Paragraph preguntaTxt = new Paragraph(i + ") " + pregunta.getTexto().toUpperCase(),NORMAL);
        preguntaTxt.setAlignment(Element.ALIGN_JUSTIFIED);
        preguntaTxt.setSpacingAfter(7);
        ct.addElement(preguntaTxt);
        com.itextpdf.text.List listaRespuestas = new com.itextpdf.text.List(com.itextpdf.text.List.ORDERED, com.itextpdf.text.List.ALPHABETICAL);
        listaRespuestas.setPostSymbol(") ");
        List<Respuesta> respuestas = pregunta.getRespuestas();
        if(pregunta.getIsMezclable()) {
            Random random = new Random(i);
            Collections.shuffle(respuestas, random);
        }           
        for(Respuesta respuesta:respuestas) {
            ListItem respuestaTxt = new ListItem(respuesta.getTexto(),NORMAL);
            respuestaTxt.setAlignment(Element.ALIGN_JUSTIFIED);
            listaRespuestas.add(respuestaTxt);
        }
        ct.addElement(listaRespuestas);
        ct.addElement(Chunk.NEWLINE);
    }
    
    /**
     * Añade la plantilla de respuestas para rellenar por el alumno
     * 
     * @param Documento donde añadir el texto
     */
    private void addPlantilla() {
        String texto;
        Paragraph parrafo;
        try {
            parrafo = addTitulo();
            parrafo.setLeading(10F);
            parrafo.setAlignment(Element.ALIGN_CENTER);
            documento.add(parrafo);
            texto = Utils.getMessageResourceString("examen", "Subtitulo");
            parrafo = new Paragraph(texto, NORMAL);
            parrafo.setAlignment(Element.ALIGN_CENTER);
            parrafo.setSpacingAfter(30);
            documento.add(parrafo);
            texto = Utils.getMessageResourceString("examen", "NombreYApellidos");
            parrafo = new Paragraph(texto, NORMAL);
            parrafo.setSpacingAfter(10);
            documento.add(parrafo);
            texto = Utils.getMessageResourceString("examen", "DniYFirma");
            parrafo = new Paragraph(texto, NORMAL);
            parrafo.setSpacingAfter(10);
            documento.add(parrafo);
            texto = Utils.getMessageResourceString("examen", "NumCorrectas");
            parrafo = new Paragraph(texto, NORMAL);
            parrafo.setSpacingAfter(10);
            documento.add(parrafo);
            texto = Utils.getMessageResourceString("examen", "NumIncorrectas");
            parrafo = new Paragraph(texto, NORMAL);
            parrafo.setSpacingAfter(10);
            documento.add(parrafo);
            texto = Utils.getMessageResourceString("examen", "PuntuacionYPorcentaje");
            parrafo = new Paragraph(texto, NORMAL);
            parrafo.setSpacingAfter(10);
            documento.add(parrafo);
            texto = Utils.getMessageResourceString("examen", "InstruccionesTitulo");
            parrafo = new Paragraph(texto, NORMAL);
            parrafo.setAlignment(Element.ALIGN_CENTER);
            parrafo.setSpacingAfter(10);
            documento.add(parrafo);
            com.itextpdf.text.List listaInstrucciones = new com.itextpdf.text.List(com.itextpdf.text.List.ORDERED, com.itextpdf.text.List.NUMERICAL);
            listaInstrucciones.add(new ListItem(Utils.getMessageResourceString("examen", "Instrucciones1"), NORMAL));
            listaInstrucciones.add(new ListItem(Utils.getMessageResourceString("examen", "Instrucciones2"), NORMAL));
            listaInstrucciones.add(new ListItem(Utils.getMessageResourceString("examen", "Instrucciones3"), NORMAL));
            listaInstrucciones.add(new ListItem(Utils.getMessageResourceString("examen", "Instrucciones4"), NORMAL));
            listaInstrucciones.add(new ListItem(Utils.getMessageResourceString("examen", "Instrucciones5"), NORMAL));
            MessageFormat textoInstrucciones = new MessageFormat(Utils.getMessageResourceString("examen", "Instrucciones6"));
            Object[] args = {new Integer(version.getPreguntas().size()/2)};
            texto = textoInstrucciones.format(args);
            listaInstrucciones.add(new ListItem(texto, NORMAL));
            documento.add(listaInstrucciones);
            texto = Utils.getMessageResourceString("examen", "InstruccionesTitulo");
            parrafo = new Paragraph(texto, NORMAL);
            parrafo.setAlignment(Element.ALIGN_CENTER);
            parrafo.setSpacingAfter(10);
            documento.add(addTable());
        } catch (DocumentException ex) {
            Logger.getLogger(DocumentoPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private PdfPTable addTable() {
        PdfPTable tabla = new PdfPTable(4);
        tabla.setSpacingBefore(20);
        PdfPCell cell;
        Boolean fin=false;
        int numPreguntas=version.getPreguntas().size();
        System.out.println("Número de preguntas: " + numPreguntas);
        System.out.println("Resto: " + numPreguntas%10);
        for(int i=0;i<8;i++) {
            int inicio=i*10+1;
            if((i+1)*10<numPreguntas) {
                cell = new PdfPCell(addTablaBloque(inicio, 10));
                cell.setBorder(PdfPCell.NO_BORDER);
                cell.setPadding(10);
                tabla.addCell(cell);
            } else {
                if(!fin) {
                    cell = new PdfPCell(addTablaBloque(inicio, numPreguntas%10));
                    cell.setBorder(PdfPCell.NO_BORDER);
                    cell.setPadding(10);
                    tabla.addCell(cell);
                    fin=true;
                } else {
                    cell = new PdfPCell(new Phrase(""));
                    cell.setBorder(PdfPCell.NO_BORDER);
                    tabla.addCell(cell);
                }
            }
        }
        return tabla;
    }
    
    private PdfPTable addTablaBloque(int inicio, int cantidad) {
        PdfPTable tabla = new PdfPTable(6);
        PdfPCell cell;
        cell = new PdfPCell(new Paragraph("", NORMAL_BOLD));
        cell.setBorder(PdfPCell.NO_BORDER);
        tabla.addCell(cell);
        cell = new PdfPCell(new Paragraph("A", NORMAL_BOLD));
        cell.setBorder(PdfPCell.NO_BORDER);
        tabla.addCell(cell);
        cell = new PdfPCell(new Paragraph("B", NORMAL_BOLD));
        cell.setBorder(PdfPCell.NO_BORDER);
        tabla.addCell(cell);
        cell = new PdfPCell(new Paragraph("C", NORMAL_BOLD));
        cell.setBorder(PdfPCell.NO_BORDER);
        tabla.addCell(cell);
        cell = new PdfPCell(new Paragraph("D", NORMAL_BOLD));
        cell.setBorder(PdfPCell.NO_BORDER);
        tabla.addCell(cell);
        cell = new PdfPCell(new Paragraph("E", NORMAL_BOLD));
        cell.setBorder(PdfPCell.NO_BORDER);
        tabla.addCell(cell);
        for(int i=0;i<cantidad;i++) {
            cell = new PdfPCell(new Phrase(Integer.toString(inicio+i),NORMAL));
            cell.setBorder(PdfPCell.NO_BORDER);
            tabla.addCell(cell);
            cell = new PdfPCell(new Phrase("0", NORMAL));
            cell.setBorder(PdfPCell.NO_BORDER);
            for(int t=0;t<5;t++) {
                tabla.addCell(cell);
            }
        }
        return tabla;
    }
    
    private void addResultados() throws DocumentException {
        Paragraph texto;
        PdfPCell cell;
        
        texto = new Paragraph(Utils.getMessageResourceString("examen", "RespuestasTitulo") + version.getNumVersion(), TITULO);
        texto.setSpacingAfter(20);
        documento.add(texto);
        int i = 1;
        PdfPTable tabla = new PdfPTable(5);
//        tabla.setWidthPercentage(50F);
        tabla.setWidths(new float[] {.2F,.5F,.5F,1,2});
        tabla.getDefaultCell().setBackgroundColor(BaseColor.LIGHT_GRAY);
        tabla.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        texto = new Paragraph(Utils.getMessageResourceString("examen", "NumPregunta"),NORMAL);
        tabla.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
        tabla.addCell(texto);
        texto = new Paragraph(Utils.getMessageResourceString("examen", "Correcta"),NORMAL);
        tabla.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        tabla.addCell(texto);
        texto = new Paragraph(Utils.getMessageResourceString("examen", "IdPregunta"), NORMAL);
        tabla.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(texto);
        texto = new Paragraph(Utils.getMessageResourceString("examen", "Bloque"), NORMAL);
        tabla.addCell(texto);
        texto = new Paragraph(Utils.getMessageResourceString("examen", "Profesor"), NORMAL);
        tabla.addCell(texto);
        tabla.getDefaultCell().setBackgroundColor(null);
        tabla.setHeaderRows(1);
        tabla.getDefaultCell().setBorder(PdfPCell.BOTTOM);
        for(Pregunta pregunta:version.getPreguntas()) {
            //Número de pregunta
            texto = new Paragraph(Integer.toString(i), NORMAL);
            tabla.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
            tabla.addCell(texto);
            //Respuesta correcta
            texto = new Paragraph(pregunta.getCorrecta(), NORMAL);
            tabla.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
            tabla.addCell(texto);
            //PreguntaId
            texto = new Paragraph(Long.toString(pregunta.getId()), NORMAL);
            tabla.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            tabla.addCell(texto);
            //Bloque de pregunta
            texto = new Paragraph(pregunta.getTema().getBloque().getDescripcion(), NORMAL);
            tabla.addCell(texto);
            //Profesor
            texto = new Paragraph(pregunta.getUsuario().getNombre() +" "+pregunta.getUsuario().getApellidos(), NORMAL);
            tabla.addCell(texto);
            i++;
        }
        documento.add(tabla);
    }       
    
    
    /** Inner class to add a header and a footer. */
    class HeaderFooter extends PdfPageEventHelper {
        /** Current page number (will be reset for every chapter). */
        int pagenumber;
        
        /**
         * Retorna el número de página
         * 
         * @return Númeron de página 
         */
        public int getPagenumber() {
            return pagenumber;
        }
        
        /**
         * Initialize one of the headers.
         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onOpenDocument(
         *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
         */
        @Override
        public void onOpenDocument(PdfWriter writer, Document document) {
        }
        

        /**
         * Increase the page number.
         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onStartPage(
         *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
         */
        @Override
        public void onStartPage(PdfWriter writer, Document document) {
            pagenumber++;
        }
        
        /**
         * Adds the header and the footer.
         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onEndPage(
         *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
         */
        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            Rectangle rect = writer.getPageSize();
            switch(writer.getPageNumber()) {
            case 1:
                break;
            default:
                ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT,
                        new Phrase(String.format("v.%d", version.getNumVersion()),NORMAL),
                        rect.getRight()-35, rect.getBottom()+18, 0);
                break;
            }
            String pagina = Utils.getMessageResourceString("examen", "Pagina");
            ColumnText.showTextAligned(writer.getDirectContent(),
            Element.ALIGN_CENTER, new Phrase(String.format("%s %d", pagina, pagenumber),NORMAL),
            (rect.getLeft() + rect.getRight()) / 2, rect.getBottom() + 18, 0);
        }
    }
}
