package home.management.api.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import home.management.api.model.Item;
import home.management.api.repository.ItemRepository;

@Service
public class ItemService {
    private ItemRepository itemRepository;
    
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public Item getItemById(UUID id) {
        return itemRepository.findById(id).orElse(null);
    }

    public void deleteItem(UUID id) {
        itemRepository.deleteById(id);
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public void updateItem(Item item) {
        itemRepository.save(item);
    }

    public List<Item> getItemsByRoomId(UUID roomId) {
        return itemRepository.findByRoom_Id(roomId);
    }

    public void deleteAllItems() {
        itemRepository.deleteAll();
    }
}
