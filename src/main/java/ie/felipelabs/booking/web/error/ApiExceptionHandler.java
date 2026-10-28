package ie.felipelabs.booking.web.error;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;
@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    ProblemDetail notFound(ResourceNotFoundException e) { return problem(HttpStatus.NOT_FOUND, "Resource not found", e.getMessage()); }
    @ExceptionHandler(BookingConflictException.class)
    ProblemDetail conflict(BookingConflictException e) { return problem(HttpStatus.CONFLICT, "Booking conflict", e.getMessage()); }
    @ExceptionHandler(InvalidBookingException.class)
    ProblemDetail invalidBooking(InvalidBookingException e) { return problem(HttpStatus.BAD_REQUEST, "Invalid booking", e.getMessage()); }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ProblemDetail invalidRequest(MethodArgumentNotValidException e) {
        String detail = e.getBindingResult().getFieldErrors().stream()
                .map(x -> x.getField() + ": " + x.getDefaultMessage()).collect(Collectors.joining("; "));
        return problem(HttpStatus.BAD_REQUEST, "Invalid request", detail);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    ProblemDetail invalidParameter(IllegalArgumentException e) { return problem(HttpStatus.BAD_REQUEST, "Invalid request", e.getMessage()); }
    private ProblemDetail problem(HttpStatus status, String title, String detail) {
        ProblemDetail body = ProblemDetail.forStatusAndDetail(status, detail);
        body.setTitle(title);
        return body;
    }
}
