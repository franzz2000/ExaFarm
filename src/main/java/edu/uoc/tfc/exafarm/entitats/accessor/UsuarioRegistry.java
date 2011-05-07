/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.uoc.tfc.exafarm.entitats.accessor;

import edu.uoc.tfc.exafarm.entitats.Grupo;
import edu.uoc.tfc.exafarm.entitats.Usuario;
import java.io.Serializable;
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
        em.persist(new Usuario("admin", "jGl25bVBBBW96Qi9Te4V37Fnqchz/Eu4qB9vKrRIqRg=", "Franz", "Jimeno", "fjimeno@uoc.edu"));
    }

    public Usuario getUsuarioByIdUsuario (final String idUsuario) {
        Usuario result = null;
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

    public void addGrupos(final List<Grupo> toAdd) throws EntityAccessorException {
        doInTransaction(new PersistenceActionWithoutResult() {

            @Override
            public void execute(EntityManager em) {
                for (Grupo t:toAdd)
                    em.persist(t);
            }
        });
    }

    public void updateGrupos(final Grupo toUpdate) throws EntityAccessorException {
        doInTransaction(new PersistenceActionWithoutResult() {

            @Override
            public void execute(EntityManager em) {
                em.merge(toUpdate);
            }
        });
    }
}