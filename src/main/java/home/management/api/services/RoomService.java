package home.management.api.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import home.management.api.model.Room;
import home.management.api.repository.RoomRepository;

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

    public void deleteRoom(UUID id) {
        roomRepository.deleteById(id);
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
