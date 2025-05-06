package house.management.api.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import house.management.api.model.Item;
import house.management.api.repository.ItemRepository;

@Service
public class ItemService {
    private ItemRepository itemRepository;
    
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    public Item getItemById(UUID id) {
        return itemRepository.findById(id).orElse(null);
    }

    public void deleteItem(UUID id) {
        itemRepository.deleteById(id);
    }
}
