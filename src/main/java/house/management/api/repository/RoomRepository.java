package house.management.api.repository;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import house.management.api.model.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {
    Room findBySlug(String slug);
}
