package jee19.web.utilities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

/**
 * Faces converter for support of LocalDate. This converter uses locale specific
 * patterns to convert (i.e. parse and format) the LocalDate value. The patterns
 * are defined in the application messagebundle named "msg".
 *
 * The formattig pattern for getAsString has the key
 * "localDateConverterFormatPattern", the pattern for getAsObject is named
 * "localDateConverterParsePattern".
 *
 * @author Dr. Volker Riediger <riediger@uni-koblenz.de>
 */
@FacesConverter(forClass = LocalDate.class, value = "localDateConverter")
public class LocalDateConverter implements javax.faces.convert.Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        String pattern = context.getApplication()
                .getResourceBundle(context, "msg")
                .getString("localDateConverterParsePattern");
        return LocalDate.parse(value,
                DateTimeFormatter.ofPattern(pattern)
                .withLocale(context.getViewRoot().getLocale())
        );
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String pattern = context.getApplication()
                .getResourceBundle(context, "msg")
                .getString("localDateConverterFormatPattern");
        LocalDate dateValue = (LocalDate) value;
        return dateValue.format(
                DateTimeFormatter.ofPattern(pattern)
                .withLocale(context.getViewRoot().getLocale())
        );
    }

}
