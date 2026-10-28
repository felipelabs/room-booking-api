package ie.felipelabs.booking.web;
import ie.felipelabs.booking.repository.RoomRepository;
import ie.felipelabs.booking.web.dto.RoomResponse;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/v1/rooms")
public class RoomController {
    private final RoomRepository rooms;
    public RoomController(RoomRepository rooms) { this.rooms = rooms; }
    @GetMapping public List<RoomResponse> list() {
        return rooms.findAllByActiveTrueOrderByCodeAsc().stream().map(RoomResponse::from).toList();
    }
}
