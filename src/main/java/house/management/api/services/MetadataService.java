package house.management.api.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import house.management.api.model.Metadata;
import house.management.api.repository.MetadataRepository;
import jakarta.transaction.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

@Service
public class MetadataService {

    private final MetadataRepository metadataRepository;

    public MetadataService(MetadataRepository metadataRepository) {
        this.metadataRepository = metadataRepository;
    }

    @Transactional
    public Metadata saveMetadata(Metadata metadata) {
        return metadataRepository.save(metadata);
    }

    public Metadata getMetadataById(UUID id) {
        return metadataRepository.findById(id).orElse(null);
    }

    public Metadata extractMetadata(String url) throws IOException {
        Document doc = Jsoup.connect(url)
                .userAgent(
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                .timeout(10000)
                .get();

        Metadata metadata = new Metadata();
        String title = getMetaContent(doc, "title");

        if (title == null) {
            title = doc.title();
        }

        metadata.setUrl(url);
        metadata.setTitle(title);
        metadata.setImageUrl(extractImageUrl(doc));

        String price = getPrice(doc);

        metadata.setPrice(price != null ? convertMoneyStringToBigDecimal(price) : null);

        return saveMetadata(metadata);
    }

    private String getPrice(Document doc) {
        String[] priceSelectors = {
                "[itemprop=price]",
                ".price", ".product-price", ".price-value",
                "[data-product-price]", ".current-price",
                ".priceToPay", ".tr-productPrice__price", "[data-testid=price-value]"
        };

        for (String selector : priceSelectors) {
            Element priceElement = doc.selectFirst(selector);
            if (priceElement != null) {
                String price = priceElement.text().trim();
                return price.isEmpty() || price.isBlank() ? priceElement.attr("content") : price;
            }
        }

        return null;
    }

    private String extractImageUrl(Document doc) {
        String[] selectors = {
                "meta[property=og:image]",
                "meta[name=twitter:image]",
                "img#landingImage",
                "img#imgBlkFront",
                ".ui-pdp-image",
                "img[class=ui-pdp-image]",
                "figure[class=ui-pdp-gallery__figure]",
                "img[data-a-image-name='landingImage']",
                "[itemprop=image]",
                "[data-testid=image-selected-thumbnail]",
                "img[data-old-hires]"
        };

        for (String selector : selectors) {
            Elements elements = doc.select(selector);
            if (!elements.isEmpty()) {
                String attr = selector.startsWith("meta") ? "content" : "src";
                if (selector.contains("[data-old-hires]"))
                    attr = "data-old-hires";

                String imageUrl = elements.first().attr(attr);
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    return imageUrl;
                }
            }
        }

        return "";
    }

    private String getMetaContent(Document doc, String... names) {
        for (String name : names) {
            Element meta = doc.selectFirst("meta[name=" + name + "], meta[property=" + name + "]");
            if (meta != null) {
                return meta.attr("content");
            }
        }
        return null;
    }

    private BigDecimal convertMoneyStringToBigDecimal(String priceString) {
        String sanitizedPrice = priceString.replaceAll("[^\\d.,]", "").replace(",", ".");
        try {
            return new BigDecimal(sanitizedPrice);
        } catch (NumberFormatException e) {
            return null; // Handle the case where conversion fails
        }
    }
}
