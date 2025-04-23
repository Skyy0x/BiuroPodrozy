package pl.sky0x.travelAgency.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

public class ResponseMessage {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime timestamp = LocalDateTime.now();

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private final String path;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    private final int status;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String error;

    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    private final Map<String, Object> data;

    public ResponseMessage(String path, HttpStatus status, ResponseData data) {
        this.path = path;
        this.status = status.value();
        this.data = data.getData();
    }
}
