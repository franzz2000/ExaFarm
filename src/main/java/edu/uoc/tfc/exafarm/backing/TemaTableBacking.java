/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uoc.tfc.exafarm.backing;

import javax.faces.bean.ManagedBean;
import javax.faces.component.UIData;

/**
 *
 * @author Franz
 */
@ManagedBean
public class TemaTableBacking extends AbstractBacking {
    private UIData temas;
    
    public UIData getTemas() {
        return temas;
    }
    
    public void setTemas(UIData temas) {
        this.temas = temas;
    }
}
