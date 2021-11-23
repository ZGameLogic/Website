package controllers;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import application.App;
import dataStructures.database.project.Project;
import dataStructures.database.project.ProjectRepository;
import dataStructures.json.Commits;
import dataStructures.json.Repositories.Value;
import services.WebRequester;

@Controller
public class ProjectsController {

	@Autowired
	private ProjectRepository dbProjects;

	@GetMapping("projects")
	public String main(Model model) {
		IndexController.addPages(model);
		LinkedList<Project> projects = new LinkedList<Project>();
		List<Value> result = WebRequester.getBitbucketRepos(App.getConfig().getBitbucketAPIKey()).getValues();

		// Get all the bitbucket repo stuff
		for (Value current : result) {
			String name = current.getName();
			String description = current.getDescription();
			String URL = current.getLinks().getSelf().get(0).getHref();
			String info = WebRequester.getBitbucketRepoWebInfo(current.getSlug(), App.getConfig().getBitbucketAPIKey());
			Commits commits = WebRequester.getBitbucketRepoCommits(App.getConfig().getBitbucketAPIKey(), current.getSlug());
			projects.add(new Project(0, name, description, URL, info, commits));
		}
		// Get all the additions from the database
		projects.addAll(dbProjects.findAll());

		model.addAttribute("projects", projects);
		return "projects";
	}

	@GetMapping("projects/api")
	public ResponseEntity<String> getAll(@RequestBody String bodyString) {
		try {
			JSONObject body = new JSONObject(bodyString);
			if (body.has("apiKey")) {

			}
		} catch (JSONException e) {
			return ResponseEntity.ok("Invalid JSON format");
		}
		return null;
	}
}
