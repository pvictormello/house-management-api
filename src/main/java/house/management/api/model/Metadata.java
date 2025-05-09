package house.management.api.model;

import java.math.BigDecimal;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name="metadata")
public class Metadata {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "url", columnDefinition = "TEXT", nullable = false)
    @JsonIgnore
    private String url;
    @Column(name = "title", columnDefinition = "TEXT")
    private String title;
    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;
    @Column(name = "price", columnDefinition = "NUMERIC(10,2)")
    private BigDecimal price;

    public Metadata(){
    }

    public Metadata(UUID id, String url, String title, String imageUrl, BigDecimal price) {
        this.id = id;
        this.url = url;
        this.title = title;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public Metadata(BigDecimal price) {
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
