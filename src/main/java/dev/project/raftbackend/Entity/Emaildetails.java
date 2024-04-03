package dev.project.raftbackend.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Annotations
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Emaildetails {
    private String recipient;
    private String msgBody;
    private String subject="Token Received";
    private String attachment="None";
}
