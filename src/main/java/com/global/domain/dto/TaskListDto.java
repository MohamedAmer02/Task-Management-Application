package com.global.domain.dto;

import java.util.List;
import java.util.UUID;

import lombok.Builder;

@Builder
public record TaskListDto(
		UUID id,
		String title,
		String description,
		Integer count,
		Double progress,
		List<TaskDto> tasks
) {
}
