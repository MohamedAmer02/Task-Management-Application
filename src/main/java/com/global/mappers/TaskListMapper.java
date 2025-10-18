package com.global.mappers;

import com.global.domain.dto.TaskListDto;
import com.global.domain.entities.TaskList;

public interface TaskListMapper {

	TaskList fromDto(TaskListDto taskListDto);
	
	TaskListDto toDto(TaskList taskList);
}
