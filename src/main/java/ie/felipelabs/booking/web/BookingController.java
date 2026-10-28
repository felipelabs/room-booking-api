package ie.felipelabs.booking.web;
import ie.felipelabs.booking.service.BookingService;
import ie.felipelabs.booking.web.dto.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.UUID;
@RestController @RequestMapping("/api/v1/bookings")
public class BookingController {
    private final BookingService service;
    public BookingController(BookingService service) { this.service = service; }
    @PostMapping public ResponseEntity<BookingResponse> create(@Valid @RequestBody CreateBookingRequest request) {
        BookingResponse response = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/bookings/" + response.id())).body(response);
    }
    @GetMapping public Page<BookingResponse> list(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
        if (page < 0 || size < 1 || size > 100) throw new IllegalArgumentException("page must be >= 0 and size must be between 1 and 100");
        return service.list(PageRequest.of(page, size));
    }
    @GetMapping("/{id}") public BookingResponse get(@PathVariable UUID id) { return service.get(id); }
    @PostMapping("/{id}/cancellation") public BookingResponse cancel(@PathVariable UUID id) { return service.cancel(id); }
}
