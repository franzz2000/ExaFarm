package exafarm;

import java.util.Map;
import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.PropertyNotFoundException;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIInput;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

public class loginPanel extends UINamingContainer {

    private UIInput userId;

    private UIInput password;

    private String action;

    public UIInput getPassword() {
        return password;
    }

    public void setPassword(UIInput password) {
        this.password = password;
    }

    public UIInput getUserid() {
        return userId;
    }

    public void setUserid(UIInput userId) {
        this.userId = userId;
    }

    public String action() {
        return action;
    }

    public void actionListener(ActionEvent e) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = null;
        ELContext elContext = context.getELContext();
        ELResolver resolver = elContext.getELResolver();
        Map<String, Object> attrs = this.getAttributes();
        Class paramTypes[] = { String.class };
        Object params[] = new Object[1];
        boolean useridIsValid, passwordIsValid;

        Object model = attrs.get("model");
        params[0] = getUserid().getValue();
        useridIsValid = (Boolean) resolver.invoke(elContext, model, 
                "useridIsValid", paramTypes, params);

        // failureOutcome is not a required attribute
        try {
            action = (String) resolver.getValue(elContext, model,
                    "failureOutcome");
        } catch (PropertyNotFoundException pnfe) {
            action = null;
        }

        if (!useridIsValid) {
            message = new FacesMessage("Userid " + params[0].toString() + 
                    " is not recognized.");
            context.addMessage(getUserid().getClientId(context), message);
        } else {
            params[0] = getPassword().getValue();
            passwordIsValid = (Boolean) resolver.invoke(elContext, model,
                    "passwordIsValid", paramTypes, params);
            if (!passwordIsValid) {
                message = new FacesMessage("Password for userid " +
                        (String) getUserid().getValue() + " is incorrect.");
                context.addMessage(password.getClientId(context), message);
            } else {
                action = (String) resolver.getValue(elContext, model,
                        "successOutcome");
            }
        }
    }
}

