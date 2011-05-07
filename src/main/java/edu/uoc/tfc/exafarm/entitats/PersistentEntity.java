package edu.uoc.tfc.exafarm.entitats;

import java.io.Serializable;


/**
 *
 * @author franzz2000
 */
public interface PersistentEntity<PK extends Serializable> extends Serializable {
    PK getId();
}
