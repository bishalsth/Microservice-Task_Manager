package com.micro.service;

import com.micro.model.Submission;

import java.util.List;

public interface SubmissionService {

    Submission submitTask(Long taskId,Long userId,String githHubLink,String jwt) throws Exception;
    Submission getTaskSubmissionById(Long submissionId)throws Exception;
    List<Submission> getAllTaskSubmission();
    List<Submission> getTaskSubmissionByTaskId(Long taskId) throws Exception;
    Submission acceptDeclineSubmission(Long id,String status) throws Exception;
}
