package com.task_management_system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
public class UserController {
    @GetMapping("/add")
    String addTask(){
        return "Task added Successfully";
    }
}
