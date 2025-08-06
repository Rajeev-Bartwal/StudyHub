package com.rajeev.StudyHub.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    private int role_id;

    @Column(nullable = false, unique = true)
    private String role_name;
}
