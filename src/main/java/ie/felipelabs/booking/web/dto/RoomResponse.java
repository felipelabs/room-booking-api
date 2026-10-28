package ie.felipelabs.booking.web.dto;
import ie.felipelabs.booking.domain.Room;
import java.util.UUID;
public record RoomResponse(UUID id, String code, String name, int capacity) {
    public static RoomResponse from(Room room) { return new RoomResponse(room.getId(), room.getCode(), room.getName(), room.getCapacity()); }
}
