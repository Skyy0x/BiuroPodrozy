package pl.sky0x.travelAgency.response.utility;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.sky0x.travelAgency.response.ResponseData;
import pl.sky0x.travelAgency.response.ResponseMessage;

import java.util.Collection;
import java.util.Map;

public final class ApiResponse  {

    private ApiResponse() {

    }

    public static ResponseEntity<ResponseMessage> success(HttpServletRequest request, String message) {
        return success(request, ResponseData.create("message", message));
    }

    public static ResponseEntity<ResponseMessage> success(HttpServletRequest request, ResponseData responseData) {
        return createResponse(request, HttpStatus.OK, responseData);
    }

    public static ResponseEntity<ResponseMessage> badRequest(HttpServletRequest request, Exception exception) {
        return createResponse(request, HttpStatus.BAD_REQUEST, ResponseData.create("error", exception.getMessage()));
    }

    public static ResponseEntity<ResponseMessage> createResponse(HttpServletRequest request, HttpStatus status, ResponseData data) {
        final ResponseMessage responseMessage = new ResponseMessage(
                request.getRequestURI(),
                status,
                data
        );

        return ResponseEntity.status(status).body(responseMessage);
    }

    public static ResponseEntity<ResponseMessage> createSuccessResponse(String entityName, Object object) {
        ResponseData responseData = ResponseData.create(entityName, object);

        ResponseMessage responseMessage = new ResponseMessage(
                ServletUriComponentsBuilder.fromCurrentRequest().toUriString(),
                HttpStatus.OK,
                responseData
        );

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    public static ResponseEntity<ResponseMessage> createSuccessResponse(Class<?> type, Object object) {
        return createSuccessResponse(getEntityName(type), object);
    }

    private static String getEntityName(Class<?> type) {
        String className = type.getSimpleName().toLowerCase();

        return pluralize(className);
    }

    private static String pluralize(String word) {
        if (word.endsWith("y")) {
            return word.substring(0, word.length() - 1) + "ies";
        } else if (word.endsWith("s") || word.endsWith("x") || word.endsWith("z") || word.endsWith("ch") || word.endsWith("sh")) {
            return word + "es";
        } else {
            return word + "s";
        }
    }
}
