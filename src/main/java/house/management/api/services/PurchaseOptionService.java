package house.management.api.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import house.management.api.model.PurchaseOption;
import house.management.api.repository.PurchaseOptionRepository;
import jakarta.transaction.Transactional;

@Service
public class PurchaseOptionService {

    private PurchaseOptionRepository purchaseOptionRepository;

    public PurchaseOptionService(PurchaseOptionRepository purchaseOptionRepository) {
        this.purchaseOptionRepository = purchaseOptionRepository;
    }

    public PurchaseOption savePurchaseOption(PurchaseOption purchaseOption) {
        return purchaseOptionRepository.save(purchaseOption);
    }

    public PurchaseOption getPurchaseOptionById(UUID id) {
        return purchaseOptionRepository.findById(id).orElse(null);
    }

    public void deletePurchaseOption(UUID id) {
        purchaseOptionRepository.deleteById(id);
    }

    public List<PurchaseOption> getAllPurchaseOptions() {
        return purchaseOptionRepository.findAll();
    }

    public void updatePurchaseOption(PurchaseOption purchaseOption) {
        purchaseOptionRepository.save(purchaseOption);
    }

    @Transactional
    public void unmarkFavoritePurchaseOptionPerItem(UUID itemId){
        List<PurchaseOption> purchaseOptions = purchaseOptionRepository.findByItem_Id(itemId);
        purchaseOptions.stream()
            .filter(PurchaseOption::getIsFavorite)
            .forEach(purchaseOption -> {
                purchaseOption.setIsFavorite(false);
                purchaseOptionRepository.save(purchaseOption);
            });
    }
}
