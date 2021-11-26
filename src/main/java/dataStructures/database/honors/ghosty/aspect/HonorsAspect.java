package dataStructures.database.honors.ghosty.aspect;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.json.JSONException;
import org.json.JSONObject;

import dataStructures.database.ToJSONObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "HonorsAspects")
@ToString
@Getter
public class HonorsAspect extends ToJSONObject {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Lob
    private String content;
    private String type;

    public HonorsAspect(JSONObject body) {
    	try {
			id = body.getInt("id");
		}catch(JSONException e) {
			
		}
    	
		try {
			name = body.getString("name");
			content = body.getString("content");
			type = body.getString("type");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
    
    public void setAspect(HonorsAspect aspect) {
    	this.name = aspect.name;
    	this.content = aspect.content;
    	this.type = aspect.type;
    }
}
