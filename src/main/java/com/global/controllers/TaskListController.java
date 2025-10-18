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

import com.global.domain.dto.TaskListDto;
import com.global.domain.entities.TaskList;
import com.global.mappers.TaskListMapper;
import com.global.services.TaskListService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping(path = "/task-lists")
@RestController
public class TaskListController {

	private final TaskListService taskListService;
	private final TaskListMapper taskListMapper;
	
	@GetMapping
	public ResponseEntity<List<TaskListDto>> listTaskLists(){
		return ResponseEntity.ok(taskListService.listTaskLists()
				.stream()
				.map(taskListMapper::toDto)
				.toList());
	}
	
	@PostMapping
	public ResponseEntity<TaskListDto> createTaskLists(@RequestBody TaskListDto taskListDto){
		TaskList createdTaskList = taskListService.createTaskLists(
				taskListMapper.fromDto(taskListDto));
		return ResponseEntity.ok(taskListMapper.toDto(createdTaskList));
	}
	
	@GetMapping("/{task_list_id}")
	public ResponseEntity<Optional<TaskListDto>> getTaskList(@PathVariable("task_list_id") UUID taskListId){
		return ResponseEntity.ok(taskListService.getTaskList(taskListId)
				.map(taskListMapper::toDto));
	}
	
	@PutMapping("/{task_list_id}")
	public ResponseEntity<TaskListDto> updateTaskLists(
			@PathVariable("task_list_id") UUID taskListId,
			@RequestBody TaskListDto taskListDto){
		TaskList updatedTaskList = taskListService.updateTaskList(
				taskListId,
				taskListMapper.fromDto(taskListDto)
		);
		return ResponseEntity.ok(taskListMapper.toDto(updatedTaskList));
	}
	
	@DeleteMapping("/{task_list_id}")
	public ResponseEntity<Void> deleteTaskList(@PathVariable("task_list_id") UUID taskListId){
		taskListService.deleteTaskList(taskListId);
		return ResponseEntity.noContent().build();
	}
}
