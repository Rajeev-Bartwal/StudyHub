package com.rajeev.StudyHub.Services.ServiceImpl;

import com.rajeev.StudyHub.Configuration.AppConstants;
import com.rajeev.StudyHub.Exception.ResourceNotFoundException;
import com.rajeev.StudyHub.Models.Role;
import com.rajeev.StudyHub.Models.User;
import com.rajeev.StudyHub.Payload.UserDTO;
import com.rajeev.StudyHub.Repository.RoleRepo;
import com.rajeev.StudyHub.Repository.UserRepo;
import com.rajeev.StudyHub.Services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private ModelMapper mod;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO registerNewUser(UserDTO userDto) {

        if (userRepo.findByEmail(userDto.getEmail()) != null) {
            throw new RuntimeException("Email already exists");
        }

        User user = modelMapper.map(userDto , User.class);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role role = roleRepo.findById(AppConstants.ROLE_STUDENT_ID).get();
        user.getRoles().add(role);

        return modelMapper.map(userRepo.save(user) , UserDTO.class);
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = mod.map(userDTO , User.class);

        User savedUser = userRepo.save(user);

        return mod.map(savedUser , UserDTO.class);
    }

    @Override
    public UserDTO updateUser(UserDTO user, Integer userId) {
        return null;
    }

    @Override
    public UserDTO getUserById(Integer userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User" , "Id", userId));

        return  mod.map(user , UserDTO.class);
    }

    @Override
    public List<UserDTO> getAllUsers() {

        List<User> users = userRepo.findAll();
        return users.stream().map( user -> mod.map(user , UserDTO.class)).toList();
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User" , "Id", userId));

        user.getRoles().clear();
        userRepo.save(user);

        userRepo.delete(user);
    }
}
