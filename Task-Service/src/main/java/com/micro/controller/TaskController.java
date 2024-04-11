package com.micro.controller;

import com.micro.model.Task;
import com.micro.model.TaskStatus;
import com.micro.model.UserDto;
import com.micro.service.TaskService;
import com.micro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;


    @PostMapping("/")
    public ResponseEntity<Task> createTask(@RequestBody Task task,
                                           @RequestHeader("Authorization")String jwt) throws Exception {
        UserDto user = userService.getUser(jwt);

        Task craetedTAsk=taskService.createTask(task, user.getRole());
        return new ResponseEntity<>(craetedTAsk, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id,
                                           @RequestHeader("Authorization")String jwt) throws Exception {
        UserDto user = userService.getUser(jwt);

        Task getTask=taskService.getTaskById(id);
        return new ResponseEntity<>(getTask, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Task>> getAssignedTask(@RequestParam(required = false)TaskStatus status,
                                        @RequestHeader("Authorization")String jwt) throws Exception {
        UserDto user = userService.getUser(jwt);

        List<Task> tasks = taskService.assignedUsersTask(user.getId(), status);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<Task>> getAllTask(@RequestParam(required = false)TaskStatus status,
                                              @RequestHeader("Authorization")String jwt) throws Exception {


        List<Task> tasks = taskService.getAllTask(status);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PutMapping("/{id}/user/{userId}/assigned")
    public ResponseEntity<Task> assignedTaskToUser(
            @PathVariable Long id,
            @PathVariable Long userId,
            @RequestHeader("Authorization")String jwt) throws Exception {
        UserDto user = userService.getUser(jwt);

        Task tasks = taskService.assignedToUser(id,userId);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(
            @PathVariable Long id,
            @RequestBody Task req,
            @RequestHeader("Authorization")String jwt) throws Exception {
        UserDto user = userService.getUser(jwt);

        Task tasks = taskService.updateTask(id,req,user.getId());
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<Task> completeTask(
            @PathVariable Long id
            ) throws Exception {


        Task tasks = taskService.completeTask(id);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long id
    ) throws Exception {


        taskService.deletTask(id);
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }


}
