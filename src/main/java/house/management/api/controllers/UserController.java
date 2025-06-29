package house.management.api.controllers;

import house.management.api.services.S3Service;
import house.management.api.services.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import house.management.api.model.User;
import house.management.api.model.dto.UserRequest;
import house.management.api.model.dto.UserResponse;

@RestController
@RequestMapping("/users")
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final S3Service s3Service;

    UserController(UserService userService, PasswordEncoder passwordEncoder, S3Service s3Service) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.s3Service = s3Service;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestPart("user") UserRequest request,
            @RequestPart("file") MultipartFile profileImage) {
        if (userService.existsByUsername(request.getUsername()) || profileImage.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        String prefix = request.getUsername();
        String profileImageFileName;
        
        try {
            profileImageFileName = s3Service.uploadFile(profileImage, prefix);
        } catch (Exception e) {
            profileImageFileName = null;
        }

        User user = new User(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setProfileImageUrl(profileImageFileName);

        User savedUser = userService.saveUser(user);
        UserResponse response = new UserResponse(savedUser);
        
        // Set the full URL for the profile image in the response
        if (savedUser.getProfileImageUrl() != null) {
            response.setProfileImageUrl(s3Service.getFullImageUrl(savedUser.getProfileImageUrl()));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}