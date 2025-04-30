package house.management.api.controllers;

import org.springframework.web.bind.annotation.RestController;

import house.management.api.dto.RoomRequest;
import house.management.api.model.Room;
import house.management.api.services.RoomService;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    public ResponseEntity<Void> saveRoom(@Valid @RequestBody RoomRequest request) {

        roomService.saveRoom(new Room(request));

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<Room>> getRooms() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    @GetMapping("/{slug}")
    public ResponseEntity<Room> getRoomBySlug(@PathVariable String slug) {
        Room room = roomService.getRoomBySlug(slug);
        if (room != null) {
            return ResponseEntity.ok(room);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    

}
