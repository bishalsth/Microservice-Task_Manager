package com.micro.controller;

import com.micro.model.Submission;
import com.micro.model.UserDto;
import com.micro.service.SubmissionService;
import com.micro.service.TaskService;
import com.micro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/submissions")
public class SubmissionController {

    @Autowired
    private SubmissionService submissionService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<Submission> submitTask(
            @RequestParam Long taskId,
            @RequestParam String githHubLink,
            @RequestHeader("Authorization")String jwt
    )throws Exception{
        UserDto user = userService.getUser(jwt);
        Submission submission = submissionService.submitTask(taskId, user.getId(), githHubLink, jwt);
        return new ResponseEntity<>(submission, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Submission> getSubmissionById(
          @PathVariable Long id,
            @RequestHeader("Authorization")String jwt
    )throws Exception{
        UserDto user = userService.getUser(jwt);
        Submission submission = submissionService.getTaskSubmissionById(id);
        return new ResponseEntity<>(submission, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Submission>> getAllSubmission(

            @RequestHeader("Authorization")String jwt
    )throws Exception{
        UserDto user = userService.getUser(jwt);
        List<Submission> submission = submissionService.getAllTaskSubmission();
        return new ResponseEntity<>(submission, HttpStatus.OK);
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<Submission>> getAllSubmission(
            @PathVariable Long taskId,
            @RequestHeader("Authorization")String jwt
    )throws Exception{
        UserDto user = userService.getUser(jwt);
        List<Submission> submission = submissionService.getTaskSubmissionByTaskId(taskId);
        return new ResponseEntity<>(submission, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Submission> acceptDeclineSubmission(
            @PathVariable Long id,
            @RequestParam("status") String status,
            @RequestHeader("Authorization")String jwt
    )throws Exception{
        UserDto user = userService.getUser(jwt);
        Submission submission = submissionService.acceptDeclineSubmission(id, status);
        return new ResponseEntity<>(submission, HttpStatus.OK);
    }


}
