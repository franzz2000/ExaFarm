package edu.uoc.tfc.exafarm.entitats.accessor;

import edu.uoc.tfc.exafarm.entitats.Usuario;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author franzz2000
 */
@ManagedBean(eager = true)
@ApplicationScoped
public class UsuarioRegistry extends AbstractEntityAccessor implements Serializable {

// <editor-fold defaultstate="collapsed" desc="Accessing and initializing the instance">
    public static UsuarioRegistry getCurrentInstance() {
        UsuarioRegistry result = null;
        Map<String, Object> appMap = FacesContext.getCurrentInstance().getExternalContext().getApplicationMap();
        result = (UsuarioRegistry) appMap.get("usuarioRegistry");

        return result;
    }

    @PostConstruct
    public void perApplicationConstructor() {
        try {
            doInTransaction(new PersistenceActionWithoutResult() {

                @Override
                public void execute(EntityManager em) {
                    Query query = em.createNamedQuery("usuarios.getAll");
                    List<Usuario> results = query.getResultList();
                    if(results.isEmpty()) {
                        populateUsers(em);
                        query = em.createNamedQuery("usuarios.getAll");
                        results = query.getResultList();
                        assert(!results.isEmpty());
                    }
                }
            });
        } catch (EntityAccessorException ex) {
            Logger.getLogger(UsuarioRegistry.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void populateUsers(EntityManager em) {
    }
 // </editor-fold>
    
// <editor-fold defaultstate="collapsed" desc="Reading Usuario instances">
    public Usuario getUsuarioByIdUsuario (final String idUsuario) {
        Usuario result = null;
        try {
            result = doInTransaction(new PersistenceAction<Usuario>() {

                public Usuario execute(EntityManager em) {
                    Query query = em.createNamedQuery("usuarios.getUsuarioById");
                    query.setParameter("id", idUsuario);
                    List<Usuario> results = query.getResultList();
                    return results.get(0);
                }
            });
        } catch (EntityAccessorException ex) {
            Logger.getLogger(UsuarioRegistry.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public void addUsuario(final Usuario toAdd) throws EntityAccessorException {
        doInTransaction(new PersistenceActionWithoutResult() {

            @Override
            public void execute(EntityManager em) {
                em.merge(toAdd);
            }
        });
    }

    public void updateUsuario(final Usuario toUpdate) throws EntityAccessorException {
        doInTransaction(new PersistenceActionWithoutResult() {

            @Override
            public void execute(EntityManager em) {
                em.merge(toUpdate);
            }
        });
    }
    
    public List<Usuario> getUserList() {
        List<Usuario> result = Collections.emptyList();
        try {
            result = doInTransaction(new PersistenceAction<List<Usuario>>() {

                public List<Usuario> execute(EntityManager em) {
                    Query query = em.createNamedQuery("usuarios.findAll");
                    List<Usuario> results = query.getResultList();
                    return results;
                }
            });
        } catch (EntityAccessorException ex) {
            Logger.getLogger(UsuarioRegistry.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }
//</editor-fold>

}