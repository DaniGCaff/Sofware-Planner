package com.danigcaff.springframework.samples.spring_web.api;

import java.util.List;
import java.util.Map;

public interface ReposApi {

	List<Map<String, String>> listView(String owner);

	Map<String, String> asociate(String owner, String repoId, String boardId);

	Map<String, String> list(String repoId);

}