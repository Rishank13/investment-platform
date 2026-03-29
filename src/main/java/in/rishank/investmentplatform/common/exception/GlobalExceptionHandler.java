package in.rishank.investmentplatform.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleResourceNotFound(ResourceNotFoundException ex){
        return ex.getMessage();
    }

    @ExceptionHandler(InsufficientDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInsufficientData(InsufficientDataException ex){
        return ex.getMessage();
    }

    @ExceptionHandler(InvalidMetricException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidMetric(InsufficientDataException ex) {
        return ex.getMessage();
    }
}
