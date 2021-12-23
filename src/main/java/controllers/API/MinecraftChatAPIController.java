package controllers.API;

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
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.App;
import dataStructures.database.minecraft.chat.ChatMessage;
import dataStructures.database.minecraft.chat.ChatRepository;

@Controller
@RequestMapping("api/Minecraft")
public class MinecraftChatAPIController {

	@Autowired
	private ChatRepository chat;
	
	@GetMapping
	public ResponseEntity<String> getAllChat() {
		try {
			JSONObject returnBody = new JSONObject();
			JSONArray chatJSON = new JSONArray();
			for(ChatMessage current : chat.findAll()) {
				chatJSON.put(current.toJSONObject());
			}
			returnBody.put("chat", chatJSON);
			return ResponseEntity.ok(returnBody.toString());
		} catch (JSONException e) {
			return ResponseEntity.ok("Invalid JSON format");
		}
	}
	
	@PostMapping
	public ResponseEntity<String> createChat(@RequestHeader(value="apiKey") String key, @RequestBody String bodyString) {
		
		if(validateKey(key)) {
			try {
				bodyString = bodyString.replace("%7B", "{");
				bodyString = bodyString.replace("%22", "\"");
				bodyString = bodyString.replace("%3A", ":");
				bodyString = bodyString.replace("%7D=", "}");
				bodyString = bodyString.replace("%2C", ",");
				System.out.println(bodyString);
				ChatMessage newChat = new ObjectMapper().readValue(bodyString, ChatMessage.class);
				chat.save(newChat);
			} catch (JsonMappingException e) {
				return ResponseEntity.ok("Invalid JSON format");
			} catch (JsonProcessingException e) {
				return ResponseEntity.ok("Invalid JSON format");
			}
			return ResponseEntity.ok("Chat changed");
		}
		return ResponseEntity.ok("Invalid apiKey");
	}
	
	private boolean validateKey(String key) {
		return key.equals(App.getConfig().getWebsiteApi());
	}
}
