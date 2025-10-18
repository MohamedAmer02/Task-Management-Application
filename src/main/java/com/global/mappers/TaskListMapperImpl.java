package com.global.mappers;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.global.domain.dto.TaskListDto;
import com.global.domain.entities.Task;
import com.global.domain.entities.TaskList;
import com.global.domain.entities.TaskStatus;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class TaskListMapperImpl implements TaskListMapper {

	private final TaskMapper taskMapper;

	@Override
	public TaskList fromDto(TaskListDto taskListDto) {
		return TaskList.builder()
				.id(taskListDto.id())
				.title(taskListDto.title())
				.description(taskListDto.description())
				.tasks(Optional.ofNullable(taskListDto.tasks())
						.map(tasks -> tasks.stream()
								.map(taskMapper::fromDto)
								.toList()
						).orElse(null))
				.created(null)
				.updated(null)
				.build();
	}

	@Override
	public TaskListDto toDto(TaskList taskList) {
		return TaskListDto.builder()
				.id(taskList.getId())
				.title(taskList.getTitle())
				.description(taskList.getDescription())
				.count(Optional.ofNullable(taskList.getTasks())
						.map(List::size)
						.orElse(0))
				.progress(calculateTaskListProgress(taskList.getTasks()))
				.tasks(Optional.ofNullable(taskList.getTasks())
						.map(tasks -> tasks.stream()
								.map(taskMapper::toDto)
								.toList())
						.orElse(null))
				.build();
	}
	
	private Double calculateTaskListProgress(List<Task> tasks) {
		if (tasks == null) {
			return null;
		}
		long closedTaskCount = tasks.stream()
				.filter(task -> TaskStatus.CLOSED == task.getStatus())
				.count();
		return (double) closedTaskCount / tasks.size();
	}
	
}
