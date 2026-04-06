package com.mikadev.householdtracker.user;

import com.mikadev.householdtracker.user.dto.UserGetDTO;
import com.mikadev.householdtracker.user.dto.UserPostDTO;
import com.mikadev.householdtracker.user.dto.UserPutDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserGetDTO>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserGetDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping
    public ResponseEntity<UserGetDTO> save(@Valid @RequestBody UserPostDTO userPostDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userPostDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserGetDTO> update(@PathVariable Long id,
                                             @Valid @RequestBody UserPutDTO userPutDTO) {
        return ResponseEntity.ok(userService.update(id, userPutDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}