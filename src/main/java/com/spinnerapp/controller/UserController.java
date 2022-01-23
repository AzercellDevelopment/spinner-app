package com.spinnerapp.controller;

import com.spinnerapp.model.dto.UserRequestDto;
import com.spinnerapp.model.dto.UserResponseDto;
import com.spinnerapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long userId) {
        UserResponseDto user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> addUser(@RequestBody UserRequestDto userRequestDto) {
        UserResponseDto userResponseDto = userService.addUser(userRequestDto);
        return ResponseEntity.ok(userResponseDto);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long userId,
                                                      @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto userResponseDto = userService.updateUser(userId, userRequestDto);
        return ResponseEntity.ok(userResponseDto);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<UserResponseDto> deleteUser(@PathVariable Long userId) {
        UserResponseDto userResponseDto = userService.deleteUser(userId);
        return ResponseEntity.ok(userResponseDto);
    }
}
