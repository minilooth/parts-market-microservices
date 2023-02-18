package by.minilooth.vehicleservice.beans;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime timestamp;

    private HttpStatus status;
    private String error;
    private String message;
    private String path;

    @JsonSetter("status")
    private void setStatus(Integer code) {
        this.status = HttpStatus.resolve(code);
    }

}
