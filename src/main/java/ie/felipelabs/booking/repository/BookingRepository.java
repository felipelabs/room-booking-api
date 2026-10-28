package ie.felipelabs.booking.repository;
import ie.felipelabs.booking.domain.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.time.Instant;
import java.util.UUID;
public interface BookingRepository extends JpaRepository<RoomBooking, UUID> {
    @Query("select count(b) from RoomBooking b where b.room.id = :roomId and b.status = :status and b.startsAt < :endsAt and b.endsAt > :startsAt")
    long countOverlapping(@Param("roomId") UUID roomId, @Param("startsAt") Instant startsAt,
                          @Param("endsAt") Instant endsAt, @Param("status") BookingStatus status);
    Page<RoomBooking> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
