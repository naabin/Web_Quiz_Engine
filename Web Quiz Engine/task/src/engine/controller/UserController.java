package engine.controller;

import engine.model.User;
import engine.requestmapper.UserRequest;
import engine.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRequest requestedUser) {
        User user = new User();
        user.setEmail(requestedUser.getEmail());
        user.setPassword(requestedUser.getPassword());
        User saved = this.userService.getByEmail(requestedUser.getEmail());
        if (saved != null) {
            return ResponseEntity.badRequest().build();
        } else {
            User savedUser = this.userService.register(user);
            return ResponseEntity.ok().build();
        }
    }
}
