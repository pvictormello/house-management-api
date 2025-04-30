package house.management.api.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import house.management.api.model.Item;
import house.management.api.model.Room;
import house.management.api.model.dto.ItemsRequest;
import house.management.api.services.ItemService;
import house.management.api.services.RoomService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;
    private final RoomService roomService;

    public ItemController(ItemService itemService, RoomService roomService) {
        this.itemService = itemService;
        this.roomService = roomService;
    }

    @GetMapping
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    @PostMapping
    public ResponseEntity<Void> saveItem(@Valid @RequestBody ItemsRequest request) {
        Room room = roomService.getRoomById(request.getRoomId());

        Item itemToSave = new Item(request);
        itemToSave.setRoom(room);

        itemService.saveItem(itemToSave);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{itemId}/toggle-purchased")
    public ResponseEntity<Void> toggleIsPurchased(@PathVariable UUID itemId) {
        Item item = itemService.getItemById(itemId);
        if (item != null) {
            item.setIsPurchased(!item.getIsPurchased());
            itemService.updateItem(item);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable UUID itemId) {
        Item item = itemService.getItemById(itemId);
        if (item != null) {
            itemService.deleteItem(itemId);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        itemService.deleteAllItems();
        return ResponseEntity.ok().build();
    }
}
