package dataStructures;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
@ToString
@Entity
@Table(name = "Projects")
public class Project {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
	private String name;
	private String description;
	private String url;
	private String websiteInfo;
	
	public Project() {
		
	}
	
	public Project(JSONObject body) throws JSONException {
		if(body.has("name")) {
			name = body.getString("name");
			if(body.has("description")) {
				description = body.getString("description");
			}
			if(body.has("id")) {
				id = body.getInt("id");
			}
			if(body.has("url")) {
				url = body.getString("url");
			}
			if(body.has("websiteInfo")) {
				websiteInfo = body.getString("websiteInfo");
			}
		} else {
			throw new JSONException("No key name in JSONObject");
		}
	}
	
	public JSONObject toJSONObject() {
		try {
			return new JSONObject(new ObjectMapper().writeValueAsString(this));
		} catch (JsonProcessingException | JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void setProject(Project p) {
		id = p.getId();
		name = p.getName();
		description = p.getDescription();
		url = p.getUrl();
		websiteInfo = p.getWebsiteInfo();
	}

}
