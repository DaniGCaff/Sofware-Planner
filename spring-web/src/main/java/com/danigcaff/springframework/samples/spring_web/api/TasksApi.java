package com.danigcaff.springframework.samples.spring_web.api;

import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

public interface TasksApi {

	Map<String, String> list(int boardId, int taskId);

}