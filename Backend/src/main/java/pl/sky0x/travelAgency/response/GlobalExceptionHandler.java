package pl.sky0x.travelAgency.response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.sky0x.travelAgency.response.utility.ApiResponse;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseMessage> handleValidationExceptions(MethodArgumentNotValidException ex, Locale locale) {
        Map<String, Object> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            if (error instanceof FieldError) {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = messageSource.getMessage(error, locale);
                errors.put(fieldName, errorMessage);
            } else {
                String errorMessage = messageSource.getMessage(error, locale);
                errors.put(error.getObjectName(), errorMessage);
            }
        });

        return ApiResponse.createErrorResponse(errors);
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseMessage> handleMissingRequestBody() {
        Map<String, Object> errors = new HashMap<>();
        errors.put("error", "Request body is missing or malformed");
        return ApiResponse.createErrorResponse(errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseMessage> handleAllExceptions(Exception ex) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("error", ex.getMessage());
        return ApiResponse.createErrorResponse(errors);
    }
}
