package home.management.api.repository;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import home.management.api.model.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {
    // You can define custom query methods here if needed

}
