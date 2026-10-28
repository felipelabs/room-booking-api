package ie.felipelabs.booking.service;
import ie.felipelabs.booking.domain.*;
import ie.felipelabs.booking.repository.*;
import ie.felipelabs.booking.web.dto.*;
import ie.felipelabs.booking.web.error.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Clock;
import java.time.Instant;
import java.util.UUID;
@Service
public class BookingService {
    private final RoomRepository rooms;
    private final BookingRepository bookings;
    private final Clock clock;
    public BookingService(RoomRepository rooms, BookingRepository bookings, Clock clock) {
        this.rooms = rooms; this.bookings = bookings; this.clock = clock;
    }
    @Transactional
    public BookingResponse create(CreateBookingRequest request) {
        if (!request.startsAt().isAfter(clock.instant()))
            throw new InvalidBookingException("startsAt must be in the future");
        if (!request.endsAt().isAfter(request.startsAt()))
            throw new InvalidBookingException("endsAt must be after startsAt");
        Room room = rooms.findByIdForUpdate(request.roomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));
        if (!room.isActive()) throw new BookingConflictException("Room is not active");
        if (bookings.countOverlapping(room.getId(), request.startsAt(), request.endsAt(), BookingStatus.CONFIRMED) > 0)
            throw new BookingConflictException("The room already has a booking during this time");
        RoomBooking booking = new RoomBooking(room, request.requesterName().trim(),
                request.requesterEmail().trim().toLowerCase(), request.startsAt(), request.endsAt(), Instant.now(clock));
        return BookingResponse.from(bookings.save(booking));
    }
    @Transactional(readOnly = true)
    public Page<BookingResponse> list(Pageable pageable) {
        return bookings.findAllByOrderByCreatedAtDesc(pageable).map(BookingResponse::from);
    }
    @Transactional(readOnly = true)
    public BookingResponse get(UUID id) {
        return BookingResponse.from(bookings.findById(id).orElseThrow(() -> new ResourceNotFoundException("Booking not found")));
    }
    @Transactional
    public BookingResponse cancel(UUID id) {
        RoomBooking booking = bookings.findById(id).orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
        booking.cancel();
        return BookingResponse.from(booking);
    }
}
