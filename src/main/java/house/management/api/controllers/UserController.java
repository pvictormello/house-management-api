package house.management.api.controllers;

import house.management.api.services.S3Service;
import house.management.api.services.UserService;

import org.springframework.beans.factory.annotation.Value;
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

@RestController
@RequestMapping("/users")
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final S3Service s3Service;

    @Value("${aws.s3.bucket-url}")
    private String bucketUrl;

    UserController(UserService userService, PasswordEncoder passwordEncoder, S3Service s3Service) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.s3Service = s3Service;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestPart("user") UserRequest request,
            @RequestPart("file") MultipartFile profileImage) {
        if (userService.existsByUsername(request.getUsername()) || profileImage.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        String prefix = request.getUsername();
        String profileImageUrl;
        
        try {
            profileImageUrl = s3Service.uploadFile(profileImage, prefix);

        } catch (Exception e) {
            profileImageUrl = null;
        }

        User user = new User(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setProfileImageUrl(profileImageUrl);

        user = userService.saveUser(user);
        user.setProfileImageUrl(bucketUrl + "/" + user.getProfileImageUrl());
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}