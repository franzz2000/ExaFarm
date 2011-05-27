/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uoc.tfc.exafarm.entitats.converter;

import edu.uoc.tfc.exafarm.entitats.Bloque;
import edu.uoc.tfc.exafarm.entitats.Tema;
import edu.uoc.tfc.exafarm.entitats.accessor.ExamenRegistry;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author franzz2000
 */
@ManagedBean
public class BloqueListConverter implements Converter, Serializable{
    
    public BloqueListConverter() {}
    
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent component, String value) {
        if (value.isEmpty()) {
            return null;
        }
        Integer id = new Integer(value);
        return ExamenRegistry.getCurrentInstance().getBloqueById(id);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null || value.toString().isEmpty()) {
            return "";
        }
        Long id =  ((Bloque)value).getId();
        return id.toString();
    }       
}
