package com.global.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.global.domain.entities.TaskList;

public interface TaskListService {

	List<TaskList> listTaskLists();
	TaskList createTaskLists(TaskList taskList);
	Optional<TaskList> getTaskList(UUID id);
	TaskList updateTaskList(UUID taskListId, TaskList taskList);
	void deleteTaskList(UUID taskListId);
}
