package dataStructures.database.project;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.json.JSONException;
import org.json.JSONObject;

import dataStructures.database.ToJSONObject;
import dataStructures.json.Commits;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
@ToString
@Entity
@Table(name = "Projects")
public class Project extends ToJSONObject {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
	private String name;
	@Column(name = "description", length = 1000)
	private String description;
	private String url;
	@Column(name = "website_info", length = 10000)
	private String websiteInfo;
	
	@Transient
	private Commits commits;
	
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
	
	public void setProject(Project p) {
		id = p.getId();
		name = p.getName();
		description = p.getDescription();
		url = p.getUrl();
		websiteInfo = p.getWebsiteInfo();
	}

}
