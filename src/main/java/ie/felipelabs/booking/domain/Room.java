package ie.felipelabs.booking.domain;
import jakarta.persistence.*;
import java.util.UUID;
@Entity @Table(name = "rooms")
public class Room {
    @Id private UUID id;
    @Column(nullable = false, unique = true, length = 24) private String code;
    @Column(nullable = false, length = 120) private String name;
    @Column(nullable = false) private int capacity;
    @Column(nullable = false) private boolean active;
    protected Room() {}
    public UUID getId() { return id; }
    public String getCode() { return code; }
    public String getName() { return name; }
    public int getCapacity() { return capacity; }
    public boolean isActive() { return active; }
}
