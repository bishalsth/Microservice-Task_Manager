package com.micro.service;

import com.micro.model.Submission;
import com.micro.model.TaskDto;
import com.micro.repo.SubmissionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SubmissionServiceImpl implements SubmissionService{

    @Autowired
    private SubmissionRepo submissionRepo;

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;
    @Override
    public Submission submitTask(Long taskId, Long userId, String githHubLink,String jwt) throws Exception {
        TaskDto task = taskService.getTask(taskId, jwt);
        if(task!=null){
            Submission submission= new Submission();
            submission.setTaskId(taskId);
            submission.setUserId(userId);
            submission.setGithHubLink(githHubLink);
            submission.setSubmissionTime(LocalDateTime.now());
            return submissionRepo.save(submission);
        }

        throw new Exception("Task not found with id"+taskId);
    }

    @Override
    public Submission getTaskSubmissionById(Long submissionId) throws Exception {
        return submissionRepo.findById(submissionId).orElseThrow(()->new Exception("Task Submission not found with id"+submissionId));
    }

    @Override
    public List<Submission> getAllTaskSubmission() {
        return submissionRepo.findAll();
    }

    @Override
    public List<Submission> getTaskSubmissionByTaskId(Long taskId) throws Exception {
        return  submissionRepo.findByTaskId(taskId);
//        return null;
    }

    @Override
    public Submission acceptDeclineSubmission(Long id, String status) throws Exception {
        Submission submission =getTaskSubmissionById(id);
        submission.setStatus(status);
        if(status.equals("Accept")){
            taskService.completeTask(submission.getTaskId());
        }
        return submissionRepo.save(submission);
    }
}
