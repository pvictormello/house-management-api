package house.management.api.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import house.management.api.model.dto.PurchaseOptionRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity(name = "purchase_options")
public class PurchaseOption {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "url", columnDefinition = "TEXT", nullable = false)
    private String url;
    
    @Column(name = "is_favorite", nullable = false)
    private Boolean isFavorite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    @JsonIgnore
    private Item item;

    @OneToOne
    @JoinColumn(name = "metadata_id", referencedColumnName = "id")
    private Metadata metadata;

    public PurchaseOption() {
    }

    public PurchaseOption(UUID id, Item item, String url, Boolean isFavorite, Metadata metadata) {
        this.id = id;
        this.item = item;
        this.url = url;
        this.isFavorite = isFavorite;
        this.metadata = metadata;
    }

    public PurchaseOption(PurchaseOptionRequest request, Item item, Metadata metadata) {
        this.item = item;
        this.url = request.getUrl();
        this.isFavorite = false;
        this.metadata = metadata;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(Boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }
}
