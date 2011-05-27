/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uoc.tfc.exafarm.entitats.converter;

import edu.uoc.tfc.exafarm.entitats.Grupo;
import edu.uoc.tfc.exafarm.entitats.accessor.UsuarioRegistry;
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

public class GrupoListConverter implements Converter, Serializable{
    
    public GrupoListConverter() {}
    
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent component, String value) {
        if (value.isEmpty()) {
            return null;
        }
        return UsuarioRegistry.getCurrentInstance().getGrupoById(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null || value.toString().isEmpty()) {
            return "";
        }
        String id = ((Grupo) value).getIdGrupo();
        return id;
    }       
}
