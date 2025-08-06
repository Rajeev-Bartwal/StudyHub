package com.rajeev.StudyHub.Controllers;

import com.rajeev.StudyHub.Payload.ApiResponse;
import com.rajeev.StudyHub.Payload.UserDTO;
import com.rajeev.StudyHub.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/study_hub/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public  ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO user) {
          return new ResponseEntity<>(userService.createUser(user) , HttpStatus.CREATED);
    }

    @GetMapping("/get_all_users")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<UserDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users , HttpStatus.OK);
    }

    @GetMapping("/{user_id}")
    public  ResponseEntity<UserDTO> getUserById(@PathVariable Integer user_id){
       UserDTO user = userService.getUserById(user_id);
       return new ResponseEntity<>(user , HttpStatus.FOUND);
    }

    @DeleteMapping("/{user_id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer user_id){
        userService.deleteUser(user_id);
        return new ResponseEntity<>(new ApiResponse("User Deleted Successfully." , true) , HttpStatus.OK);
    }
}
