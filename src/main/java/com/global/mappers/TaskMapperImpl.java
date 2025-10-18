package com.global.mappers;

import org.springframework.stereotype.Component;

import com.global.domain.dto.TaskDto;
import com.global.domain.entities.Task;

@Component
public class TaskMapperImpl implements TaskMapper{

	@Override
	public Task fromDto(TaskDto dto) {
		return Task.builder()
				.id(dto.id())
				.title(dto.title()) 
				.description(dto.description())
				.dueDate(dto.dueDate())
				.priority(dto.priority())
				.status(dto.status())
				.taskList(null)
				.created(null)
				.updated(null)
				.build();
	}

	@Override
	public TaskDto toDto(Task task) {
		return TaskDto.builder()
				.id(task.getId())
				.title(task.getTitle())
				.description(task.getDescription())
				.dueDate(task.getDueDate())
				.priority(task.getPriority())
				.status(task.getStatus())
				.build();
	}

}
