package dataStructures.database.websiteInfo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONException;
import org.json.JSONObject;

import dataStructures.database.ToJSONObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "Information")
public class WebsiteInfo extends ToJSONObject {
	
	@Id
	private String name;
	private String information;
	
	public WebsiteInfo(JSONObject body) {
		try {
			name = body.getString("name");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		try {
			information = body.getString("information");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
