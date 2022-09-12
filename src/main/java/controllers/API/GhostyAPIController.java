package controllers.API;

import java.util.HashSet;
import java.util.Set;

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
import dataStructures.database.ghosty.aspect.Aspect;
import dataStructures.database.ghosty.aspect.AspectRepository;
import dataStructures.database.ghosty.ghost.Ghost;
import dataStructures.database.ghosty.ghost.GhostRepository;
import services.WebRequester;

@Controller
@RequestMapping("api/ghosty/")
public class GhostyAPIController {
	
	@Autowired
	private GhostRepository ghosts;
	
	@Autowired
	private AspectRepository aspects;
	
	/**
	 * @return JSONArray of ghosts JSONObjects
	 */
	@GetMapping("Ghosts")
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

	/**
	 * @return JSONArray of ghosts JSONObjects
	 */
	@GetMapping("Ghosts2")
	public ResponseEntity<String> getAllGhostsV2() {
		try {
			JSONObject returnBody = new JSONObject();
			JSONArray ghostsArray = new JSONArray();
			for(Ghost current : ghosts.findAll()) {
				JSONObject ghost = new JSONObject();
				JSONArray evidenceArray = new JSONArray();
				for(String e : current.getEvidence().split(",")) {
					evidenceArray.put(e);
				}
				ghost.put("evidence", evidenceArray);
				ghost.put("name", current.getName());
				ghost.put("description", current.getDescription());
				ghost.put("id", current.getId());
				ghostsArray.put(ghost);
			}
			returnBody.put("ghosts", ghostsArray);
			return ResponseEntity.ok(returnBody.toString());
		} catch (JSONException e) {
			return ResponseEntity.ok("Invalid JSON format");
		}
	}
	
	/**
	 * Use this request to get the current released version ghotsy application.
	 * @return Version for ghosty application
	 */
	@GetMapping("Version")
	public ResponseEntity<String> getVersion() {
		JSONObject returnBody = new JSONObject();
		try {
			returnBody.put("version", WebRequester.getBitbucketRepoPomVersion("ghosty", App.getConfig().getBitbucketAPIKey()));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(returnBody.toString());
	}
	
	/**
	 * @return JSONArray of Aspect JSONObjects
	 */
	@GetMapping("Aspects")
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
	
	/**
	 * Use this request to get a list of aspect types
	 * @return list of aspect types
	 */
	@GetMapping("AspectTypes")
	public ResponseEntity<String> getAllAspectTypes() {
		try {
			JSONObject returnBody = new JSONObject();				
			Set<String> types = new HashSet<String>();
				
			for(Aspect current : aspects.findAll()) {
				types.add(current.getType());
			}
				
			JSONArray aspectTypesArray = new JSONArray(types);
				
			returnBody.put("aspectTypes", aspectTypesArray);
			return ResponseEntity.ok(returnBody.toString());
		} catch (JSONException e) {
			return ResponseEntity.ok("Invalid JSON format");
		}
	}
	
	/**
	 * @return A list of evidence
	 */
	@GetMapping("Evidence")
	public ResponseEntity<String> getAllEvidence() {
		try {
			JSONObject returnBody = new JSONObject();				
			Set<String> types = new HashSet<String>();
				
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
	
	/**
	 * @param key apiKey for the program
	 * @param ghostID ID of record to be deleted
	 * @return Response to delete request
	 */
	@DeleteMapping("Ghosts/{ghostID:.+}")
	public ResponseEntity<String> deleteGhost(@RequestHeader(value="apiKey") String key, @PathVariable("ghostID") int ghostID) {
		try {
			if(validateKey(key)) {
				if(ghosts.existsById(ghostID)){
					ghosts.deleteById(ghostID);
					return ResponseEntity.ok("Ghost deleted");
				}
				
			}
			return ResponseEntity.ok("Invalid apiKey");
		} catch (IllegalArgumentException e) {
			return ResponseEntity.ok("Invalid project id");
		}
	}
	
	/**
	 * @param key apiKey for the program
	 * @param aspectID ID of record to be deleted
	 * @return Response to delete request
	 */
	@DeleteMapping("Aspects/{aspectID:.+}")
	public ResponseEntity<String> deleteAspect(@RequestHeader(value="apiKey") String key, @PathVariable("aspectID") int aspectID) {
		try {
			if(validateKey(key)) {
				if(aspects.existsById(aspectID)) {
					aspects.deleteById(aspectID);
					return ResponseEntity.ok("Aspect deleted");
				}
				return ResponseEntity.ok("Invalid id");
			}
			return ResponseEntity.ok("Invalid apiKey");
		} catch (IllegalArgumentException e1) {
			return ResponseEntity.ok("Invalid project id");
		}
	}
	
	@PostMapping("Ghosts")
	public ResponseEntity<String> saveGhost(@RequestHeader(value="apiKey") String key, @RequestBody String bodyString) {
		try {
			JSONObject body = new JSONObject(bodyString);
			if(validateKey(key)) {
				if(body.has("evidence") && body.has("name") && body.has("description")) {
					Ghost newGhost = new Ghost(body);
					ghosts.save(newGhost);
					return ResponseEntity.ok("Ghost saved");
				}
				return ResponseEntity.ok("Ghost not saved! We need evidence, name, and decription");
			}
			return ResponseEntity.ok("Invalid apiKey");
		} catch (JSONException e) {
			return ResponseEntity.ok("Invalid JSON format");
		}
	}
	
	@PostMapping("Aspects")
	public ResponseEntity<String> saveAspect(@RequestHeader(value="apiKey") String key, @RequestBody String bodyString) {
		try {
			JSONObject body = new JSONObject(bodyString);
			if(validateKey(key)) {
				if(body.has("name") && body.has("content") && body.has("type")) {
					Aspect newAspect = new Aspect(body);
					aspects.save(newAspect);
					return ResponseEntity.ok("Content saved");
				}
				return ResponseEntity.ok("Content not saved! We need content, type and name");
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