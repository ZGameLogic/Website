package controllers;

import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import application.App;
import dataStructures.database.ghost.Ghost;
import dataStructures.database.ghost.GhostRepository;
import dataStructures.database.project.Project;
import dataStructures.database.project.ProjectRepository;

@Controller
public class APIController {
	
	@Autowired
	private ProjectRepository dbProjects;
	
	@Autowired
	private GhostRepository ghosts;
	
	@GetMapping("api/getProjects")
	public ResponseEntity<String> getAllProjects(@RequestHeader(value="apiKey") String key) {
		try {
			if(validateKey(key)) {
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
	public ResponseEntity<String> deleteProject(@RequestHeader(value="apiKey") String key, @RequestBody String bodyString) {
		try {
			JSONObject body = new JSONObject(bodyString);
			if(validateKey(key)) {
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
	public ResponseEntity<String> createProject(@RequestHeader(value="apiKey") String key, @RequestBody String bodyString) {
		try {
			JSONObject body = new JSONObject(bodyString);
			if(validateKey(key)) {
				Project newProject = new Project(body);
				dbProjects.save(newProject);
				return ResponseEntity.ok("Project added");
			}
			return ResponseEntity.ok("Invalid apiKey");
		} catch (JSONException e) {
			return ResponseEntity.ok("Invalid JSON format");
		}
	}
	
	@PostMapping("api/editProject")
	public ResponseEntity<String> editProject(@RequestHeader(value="apiKey") String key, @RequestBody String bodyString) {
		try {
			JSONObject body = new JSONObject(bodyString);
			if(validateKey(key)) {
				Project newProject = new Project(body);
				Project oldProject = dbProjects.getOne(newProject.getId());
				oldProject.setProject(newProject);
				dbProjects.save(oldProject);
				return ResponseEntity.ok("Project edited");
			}
			return ResponseEntity.ok("Invalid apiKey");
		} catch (JSONException e) {
			return ResponseEntity.ok("Invalid JSON format");
		}
	}
	
	@GetMapping("api/getGhosts")
	public ResponseEntity<String> getAllGhosts(@RequestHeader(value="apiKey") String key) {
		try {
			if(validateKey(key)) {
				JSONObject returnBody = new JSONObject();
				JSONArray ghostsArray = new JSONArray();
				for(Ghost current : ghosts.findAll()) {
					ghostsArray.put(current.toJSONObject());
				}
				returnBody.put("ghosts", ghostsArray);
				return ResponseEntity.ok(returnBody.toString());
			}
			return ResponseEntity.ok("Invalid apiKey");
		} catch (JSONException e) {
			return ResponseEntity.ok("Invalid JSON format");
		}
	}
	
	@GetMapping("api/getEvidence")
	public ResponseEntity<String> getAllEvidence(@RequestHeader(value="apiKey") String key) {
		try {
			if(validateKey(key)) {
				JSONObject returnBody = new JSONObject();				
				Set<String> types = new HashSet<String>();
				
				System.out.println(ghosts.findAll().size());
				
				for(Ghost current : ghosts.findAll()) {
					for(String x : current.getEvidence().split(",")) {
						types.add(x);
					}
				}
				
				JSONArray ghostsArray = new JSONArray(types);
				
				returnBody.put("evidence", ghostsArray);
				return ResponseEntity.ok(returnBody.toString());
			}
			return ResponseEntity.ok("Invalid apiKey");
		} catch (JSONException e) {
			return ResponseEntity.ok("Invalid JSON format");
		}
	}
	
	@PostMapping("api/deleteGhost")
	public ResponseEntity<String> deleteGhost(@RequestHeader(value="apiKey") String key, @RequestBody String bodyString) {
		try {
			JSONObject body = new JSONObject(bodyString);
			if(validateKey(key)) {
				if(body.has("id")) {
					ghosts.deleteById(body.getInt("id"));
					return ResponseEntity.ok("Ghost deleted");
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
	
	@PostMapping("api/createGhost")
	public ResponseEntity<String> createGhost(@RequestHeader(value="apiKey") String key, @RequestBody String bodyString) {
		try {
			JSONObject body = new JSONObject(bodyString);
			if(validateKey(key)) {
				if(body.has("evidence") && body.has("name") && body.has("description")) {
					Ghost newGhost = new Ghost(body);
					ghosts.save(newGhost);
					return ResponseEntity.ok("Ghost added");
				}
				return ResponseEntity.ok("Ghost not added! We need evidence, name, and decription");
			}
			return ResponseEntity.ok("Invalid apiKey");
		} catch (JSONException e) {
			return ResponseEntity.ok("Invalid JSON format");
		}
	}
	
	@PostMapping("api/editGhost")
	public ResponseEntity<String> editGhost(@RequestHeader(value="apiKey") String key, @RequestBody String bodyString) {
		try {
			JSONObject body = new JSONObject(bodyString);
			if(validateKey(key)) {
				Ghost newGhost = new Ghost(body);
				Ghost oldGhost = ghosts.getOne(newGhost.getId());
				oldGhost.setGhost(newGhost);
				ghosts.save(oldGhost);
				return ResponseEntity.ok("Ghost edited");
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
