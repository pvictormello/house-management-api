package house.management.api.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import house.management.api.model.PurchaseOption;

@Repository
public interface PurchaseOptionRepository extends JpaRepository<PurchaseOption, UUID> {
    List<PurchaseOption> findByItem_Id(UUID itemId);
}
