package com.micro.repo;

import com.micro.model.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmissionRepo extends JpaRepository<Submission,Long> {
    List<Submission> findByTaskId(Long taskId);
}
