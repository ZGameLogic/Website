package dataStructures.database.honors.ghosty.ghost;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONException;
import org.json.JSONObject;

import dataStructures.database.ToJSONObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "HonorsGhosts")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HonorsGhost extends ToJSONObject {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	private String name;
	@Column(name = "description", length = 5000)
	private String description;
	private String evidence;

	public HonorsGhost(JSONObject body) {
		try {
			id = body.getInt("id");
		}catch(JSONException e) {
			
		}
		
		try {
			name = body.getString("name");
			description = body.getString("description");
			evidence = body.getString("evidence");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void setGhost(HonorsGhost newGhost) {
		this.name = newGhost.getName();
		this.description = newGhost.getDescription();
		this.evidence = newGhost.getEvidence();
	}
}
