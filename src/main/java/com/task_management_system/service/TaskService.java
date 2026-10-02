package com.task_management_system.service;

import com.task_management_system.entity.Task;
import com.task_management_system.entity.User;
import com.task_management_system.repository.TaskRepository;
import com.task_management_system.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private TaskRepository taskRepository;
    private UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }


    public Task createTask(Task task, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    return new RuntimeException("User not Found");
                });

        task.setUser(user);
        return taskRepository.save(task);

    }

    public List<Task> getAllUserTask(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    return new RuntimeException("User not Found");
                });
        return taskRepository.findByUser(user);
    }

    public Task updateTask(long taskId, Task updatedTask, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(
                        () -> {
                            return new UsernameNotFoundException("User not Found Exception");
                        }
                );

        Task taskFound = taskRepository.findByIdAndUser(taskId, user)
                .orElseThrow(
                        () -> {
                            return new RuntimeException("Task not Found with Given Id");
                        }
                );

        taskFound.setTitle(updatedTask.getTitle());
        taskFound.setDescription(updatedTask.getDescription());
        taskFound.setCompleted(taskFound.isCompleted());
        return taskRepository.save(taskFound);


    }

    public Task getUserTask(Long id, String username) {
        User userFound = userRepository.findByUsername(username)
                .orElseThrow(
                        () -> {
                            return new UsernameNotFoundException("User not Found");
                        }
                );
        Task taskFound = taskRepository.findByIdAndUser(id, userFound).orElseThrow(
                () -> {
                    return new RuntimeException("Task not found By Given id");
                }
        );

        return taskFound;
    }

    public String deleteById(Long id, String username){
        User userFound = userRepository.findByUsername(username).orElseThrow(
                () -> {
                    return new UsernameNotFoundException("User not Foumd");
                }
        );

        Task taskFound=taskRepository.findByIdAndUser(id,userFound).orElseThrow(
                ()-> {
                    return new RuntimeException("Task not found By Given id");
                }
        );
        taskRepository.delete(taskFound);
        return "Deleted";
    }
}
