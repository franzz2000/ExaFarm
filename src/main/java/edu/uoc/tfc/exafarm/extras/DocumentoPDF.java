package edu.uoc.tfc.exafarm.extras;

import com.itextpdf.text.*;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import edu.uoc.tfc.exafarm.entitats.Pregunta;
import edu.uoc.tfc.exafarm.entitats.Respuesta;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.Format;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 * Genera un examen en PDF a partir de los datos de Versión del examen
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

/*  43 */   public static final float[][] COLUMNS = { { 36.0F, 36.0F, 286.0F, 750.0F }, { 319.0F, 36.0F, 559.0F, 750.0F } };//Columnas para las preguntas de la primera página
   /*  46 */   public static final float[][] COLUMNS2 = { { 36.0F, 36.0F, 286.0F, 806.0F }, { 319.0F, 36.0F, 559.0F, 806.0F } };//Columnas para las preguntas de las páginas consecutivas
    
    //Definición de tipos de letra utilizados en el documento
   public static final String FONT = "resources/fuentes/ionicons.ttf";
   public static final float TAMANO_FUENTE = 10.0F;
   public static final String CIRCULO_BLANCO = "\uf153";//"◯";// White circle \u25cb
   public static final String CIRCULO_NEGRO = "\uf158";//"⬤";// Black circle \u25cf
   public static final Font NORMAL = new Font(Font.FontFamily.HELVETICA, 9.0F, Font.NORMAL);//Fuente por defecto
   public static final Font INSTRUCCIONES = new Font(Font.FontFamily.HELVETICA, 7.0F, Font.NORMAL);
   public static final Font NORMAL_BOLD = new Font(Font.FontFamily.HELVETICA, 10.0F, Font.BOLD);
   public static final Font TITULO = new Font(FontFamily.HELVETICA, 12, Font.BOLD);
   public static final Font CRUZ = new Font(FontFamily.HELVETICA, 10, Font.NORMAL);
   public static final String CIRCULO_0 = "\uf138";
   public static final String CIRCULO_1 = "\uf13b";
   public static final String CIRCULO_2 = "\uf13c";
   public static final String CIRCULO_3 = "\uf145";
   public static final String CIRCULO_4 = "\uf146";
   public static final String CIRCULO_5 = "\uf14c";
   public static final String CIRCULO_6 = "\uf14d";
   public static final String CIRCULO_7 = "\uf14e";
   public static final String CIRCULO_8 = "\uf14f";
   public static final String CIRCULO_9 = "\uf150";
   public static final String CIRCULO_A = "\uf151";
   public static final String CIRCULO_B = "\uf152";
   public static final String CIRCULO_C = "\uf154";
   public static final String CIRCULO_D = "\uf155";
   public static final String CIRCULO_E = "\uf156";
   public static final String CIRCULO_F = "\uf157";


    private Version version; //Datos de la versión
    private Document documento;
    private PdfWriter writer;
    private HeaderFooter event;
    private String bloque;
    private Rectangle rect;
    private BaseFont bf;
    private Font fuenteCirculoGris;
    private Font fuenteCirculoNegro;
    
    /**
     * Método principal para generar el documento PDF
     */
    public void generaPDF() {
        Format formatter;
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        version = (Version) ec.getRequestMap().get("lista");
        try {
            this.bf = BaseFont.createFont(ec.getRealPath(FONT), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            fuenteCirculoGris = new Font(this.bf, 13.0F);
            fuenteCirculoGris.setColor(BaseColor.LIGHT_GRAY);
            fuenteCirculoNegro = new Font(this.bf, 13.0F);            
        } catch (IOException e) {
            System.out.println("Imposible leer la fuente");
            Logger.getLogger(DocumentoPDF.class.getName()).log(Level.SEVERE, null, e);
        } catch (DocumentException ex) {
            System.out.println("La fuente es inválida");
            Logger.getLogger(DocumentoPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        formatter = new SimpleDateFormat("MMMMMyyyy", new Locale("es_ES"));
        String fecha = formatter.format(version.getFechaExamen());
        String nombreFichero = fecha+"_"+ version.getNumVersion();
        ec.setResponseHeader("Content-Type", "application/pdf");
        ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + nombreFichero + ".pdf" + "\"");
        try {
            documento = new Document();
            try {
		bloque = "preguntas";
                writer = PdfWriter.getInstance(documento, ec.getResponseOutputStream());
                rect = writer.getPageSize();
                event = new HeaderFooter();
                writer.setPageEvent(event);
                documento.open();
                documento.add(addTitulo()); 
                addPreguntas();
                documento.newPage();

                if(writer.getPageNumber()%2==0) {
                    writer.setPageEmpty(false);
                    documento.newPage();
                }

		bloque = "plantilla";
                addPlantilla(false);
                documento.newPage();
		bloque = "resultados";
                addResultados();
                documento.newPage();
                addPlantilla(true);
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
     * Devuelve un párrafo con el título del examen
     * 
     * @return 
     */
    private Paragraph addTitulo() {
        Format formatter = new SimpleDateFormat("MMMMM yyyy", new Locale("es", "ES"));
        String titulo = Utils.getMessageResourceString("textoexamen", "Titulo")+ " - " + formatter.format(version.getFechaExamen());
        Paragraph texto = new Paragraph(titulo, TITULO);
        return texto;
    }
    
    /**
     * Devuelve una tabla con los círculos para el DNI
     * 
     * @return 
     */
    private PdfPTable addDNI(Boolean maestra) {
        PdfPTable tabla = new PdfPTable(11);
        String numerosTxt[] = {"", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
	String numerosCirculo[] = {CIRCULO_0, CIRCULO_1, CIRCULO_2, CIRCULO_3, CIRCULO_4, CIRCULO_5, CIRCULO_6, CIRCULO_7, CIRCULO_8, CIRCULO_9};  
        //Añade a la tabla los números en la primera fila
        for (String numero : numerosTxt) {
            PdfPCell cell = new PdfPCell(new Phrase(numero, NORMAL_BOLD));
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            tabla.addCell(cell);
        }

        for(int i=0;i<8;i++) { //Cantidad de números del DNI
            //Inserta el recuadro para que se incluya el número de DNI 
            PdfPCell cell = new PdfPCell(new Phrase(" ",NORMAL));
            //cell.setBorder(PdfPCell.NO_BORDER);
            tabla.addCell(cell);
            //Inserta los círculos
	    for (int t=0; t<10; t++) {
                if ((maestra) && (t==0)) {
                        cell = new PdfPCell(new Phrase(CIRCULO_NEGRO, fuenteCirculoNegro));
                } else {
                        cell = new PdfPCell(new Phrase(numerosCirculo[t], fuenteCirculoGris));
                }
                cell.setHorizontalAlignment(1);
	    	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBorder(0);
                tabla.addCell(cell);
	    }
        }
        return tabla;
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
        int i = 1;
        for(Pregunta pregunta:version.getPreguntas()) {
            float y = ct.getYLine();
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
        preguntaTxt.setAlignment(Element.ALIGN_JUSTIFIED);// 3
        preguntaTxt.setSpacingAfter(7.0F);
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
            respuestaTxt.setAlignment(Element.ALIGN_JUSTIFIED);// 3
            listaRespuestas.add(respuestaTxt);
        }
        ct.addElement(listaRespuestas);
        ct.addElement(Chunk.NEWLINE);
    }
    
    /**
     * Añade la plantilla de respuestas
     * 
     * @param maestra True si se quiere que marque las respuestas correctas, false si se trata de la plantilla para dar a los estudiantes
     */
    private void addPlantilla(Boolean maestra) {
        long numVersion=version.getNumVersion();
        try {
            Paragraph parrafo = addTitulo();
            //parrafo.setLeading(10F);
            parrafo.setAlignment(Element.ALIGN_CENTER);// 1
            documento.add(parrafo);
            String texto = Utils.getMessageResourceString("textoexamen", "Subtitulo");
            if(maestra) texto = texto + " Maestra";
            parrafo = new Paragraph(texto, NORMAL);
            parrafo.setAlignment(Element.ALIGN_CENTER);// 1
            parrafo.setSpacingAfter(10);
            documento.add(parrafo);
            PdfPCell izquierda = new PdfPCell();
            izquierda.setBorder(PdfPCell.NO_BORDER);// 0
            izquierda.setVerticalAlignment(Element.ALIGN_MIDDLE);// 5
            //Datos del estudiante (los meto en una Lista
            texto = Utils.getMessageResourceString("textoexamen", "NombreYApellidos");
            parrafo = new Paragraph(texto, NORMAL);
            parrafo.setSpacingAfter(10);
            izquierda.addElement(parrafo);
            texto = Utils.getMessageResourceString("textoexamen", "DniYFirma");
            parrafo = new Paragraph(texto, NORMAL);
            parrafo.setSpacingAfter(10);
            izquierda.addElement(parrafo);
            texto = Utils.getMessageResourceString("textoexamen", "NumRespuestas");
            parrafo = new Paragraph(texto, NORMAL);
            parrafo.setSpacingAfter(10);
            izquierda.addElement(parrafo);
            texto = Utils.getMessageResourceString("textoexamen", "PuntuacionYPorcentaje");
            parrafo = new Paragraph(texto, NORMAL);
            parrafo.setSpacingAfter(10);
            izquierda.addElement(parrafo);
            parrafo = new Paragraph(new Chunk("Versión: ", NORMAL));
            for(int i = 1; i<=version.getNumVersiones();i++) {
                if (i==numVersion) {
                    parrafo.add(new Chunk(CIRCULO_NEGRO, fuenteCirculoNegro));
		    parrafo.add(new Chunk(" ", NORMAL));
                } else {
                    parrafo.add(new Chunk(CIRCULO_BLANCO, fuenteCirculoNegro));
		    parrafo.add(new Chunk(" ", NORMAL));
                }
            }
            //Phrase frase = new Phrase(texto, new Font(this.bf, 12));
            //parrafo.add(frase);
            parrafo.setSpacingAfter(10);
            izquierda.addElement(parrafo);

            PdfPTable tabla = new PdfPTable(2);
            tabla.setWidthPercentage(100);
            tabla.setWidths(new int[]{12,10});
            PdfPCell derecha = new PdfPCell();
            derecha.addElement(new Phrase("DNI"));
            derecha.addElement(addDNI(maestra));
            derecha.setBorder(PdfPCell.NO_BORDER);// 0
            derecha.setPaddingRight(50.0F);
            tabla.addCell(izquierda);
            tabla.addCell(derecha);
            documento.add(tabla);

            //Comienzan las instrucciones
            texto = Utils.getMessageResourceString("textoexamen", "InstruccionesTitulo");
            parrafo = new Paragraph(texto, NORMAL);
            parrafo.setAlignment(Element.ALIGN_CENTER);// 1
            parrafo.setSpacingAfter(10);
            documento.add(parrafo);
            com.itextpdf.text.List listaInstrucciones = new com.itextpdf.text.List(com.itextpdf.text.List.ORDERED, com.itextpdf.text.List.NUMERICAL);// true, false
            int numeroInstrucciones = Integer.parseInt(Utils.getMessageResourceString("textoexamen", "NumInstrucciones"));
            for (int i = 1; i < numeroInstrucciones+1; i++) {
                listaInstrucciones.add(new ListItem(Utils.getMessageResourceString("textoexamen", "Instrucciones"+i), INSTRUCCIONES));
            }
            MessageFormat textoInstrucciones = new MessageFormat(Utils.getMessageResourceString("textoexamen", "Instrucciones7"));
            Object[] args = {(float) version.getPreguntas().size()/2}; //Calcula la puntuación del aprobado (50%)
            texto = textoInstrucciones.format(args);
            listaInstrucciones.add(new ListItem(texto, INSTRUCCIONES));
            documento.add(listaInstrucciones);
            texto = Utils.getMessageResourceString("textoexamen", "InstruccionesTitulo");
            parrafo = new Paragraph(texto, NORMAL);
            parrafo.setAlignment(Element.ALIGN_CENTER);// 1
            parrafo.setSpacingAfter(10);
            documento.add(addTable(maestra));
            //Círculos en la maestra
            float porcentaje;
            porcentaje = 25f;
            float margen = 10f;
            URL resourceURL = Thread.currentThread().getContextClassLoader().getResource("images/circulo.png");
            Image imagen = Image.getInstance(resourceURL);
            float desplazamiento = imagen.getWidth()*porcentaje/100; //Tamaño de la imagen
            imagen.scalePercent(porcentaje);
            //Superior izquierda x,y
            imagen.setAbsolutePosition(rect.getLeft(margen), rect.getTop(margen)-desplazamiento);
            documento.add(imagen);
            //Superior derecha
            imagen.setAbsolutePosition(rect.getRight(margen)-desplazamiento, rect.getTop(margen)-desplazamiento);
            documento.add(imagen);
            //Inferior izquierda
            imagen.setAbsolutePosition(rect.getLeft(margen), rect.getBottom(margen));
            documento.add(imagen);
            //Inferior derecha
            imagen.setAbsolutePosition(rect.getRight(margen)-desplazamiento, rect.getBottom(margen));
            documento.add(imagen);
        } catch (DocumentException ex) {
            Logger.getLogger(DocumentoPDF.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(DocumentoPDF.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DocumentoPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Añade la tabla de respuestas para que marque el estudiante. Dividida en bloques de 10 preguntas
     * 
     * @return 
     */
    private PdfPTable addTable(Boolean maestra) {
        PdfPTable tabla = new PdfPTable(4);
        tabla.setSpacingBefore(20);
        int numPreguntas=version.getPreguntas().size();
        int grupos = numPreguntas/10;
        //Genera bloques de 10 respuestas
        for(int i=0; i<grupos;i++){
            int inicio=i*10+1;
            PdfPCell cell = new PdfPCell(addTablaBloque(inicio, 10, maestra));
            cell.setBorder(PdfPCell.NO_BORDER); // 0 
            cell.setPadding(10);
            tabla.addCell(cell);
        }
        //Genera el bloque con menos de 10 respuestas
        if (numPreguntas%10 != 0){
            int inicio = grupos * 10 + 1;
            PdfPCell cell = new PdfPCell(addTablaBloque(inicio, numPreguntas % 10, maestra));
            cell.setBorder(PdfPCell.NO_BORDER); // 0
            cell.setPadding(10);
            tabla.addCell(cell);
        }
        //Rellena la tabla con celdas en blanco
        for (int i=0;i<4-((grupos+1)%4);i++) {
            PdfPCell cell = new PdfPCell(new Phrase(""));
            cell.setBorder(PdfPCell.NO_BORDER);
            tabla.addCell(cell);
        }
        return tabla;
    }
    
    /**
     * Añade una columna de respuestas con círculos.
     * 
     * @param inicio
     * @param cantidad
     * @return 
     */
    private PdfPTable addTablaBloque(int inicio, int cantidad, Boolean maestra) {
	//Genero un array con círculos de letras
	String[] letrasCirculo = {CIRCULO_A, CIRCULO_B, CIRCULO_C, CIRCULO_D, CIRCULO_E};  
	PdfPCell[] cellLetra = new PdfPCell[letrasCirculo.length];
	for(int i = 0; i<letrasCirculo.length; i++) {
		cellLetra[i] = new PdfPCell(new Phrase(letrasCirculo[i], fuenteCirculoGris));
		cellLetra[i].setBorder(PdfPCell.NO_BORDER);//0
	}
	PdfPCell cellCorrecta = new PdfPCell(new Phrase(CIRCULO_NEGRO, fuenteCirculoNegro));
        cellCorrecta.setBorder(PdfPCell.NO_BORDER); // 0

	//Nueva tabla
        PdfPTable tabla = new PdfPTable(6);
	String[] letras = {"", "A", "B", "C", "D", "E"};
        PdfPCell cell;
	for (String letra : letras) {
		cell = new PdfPCell(new Paragraph(letra, NORMAL_BOLD));
		cell.setBorder(PdfPCell.NO_BORDER); // 0
		tabla.addCell(cell);
	}
        java.util.List preguntas = version.getPreguntas();

        for(int i=0;i<cantidad;i++) {
            //Inserta el número de la pregunta
            cell = new PdfPCell(new Phrase(Integer.toString(inicio+i),NORMAL));
            cell.setBorder(PdfPCell.NO_BORDER); // 0
	    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            tabla.addCell(cell);
            //Inserta los círculos
	    //Genera círculos con las letras A-E
            if(!maestra) {
                for(int t = 0; t < 5; t++) {
                    tabla.addCell(cellLetra[t]);
                }
            } else {
                Integer correcta = ((Pregunta) preguntas.get(inicio+i-1)).getNumCorrecta();
                for(int t=0;t<5;t++) {
                    if (t==correcta)
                        tabla.addCell(cellCorrecta);
                    else
                        tabla.addCell(cellLetra[t]);
                }
            }
        }
	for(int i=0; i<10-cantidad; i++) {
		for(int t=0; t<5; t++) {
			PdfPCell celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(PdfPCell.NO_BORDER);
			tabla.addCell(celda);
		}
	};
        return tabla;
    }
    
    /**
     * Método que añade una hoja con las respuestas correctas del examen. Genera una tabla con el número 
     * correcto de pregunta, el tema y el profesor que ha hecho la pregunta.
     * 
     * @throws DocumentException 
     */
    private void addResultados() throws DocumentException {
        Paragraph texto;
        PdfPCell cell;
        
        documento.add(addTitulo());
        texto = new Paragraph(Utils.getMessageResourceString("textoexamen", "RespuestasTitulo") +" "+ version.getNumVersion(), TITULO);
        texto.setSpacingAfter(20);
        documento.add(texto);
        int i = 1;
        PdfPTable tabla = new PdfPTable(5);
//        tabla.setWidthPercentage(50F);
        tabla.setWidths(new float[] {.2F,.5F,.5F,1,2});
        tabla.getDefaultCell().setBackgroundColor(BaseColor.LIGHT_GRAY);
        tabla.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        texto = new Paragraph(Utils.getMessageResourceString("textoexamen", "NumPregunta"),NORMAL);
        tabla.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
        tabla.addCell(texto);
        texto = new Paragraph(Utils.getMessageResourceString("textoexamen", "Correcta"),NORMAL);
        tabla.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        tabla.addCell(texto);
        texto = new Paragraph(Utils.getMessageResourceString("textoexamen", "IdPregunta"), NORMAL);
        tabla.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(texto);
        texto = new Paragraph(Utils.getMessageResourceString("textoexamen", "Bloque"), NORMAL);
        tabla.addCell(texto);
        texto = new Paragraph(Utils.getMessageResourceString("textoexamen", "Profesor"), NORMAL);
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

	HeaderFooter(){

	};
        
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
		int posicionY = 22;
		if((DocumentoPDF.this.bloque.equals("plantilla"))|| (DocumentoPDF.this.bloque.equals("maestra"))) {
			posicionY = 43;
		}
            //Rectangle rect = writer.getPageSize();
            switch(writer.getPageNumber()) {
            case 1:
                break;
            default:
                ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT,
                        new Phrase(String.format("v.%d", version.getNumVersion()),DocumentoPDF.NORMAL),
                        rect.getRight()-105, rect.getBottom()+posicionY, 0);
                break;
            }
            String pagina = Utils.getMessageResourceString("textoexamen", "Pagina");
            ColumnText.showTextAligned(writer.getDirectContent(),
            Element.ALIGN_CENTER, new Phrase(String.format("%s %d", pagina, pagenumber),NORMAL),
            (rect.getLeft() + rect.getRight()) / 2, rect.getBottom() + posicionY, 0);
        }
    }
}
