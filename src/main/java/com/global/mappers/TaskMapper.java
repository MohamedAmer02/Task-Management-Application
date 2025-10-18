package com.global.mappers;

import com.global.domain.dto.TaskDto;
import com.global.domain.entities.Task;

public interface TaskMapper {

	Task fromDto(TaskDto dto);
	
	TaskDto toDto(Task task);
}
