package com.rajeev.StudyHub.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String description;
    private String fileUrl;
    private String subject;
    private boolean approved;

    @ManyToOne
    private User uploader;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime uploadedAt = LocalDateTime.now();
}
