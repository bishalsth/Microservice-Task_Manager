package com.micro.service;

import com.micro.model.Task;
import com.micro.model.TaskStatus;
import com.micro.repo.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService{

    @Autowired
    private TaskRepo taskRepo;
    @Override
    public Task createTask(Task task, String requestRole) throws Exception {
        if(!requestRole.equals("ROLE_ADMIN")){
            throw new Exception("Only ADMOIN can create task");
        }
        task.setTaskStatus(TaskStatus.PENDING);
        task.setCreatedAt(LocalDateTime.now());
        return taskRepo.save(task);
    }

    @Override
    public Task getTaskById(Long id) throws Exception {
        return taskRepo.findById(id).orElseThrow(()->new Exception("Task not found with id "+id));

    }

    @Override
    public List<Task> getAllTask(TaskStatus taskStatus) {
        List<Task> allTask = taskRepo.findAll();

        List<Task> filteredTask = allTask.stream().filter(
                task-> taskStatus==null || task.getTaskStatus().name().equalsIgnoreCase(task.toString())
        ).collect(Collectors.toList());
        return filteredTask;
    }

    @Override
    public Task updateTask(Long id, Task updatedTask, Long userId) throws Exception {
        Task existingTask = getTaskById(id);

        if(updatedTask.getTitle() !=null){
            existingTask.setTitle(updatedTask.getTitle());

        }
        if(updatedTask.getDescription() !=null){
            existingTask.setDescription(updatedTask.getDescription());
        }
        if(updatedTask.getImage() !=null){
            existingTask.setImage(updatedTask.getImage());
        }
        if(updatedTask.getTaskStatus() !=null){
            existingTask.setTaskStatus(updatedTask.getTaskStatus());
        }
        if(updatedTask.getDeadline() !=null){
            existingTask.setDeadline(updatedTask.getDeadline());
        }
        return taskRepo.save(existingTask);
    }

    @Override
    public void deletTask(Long id) throws Exception {
        getTaskById(id);
        taskRepo.deleteById(id);

    }

    @Override
    public Task assignedToUser(Long userId, Long taskId) throws Exception {

        Task task = getTaskById(taskId);
        task.setAssignedUserId(userId);
        task.setTaskStatus(TaskStatus.DONE);
        return taskRepo.save(task);
    }

    @Override
    public List<Task> assignedUsersTask(Long userId, TaskStatus taskStatus) {
        List<Task> allTask = taskRepo.findByAssignedUserId(userId);
        List<Task> filteredTask = allTask.stream().filter(
                task-> taskStatus ==null || task.getTaskStatus().name().equalsIgnoreCase(taskStatus.toString())
        ).collect(Collectors.toList());
        return filteredTask;
    }

    @Override
    public Task completeTask(Long taskId) throws Exception {
        Task task = getTaskById(taskId);
        task.setTaskStatus(TaskStatus.DONE);
        return taskRepo.save(task);
    }
}
