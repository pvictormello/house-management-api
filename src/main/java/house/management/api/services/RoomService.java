package house.management.api.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import house.management.api.model.Room;
import house.management.api.repository.RoomRepository;

@Service
public class RoomService {
    private RoomRepository roomRepository;
    
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public void saveRoom(Room room) {
        roomRepository.save(room);
    }

    public Room getRoomById(UUID id) {
        return roomRepository.findById(id).orElse(null);
    }

    public void deleteRoom(Room room) {
        roomRepository.delete(room);
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public void updateRoom(Room room) {
        roomRepository.save(room);
    }

    public Room getRoomBySlug(String slug) {
        return roomRepository.findBySlug(slug);
    }
}
