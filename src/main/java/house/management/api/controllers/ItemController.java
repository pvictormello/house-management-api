package house.management.api.controllers;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import house.management.api.model.Item;
import house.management.api.model.Room;
import house.management.api.model.dto.ItemRequest;
import house.management.api.services.ItemService;
import house.management.api.services.RoomService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;
    private final RoomService roomService;

    public ItemController(ItemService itemService, RoomService roomService) {
        this.itemService = itemService;
        this.roomService = roomService;
    }

    @PostMapping
    public ResponseEntity<Item> saveItem(@RequestBody ItemRequest request) {
        Room room = roomService.getRoomById(request.getRoomId());

        if (room == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Item itemToSave = new Item(request);
        itemToSave.setRoom(room);

        return ResponseEntity.status(HttpStatus.CREATED).body(itemService.saveItem(itemToSave));
    }

    @PatchMapping("/{itemId}/toggle-purchased")
    public ResponseEntity<Void> toggleIsPurchased(@PathVariable UUID itemId) {
        Item item = itemService.getItemById(itemId);
        if (item != null) {
            item.setIsPurchased(!item.getIsPurchased());
            itemService.saveItem(item);
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
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<Item> updateItem(@PathVariable UUID itemId, @RequestBody ItemRequest request) {
        Item item = itemService.getItemById(itemId);

        if (item == null) {
            return ResponseEntity.notFound().build();
        } else {
            Item itemToUpdate = new Item(request);
            Room room = roomService.getRoomById(request.getRoomId());

            if (room == null) {
                return ResponseEntity.notFound().build();
            }
            itemToUpdate.setId(itemId);
            itemToUpdate.setRoom(room);
            itemToUpdate.setPurchaseOptions(item.getPurchaseOptions());
            itemToUpdate.setIsPurchased(item.getIsPurchased());
            itemToUpdate.setCreatedAt(item.getCreatedAt());
            
            return ResponseEntity.ok().body(itemService.saveItem(itemToUpdate));
        }
    }
}
