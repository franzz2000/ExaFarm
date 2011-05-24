package edu.uoc.tfc.exafarm.entitats;

import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.Column;
import javax.persistence.Entity;
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
    @NamedQuery(name="usuarios.getUsuarioById", query="SELECT u FROM Usuario AS u WHERE u.idUsuario=:id")
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
    private String grupo;
    @Column(name="is_activo")
    private Boolean isActivo;
    @OneToMany(mappedBy = "usuario")
    private List<Pregunta> preguntas;

    public Usuario(String usuarioId, String password, String nombre, String apellidos, String email, Boolean isActivo) {
        this.setIdUsuario(usuarioId);
        this.setPassword(password);
        this.setNombre(nombre);
        this.setApellidos(apellidos);
        this.setEmail(email);
        this.setIsActivo(isActivo);
    }

    public Usuario(){
        id=0L;
        idUsuario = "a";
        password = "a";
        nombre = "a";
        apellidos="a";
        email="a";
        isActivo=true;
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

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupos) {
        this.grupo = grupos;
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
        this.password = password;
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