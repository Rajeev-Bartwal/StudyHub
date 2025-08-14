package com.rajeev.StudyHub.Payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceDTO {

        private int id; // Optional during creation, present in response
        private String title;
        private String description;
        private String fileUrl;
        private String subject;
        private String uploaderName; // Optional during creation, used in response
        private boolean approved;

        @JsonFormat(pattern = "dd-MM-yyyy")
        private LocalDateTime uploadedAt;
}

