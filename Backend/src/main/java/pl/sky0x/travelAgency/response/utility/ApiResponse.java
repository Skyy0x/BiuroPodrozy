package pl.sky0x.travelAgency.response.utility;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.sky0x.travelAgency.response.ResponseData;
import pl.sky0x.travelAgency.response.ResponseMessage;

import java.util.Map;

public final class ApiResponse {

    private ApiResponse() {}

    public static ResponseEntity<ResponseMessage> ok(HttpServletRequest request, ResponseData data) {
        return buildResponse(request.getRequestURI(), HttpStatus.OK, data);
    }

    public static ResponseEntity<ResponseMessage> createErrorResponse(Map<String, Object> errors) {
        ResponseMessage responseMessage = new ResponseMessage(
                null,
                HttpStatus.BAD_REQUEST,
                ResponseData.create("errors", errors)
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
    }

    public static ResponseEntity<ResponseMessage> withStatus(HttpServletRequest request, HttpStatus status, ResponseData data) {
        return buildResponse(request.getRequestURI(), status, data);
    }

    public static ResponseEntity<ResponseMessage> success(String resourceName, Object content) {
        String requestUri = ServletUriComponentsBuilder.fromCurrentRequest().toUriString();
        ResponseData payload = ResponseData.create(resourceName, content);
        return buildResponse(requestUri, HttpStatus.OK, payload);
    }

    public static ResponseEntity<ResponseMessage> success(Class<?> entityClass, Object content) {
        String resourceName = toPlural(entityClass.getSimpleName().toLowerCase());
        return success(resourceName, content);
    }

    private static ResponseEntity<ResponseMessage> buildResponse(String uri, HttpStatus status, ResponseData data) {
        ResponseMessage body = new ResponseMessage(uri, status, data);
        return ResponseEntity.status(status).body(body);
    }

    private static String toPlural(String name) {
        if (name.endsWith("y")) {
            return name.substring(0, name.length() - 1) + "ies";
        }
        if (name.matches(".*(s|x|z|ch|sh)$")) {
            return name + "es";
        }
        return name + "s";
    }
}
