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
import dataStructures.database.websiteInfo.WebsiteInfo;
import dataStructures.database.websiteInfo.WebsiteInfoRepository;

@Controller
@RequestMapping("api/info/")
public class WebsiteInfoAPIController {
	
	@Autowired
	private WebsiteInfoRepository wis;
	
	@GetMapping
	public ResponseEntity<String> getWebsiteInfo(){
		try {
			JSONObject returnBody = new JSONObject();
			JSONArray websiteInfo = new JSONArray();
			for(WebsiteInfo current : wis.findAll()) {
				websiteInfo.put(current.toJSONObject());
			}
			returnBody.put("info", websiteInfo);
			return ResponseEntity.ok(returnBody.toString());
		} catch (JSONException e) {
			return ResponseEntity.ok("Invalid JSON format");
		}
	}
	
	@PostMapping
	public ResponseEntity<String> saveWebsiteInfo(
			@RequestHeader(value="apiKey") String key, 
			@RequestBody String bodyString){
		try {
			JSONObject body = new JSONObject(bodyString);
			if(validateKey(key)) {
				WebsiteInfo newWebsiteInfo = new WebsiteInfo(body);
				wis.save(newWebsiteInfo);
				return ResponseEntity.ok("Info changed");
			}
			return ResponseEntity.ok("Invalid apiKey");
		} catch (IllegalArgumentException e1) {
			return ResponseEntity.ok("Invalid Info id");
		} catch (JSONException e) {
			return ResponseEntity.ok("Invalid JSONObject");
		}
	}
	
	@DeleteMapping("{infoID}")
	public ResponseEntity<String> deleteReservation(
			@RequestHeader(value="apiKey") String key,
			@PathVariable("infoID") String infoID){
		try {
			if(validateKey(key)) {
				wis.deleteById(infoID);
				return ResponseEntity.ok("Deleted Info");
			}
			return ResponseEntity.ok("Invalid apiKey");
		} catch (IllegalArgumentException e1) {
			return ResponseEntity.ok("Invalid Info id");
		}
	}

	private boolean validateKey(String key) {
		return key.equals(App.getConfig().getWebsiteApi());
	}
}
