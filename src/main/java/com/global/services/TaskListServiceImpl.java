package com.global.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.global.domain.entities.TaskList;
import com.global.repository.TaskListRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TaskListServiceImpl implements TaskListService {

	private final TaskListRepository taskListRepository;
	
	@Override
	public List<TaskList> listTaskLists() {
		return taskListRepository.findAll();
	}

	@Override
	public TaskList createTaskLists(TaskList taskList) {
		if (taskList.getId() != null) {
			throw new IllegalArgumentException("Task list already have id");
		}
		if (taskList.getTitle() == null || taskList.getTitle().isBlank()) {
	        throw new IllegalArgumentException("Task list title must be present");
	    }
		TaskList newTaskList = TaskList.builder()
				.title(taskList.getTitle())
				.description(taskList.getDescription())
				.created(LocalDateTime.now())
				.updated(LocalDateTime.now())
				.build();
		   
		return taskListRepository.save(newTaskList);
	}

	@Override
	public Optional<TaskList> getTaskList(UUID id) {
		return taskListRepository.findById(id);
	}

	@Override
	public TaskList updateTaskList(UUID taskListId, TaskList taskList) {
		if (taskList.getId() == null) {
			throw new IllegalArgumentException("The task list id must be exist");
		}
		
		if (!Objects.equals(taskListId, taskList.getId())) {
			throw new IllegalArgumentException("The id must equal task list id");
		}
		
		TaskList existTaskList = taskListRepository.findById(taskListId)
				.orElseThrow(() -> new IllegalArgumentException("List task not found"));
		
		existTaskList.setTitle(taskList.getTitle());
		existTaskList.setDescription(taskList.getDescription());
	    existTaskList.setUpdated(LocalDateTime.now());
		
		return taskListRepository.save(existTaskList);
	}

	@Override
	public void deleteTaskList(UUID taskListId) {
		taskListRepository.deleteById(taskListId);
	}

}
