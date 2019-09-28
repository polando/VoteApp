package foxtrot.jee19.web.utilities;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * @author Dr. Volker Riediger <riediger@uni-koblenz.de>
 */
public class MessageUtilities {

    /**
     * The name of the default resource bundle for use in the convenience
     * methods below.
     */
    public static final String DEFAULT_BUNDLE_NAME = "msg";

    /**
     * Adds an INFO message to the target object. Detail text is obtained from
     * the default resource bundle with the specified key.
     *
     * @param targetObject an UIComponent
     * @param key the key for the message detail string
     */
    public static void addMessage(Object targetObject, String key) {
        addMessage(targetObject, FacesMessage.SEVERITY_INFO, DEFAULT_BUNDLE_NAME, key, null);
    }

    /**
     * Adds an message of given severity to the target object. Detail text is
     * obtained from the default resource bundle with the specified key.
     *
     * @param targetObject an UIComponent
     * @param severity the severity for the message
     * @param key the key for the message detail string
     */
    public static void addMessage(Object targetObject, FacesMessage.Severity severity, String key) {
        addMessage(targetObject, severity, DEFAULT_BUNDLE_NAME, key, null);
    }

    /**
     * Adds an INFO message to the target object. Detail text is obtained from
     * the default resource bundle with the specified key. If params is not
     * null, the message is formatted using String.format.
     *
     * @param targetObject an UIComponent
     * @param key the key for the message detail string
     * @param params parameters to pass to String.format
     */
    public static void addMessage(Object targetObject, String key, Object[] params) {
        addMessage(targetObject, FacesMessage.SEVERITY_INFO, DEFAULT_BUNDLE_NAME, key, params);
    }

    /**
     * Adds an message of given severity to the target object. Detail text is
     * obtained from the default resource bundle with the specified key. If
     * params is not null, the message is formatted using String.format.
     *
     * @param targetObject an UIComponent
     * @param severity the severity for the message
     * @param key the key for the message detail string
     * @param params parameters to pass to String.format
     */
    public static void addMessage(Object targetObject, FacesMessage.Severity severity, String key, Object[] params) {
        addMessage(targetObject, severity, DEFAULT_BUNDLE_NAME, key, params);
    }

    /**
     * Adds a FacesMessage to the specified targetObject. The object must be an
     * UIComponent or a subclass. The message detail text is loaded from an
     * application resource bundle with the bundleName and key provided. When
     * params is not null, the message is formatted using String.format.
     *
     * @param targetObject a UIComponent to add the message to
     * @param severity the severity for the message
     * @param bundleName the resource bundle name (cf. faces-context.xml)
     * @param key the key for the message detail string
     * @param params parameters to pass to String.format
     */
    public static void addMessage(Object targetObject,
            FacesMessage.Severity severity,
            String bundleName, String key,
            Object[] params) {
        // target is a UIComponent
        UIComponent target = (UIComponent) targetObject;
        FacesContext context = FacesContext.getCurrentInstance();

        // get message text from resource bundle
        String message = context.getApplication()
                .getResourceBundle(context, bundleName)
                .getString(key);

        // format the message in case of parameters
        String detail = params == null ? message : String.format(message, params);

        // add the message as FacesMessage to the UIComponent
        context.addMessage(
                target.getClientId(),
                new FacesMessage(severity, null, detail));
    }
}
