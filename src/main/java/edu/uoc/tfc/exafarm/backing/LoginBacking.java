/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.uoc.tfc.exafarm.backing;

import edu.uoc.tfc.exafarm.entitats.Usuario;
import edu.uoc.tfc.exafarm.entitats.accessor.UsuarioRegistry;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIInput;
import javax.faces.event.ComponentSystemEvent;

/**
 *
 * @author franzz2000
 */
@ManagedBean
public class LoginBacking extends AbstractBacking {
    private UIInput loginOutcomeChoiceList;

    private Usuario usuarioNoAutentificado;

    public boolean userIdIsValid(String toTest) {
        boolean result = false;

        UsuarioRegistry registry = UsuarioRegistry.getCurrentInstance();
        result = null !=(usuarioNoAutentificado = registry.getUsuarioByIdUsuario(toTest));
        return result;
    }

    public boolean passwordIsValid(String toTest) {
        boolean result = false;

        if (null != usuarioNoAutentificado) {
            String usuarioPassword = usuarioNoAutentificado.getPassword();
            if (null != usuarioPassword && usuarioPassword.equals(toTest)) {
                setCurrentUsuario(usuarioNoAutentificado);
                result = true;
            }
        }
        usuarioNoAutentificado = null;
        return result;
    }

    public String getSuccessOutcome() {
        String choice = (String) getLoginOutcomeChoiceList().getValue();
        return "/user/" + choice + "?faces-redirect=true";
    }

    public void forwardToMainIfLoggedIn(ComponentSystemEvent cse) {
        if (isUsuarioLoggedIn()) {
            getFacesContext().getApplication().getNavigationHandler().handleNavigation(getFacesContext(), null, "/user/allEvents?faces-redirect=true");
        }
    }

    public void forwardToLoginIfNotLoggedIn(ComponentSystemEvent cse) {
        String viewId = getFacesContext().getViewRoot().getViewId();
        if(!isUsuarioLoggedIn() && !viewId.startsWith("/login")&&!viewId.startsWith("/register")) {
            getFacesContext().getApplication().getNavigationHandler().handleNavigation(getFacesContext(), null, "/login?faces-redirect=true");
        }
    }

    public String performLogout() {
        String result = "/login?faces-redirect=true";
        setCurrentUsuario(null);
        getFacesContext().getExternalContext().invalidateSession();

        return result;
    }

    public UIInput getLoginOutcomeChoiceList() {
        return loginOutcomeChoiceList;
    }

    public void setLoginOutomeChoiceList(UIInput loginOutcomeChoiceList) {
        this.loginOutcomeChoiceList = loginOutcomeChoiceList;
    }
}
