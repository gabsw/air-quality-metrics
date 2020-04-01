package tqs.air.quality.metrics.exception;

public class UnexpectedException extends RuntimeException {

    public UnexpectedException() {
    }

    public UnexpectedException(String s) {
        super(s);
    }

    public UnexpectedException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public UnexpectedException(Throwable throwable) {
        super(throwable);
    }

    public UnexpectedException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
