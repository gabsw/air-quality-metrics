package tqs.air.quality.metrics.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadInputException extends IllegalArgumentException{
    public BadInputException(String s) {
        super(s);
    }
}
