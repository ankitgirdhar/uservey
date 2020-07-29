package com.spring.uservey.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@Entity
public class Survey extends Audit{

    @Getter
    @Setter
    @NotBlank(message = "enter subject for the mail")
    private String subject;

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    @NotBlank(message = "enter any title for the survey")
    private String title;

    @Getter
    @Setter
    private int yesCount=0;

    @Getter
    @Setter
    private int noCount=0;

    @Getter
    @Setter
    @NotBlank(message = "enter the email list")
    private String recipientEmails;

    @Getter
    @Setter
    private String content;


}
