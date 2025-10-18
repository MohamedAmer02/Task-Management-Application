package com.global.domain.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.global.domain.entities.TaskPriority;
import com.global.domain.entities.TaskStatus;

import lombok.Builder;

@Builder
public record TaskDto(
		UUID id,
		String title,
		String description,
		LocalDateTime dueDate,
		TaskPriority priority,
		TaskStatus status
) {	
}
