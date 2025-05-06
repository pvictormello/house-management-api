package house.management.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import house.management.api.model.Item;
import house.management.api.model.Metadata;
import house.management.api.model.PurchaseOption;
import house.management.api.model.User;
import house.management.api.model.dto.PurchaseOptionRequest;
import house.management.api.services.ItemService;
import house.management.api.services.MetadataService;
import house.management.api.services.PurchaseOptionService;
import house.management.api.services.UserService;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/purchase-options")
public class PurchaseOptionController {

    private final PurchaseOptionService purchaseOptionService;
    private final ItemService itemService;
    private final MetadataService metadataService;
    private final UserService userService;

    public PurchaseOptionController(PurchaseOptionService purchaseOptionService, ItemService itemService,
            MetadataService metadataService, UserService userService) {
        this.purchaseOptionService = purchaseOptionService;
        this.itemService = itemService;
        this.metadataService = metadataService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<PurchaseOption> savePurchaseOption(@RequestBody PurchaseOptionRequest purchaseOption,
            HttpServletRequest request) throws IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;

        jwt = authHeader.substring(7);

        Item item = itemService.getItemById(purchaseOption.getItemId());
        if (item == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Metadata metadata = metadataService.extractMetadata(purchaseOption.getUrl());
        User user = userService.getUserByToken(jwt);
        PurchaseOption purchaseOptionToSave = new PurchaseOption(purchaseOption, item, metadata, user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(purchaseOptionService.savePurchaseOption(purchaseOptionToSave));
    }

    @GetMapping
    public ResponseEntity<List<PurchaseOption>> getAllPurchaseOptions() {
        List<PurchaseOption> purchaseOptions = purchaseOptionService.getAllPurchaseOptions();

        if (purchaseOptions.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(purchaseOptions);
        }
    }

    @DeleteMapping("/{purchaseOptionId}")
    public ResponseEntity<Void> deletePurchaseOption(@PathVariable UUID purchaseOptionId) {
        PurchaseOption purchaseOption = purchaseOptionService.getPurchaseOptionById(purchaseOptionId);
        if (purchaseOption != null) {
            purchaseOptionService.deletePurchaseOption(purchaseOption.getId());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{purchaseOptionId}/toggle-favorite")
    public ResponseEntity<Void> toggleIsFavorite(@PathVariable UUID purchaseOptionId) {
        PurchaseOption purchaseOption = purchaseOptionService.getPurchaseOptionById(purchaseOptionId);
        if (purchaseOption != null) {
            purchaseOptionService.unmarkFavoritePurchaseOptionPerItem(purchaseOption.getItem().getId());
            purchaseOption.setIsFavorite(!purchaseOption.getIsFavorite());
            purchaseOptionService.savePurchaseOption(purchaseOption);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{purchaseOptionId}")
    public ResponseEntity<PurchaseOption> updatePurchaseOption(@PathVariable UUID purchaseOptionId,
            @RequestBody PurchaseOptionRequest request) {

        PurchaseOption purchaseOption = purchaseOptionService.getPurchaseOptionById(purchaseOptionId);
        if (purchaseOption != null) {
            if (request.getMetadata() != null) {

                purchaseOption.getMetadata().setTitle(request.getMetadata().getTitle());
                purchaseOption.getMetadata().setImageUrl(request.getMetadata().getImageUrl());
                purchaseOption.getMetadata().setPrice(request.getMetadata().getPrice());

                return ResponseEntity.ok().body(purchaseOptionService.savePurchaseOption(purchaseOption));
            } else {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }

    }

}
