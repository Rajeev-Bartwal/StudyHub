package com.rajeev.StudyHub.Controllers;

import com.rajeev.StudyHub.Payload.UserDTO;
import com.rajeev.StudyHub.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/study_hub/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public  ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO user) {
          return new ResponseEntity<>(userService.createUser(user) , HttpStatus.CREATED);
    }
}
