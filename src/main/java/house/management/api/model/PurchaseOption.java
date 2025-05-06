package house.management.api.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import house.management.api.model.dto.PurchaseOptionRequest;
import jakarta.persistence.CascadeType;
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

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    @JsonIgnore
    private Item item;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "metadata_id", referencedColumnName = "id")
    private Metadata metadata;

    @OneToOne
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private User user;

    public PurchaseOption() {
    }

    public PurchaseOption(UUID id, String url, Boolean isFavorite, Item item, Metadata metadata, User user) {
        this.id = id;
        this.url = url;
        this.isFavorite = isFavorite;
        this.item = item;
        this.metadata = metadata;
        this.user = user;
    }

    public PurchaseOption(PurchaseOptionRequest request, Item item, Metadata metadata, User user) {
        this.item = item;
        this.url = request.getUrl();
        this.isFavorite = false;
        this.metadata = metadata;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
