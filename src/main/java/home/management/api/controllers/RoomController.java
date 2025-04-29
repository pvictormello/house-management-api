package home.management.api.controllers;

import org.springframework.web.bind.annotation.RestController;

import home.management.api.dto.RoomRequest;
import home.management.api.model.Room;
import home.management.api.services.RoomService;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/room")
    public ResponseEntity<Void> saveRoom(@Valid @RequestBody RoomRequest request) {

        roomService.saveRoom(new Room(request));

        return ResponseEntity.ok().build();
    }

    @GetMapping("/rooms")
    public ResponseEntity<List<Room>> getRooms() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<Room> getRoomById(@PathVariable UUID roomId) {
        return ResponseEntity.ok(roomService.getRoomById(roomId));
    }

}
