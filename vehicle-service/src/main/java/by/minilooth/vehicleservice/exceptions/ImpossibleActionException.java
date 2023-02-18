package by.minilooth.vehicleservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ImpossibleActionException extends Exception {

    public ImpossibleActionException(String message) {
        super(message);
    }

}
