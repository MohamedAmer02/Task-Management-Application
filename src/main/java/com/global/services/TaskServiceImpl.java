package com.global.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.global.domain.entities.Task;
import com.global.domain.entities.TaskList;
import com.global.domain.entities.TaskPriority;
import com.global.domain.entities.TaskStatus;
import com.global.repository.TaskListRepository;
import com.global.repository.TaskRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TaskServiceImpl implements TaskService {
	
	private final TaskRepository taskRepository;
	private final TaskListRepository taskListRepository;
	
	@Override
	public List<Task> listTasks(UUID taskListId) {
		return taskRepository.findByTaskListId(taskListId);
	}

	@Override
	public Task createTask(UUID taskListId, Task task) {
		TaskList taskList = taskListRepository.findById(taskListId)
				.orElseThrow(() -> new IllegalArgumentException("There are no task list with id : "+ taskListId));
		if (task.getId() != null) {
			throw new IllegalArgumentException("Task already has an ID");
		}
		if (task.getTitle() == null || task.getTitle().isBlank()) {
			throw new IllegalArgumentException("Task must have a title");
		}
		TaskPriority priority = Optional.ofNullable(task.getPriority())
				.orElse(TaskPriority.MEDIUM);
		TaskStatus status = TaskStatus.OPEN;
		var newTask = Task.builder()
						.title(task.getTitle())
						.description(task.getDescription())
						.dueDate(task.getDueDate())
						.taskList(taskList)
						.priority(priority)
						.status(status)
						.created(LocalDateTime.now())
						.updated(LocalDateTime.now())
						.build();
		return taskRepository.save(newTask);
	}

	@Override
	public Optional<Task> getTask(UUID taskListId, UUID taskId) {
		return taskRepository.findByTaskListIdAndId(taskListId, taskId);
	}
	
	@Override
	public Task updateTask(UUID taskListId, UUID taskId, Task task) {
	    if (task.getId() == null) {
	        throw new IllegalArgumentException("Task must have an ID");
	    }

	    if (!Objects.equals(taskId, task.getId())) {
	        throw new IllegalArgumentException("Task IDs do not match");
	    }

	    if (task.getPriority() == null) {
	        throw new IllegalArgumentException("Task must have a valid priority");
	    }

	    if (task.getStatus() == null) {
	        throw new IllegalArgumentException("Task must have a valid status");
	    }

	    Task existingTask = taskRepository.findByTaskListIdAndId(taskListId, taskId)
	            .orElseThrow(() -> new IllegalArgumentException("Task not found"));

	    existingTask.setTitle(task.getTitle());
	    existingTask.setDescription(task.getDescription());
	    existingTask.setDueDate(task.getDueDate());
	    existingTask.setPriority(task.getPriority());
	    existingTask.setStatus(task.getStatus());
	    existingTask.setUpdated(LocalDateTime.now());

	    return taskRepository.save(existingTask);
	}

	@Override
	@Transactional
	public void deleteTask(UUID taskListId, UUID taskId) {
	    taskRepository.deleteByTaskListIdAndId(taskListId, taskId);
	}
}
