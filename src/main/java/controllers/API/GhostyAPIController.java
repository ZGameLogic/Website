package controllers.API;

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
import dataStructures.database.ghosty.aspect.Aspect;
import dataStructures.database.ghosty.aspect.AspectRepository;
import dataStructures.database.ghosty.ghost.Ghost;
import dataStructures.database.ghosty.ghost.GhostRepository;

@Controller
public class GhostyAPIController {
	
	@Autowired
	private GhostRepository ghosts;
	
	@Autowired
	private AspectRepository aspects;
	
	@GetMapping("api/getGhosts")
	public ResponseEntity<String> getAllGhosts() {
		try {
			JSONObject returnBody = new JSONObject();
			JSONArray ghostsArray = new JSONArray();
			for(Ghost current : ghosts.findAll()) {
				ghostsArray.put(current.toJSONObject());
			}
			returnBody.put("ghosts", ghostsArray);
			return ResponseEntity.ok(returnBody.toString());
		} catch (JSONException e) {
			return ResponseEntity.ok("Invalid JSON format");
		}
	}
	
	@GetMapping("api/getAspects")
	public ResponseEntity<String> getAllAspects() {
		try {
			JSONObject returnBody = new JSONObject();
			JSONArray aspectsArray = new JSONArray();
			for(Aspect current : aspects.findAll()) {
				aspectsArray.put(current.toJSONObject());
			}
			returnBody.put("aspects", aspectsArray);
			return ResponseEntity.ok(returnBody.toString());
		} catch (JSONException e) {
			return ResponseEntity.ok("Invalid JSON format");
		}
	}
	
	@GetMapping("api/getEvidence")
	public ResponseEntity<String> getAllEvidence() {
		try {
			JSONObject returnBody = new JSONObject();				
			Set<String> types = new HashSet<String>();
				
			System.out.println(ghosts.findAll().size());
				
			for(Ghost current : ghosts.findAll()) {
				for(String x : current.getEvidence().split(",")) {
					if(!x.equals("")) {
						types.add(x);
					}
				}
			}
				
			JSONArray ghostsArray = new JSONArray(types);
				
			returnBody.put("evidence", ghostsArray);
			return ResponseEntity.ok(returnBody.toString());
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
	
	@PostMapping("api/deleteAspect")
	public ResponseEntity<String> deleteAspect(@RequestHeader(value="apiKey") String key, @RequestBody String bodyString) {
		try {
			JSONObject body = new JSONObject(bodyString);
			if(validateKey(key)) {
				if(body.has("id")) {
					aspects.deleteById(body.getInt("id"));
					return ResponseEntity.ok("Aspect deleted");
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
	
	@PostMapping("api/createAspect")
	public ResponseEntity<String> createAspect(@RequestHeader(value="apiKey") String key, @RequestBody String bodyString) {
		try {
			JSONObject body = new JSONObject(bodyString);
			if(validateKey(key)) {
				if(body.has("name") && body.has("content")) {
					Aspect newAspect = new Aspect(body);
					aspects.save(newAspect);
					return ResponseEntity.ok("Content added");
				}
				return ResponseEntity.ok("Content not added! We need content and name");
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
	
	@PostMapping("api/editAspect")
	public ResponseEntity<String> editAspect(@RequestHeader(value="apiKey") String key, @RequestBody String bodyString) {
		try {
			JSONObject body = new JSONObject(bodyString);
			if(validateKey(key)) {
				Aspect newAspect = new Aspect(body);
				Aspect oldAspect = aspects.getOne(newAspect.getId());
				oldAspect.setAspect(newAspect);
				aspects.save(oldAspect);
				return ResponseEntity.ok("Aspect edited");
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
