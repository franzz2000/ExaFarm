package edu.uoc.tfc.exafarm.extras;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 * Utilidad que permite leer los mensajes del bundle en los Beans.
 * @author Franz
 */
@ManagedBean
public class Utils {
    protected static ClassLoader getCurrentClassLoader(Object defaultObject){

        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        if(loader == null){
                loader = defaultObject.getClass().getClassLoader();
        }

        return loader;
    }

    public static String getMessageResourceString(String bundleName, String key, Object params[]){

        String text = null;

        FacesContext context = FacesContext.getCurrentInstance();
        
        ResourceBundle bundle = context.getApplication().getResourceBundle(context, bundleName);

        try{
                text = bundle.getString(key);
        } catch(MissingResourceException e){
                text = "?? key " + key + " not found ??";
        }

        if(params != null){
                MessageFormat mf = new MessageFormat(text);
                text = mf.format(params, new StringBuffer(), null).toString();
        }

        return text;
    }
    
    public static String getMessageResourceString(String bundleName, String key) {
        String text = null;
        
        FacesContext context = FacesContext.getCurrentInstance();
        
        ResourceBundle bundle = context.getApplication().getResourceBundle(context, bundleName);
        
        try{
                text = bundle.getString(key);
        } catch(MissingResourceException e){
                text = "?? key " + key + " not found ??";
        }
        
        return text;
    }
    
        /**
     * Añade un mensaje general que aparecerá en la visualización
     * 
     * @param message Texto que ha de aparecer
     */
    public static void addMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
    }
    
    /**
     * Añade un mensaje general con tipo de severidad.
     * 
     * @param severity severidad del mensaje
     * @param message Texto que aparece en el mensaje. El texto ha de estar en Bundle.properties.
     * 
     */
    public static void addMessage(FacesMessage.Severity severity, String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, message, null));
    }
}
