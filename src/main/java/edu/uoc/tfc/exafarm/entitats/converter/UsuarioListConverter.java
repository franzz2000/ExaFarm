/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uoc.tfc.exafarm.entitats.converter;

import edu.uoc.tfc.exafarm.entitats.Pregunta;
import edu.uoc.tfc.exafarm.entitats.Tema;
import edu.uoc.tfc.exafarm.entitats.Usuario;
import edu.uoc.tfc.exafarm.entitats.accessor.UsuarioRegistry;
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
public class UsuarioListConverter implements Converter, Serializable{
    
    public UsuarioListConverter() {}
    
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent component, String value) {
        if (value.isEmpty()) {
            return null;
        }
        Integer id = new Integer(value);
        return UsuarioRegistry.getCurrentInstance().getUsuarioById(id);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null || value.toString().isEmpty()) {
            return "";
        }
        Long id = ((Usuario) value).getId();
        return id.toString();
    }       
}
