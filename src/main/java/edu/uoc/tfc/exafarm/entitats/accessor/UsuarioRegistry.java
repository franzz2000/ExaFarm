package edu.uoc.tfc.exafarm.entitats.accessor;

import edu.uoc.tfc.exafarm.entitats.Grupo;
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
import javax.persistence.NoResultException;
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
 // </editor-fold>
    
// <editor-fold defaultstate="collapsed" desc="Writing Usuario instances">
    public void addUsuario(final Usuario toAdd) throws EntityAccessorException {
        doInTransaction(new PersistenceActionWithoutResult() {

            @Override
            public void execute(EntityManager em) {
                em.persist(toAdd);
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
    
    public void removeUsuario(final Usuario toRemove) throws EntityAccessorException {
        doInTransaction(new PersistenceActionWithoutResult() {

            @Override
            public void execute(EntityManager em) {
                em.remove(toRemove);
            }
        });
    }
    
   
//</editor-fold>

// <editor-fold defaultstate="collapsed" desc="Reading Usuario instances">
    public List<Usuario> getUsuarioListActivo() {
        List<Usuario> result = Collections.emptyList();
        try {
            result = doInTransaction(new PersistenceAction<List<Usuario>>() {
                public List<Usuario> execute (EntityManager em) {
                    Query query = em.createNamedQuery("usuarios.findActivos");
                    List<Usuario> results = query.getResultList();
                    return results;
                }
            });
        } catch (EntityAccessorException ex) {
            Logger.getLogger(UsuarioRegistry.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public List<Usuario> getUsuariosAdmin() {
        List<Usuario> result = Collections.emptyList();
        try {
            result = doInTransaction(new PersistenceAction<List<Usuario>>() {
                public List<Usuario> execute (EntityManager em) {
                    Query query = em.createNamedQuery("usuarios.findAdministradores");
                    List<Usuario> results = query.getResultList();
                    return results;
                }
        });
        } catch (EntityAccessorException ex) {
            Logger.getLogger(UsuarioRegistry.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public Usuario getUsuarioById (final Long id) {
        Usuario result = null;
        try {
            result = doInTransaction(new PersistenceAction<Usuario>() {

                @Override
                public Usuario execute(EntityManager em) {
                    Query query = em.createNamedQuery("usuarios.getUsuarioById");
                    query.setParameter("id", id);
                    List<Usuario> results = query.getResultList();
                    return results.get(0);
                }
            });
        } catch (EntityAccessorException ex) {
            Logger.getLogger(UsuarioRegistry.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public Usuario getUsuarioByIdUsuario (final String idUsuario) {
        Usuario result = null;
        try {
            result = doInTransaction(new PersistenceAction<Usuario>() {

                @Override
                public Usuario execute(EntityManager em) {
                    Query query = em.createNamedQuery("usuarios.getUsuarioByIdUsuario");
                    query.setParameter("id", idUsuario);
                    Usuario result = (Usuario) query.getSingleResult();
                        return result;
                }
            });
        } catch (EntityAccessorException ex) {
            Logger.getLogger(UsuarioRegistry.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoResultException ex) {
            return null;
        }
        return result;
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
    
// <editor-fold defaultstate="collapsed" desc="Writing Grupo instances">
    
    public void addGrupo(final Grupo toAdd) throws EntityAccessorException {
        doInTransaction(new PersistenceActionWithoutResult() {

            @Override
            public void execute(EntityManager em) {
                em.persist(toAdd);
            }
        });
    }

    public void updateGrupo(final Grupo toUpdate) throws EntityAccessorException {
        doInTransaction(new PersistenceActionWithoutResult() {
            
            @Override
            public void execute(EntityManager em) {
                em.merge(toUpdate);
            }
        });
    }
//</editor-fold>
    
// <editor-fold defaultstate="collapsed" desc="Reading Grupo instances">
    public List<Grupo> getGrupoList() {
        List<Grupo> result = Collections.emptyList();
        try {
            result = doInTransaction(new PersistenceAction<List<Grupo>>() {
                public List<Grupo> execute (EntityManager em) {
                    Query query = em.createNamedQuery("grupos.findAll");
                    List<Grupo> results = query.getResultList();
                    return results;
                }
            });
        } catch (EntityAccessorException ex) {
            Logger.getLogger(UsuarioRegistry.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public Grupo getGrupoById (final String id) {
        Grupo result = null;
        try {
            result = doInTransaction(new PersistenceAction<Grupo>() {

                @Override
                public Grupo execute(EntityManager em) {
                    Query query = em.createNamedQuery("grupos.findById");
                    query.setParameter("id", id);
                    List<Grupo> results = query.getResultList();
                    return results.get(0);
                }
            });
        } catch (EntityAccessorException ex) {
            Logger.getLogger(UsuarioRegistry.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
//</editor-fold>
}