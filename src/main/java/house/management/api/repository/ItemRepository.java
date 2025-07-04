package house.management.api.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import house.management.api.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, UUID> {
    List<Item> findByRoom_Id(UUID roomId);
}
