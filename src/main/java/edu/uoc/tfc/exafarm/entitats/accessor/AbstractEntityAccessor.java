/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.uoc.tfc.exafarm.entitats.accessor;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author franzz2000
 */
public abstract class AbstractEntityAccessor {
    @PersistenceUnit(unitName="Exafarm-TFC")
    private EntityManagerFactory emf;

    @Resource
    private UserTransaction userTransaction;

    private final AtomicInteger count;

    public AbstractEntityAccessor() {
        count = new AtomicInteger(0);
    }

    protected final <T> T doInTransaction(PersistenceAction<T> action) throws EntityAccessorException {
        EntityManager em = null; 
        try {
            int status = 0;
            if (Status.STATUS_ACTIVE != (status = userTransaction.getStatus())){
                count.incrementAndGet();
                userTransaction.begin();
                em = emf.createEntityManager();
            }
            T result = action.execute(em);
            if (Status.STATUS_ACTIVE == (status = userTransaction.getStatus())) {
                if (0 == count.decrementAndGet()) {
                    userTransaction.commit();
                }
            }
            return result;
        } catch (SystemException | NotSupportedException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException e) {
            try {
                userTransaction.rollback();
            } catch (IllegalStateException | SecurityException | SystemException ex) {
                Logger.getLogger(AbstractEntityAccessor.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw new EntityAccessorException(e);
        } finally {
            em.close();
        }
    }

    protected final void doInTransaction(PersistenceActionWithoutResult action) throws EntityAccessorException {
        EntityManager em = null;
        try {
            int status = 0;
            if (Status.STATUS_ACTIVE != (status = userTransaction.getStatus())) {
                count.incrementAndGet();
                userTransaction.begin();
                em = emf.createEntityManager();
            }
            action.execute(em);
            if (Status.STATUS_ACTIVE == (status = userTransaction.getStatus())) {
                if (0 == count.decrementAndGet()) {
                    userTransaction.commit();
                }
            }
        } catch (SystemException | NotSupportedException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException e) {
            try {
                userTransaction.rollback();
            } catch (IllegalStateException | SecurityException | SystemException ex) {
                Logger.getLogger(AbstractEntityAccessor.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw new EntityAccessorException(e);
        } finally {
            if(em != null) {
                em.close();
            }
        }
    }

    protected static interface PersistenceAction<T> {
        T execute(EntityManager em);
    }

    protected static interface PersistenceActionWithoutResult {
        void execute(EntityManager em);
    }

}
