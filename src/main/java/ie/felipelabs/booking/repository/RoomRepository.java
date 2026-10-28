package ie.felipelabs.booking.repository;
import ie.felipelabs.booking.domain.Room;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.*;
public interface RoomRepository extends JpaRepository<Room, UUID> {
    List<Room> findAllByActiveTrueOrderByCodeAsc();
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select r from Room r where r.id = :id")
    Optional<Room> findByIdForUpdate(@Param("id") UUID id);
}
