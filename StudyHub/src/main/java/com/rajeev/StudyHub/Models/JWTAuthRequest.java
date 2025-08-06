package com.rajeev.StudyHub.Models;

import lombok.Data;

@Data
public class JWTAuthRequest {

    private String username;

    private String password;
}
