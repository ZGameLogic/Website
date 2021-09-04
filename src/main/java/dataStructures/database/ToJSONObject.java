package dataStructures.database;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class ToJSONObject {
	
	public JSONObject toJSONObject() {
		try {
			return new JSONObject(new ObjectMapper().writeValueAsString(this));
		} catch (JsonProcessingException | JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

}
