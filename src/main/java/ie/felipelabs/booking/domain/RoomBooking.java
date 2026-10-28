package ie.felipelabs.booking.domain;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
@Entity @Table(name = "bookings")
public class RoomBooking {
    @Id private UUID id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "room_id", nullable = false) private Room room;
    @Column(name = "requester_name", nullable = false, length = 120) private String requesterName;
    @Column(name = "requester_email", nullable = false, length = 254) private String requesterEmail;
    @Column(name = "starts_at", nullable = false) private Instant startsAt;
    @Column(name = "ends_at", nullable = false) private Instant endsAt;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private BookingStatus status;
    @Column(name = "created_at", nullable = false, updatable = false) private Instant createdAt;
    protected RoomBooking() {}
    public RoomBooking(Room room, String requesterName, String requesterEmail, Instant startsAt, Instant endsAt, Instant createdAt) {
        this.id = UUID.randomUUID(); this.room = room; this.requesterName = requesterName;
        this.requesterEmail = requesterEmail; this.startsAt = startsAt; this.endsAt = endsAt;
        this.createdAt = createdAt; this.status = BookingStatus.CONFIRMED;
    }
    public void cancel() { this.status = BookingStatus.CANCELLED; }
    public UUID getId() { return id; }
    public Room getRoom() { return room; }
    public String getRequesterName() { return requesterName; }
    public String getRequesterEmail() { return requesterEmail; }
    public Instant getStartsAt() { return startsAt; }
    public Instant getEndsAt() { return endsAt; }
    public BookingStatus getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
}
