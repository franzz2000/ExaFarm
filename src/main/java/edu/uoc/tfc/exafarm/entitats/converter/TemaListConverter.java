/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uoc.tfc.exafarm.entitats.converter;

import edu.uoc.tfc.exafarm.entitats.Tema;
import edu.uoc.tfc.exafarm.entitats.accessor.ExamenRegistry;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author franzz2000
 */
@ManagedBean
public class TemaListConverter implements Converter, Serializable{
    
    public TemaListConverter() {}
    
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent component, String value) {
        if (value.isEmpty()) {
            return null;
        }
        Long id = new Long(value);
        return ExamenRegistry.getCurrentInstance().getTemaById(id);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null || value.toString().isEmpty()) {
            return "";
        }
        if(value instanceof Tema) {
            Long id =  ((Tema)value).getId();
            return id.toString();
        } else {
            return "";
        }
    }       
}
