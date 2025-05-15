package house.management.api.controllers;

import org.springframework.web.bind.annotation.RestController;

import house.management.api.model.Room;
import house.management.api.model.dto.RoomHome;
import house.management.api.model.dto.RoomRequest;
import house.management.api.model.dto.RoomResponse;
import house.management.api.services.RoomService;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    public ResponseEntity<RoomResponse> saveRoom(@RequestBody RoomRequest request) {
        Room roomToSave = new Room(request);
        Room savedRoom = roomService.saveRoom(roomToSave);

        if(savedRoom == null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(new RoomResponse(savedRoom));
    }

    @GetMapping
    public ResponseEntity<RoomHome> getRooms() {
        List<Room> rooms = roomService.getAllRooms();
        
        return ResponseEntity.ok(new RoomHome(rooms));
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

    @DeleteMapping("/{roomId}")
    public ResponseEntity<Void> deleteRoom(@PathVariable UUID roomId){
        Room room = roomService.getRoomById(roomId);
        if (room != null) {
            roomService.deleteRoom(room);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
