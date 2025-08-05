package com.rajeev.StudyHub.Services;

import com.rajeev.StudyHub.Payload.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
     UserDTO RegisterNewUser(UserDTO userDTO);

     UserDTO createUser(UserDTO userDTO);

     UserDTO updateUser(UserDTO user,Integer userId);

     UserDTO getUserById(Integer userId);

     List<UserDTO> getAllUsers();

     void deleteUser(Integer userId);

}
