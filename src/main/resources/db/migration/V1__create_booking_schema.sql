CREATE TABLE rooms (
    id UUID PRIMARY KEY,
    code VARCHAR(24) NOT NULL UNIQUE,
    name VARCHAR(120) NOT NULL,
    capacity INTEGER NOT NULL CHECK (capacity > 0),
    active BOOLEAN NOT NULL DEFAULT TRUE
);
CREATE TABLE bookings (
    id UUID PRIMARY KEY,
    room_id UUID NOT NULL REFERENCES rooms(id),
    requester_name VARCHAR(120) NOT NULL,
    requester_email VARCHAR(254) NOT NULL,
    starts_at TIMESTAMPTZ NOT NULL,
    ends_at TIMESTAMPTZ NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT booking_positive_duration CHECK (ends_at > starts_at)
);
CREATE INDEX bookings_room_time_idx ON bookings(room_id, starts_at, ends_at);
CREATE INDEX bookings_status_created_idx ON bookings(status, created_at DESC);
INSERT INTO rooms (id, code, name, capacity) VALUES
('10000000-0000-0000-0000-000000000001', 'DUB-1', 'Docklands Meeting Room', 8),
('10000000-0000-0000-0000-000000000002', 'DUB-2', 'Harbour Boardroom', 14),
('10000000-0000-0000-0000-000000000003', 'DUB-3', 'Canal Focus Room', 4);
