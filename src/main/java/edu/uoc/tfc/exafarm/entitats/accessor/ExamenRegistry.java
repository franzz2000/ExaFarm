package edu.uoc.tfc.exafarm.entitats.accessor;

import edu.uoc.tfc.exafarm.entitats.Bloque;
import edu.uoc.tfc.exafarm.entitats.Examen;
import edu.uoc.tfc.exafarm.entitats.Pregunta;
import edu.uoc.tfc.exafarm.entitats.Respuesta;
import edu.uoc.tfc.exafarm.entitats.Tema;
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
public class ExamenRegistry extends AbstractEntityAccessor implements Serializable {

// <editor-fold defaultstate="collapsed" desc="Accessing and initializing the instance">
    public static ExamenRegistry getCurrentInstance() {
    ExamenRegistry result = null;
    Map<String, Object> appMap = FacesContext.getCurrentInstance().getExternalContext().getApplicationMap();
    result = (ExamenRegistry) appMap.get("examenRegistry");

    return result;
    }

    @PostConstruct
    public void perApplicationConstructor() {
    //        try {
    //            doInTransaction(new PersistenceActionWithoutResult() {
    //
    //                @Override
    //                public void execute(EntityManager em) {
    //                    Query query = em.createNamedQuery("examenes.findAll");
    //                    List<Tema> results = query.getResultList();
    //                    if(results.isEmpty()) {
    //                        populateExamen(em);
    //                        query = em.createNamedQuery("examenes.findAll");
    //                        results = query.getResultList();
    //                        assert(!results.isEmpty());
    //                    }
    //                }
    //            });
    //        } catch (EntityAccessorException ex) {
    //            Logger.getLogger(ExamenRegistry.class.getName()).log(Level.SEVERE, null, ex);
    //        }
    }

private void populateExamen(EntityManager em) {
}
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Reading Bloque instances">
public Bloque getBloqueById (final Integer idBloque) {
Bloque result = null;
try {
    result = doInTransaction(new PersistenceAction<Bloque>() {

        public Bloque execute(EntityManager em) {
            Query query = em.createNamedQuery("bloques.findById");
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

public List<Bloque> getBloqueList() {
List<Bloque> result = Collections.emptyList();
try {
    result = doInTransaction(new PersistenceAction<List<Bloque>>() {

        @Override
        public List<Bloque> execute(EntityManager em) {
            Query query = em.createNamedQuery("bloques.findAll");
            List<Bloque> results = query.getResultList();
            return results;
        }
    });
} catch (EntityAccessorException ex) {
    Logger.getLogger(ExamenRegistry.class.getName()).log(Level.SEVERE, null, ex);
}

return result;
}

public List<Bloque> getBloqueListActivo() {
List<Bloque> result = Collections.emptyList();
try {
    result = doInTransaction(new PersistenceAction<List<Bloque>>() {

        @Override
        public List<Bloque> execute(EntityManager em) {
            Query query = em.createNamedQuery("bloques.findActivos");
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

// <editor-fold defaultstate="collapsed" desc="Writing Bloque instances">

public void addBloque(final Bloque toAdd) throws EntityAccessorException {
doInTransaction(new PersistenceActionWithoutResult() {

    @Override
    public void execute(EntityManager em) {
        em.persist(toAdd);
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


//</editor-fold>

// <editor-fold defaultstate="collapsed" desc="Reading Tema instances">
public Tema getTemaById (final Long idTema) {
Tema result = null;
try {
    result = doInTransaction(new PersistenceAction<Tema>() {

        public Tema execute(EntityManager em) {
            Query query = em.createNamedQuery("temas.findById");
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

public List<Tema> getTemaList() {
List<Tema> result = Collections.emptyList();
try {
    result = doInTransaction(new PersistenceAction<List<Tema>>() {

        public List<Tema> execute(EntityManager em) {
            Query query = em.createNamedQuery("temas.findAll");
            List<Tema> results = query.getResultList();
            return results;
        }
    });
} catch (EntityAccessorException ex) {
    Logger.getLogger(ExamenRegistry.class.getName()).log(Level.SEVERE, null, ex);
}

return result;
}

public List<Tema> getTemaListActivo() {
List<Tema> result = Collections.emptyList();
try {
    result = doInTransaction(new PersistenceAction<List<Tema>>() {

        public List<Tema> execute(EntityManager em) {
            Query query = em.createNamedQuery("temas.findActivos");
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

// <editor-fold defaultstate="collapsed" desc="Writing Tema instances">
public void addTema(final Tema toAdd) throws EntityAccessorException {
doInTransaction(new PersistenceActionWithoutResult() {

    @Override
    public void execute(EntityManager em) {
        em.persist(toAdd);
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


//</editor-fold>

// <editor-fold defaultstate="collapsed" desc="Reading Preguntas instances">
public List<Pregunta> getPreguntaByExamen (final Examen examen) {
List<Pregunta> result = null;
try {
    result = doInTransaction(new PersistenceAction<List<Pregunta>>() {
        public List<Pregunta> execute(EntityManager em) {
            Query query = em.createNamedQuery("preguntas.findByExamen");
            query.setParameter("examen", examen);
            List<Pregunta> result = query.getResultList();
            return result;
        } 
    }); 
} catch (EntityAccessorException ex) {
    Logger.getLogger(ExamenRegistry.class.getName()).log(Level.SEVERE, null, ex);
}
return result;
}

public List<Pregunta> getPreguntaByUsuario (final Usuario usuario) {
List<Pregunta> result = null;
try {
    result = doInTransaction(new PersistenceAction<List<Pregunta>>() {
        public List<Pregunta> execute(EntityManager em) {
            Query query = em.createNamedQuery("preguntas.findByUsuario");
            query.setParameter("usuario", usuario);
            List<Pregunta> result = query.getResultList();
            return result;
        } 
    }); 
} catch (EntityAccessorException ex) {
    Logger.getLogger(ExamenRegistry.class.getName()).log(Level.SEVERE, null, ex);
}
return result;
}

public Integer getCountTemas(final Tema tema) {
    Integer result = null;
    try {
        result = doInTransaction(new PersistenceAction<Integer>() {

            @Override
            public Integer execute(EntityManager em) {
                Query query = em.createNamedQuery("preguntas.countByTema");
                query.setParameter("tema", tema);
                Integer result = (Integer) query.getSingleResult();
                return result;
            }
        });
    } catch (EntityAccessorException ex) {
        Logger.getLogger(ExamenRegistry.class.getName()).log(Level.SEVERE, null, ex);
    }
    return result;
}

public Long getCountPreguntas(final Usuario usuario) {
    Long result = null;
    try {
        result = doInTransaction(new PersistenceAction<Long>() {
            @Override
            public Long execute(EntityManager em) {
                Query query = em.createNamedQuery("preguntas.countByUsuario");
                query.setParameter("usuario", usuario);
                Long result = (Long) query.getSingleResult();
                return result;
            }
        });
    } catch (EntityAccessorException ex) {
        Logger.getLogger(ExamenRegistry.class.getName()).log(Level.SEVERE, null, ex);
    }
    return result;
}

public List<Pregunta> getPreguntaByUsuarioActivos (final Usuario usuario) {
List<Pregunta> result = null;
try {
    result = doInTransaction(new PersistenceAction<List<Pregunta>>() {
        @Override
        public List<Pregunta> execute(EntityManager em) {
            Query query = em.createNamedQuery("preguntas.findByUsuarioActivos");
            query.setParameter("usuario", usuario);
            List<Pregunta> result = query.getResultList();
            return result;
        } 
    }); 
} catch (EntityAccessorException ex) {
    Logger.getLogger(ExamenRegistry.class.getName()).log(Level.SEVERE, null, ex);
}
return result;
}

public Pregunta getPreguntaById (final String idPregunta) {
Pregunta result = null;
try {
    result = doInTransaction(new PersistenceAction<Pregunta>() {

        @Override
        public Pregunta execute(EntityManager em) {
            Query query = em.createNamedQuery("preguntas.findById");
            Long id = new Long(idPregunta);
            query.setParameter("id", id);
            List<Pregunta> results = query.getResultList();
            return results.get(0);
        }
    });
} catch (EntityAccessorException ex) {
    Logger.getLogger(ExamenRegistry.class.getName()).log(Level.SEVERE, null, ex);
}
return result;
}

public List<Pregunta> getPreguntaList() {
List<Pregunta> result = Collections.emptyList();
try {
    result = doInTransaction(new PersistenceAction<List<Pregunta>>() {

        @Override
        public List<Pregunta> execute(EntityManager em) {
            Query query = em.createNamedQuery("preguntas.findAll");
            List<Pregunta> results = query.getResultList();
            return results;
        }
    });
} catch (EntityAccessorException ex) {
    Logger.getLogger(ExamenRegistry.class.getName()).log(Level.SEVERE, null, ex);
}

return result;
}

public List<Pregunta> getPreguntaListActivos() {
List<Pregunta> result = Collections.emptyList();
try {
    result = doInTransaction(new PersistenceAction<List<Pregunta>>() {

        @Override
        public List<Pregunta> execute(EntityManager em) {
            Query query = em.createNamedQuery("preguntas.findActivos");
            List<Pregunta> results = query.getResultList();
            return results;
        }
    });
} catch (EntityAccessorException ex) {
    Logger.getLogger(ExamenRegistry.class.getName()).log(Level.SEVERE, null, ex);
}

return result;
}

public List<Pregunta> getPreguntaByExamenAndUser(final Examen examen, final Usuario usuario) {
List<Pregunta> result = Collections.emptyList();
try {
    result = doInTransaction(new PersistenceAction<List<Pregunta>>() {

        @Override
        public List<Pregunta> execute(EntityManager em) {
            Query query = em.createNamedQuery("preguntas.findByExamenAndUser");
            query.setParameter("examen", examen);
            query.setParameter("usuario", usuario);
            List<Pregunta> results = query.getResultList();
            return results;
        }
    });
} catch (EntityAccessorException ex) {
    Logger.getLogger(ExamenRegistry.class.getName()).log(Level.SEVERE, null, ex);
}

return result;
}
//</editor-fold>

// <editor-fold defaultstate="collapsed" desc="Writing Preguntas instances">    
public void addPregunta(final Pregunta toAdd) throws EntityAccessorException {
doInTransaction(new PersistenceActionWithoutResult() {
    Usuario usuario;

    @Override
    public void execute(EntityManager em) {
        em.persist(toAdd);
    }
});
}

public void updatePregunta(final Pregunta toUpdate) throws EntityAccessorException {
doInTransaction(new PersistenceActionWithoutResult() {

    @Override
    public void execute(EntityManager em) {
        em.merge(toUpdate);
    }
});
}


//</editor-fold>

// <editor-fold defaultstate="collapsed" desc="Reading Exámenes instances">
public Examen getExamenById (final String idExamen) {
Examen result = null;
try {
    result = doInTransaction(new PersistenceAction<Examen>() {

        public Examen execute(EntityManager em) {
            Query query = em.createNamedQuery("examenes.findById");
            query.setParameter("id", Long.parseLong(idExamen));
            List<Examen> results = query.getResultList();
            return results.get(0);
        }
    });
} catch (EntityAccessorException ex) {
    Logger.getLogger(ExamenRegistry.class.getName()).log(Level.SEVERE, null, ex);
}
return result;
}

public List<Examen> getExamenList() {
List<Examen> result = Collections.emptyList();
try {
    result = doInTransaction(new PersistenceAction<List<Examen>>() {

        @Override
        public List<Examen> execute(EntityManager em) {
            Query query = em.createNamedQuery("examenes.findAll");
            List<Examen> results = query.getResultList();
            return results;
        }
    });
} catch (EntityAccessorException ex) {
    Logger.getLogger(ExamenRegistry.class.getName()).log(Level.SEVERE, null, ex);
}

return result;
}

public List<Examen> getExamenByNoCerrado() {
List<Examen> result = Collections.emptyList();
try {
    result = doInTransaction(new PersistenceAction<List<Examen>>() {

        @Override
        public List<Examen> execute(EntityManager em) {
            Query query = em.createNamedQuery("examenes.findByNoCerrado");
            List<Examen> results = query.getResultList();
            return results;
        }
    });
} catch (EntityAccessorException ex) {
    Logger.getLogger(ExamenRegistry.class.getName()).log(Level.SEVERE, null, ex);
}

return result;
}

public List<Examen> getExamenByActivo() {
List<Examen> result = Collections.emptyList();
try {
    result = doInTransaction(new PersistenceAction<List<Examen>>() {

        @Override
        public List<Examen> execute(EntityManager em) {
            Query query = em.createNamedQuery("examenes.findByActivo");
            List<Examen> results = query.getResultList();
            return results;
        }
    });
} catch (EntityAccessorException ex) {
    Logger.getLogger(ExamenRegistry.class.getName()).log(Level.SEVERE, null, ex);
}

return result;
}
//</editor-fold>

// <editor-fold defaultstate="collapsed" desc="Writing Exámenes instances">

public void addExamen(final Examen toAdd) throws EntityAccessorException {
doInTransaction(new PersistenceActionWithoutResult() {

    @Override
    public void execute(EntityManager em) {
        em.persist(toAdd);
    }
});
}

public void updateExamen(final Examen toUpdate) throws EntityAccessorException {
doInTransaction(new PersistenceActionWithoutResult() {

    @Override
    public void execute(EntityManager em) {
        em.merge(toUpdate);
    }
});
}


//</editor-fold>

// <editor-fold defaultstate="collapsed" desc="Writing Respuesta instances">
public void addRespuesta(final Respuesta toAdd) throws EntityAccessorException {
doInTransaction(new PersistenceActionWithoutResult() {

    @Override
    public void execute(EntityManager em) {
        em.persist(toAdd);
    }
});
}

public void updateRespuesta(final Respuesta toUpdate) throws EntityAccessorException {
doInTransaction(new PersistenceActionWithoutResult() {

    @Override
    public void execute(EntityManager em) {
        em.merge(toUpdate);
    }
});
}
//</editor-fold>

}