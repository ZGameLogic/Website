package dataStructures.database.ghosty.aspect;

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
@Table(name = "Aspects")
@ToString
@Getter
public class Aspect extends ToJSONObject {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Lob
    private String content;

    public Aspect(JSONObject body) {
    	try {
			id = body.getInt("id");
		}catch(JSONException e) {
			
		}
    	
		try {
			name = body.getString("name");
			content = body.getString("content");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
    
    public void setAspect(Aspect aspect) {
    	this.name = aspect.name;
    	this.content = aspect.content;
    }
}
