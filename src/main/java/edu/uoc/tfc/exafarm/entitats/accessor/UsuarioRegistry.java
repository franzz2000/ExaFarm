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
//                    Query query = em.createNamedQuery("grupos.findAll");
//                    List<Grupo> results = query.getResultList();
//                    if(results.isEmpty()) {
//                        populateGrupo(em);
//                        query = em.createNamedQuery("grupos.findAll");
//                        results = query.getResultList();
//                        assert(!results.isEmpty());
//                    }
                    
//                    Query query = em.createNamedQuery("usuarios.findAlle");
//                    List<Usuario> resultsUsuarios = query.getResultList();
//                    if(resultsUsuarios.isEmpty()) {
//                        populateUsers(em);
//                        query = em.createNamedQuery("usuarios.findAll");
//                        resultsUsuarios = query.getResultList();
//                        assert(!resultsUsuarios.isEmpty());
//                    }
                }
            });
        } catch (EntityAccessorException ex) {
            Logger.getLogger(UsuarioRegistry.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void populateUsers(EntityManager em) {
        
//        em.persist(new Usuario("franz", "A61rMz1EUJnH3+D/dF7FzBMw0UnvdS82w67U7+oT9yU=", "Franz", "Jimeno Demuth", "fjimeno@uoc.edu", true, getGrupoById("admin")));
//        System.out.println("Usuario por defecto creado correctamente.");
    }
    
    private void populateGrupo(EntityManager em) {
//        em.persist(new Grupo("admin", "Administrador"));
//        em.persist(new Grupo("coord", "Coordinador"));
//        em.persist(new Grupo("profe", "Profesor"));
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
                    List<Usuario> results = query.getResultList();
                    return results.get(0);
                }
            });
        } catch (EntityAccessorException ex) {
            Logger.getLogger(UsuarioRegistry.class.getName()).log(Level.SEVERE, null, ex);
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