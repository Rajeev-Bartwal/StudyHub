package com.rajeev.StudyHub.Security;


import com.rajeev.StudyHub.Models.User;
import com.rajeev.StudyHub.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

        User user =  userRepo.findByEmail(username);
        if(user==null){
            System.out.println("user not found");
            throw new UsernameNotFoundException("user not found");
        }

        return user;
    }
}
