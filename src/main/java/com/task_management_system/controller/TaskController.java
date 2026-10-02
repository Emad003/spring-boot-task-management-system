package com.task_management_system.controller;

import com.task_management_system.entity.Task;
import com.task_management_system.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping()
 public    ResponseEntity<Task> createTask(@RequestBody Task task, Authentication authentication){
        String username= authentication.getName();
        Task savedTasked = taskService.createTask(task, username);
        return ResponseEntity.ok(savedTasked);

    }
    @GetMapping
  public   List<Task> getAllTask(Authentication authentication)
    {
        List<Task> taskList=taskService.getAllUserTask(authentication.getName());
        return taskList;
    }
    @PutMapping({"/{id}"})

    public ResponseEntity<Task> updateTask(@PathVariable Long id,
                                           @RequestBody Task task,
                                           Authentication authentication){
        Task updateTask = taskService.updateTask(id, task, authentication.getName());
        return ResponseEntity.ok(updateTask);

    }
    @GetMapping("/{id}")
    ResponseEntity<Task> getTask(@PathVariable Long id,Authentication authentication)
    {
         return ResponseEntity.ok().body(taskService.getUserTask(id, authentication.getName()));
    }
    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteTask(@PathVariable Long id, Authentication authentication){
         return ResponseEntity.ok(taskService.deleteById(id,authentication.getName()));

    }



}
