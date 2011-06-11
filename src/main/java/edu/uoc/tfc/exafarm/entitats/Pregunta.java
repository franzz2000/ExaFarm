/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uoc.tfc.exafarm.entitats;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author franzz2000
 */
@Entity
@Table(name="preguntas")
@NamedQueries({
    @NamedQuery(name = "preguntas.findAll", query = "SELECT p FROM Pregunta p"),
    @NamedQuery(name = "preguntas.findById", query = "SELECT p FROM Pregunta p WHERE p.id = :id"),
    @NamedQuery(name = "preguntas.findByExamen", query = "SELECT p FROM Pregunta p WHERE p.examenes = :examen"),
    @NamedQuery(name = "preguntas.findByUsuario", query = "SELECT p FROM Pregunta p WHERE p.usuario = :usuario"),
    @NamedQuery(name = "preguntas.findByExamenAndUser", query = "SELECT P FROM Pregunta p WHERE p.examenes = :examen AND p.usuario = :usuario")
})
@ManagedBean
@RequestScoped
public class Pregunta extends AbstractEntity implements Serializable, Comparable<Pregunta> {
    private static final long serialVersionUID = 1L;
    
    @Column(name="is_activa")
    private Boolean isActiva;
    
    @Column(name="fecha_creacion")
    @Temporal(TemporalType.DATE) 
    private Date fechaCreacion;
    
    @Column(name="texto")
    private String texto;
    
    @Column(name="is_corta")
    private Boolean isCorta;
    
    @Column(name="is_mezclable")
    private Boolean isMezclable;
    
    @ManyToMany(mappedBy="preguntasList")
    private List<Examen> examenes;
    
    @ManyToOne
    @JoinColumn(name="id_usuario")
    private Usuario usuario;
    
    @ManyToOne
    @JoinColumn(name="id_tema")
    private Tema tema;
    
    @OneToMany(mappedBy="pregunta", cascade= CascadeType.ALL)
    private List<Respuesta> respuestas;
    
    public Pregunta(){
        this.id = 0L;
        this.fechaCreacion = new Date();
        this.isActiva = true;
        this.isCorta = false;
        this.isMezclable = false;
    }
    
    public Pregunta(Long id) {
        this.id = id;
    }

    public List<Examen> getExamenes() {
        return examenes;
    }

    public void setExamenes(List<Examen> examenes) {
        this.examenes = examenes;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Boolean getIsActiva() {
        return isActiva;
    }

    public void setIsActiva(Boolean isActiva) {
        this.isActiva = isActiva;
    }

    public Boolean getIsCorta() {
        return isCorta;
    }

    public void setIsCorta(Boolean isCorta) {
        this.isCorta = isCorta;
    }

    public List<Respuesta> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<Respuesta> respuestasList) {
        this.respuestas = respuestasList;
    }

    public Tema getTema() {
        return tema;
    }

    public void setTema(Tema tema) {
        this.tema = tema;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Boolean getIsMezclable() {
        return isMezclable;
    }

    public void setIsMezclable(Boolean isMezclable) {
        this.isMezclable = isMezclable;
    }
    
    public String getCorrecta() {
        String letras[] = {"A", "B", "C", "D", "E", "Ninguna"};
        int resultado=5;
        for (int i=0;i<respuestas.size();i++) {
            Boolean correcta = respuestas.get(i).getIsCorrecta();
            if (correcta) {
                resultado = i;
            }
        }
        return letras[resultado];
    }
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        hash += (texto != null ? texto.hashCode() : 0);
        hash += (isActiva != null ? isActiva.hashCode() : 0);
        hash += (fechaCreacion != null ? fechaCreacion.hashCode() : 0);
        hash += (isCorta != null ? isCorta.hashCode() : 0);
        hash += (usuario != null ? usuario.hashCode() : 0);
        hash += (tema != null ? tema.hashCode() : 0);
//        hash += (respuestas != null ? respuestas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Pregunta)) {
            return false;
        }
        Pregunta other = (Pregunta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        if ((this.isActiva == null && other.isActiva != null) || (this.isActiva != null && !this.isActiva.equals(other.isActiva))) {
            return false;
        }
        if ((this.fechaCreacion == null && other.fechaCreacion != null) || (this.fechaCreacion != null && !this.fechaCreacion.equals(other.fechaCreacion))) {
            return false;
        }
        if ((this.texto == null && other.texto != null) || (this.texto != null && !this.texto.equals(other.texto))) {
            return false;
        }
        if ((this.isCorta == null && other.isCorta != null) || (this.isCorta != null && !this.isCorta.equals(other.isCorta))) {
            return false;
        }
        if ((this.usuario == null && other.usuario != null) || (this.usuario != null && !this.usuario.equals(other.usuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return texto;
    }

    @Override
    public int compareTo(Pregunta pregunta) {
        return this.tema.getBloque().getDescripcion().compareToIgnoreCase(pregunta.tema.getBloque().getDescripcion());
    }

    
}
