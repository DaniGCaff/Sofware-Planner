package com.danigcaff.springframework.samples.spring_web;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.social.github.api.GitHub;
import org.springframework.social.github.api.GitHubHook;
import org.springframework.social.github.api.GitHubRepo;
import org.springframework.social.github.connect.GitHubConnectionFactory;
import org.springframework.social.github.connect.GitHubServiceProvider;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableOAuth2Client
@Order(7)
public class ReposController implements BeanFactoryAware {
	
	private BeanFactory beanFactory;
	
	@Value("${github.client.clientId}")
	private String clientId;
	@Value("${github.client.clientSecret}")
	private String clientSecret;

	@Autowired
	OAuth2ClientContext oauth2ClientContext;
	
	@RequestMapping("/repos")
	public List<Map<String,String>> listView() {
		
		GitHub gitHub = new GitHubConnectionFactory(clientId, clientSecret)
		.createConnection(new AccessGrant(oauth2ClientContext.getAccessToken().getValue()))
		.getApi();
		
		List<GitHubRepo> listaRepos = gitHub.userOperations().getRepositories();
		List<Map<String, String>> listaIdNombre = new ArrayList<Map<String,String>>();
		for(int i=0;i<listaRepos.size();i++){
			Map<String, String> map = new LinkedHashMap<String,String>();
			map.put("id", Long.toString(listaRepos.get(i).getId()));
			map.put("name", listaRepos.get(i).getName());
			map.put("asoc", "false");

			listaIdNombre.add(map);
		}	
		return listaIdNombre;
    }
	
	@RequestMapping("/repos/asociar/{id}")
	public Map <String, String> asociate(@PathVariable("id") int id){
		//TODO Hacer gestion en base de datos. Añadir el repositorio a una coleccion
		// de repositorios "autorizados". Hay que añadir el url del repositorio.
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("response", "ok");
		return map;
	}
	public void setBeanFactory(BeanFactory arg0) throws BeansException {
		this.beanFactory = arg0;
	}
}
