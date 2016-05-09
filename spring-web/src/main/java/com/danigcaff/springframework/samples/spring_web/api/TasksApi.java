package com.danigcaff.springframework.samples.spring_web.api;

import java.util.Map;

public interface TasksApi {

	Map<String, String> list(int boardId, int taskId);

}