package engine.excpetions;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
class GlobalExceptionHandler extends RuntimeException {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((err) -> {
            String fieldName = ((FieldError)err).getField();
            String message = err.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return errors;
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Content not found")
    @ExceptionHandler(IndexOutOfBoundsException.class)
    public Map<String, String> notFoundException(IndexOutOfBoundsException ex){
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", "Information not found");
        errorResponse.put("error", ex.getClass().getSimpleName());
        return errorResponse;
    }
}
