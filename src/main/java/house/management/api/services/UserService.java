package house.management.api.services;

import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import house.management.api.model.User;
import house.management.api.repository.UserRepository;
import house.management.api.util.JwtUtil;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final S3Service s3Service;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, S3Service s3Service, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.s3Service = s3Service;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User getUserById(UUID id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null && user.getProfileImageUrl() != null) {
            user.setProfileImageUrl(s3Service.getFullImageUrl(user.getProfileImageUrl()));
        }
        return user;
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public User getUserByToken(String token) {
        String username = jwtUtil.extractUsername(token);
        if (username == null) {
            return null;
        }

        return userRepository.findByUsername(username)
                .orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getAuthorities())
                .build();
    }
}
