package utn.frc.parcial.Api.Exception;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;
import java.util.Set;

public class ApiErrorAtributes extends DefaultErrorAttributes{
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes =
                super.getErrorAttributes(webRequest, options);

        // fines demostrativos simplemente
        Set<String> oriAttributes = errorAttributes.keySet();

        errorAttributes.put("locale", webRequest.getLocale().toString());
        errorAttributes.remove("error");

        //...

        return errorAttributes;
    }
}
