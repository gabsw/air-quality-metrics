package tqs.air.quality.metrics.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResultNotFoundException extends RuntimeException {

    public ResultNotFoundException() {
    }

    public ResultNotFoundException(String s) {
        super(s);
    }

    public ResultNotFoundException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public ResultNotFoundException(Throwable throwable) {
        super(throwable);
    }

    public ResultNotFoundException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}