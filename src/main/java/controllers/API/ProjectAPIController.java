package controllers.API;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import application.App;
import dataStructures.database.project.Project;
import dataStructures.database.project.ProjectRepository;

@Controller
@RequestMapping("api/Projects")
public class ProjectAPIController {

	@Autowired
	private ProjectRepository dbProjects;
	
	@GetMapping
	public ResponseEntity<String> getAllProjects() {
		try {
			JSONObject returnBody = new JSONObject();
			JSONArray projects = new JSONArray();
			for(Project current : dbProjects.findAll()) {
				projects.put(current.toJSONObject());
			}
			returnBody.put("projects", projects);
			return ResponseEntity.ok(returnBody.toString());
		} catch (JSONException e) {
			return ResponseEntity.ok("Invalid JSON format");
		}
	}
	
	@DeleteMapping("/{projectID:.+}")
	public ResponseEntity<String> deleteProject(@RequestHeader(value="apiKey") String key, @PathVariable("projectID") int projectID) {
		try {
			if(validateKey(key)) {
				if(dbProjects.existsById(projectID)) {
					dbProjects.deleteById(projectID);
					return ResponseEntity.ok("Project deleted");
				}
				return ResponseEntity.ok("Invalid id");
			}
			return ResponseEntity.ok("Invalid apiKey");
		} catch (IllegalArgumentException e1) {
			return ResponseEntity.ok("Invalid project id");
		}
	}
	
	@PostMapping
	public ResponseEntity<String> createProject(@RequestHeader(value="apiKey") String key, @RequestBody String bodyString) {
		try {
			JSONObject body = new JSONObject(bodyString);
			if(validateKey(key)) {
				Project newProject = new Project(body);
				dbProjects.save(newProject);
				return ResponseEntity.ok("Project changed");
			}
			return ResponseEntity.ok("Invalid apiKey");
		} catch (JSONException e) {
			return ResponseEntity.ok("Invalid JSON format");
		}
	}
	
	private boolean validateKey(String key) {
		return key.equals(App.getConfig().getWebsiteApi());
	}	
}
