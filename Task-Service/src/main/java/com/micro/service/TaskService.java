package com.micro.service;

import com.micro.model.Task;
import com.micro.model.TaskStatus;

import java.util.List;

public interface TaskService {

    Task createTask(Task task,String requestRole ) throws Exception;
    Task getTaskById(Long id) throws Exception;
    List<Task> getAllTask(TaskStatus taskStatus);
    Task updateTask(Long id,Task updatedTask,Long userId) throws Exception;
    void deletTask(Long id) throws Exception;
    Task assignedToUser(Long userId,Long taskId) throws Exception;
    List<Task>assignedUsersTask(Long userId,TaskStatus taskStatus);
    Task completeTask(Long taskId) throws Exception;

}
