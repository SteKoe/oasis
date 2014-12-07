package de.stekoe.oasis.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;

@Service
public class JSONValidator {

    @Autowired
    MessageSource messageSource;

    public JsonObject getErrors(BindingResult bindingResult, Locale locale) {
        JsonObjectBuilder jsonErrors = Json.createObjectBuilder();

        Field[] declaredFields = getFields(bindingResult);
        for(Field f : declaredFields) {

            String fieldName = f.getName();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors(fieldName);

            if(fieldErrors.size() > 0) {
                JsonArrayBuilder jsonFieldError = Json.createArrayBuilder();

                fieldErrors.forEach(error -> {
                    String errorMsg = getErrorMessage(locale, error);
                    if(errorMsg == null) {
                        errorMsg = error.getDefaultMessage();
                    }
                    jsonFieldError.add(errorMsg);
                });

                jsonErrors.add(fieldName, jsonFieldError);
            }
        }

        return jsonErrors.build();
    }

    private String getErrorMessage(Locale locale, FieldError error) {
        for(String code : error.getCodes()) {
            String msg = messageSource.getMessage(code, null, locale);
            if(!msg.equals(code)) {
                return msg;
            }
        }
        return null;
    }

    private Field[] getFields(BindingResult bindingResult) {
        return bindingResult.getTarget().getClass().getDeclaredFields();
    }
}
