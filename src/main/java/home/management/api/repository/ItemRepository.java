package home.management.api.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import home.management.api.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, UUID> {

    List<Item> findByRoomId(UUID roomId);
    // You can define custom query methods here if needed
}
