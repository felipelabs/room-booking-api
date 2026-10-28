package ie.felipelabs.booking.web.dto;
import ie.felipelabs.booking.domain.RoomBooking;
import java.time.Instant;
import java.util.UUID;
public record BookingResponse(UUID id, UUID roomId, String roomCode, String requesterName, String requesterEmail,
        Instant startsAt, Instant endsAt, String status, Instant createdAt) {
    public static BookingResponse from(RoomBooking b) {
        return new BookingResponse(b.getId(), b.getRoom().getId(), b.getRoom().getCode(), b.getRequesterName(),
                b.getRequesterEmail(), b.getStartsAt(), b.getEndsAt(), b.getStatus().name(), b.getCreatedAt());
    }
}
