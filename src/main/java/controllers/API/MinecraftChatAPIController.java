package controllers.API;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

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
				String goodOne = java.net.URLDecoder.decode(bodyString, StandardCharsets.UTF_8.name());
				ChatMessage newChat = new ObjectMapper().readValue(goodOne.substring(0, goodOne.length() - 1), ChatMessage.class);
				chat.save(newChat);
			} catch (JsonMappingException e) {
				return ResponseEntity.ok("Invalid JSON format");
			} catch (JsonProcessingException e) {
				return ResponseEntity.ok("Invalid JSON format");
			} catch (UnsupportedEncodingException e) {
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
