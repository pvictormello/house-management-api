package house.management.api.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import house.management.api.model.Room;
import house.management.api.repository.RoomRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class RoomService {
    private final RoomRepository roomRepository;
    private final EntityManager entityManager;
    
    public RoomService(RoomRepository roomRepository, EntityManager entityManager) {
        this.roomRepository = roomRepository;
        this.entityManager = entityManager;
    }

    public Room saveRoom(Room room) {
        Room savedRoom = roomRepository.saveAndFlush(room);
        entityManager.refresh(savedRoom);
        return savedRoom;
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
