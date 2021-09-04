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
		LinkedList<Project> projects = new LinkedList<Project>();
		List<Value> result = WebRequester.getBitbucketRepos(App.getConfig().getBitbucketAPIKey()).getValues();

		// Get all the bitbucket repo stuff
		for (Value current : result) {
			String name = current.getName();
			String description = current.getDescription();
			String URL = current.getLinks().getSelf().get(0).getHref();
			String info = WebRequester.getBitbucketRepoWebInfo(current.getSlug(), App.getConfig().getBitbucketAPIKey());
			Commits commits = WebRequester.getBitbucketRepoCommits(App.getConfig().getBitbucketAPIKey(), current.getSlug());
			/*
			if(commits.getValues() != null) {
				for(dataStructures.json.Commits.Value v : commits.getValues()) {
					Build build = WebRequester.getBitbucketCommitBuildStatus(App.getConfig().getBitbucketAPIKey(), v.getId());
					if(build.getValues() != null && build.getSize() > 0) {
						String buildState = build.getValues().get(0).getState();
						switch(buildState) {
							case "SUCCESSFUL":
								v.setState(1);
								break;
							case "FAILED":
								v.setState(2);
								break;
							default:
								v.setState(0);
								break;
						}
					}
				}
			}
			*/
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
