package com.danigcaff.springframework.samples.spring_web.api;

import java.security.Principal;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;

public interface UserApi {

	Map<String, String> user(Principal principal);

}