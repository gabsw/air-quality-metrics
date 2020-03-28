package tqs.air.quality.metrics.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class BreezometerResultNotFoundException extends Exception {

    public BreezometerResultNotFoundException(String s) {
        super(s);
    }
}