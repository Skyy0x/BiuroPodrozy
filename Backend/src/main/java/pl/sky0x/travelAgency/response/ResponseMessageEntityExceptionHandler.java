package pl.sky0x.travelAgency.response;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.sky0x.travelAgency.response.utility.ApiResponse;

@ControllerAdvice
public class ResponseMessageEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseMessage> handleAllExceptions(Exception ex, HttpServletRequest request) {
        return ApiResponse.badRequest(request, ex);
    }

}
