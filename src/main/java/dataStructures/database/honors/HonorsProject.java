package dataStructures.database.honors;

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
@Table(name = "HonorsProjects")
public class HonorsProject extends ToJSONObject {
	
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
	
	public HonorsProject() {
		
	}
	
	public HonorsProject(JSONObject body) {
		try {
			id = body.getInt("id");
		} catch (JSONException e) {
			
		}
		try {
			name = body.getString("name");
			url = body.getString("url");
			websiteInfo = body.getString("websiteInfo");
			description = body.getString("description");
		} catch (JSONException e) {
			
		}
	}
	
	public void setProject(HonorsProject p) {
		id = p.getId();
		name = p.getName();
		description = p.getDescription();
		url = p.getUrl();
		websiteInfo = p.getWebsiteInfo();
	}

}
