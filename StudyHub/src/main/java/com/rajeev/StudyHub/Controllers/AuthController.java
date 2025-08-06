package com.rajeev.StudyHub.Controllers;

import com.rajeev.StudyHub.Models.JWTAuthRequest;
import com.rajeev.StudyHub.Models.JWTAuthResponse;
import com.rajeev.StudyHub.Payload.ApiResponse;
import com.rajeev.StudyHub.Payload.UserDTO;
import com.rajeev.StudyHub.Security.JwtService;
import com.rajeev.StudyHub.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authManager;

    @PostMapping("/login")
    public ResponseEntity<?> createToken(@RequestBody JWTAuthRequest jwtAuthRequest) throws Exception{

        try {
            Authentication authentication =
                    authManager
                            .authenticate(new UsernamePasswordAuthenticationToken(jwtAuthRequest.getUsername(), jwtAuthRequest.getPassword()));

            String token = "";
            if (authentication.isAuthenticated()) {
                token = jwtService.generateToken(jwtAuthRequest.getUsername());
                return new ResponseEntity<>(new JWTAuthResponse(token), HttpStatus.OK);
            }

        }catch (BadCredentialsException ex) {
            throw ex;
        }
        return new ResponseEntity<>(new ApiResponse(), HttpStatus.UNAUTHORIZED);
    }


    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO){
        return new ResponseEntity<>(userService.registerNewUser(userDTO) , HttpStatus.OK);
    }

}
