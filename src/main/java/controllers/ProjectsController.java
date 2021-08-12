package controllers;

import java.util.LinkedList;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import application.App;
import dataStructures.Project;
import interfaces.WebRequester;

@Controller
public class ProjectsController {

	@GetMapping("projects")
    public String main(Model model) {
		LinkedList<Project> projects = new LinkedList<Project>();
		
		JSONObject result = WebRequester.getBitbucketRepos(App.getConfig().getBitbucketAPIKey());
		try {
			for(int i = 0; i < result.getJSONArray("values").length(); i++) {
				 JSONObject repo = result.getJSONArray("values").getJSONObject(i);
				 String name = repo.getString("name");
				 String description = repo.getString("description");
				 String URL = repo.getJSONObject("links").getJSONArray("self").getJSONObject(0).getString("href");
				 String info = WebRequester.getBitbucketRepoWebInfo(repo.getString("slug"), App.getConfig().getBitbucketAPIKey());
				 projects.add(new Project(name, description, URL, info));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		model.addAttribute("projects", projects);
        return "projects";
    }
}
