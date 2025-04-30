package house.management.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import house.management.api.dto.PurchaseOptionRequest;
import house.management.api.model.Item;
import house.management.api.model.PurchaseOption;
import house.management.api.services.ItemService;
import house.management.api.services.PurchaseOptionService;
import jakarta.validation.Valid;

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


@RestController
@RequestMapping("/purchase-options")
public class PurchaseOptionController {
    
    private final PurchaseOptionService purchaseOptionService;
    private final ItemService itemService;

    public PurchaseOptionController(PurchaseOptionService purchaseOptionService, ItemService itemService) {
        this.purchaseOptionService = purchaseOptionService;
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<Void> savePurchaseOption(@Valid @RequestBody PurchaseOptionRequest request) {
        Item item = itemService.getItemById(request.getItemId());
        if (item == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        purchaseOptionService.savePurchaseOption(new PurchaseOption(request, item));

        return ResponseEntity.status(HttpStatus.CREATED).build();
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
            return ResponseEntity.ok().build();
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
            purchaseOptionService.updatePurchaseOption(purchaseOption);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    

}
