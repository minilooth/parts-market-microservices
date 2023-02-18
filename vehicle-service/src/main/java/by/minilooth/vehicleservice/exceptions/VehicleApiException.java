package by.minilooth.vehicleservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public abstract class VehicleApiException extends Exception {

    public VehicleApiException(String message) {
        super(message);
    }

}
