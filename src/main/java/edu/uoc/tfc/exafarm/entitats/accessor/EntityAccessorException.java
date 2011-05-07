/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.uoc.tfc.exafarm.entitats.accessor;

/**
 *
 * @author franzz2000
 */
public class EntityAccessorException extends Exception {
    private static final long serialVersionUID = 1L;

    public EntityAccessorException(Throwable cause) {
        super(cause);
    }

    public EntityAccessorException(String message, Throwable cause) {
        super(message, cause);
    }
}
