package edu.uoc.tfc.exafarm.entitats;

import com.sun.org.apache.xml.internal.security.utils.Base64;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author franzz2000
 * Clase entidad de Usuario
 *
 */
@Entity
@Table (name="usuarios")
@NamedQueries ({
    @NamedQuery(name="usuarios.findAll", query="SELECT u FROM Usuario AS u"),
    @NamedQuery(name="usuarios.getUsuarioByIdUsuario", query="SELECT u FROM Usuario AS u WHERE u.idUsuario=:id"),
    @NamedQuery(name="usuarios.getUsuarioById", query="SELeCT u FROM Usuario AS u WHERE u.id =:id"),
    @NamedQuery(name="usuarios.findActivos", query="SELECT u FROM Usuario AS u WHERE u.isActivo = true"),
    @NamedQuery(name="usuarios.findAdministradores", query="SELECT u FROM Usuario AS u WHERE u.grupo.idGrupo = 'admin'")
})
@ManagedBean
@RequestScoped
public class Usuario extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name="user_id")
    private String idUsuario;
    private String password;
    private String nombre;
    private String apellidos;
    private String email;
    @ManyToOne
    @JoinColumn(name="grupo")
    private Grupo grupo;
    @Column(name="is_activo")
    private Boolean isActivo;
    @OneToMany(mappedBy = "usuario", cascade= CascadeType.ALL)
    private List<Pregunta> preguntas;

    public Usuario(String usuarioId, String password, String nombre, String apellidos, String email, Boolean isActivo, Grupo grupo) {
        this.setIdUsuario(usuarioId);
        this.setPassword(password);
        this.setNombre(nombre);
        this.setApellidos(apellidos);
        this.setEmail(email);
        this.setIsActivo(isActivo);
        this.setGrupo(grupo);
    }

    public Usuario(){
        grupo = new Grupo();
        preguntas = Collections.emptyList();
    }

    private Principal getLoggedInUser() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return request.getUserPrincipal();
    }
    
    public boolean isUserNotLogin() {
        Principal loginUser = getLoggedInUser();
        if (loginUser == null) {
            return true;
        }
        return false;
    }
    
    public boolean isUsuarioIsAdministrador() {
        FacesContext context = FacesContext.getCurrentInstance();
        Object request = context.getExternalContext().getRequest();
        boolean result = false;
        if (request instanceof HttpServletRequest) {
            result = ((HttpServletRequest)request).isUserInRole("ADMINISTRADOR");
        }
        return result;
    }
    
    public boolean isUsuarioIsCoordinador() {
        FacesContext context = FacesContext.getCurrentInstance();
        Object request = context.getExternalContext().getRequest();
        boolean result = false;
        if (request instanceof HttpServletRequest) {
            result = ((HttpServletRequest)request).isUserInRole("COORDINADOR");
        }
        return result;
    }

    public String getLoginUserName() {
        Principal loginUser = getLoggedInUser();
        if (loginUser != null) {
            return loginUser.getName();
        }
        return "Not logged in";
    }
    
    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if(!this.password.equals(password)) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                md.update(password.getBytes("UTF-8"));
                byte[] byteData = md.digest();

                this.password = Base64.encode(byteData);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public List<Pregunta> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(List<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }

    public Boolean getIsActivo() {
        return isActivo;
    }

    public void setIsActivo(Boolean isActivo) {
        this.isActivo = isActivo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + (this.nombre != null ? nombre.hashCode() : 0);
        hash = 73 * hash + (this.password != null ? password.hashCode():0);
        hash = 73 * hash + (this.apellidos != null ? apellidos.hashCode():0);
        hash = 73 * hash + (this.email != null ? email.hashCode():0);
        hash = 73 * hash + (this.grupo != null ? grupo.hashCode():0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.password == null && other.password != null) || (this.password != null && !this.password.equals(other.password))) {
            return false;
        }
        if ((this.nombre == null && other.nombre != null) || (this.nombre != null && !this.nombre.equals(other.nombre))) {
            return false;
        }
        if ((this.apellidos == null && other.apellidos != null) || (this.apellidos != null && !this.apellidos.equals(other.apellidos))) {
            return false;
        }
        if ((this.email == null && other.email != null) || (this.email != null && !this.email.equals(other.email))) {
            return false;
        }
        if ((this.grupo == null && other.grupo != null) || (this.grupo != null && !this.grupo.equals(other.grupo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getNombre() + " " + getApellidos();
    }

}