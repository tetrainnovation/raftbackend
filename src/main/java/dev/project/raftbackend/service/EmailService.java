package dev.project.raftbackend.service;

import dev.project.raftbackend.Entity.Emaildetails;
public interface EmailService {
    String sendSimpleMail(Emaildetails details);

    // Method
    // To send an email with attachment
    String sendMailWithAttachment(Emaildetails details);
}
