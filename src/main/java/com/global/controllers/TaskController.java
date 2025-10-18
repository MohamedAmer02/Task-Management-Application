package com.global.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.global.domain.dto.TaskDto;
import com.global.domain.entities.Task;
import com.global.mappers.TaskMapper;
import com.global.services.TaskService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/task-lists/{task_list_id}/tasks")
@RequiredArgsConstructor
public class TaskController {

	private final TaskService taskService;
	private final TaskMapper taskMapper;
	
	@GetMapping
	public ResponseEntity<List<TaskDto>> listTasks (
			@PathVariable("task_list_id") UUID taskListId){
		return ResponseEntity.ok(taskService.listTasks(taskListId)
				.stream()
				.map(taskMapper::toDto)
				.toList());
	}
	
	@PostMapping
	public ResponseEntity<TaskDto> createTask (
			@PathVariable("task_list_id") UUID taskListId,
			@RequestBody TaskDto taskDto 
			){
		Task newTask = taskService.createTask(taskListId, taskMapper.fromDto(taskDto));
		return ResponseEntity.ok(taskMapper.toDto(newTask));
	}
	
	@GetMapping("/{task_id}")
	public ResponseEntity<Optional<TaskDto>> getTask (
			@PathVariable("task_list_id") UUID taskListId,
			@PathVariable("task_id") UUID taskId
			){
		return ResponseEntity.ok(taskService.getTask(taskListId, taskId)
				.map(taskMapper::toDto));
	}
	
	@PutMapping("/{task_id}")
	public ResponseEntity<TaskDto> updateTask(
	        @PathVariable UUID taskListId,
	        @PathVariable UUID taskId,
	        @RequestBody TaskDto taskDto
	) {
	    Task updatedTask = taskService.updateTask(
	            taskListId,
	            taskId,
	            taskMapper.fromDto(taskDto)
	    );
	    return ResponseEntity.ok(taskMapper.toDto(updatedTask));
	}
	
	@DeleteMapping("/{taskId}")
	public ResponseEntity<Void> deleteTask(
	        @PathVariable UUID taskListId,
	        @PathVariable UUID taskId
	) {
	    taskService.deleteTask(taskListId, taskId);
	    return ResponseEntity.noContent().build();
	}

}
