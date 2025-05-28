package com.david.usuario.controller;

import com.david.usuario.business.UserService;
import com.david.usuario.business.dto.AddressDTO;
import com.david.usuario.business.dto.PhoneDTO;
import com.david.usuario.business.dto.UserDTO;
import com.david.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.createUser(userDTO));
    }

    @PostMapping("/login")
    public String login(@RequestBody UserDTO userDTO) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword()));
        return "Bearer " + jwtUtil.generateToken(authentication.getName());
    }

    @GetMapping
    public ResponseEntity<UserDTO> findUserByEmail(@RequestParam(value = "email", required = false) String email) {
        return ResponseEntity.ok(userService.findUserByEmail(email));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteUserByEmail(@PathVariable String email) {
        userService.deleteUserByEmail(email);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<UserDTO> updateUserData(
            @RequestBody UserDTO userDTO,
            @RequestHeader("Authorization") String token
    ) {
        return ResponseEntity.ok(userService.updateUserData(token, userDTO));
    }

    @PutMapping("/address")
    public ResponseEntity<AddressDTO> updateAddress(@RequestBody AddressDTO addressDTO, @RequestParam("id") Long id) {
        return ResponseEntity.ok(userService.updateAddress(id, addressDTO));
    }

    @PutMapping("/phone")
    public ResponseEntity<PhoneDTO> updatePhone(@RequestBody PhoneDTO phoneDTO, @RequestParam("id") Long id) {
        return ResponseEntity.ok(userService.updatePhone(id, phoneDTO));
    }

    @PostMapping("/address")
    public ResponseEntity<AddressDTO> newAddress(
            @RequestHeader("Authorization") String token,
            @RequestBody AddressDTO addressDTO
    ) {
        return ResponseEntity.ok(userService.newAddress(token, addressDTO));
    }

    @PostMapping("/phone")
    public ResponseEntity<PhoneDTO> newPhone(
            @RequestHeader("Authorization") String token,
            @RequestBody PhoneDTO phoneDTO
    ) {
        return ResponseEntity.ok(userService.newPhone(token, phoneDTO));
    }
}
