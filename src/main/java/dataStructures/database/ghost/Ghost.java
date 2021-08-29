package dataStructures.database.ghost;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "Ghosts")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Ghost {
	
	public enum Evidence {
		EMF_5, Ghost_Orbs, Spirit_Box, Freezing_Temperatures, Fingerprints, Ghost_Writing, DOTS_Projector;
	}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	private String name;
	@Column(name = "description", length = 5000)
	private String description;
	
	
	@ElementCollection(targetClass=Evidence.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name="Evidence")
    @Column(name="evidence")
	private List<Evidence> evidence;
	
	public JSONObject toJSONObject() {
		try {
			return new JSONObject(new ObjectMapper().writeValueAsString(this));
		} catch (JsonProcessingException | JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Ghost(JSONObject body) {
		try {
			id = body.getInt("id");
			name = body.getString("name");
			description = body.getString("description");
			
			evidence = new LinkedList<Evidence>();
			
			for(int i = 0; i < body.getJSONArray("evidence").length(); i++) {
				switch(body.getJSONArray("evidence").getString(i)){
				case "DOTS_Projector":
					evidence.add(Evidence.DOTS_Projector);
					break;
				case "EMF_5":
					evidence.add(Evidence.EMF_5);
					break;
				case "Ghost_Orbs":
					evidence.add(Evidence.Ghost_Orbs);
					break;
				case "Spirit_Box":
					evidence.add(Evidence.Spirit_Box);
					break;
				case "Freezing_Temperatures":
					evidence.add(Evidence.Freezing_Temperatures);
					break;
				case "Fingerprints":
					evidence.add(Evidence.Fingerprints);
					break;
				case "Ghost_Writing":
					evidence.add(Evidence.Ghost_Writing);
					break;
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void setGhost(Ghost newGhost) {
		this.name = newGhost.getName();
		this.description = newGhost.getDescription();
		this.evidence = newGhost.getEvidence();
	}
}
