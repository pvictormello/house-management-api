package house.management.api.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class MetadataService {

    public String extractImageUrl(String url) throws IOException {
        Document doc = Jsoup.connect(url)
                .userAgent(
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                .header("Accept-Language", "en-US,en;q=0.9")
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("Accept", "text/html,application/xhtml+xml")
                .timeout(10000)
                .get();

        String[] selectors = {
                "meta[property=og:image]",
                "meta[name=twitter:image]",
                "img#landingImage",
                "img#imgBlkFront",
                "img.ui-pdp-image",
                "img[data-a-image-name='landingImage']",
                "div.imgTagWrapper img",
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
}
