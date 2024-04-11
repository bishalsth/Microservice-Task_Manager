package com.micro.repo;

import com.micro.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepo extends JpaRepository<Task,Long> {

    public List<Task> findByAssignedUserId(long userId);
}
