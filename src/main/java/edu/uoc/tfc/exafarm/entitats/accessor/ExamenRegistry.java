package edu.uoc.tfc.exafarm.entitats.accessor;

import edu.uoc.tfc.exafarm.entitats.Bloque;
import edu.uoc.tfc.exafarm.entitats.Tema;
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
public class ExamenRegistry extends AbstractEntityAccessor implements Serializable {

// <editor-fold defaultstate="collapsed" desc="Accessing and initializing the instance">
    public static ExamenRegistry getCurrentInstance() {
        ExamenRegistry result = null;
        Map<String, Object> appMap = FacesContext.getCurrentInstance().getExternalContext().getApplicationMap();
        result = (ExamenRegistry) appMap.get("usuarioRegistry");

        return result;
    }

    @PostConstruct
    public void perApplicationConstructor() {
        try {
            doInTransaction(new PersistenceActionWithoutResult() {

                @Override
                public void execute(EntityManager em) {
                    Query query = em.createNamedQuery("examenes.getAll");
                    List<Tema> results = query.getResultList();
                    if(results.isEmpty()) {
                        populateUsers(em);
                        query = em.createNamedQuery("examenes.getAll");
                        results = query.getResultList();
                        assert(!results.isEmpty());
                    }
                }
            });
        } catch (EntityAccessorException ex) {
            Logger.getLogger(ExamenRegistry.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void populateUsers(EntityManager em) {
    }
 // </editor-fold>
    
// <editor-fold defaultstate="collapsed" desc="Reading Usuario instances">
    public Bloque getBloqueById (final String idBloque) {
        Bloque result = null;
        try {
            result = doInTransaction(new PersistenceAction<Bloque>() {

                public Bloque execute(EntityManager em) {
                    Query query = em.createNamedQuery("usuarios.getUsuarioById");
                    query.setParameter("id", idBloque);
                    List<Bloque> results = query.getResultList();
                    return results.get(0);
                }
            });
        } catch (EntityAccessorException ex) {
            Logger.getLogger(ExamenRegistry.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public void addBloque(final Bloque toAdd) throws EntityAccessorException {
        doInTransaction(new PersistenceActionWithoutResult() {

            @Override
            public void execute(EntityManager em) {
                em.merge(toAdd);
            }
        });
    }

    public void updateBloque(final Bloque toUpdate) throws EntityAccessorException {
        doInTransaction(new PersistenceActionWithoutResult() {

            @Override
            public void execute(EntityManager em) {
                em.merge(toUpdate);
            }
        });
    }
    
    public List<Bloque> getBloqueList() {
        List<Bloque> result = Collections.emptyList();
        try {
            result = doInTransaction(new PersistenceAction<List<Bloque>>() {

                public List<Bloque> execute(EntityManager em) {
                    Query query = em.createNamedQuery("bloques.getAll");
                    List<Bloque> results = query.getResultList();
                    return results;
                }
            });
        } catch (EntityAccessorException ex) {
            Logger.getLogger(ExamenRegistry.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }
//</editor-fold>

// <editor-fold defaultstate="collapsed" desc="Reading Tema instances">
    public Tema getTemaById (final String idTema) {
        Tema result = null;
        try {
            result = doInTransaction(new PersistenceAction<Tema>() {

                public Tema execute(EntityManager em) {
                    Query query = em.createNamedQuery("temas.getTemaById");
                    query.setParameter("id", idTema);
                    List<Tema> results = query.getResultList();
                    return results.get(0);
                }
            });
        } catch (EntityAccessorException ex) {
            Logger.getLogger(ExamenRegistry.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public void addTema(final Tema toAdd) throws EntityAccessorException {
        doInTransaction(new PersistenceActionWithoutResult() {

            @Override
            public void execute(EntityManager em) {
                em.merge(toAdd);
            }
        });
    }

    public void updateTema(final Tema toUpdate) throws EntityAccessorException {
        doInTransaction(new PersistenceActionWithoutResult() {

            @Override
            public void execute(EntityManager em) {
                em.merge(toUpdate);
            }
        });
    }
    
    public List<Tema> getTemaList() {
        List<Tema> result = Collections.emptyList();
        try {
            result = doInTransaction(new PersistenceAction<List<Tema>>() {

                public List<Tema> execute(EntityManager em) {
                    Query query = em.createNamedQuery("temas.getAll");
                    List<Tema> results = query.getResultList();
                    return results;
                }
            });
        } catch (EntityAccessorException ex) {
            Logger.getLogger(ExamenRegistry.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }
//</editor-fold>

}