package ie.felipelabs.booking.web.dto;
import jakarta.validation.constraints.*;
import java.time.Instant;
import java.util.UUID;
public record CreateBookingRequest(@NotNull UUID roomId, @NotBlank @Size(max = 120) String requesterName,
        @NotBlank @Email @Size(max = 254) String requesterEmail, @NotNull Instant startsAt, @NotNull Instant endsAt) {}
