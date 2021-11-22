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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.App;
import dataStructures.database.seaofthieves.Voyage;
import dataStructures.database.seaofthieves.VoyageRepository;

@Controller
@RequestMapping("api/Voyages")
public class VoyageAPIController {
	
	@Autowired
	private VoyageRepository voyages;
	
	@GetMapping
	public ResponseEntity<String> getAllVoyages() {
		try {
			JSONObject returnBody = new JSONObject();
			JSONArray voyageJSON = new JSONArray();
			for(Voyage current : voyages.findAll()) {
				voyageJSON.put(current.toJSONObject());
			}
			returnBody.put("voyages", voyageJSON);
			return ResponseEntity.ok(returnBody.toString());
		} catch (JSONException e) {
			return ResponseEntity.ok("Invalid JSON format");
		}
	}
	
	@DeleteMapping("/{voyageID:.+}")
	public ResponseEntity<String> deleteVoyage(@RequestHeader(value="apiKey") String key, @PathVariable("voyageID") int voyageID) {
		try {
			if(validateKey(key)) {
				if(voyages.existsById(voyageID)) {
					voyages.deleteById(voyageID);
					return ResponseEntity.ok("Voyage deleted");
				}
				return ResponseEntity.ok("Invalid id");
			}
			return ResponseEntity.ok("Invalid apiKey");
		} catch (IllegalArgumentException e1) {
			return ResponseEntity.ok("Invalid voyage id");
		}
	}
	
	@PostMapping
	public ResponseEntity<String> createVoyage(@RequestHeader(value="apiKey") String key, @RequestBody String bodyString) {
		
		if(validateKey(key)) {
			try {
				Voyage newVoyage = new ObjectMapper().readValue(bodyString, Voyage.class);
				voyages.save(newVoyage);
			} catch (JsonMappingException e) {
				return ResponseEntity.ok("Invalid JSON format");
			} catch (JsonProcessingException e) {
				return ResponseEntity.ok("Invalid JSON format");
			}
			return ResponseEntity.ok("Voyage changed");
		}
		return ResponseEntity.ok("Invalid apiKey");
	}
	
	private boolean validateKey(String key) {
		return key.equals(App.getConfig().getWebsiteApi());
	}	

}
