package tqs.air.quality.metrics.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ApiServerException extends RuntimeException {

    public ApiServerException(String s) {
        super(s);
    }

    public ApiServerException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
