package controllers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import application.App;
import dataStructures.Project;
import dataStructures.ProjectRepository;

@Controller
public class APIController {
	
	@Autowired
	private ProjectRepository dbProjects;
	
	@GetMapping("api/getProjects")
	public ResponseEntity<String> getAllProjects(@RequestBody String bodyString) {
		try {
			JSONObject body = new JSONObject(bodyString);
			if(validateKey(body)) {
				JSONObject returnBody = new JSONObject();
				JSONArray projects = new JSONArray();
				for(Project current : dbProjects.findAll()) {
					projects.put(current.toJSONObject());
				}
				returnBody.put("projects", projects);
				return ResponseEntity.ok(returnBody.toString());
			}
			return ResponseEntity.ok("Invalid apiKey");
		} catch (JSONException e) {
			return ResponseEntity.ok("Invalid JSON format");
		}
	}
	
	@PostMapping("api/deleteProject")
	public ResponseEntity<String> deleteProject(@RequestBody String bodyString) {
		try {
			JSONObject body = new JSONObject(bodyString);
			if(validateKey(body)) {
				if(body.has("id")) {
					dbProjects.deleteById(body.getInt("id"));
					return ResponseEntity.ok("Project deleted");
				}
				return ResponseEntity.ok("Invalid id");
			}
			return ResponseEntity.ok("Invalid apiKey");
		} catch (JSONException e) {
			return ResponseEntity.ok("Invalid JSON format");
		} catch (IllegalArgumentException e1) {
			return ResponseEntity.ok("Invalid project id");
		}
	}
	
	@PostMapping("api/createProject")
	public ResponseEntity<String> createProject(@RequestBody String bodyString) {
		try {
			JSONObject body = new JSONObject(bodyString);
			if(validateKey(body)) {
				Project newProject = new Project(body);
				dbProjects.save(newProject);
				return ResponseEntity.ok("Project added");
			}
			return ResponseEntity.ok("Invalid apiKey");
		} catch (JSONException e) {
			return ResponseEntity.ok("Invalid JSON format");
		}
	}
	
	
	private boolean validateKey(JSONObject body) {
		if(body.has("apiKey")) {
			try {
				return body.getString("apiKey").equals(App.getConfig().getWebsiteApi());
			} catch (JSONException e) {
				return false;
			}
		}
		return false;
	}
}
