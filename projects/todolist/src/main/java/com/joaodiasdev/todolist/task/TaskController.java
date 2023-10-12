package com.joaodiasdev.todolist.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joaodiasdev.todolist.utils.Utils;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {

  @Autowired
  private ITaskRepository taskRepository;

  @PostMapping("/")
  public ResponseEntity<Object> create(@RequestBody TaskModel taskModel, HttpServletRequest request) {
    taskModel.setIdUser((UUID) (request.getAttribute("idUser")));
    var currentDate = LocalDateTime.now();
    if (currentDate.isAfter(taskModel.getStartAt())
        || currentDate.isAfter(taskModel.getEndAt())) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("Dates cannot be in the past!");
    }

    if (taskModel.getStartAt().isAfter(taskModel.getEndAt())) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("Start date cannot be before end date!");
    }

    var task = this.taskRepository.save(taskModel);
    return ResponseEntity.status(HttpStatus.OK).body(task);
  }

  @GetMapping("/")
  public List<TaskModel> list(HttpServletRequest request) {
    var idUser = (UUID) request.getAttribute("idUser");
    var tasks = this.taskRepository.findByIdUser(idUser);
    return tasks;
  }

  @PutMapping("/{id}")
  public TaskModel update(@RequestBody TaskModel taskModel, @PathVariable UUID id,
      HttpServletRequest request) {

    var task = this.taskRepository.findById(id).orElse(null);

    Utils.copyNonNullProperties(taskModel, task);

    return this.taskRepository.save(task);
  }
}
