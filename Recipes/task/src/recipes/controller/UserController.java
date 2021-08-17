package recipes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.model.User;
import recipes.service.UserService;

import javax.management.InstanceAlreadyExistsException;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("register")
    public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
        try {
            userService.add(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InstanceAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("delete-account")
    public ResponseEntity<User> deleteUser(@AuthenticationPrincipal UserDetails userDetails) {
        userService.delete(userDetails);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
