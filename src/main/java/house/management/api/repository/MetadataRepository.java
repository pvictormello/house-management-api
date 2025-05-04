package house.management.api.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import house.management.api.model.Metadata;

@Repository
public interface MetadataRepository extends JpaRepository<Metadata, UUID> {
    
}
