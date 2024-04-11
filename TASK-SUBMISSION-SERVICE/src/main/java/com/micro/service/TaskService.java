package com.micro.service;

import com.micro.model.TaskDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "TASK-SERVICE",url = "http://localhost:8081/")
public interface TaskService {


    @GetMapping("/api/task/{id}")
    public TaskDto getTask(@PathVariable Long id,
                           @RequestHeader("Authorization")String jwt) throws Exception;

    @PutMapping("/{id}/complete")
    public TaskDto completeTask(
            @PathVariable Long id
    ) throws Exception;
}
