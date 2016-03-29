package com.danigcaff.springframework.samples.spring_web;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.social.github.api.GitHub;
import org.springframework.social.github.api.GitHubRepo;
import org.springframework.social.github.connect.GitHubConnectionFactory;
import org.springframework.social.github.connect.GitHubServiceProvider;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
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
	public String listView(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
		
		GitHub gitHub = new GitHubConnectionFactory(clientId, clientSecret)
		.createConnection(new AccessGrant(oauth2ClientContext.getAccessToken().getValue()))
		.getApi();
		
		//GitHub gitHub = (GitHub)beanFactory.getBean("GITHUB_API");
		List<GitHubRepo> listaRepos = gitHub.userOperations().getRepositories();
		model.addAttribute("name", listaRepos.size());
		return "repos_list";
    }

	public void setBeanFactory(BeanFactory arg0) throws BeansException {
		this.beanFactory = arg0;
	}
}
