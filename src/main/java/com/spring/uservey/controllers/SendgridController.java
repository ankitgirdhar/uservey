package com.spring.uservey.controllers;

import com.sendgrid.Response;
import com.spring.uservey.models.Survey;
import com.spring.uservey.repositories.SurveyRepository;
import com.spring.uservey.services.MapValidationErrorService;
import com.spring.uservey.services.SendgridEvent;
import com.spring.uservey.services.SendgridService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

@RestController
@RequestMapping("/sendgrid")
@CrossOrigin
public class SendgridController {

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private SendgridService sendgridService;

    @Autowired
    private SurveyRepository surveyRepository;

    @GetMapping("/all")
    public Iterable<Survey> getAllSurveys(Principal principal) {
        return sendgridService.findAllProjects(principal.getName());
    }

    @PostMapping("/send")
    public ResponseEntity<?> sendMail(@Valid @RequestBody Survey survey, BindingResult result, Principal principal) throws IOException, MessagingException {
        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
        if(errorMap != null)
            return errorMap;

        String response = sendgridService.sendMail(survey,principal.getName());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/response/{surveyId}/{response}")
    public ResponseEntity<?> sendThanks(@PathVariable String surveyId, @PathVariable String response) {
        Long surveyLong = Long.parseLong(surveyId);
        Optional<Survey> optionalSurvey = surveyRepository.findById(surveyLong);
        if(optionalSurvey.isPresent()) {
            Survey survey = optionalSurvey.get();
            if(response.equals("no")) {
                int noCount = survey.getNoCount();
                noCount++;
                survey.setNoCount(noCount);
            } else {
                int yesCount = survey.getYesCount();
                yesCount++;
                survey.setYesCount(yesCount);
            }
            surveyRepository.save(survey);
        }
        return new ResponseEntity<>("Thanks for voting " + response +  "." +  "You can close this Web Page now. ", HttpStatus.OK);
    }

    @DeleteMapping("/{surveyId}")
    public ResponseEntity<?> deleteProject(@PathVariable String surveyId, Principal principal) {
        sendgridService.deleteSurveyByIdentifier(surveyId,principal.getName());
        return new ResponseEntity<>("Survey with ID " + surveyId + " is deleted!", HttpStatus.ACCEPTED);
    }

}


