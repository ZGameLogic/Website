package dataStructures.database.seaofthieves;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import dataStructures.database.ToJSONObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Voyages")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Voyage extends ToJSONObject {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // gets made when record gets created
	
	@Column(name = "compelted_on")
	private Date date; // gets made when record gets created
	
	@Column(name = "emissary_raised")
	private String emissaryRaised; // gold hoarder
	
	@Column(name = "activity")
	private String voyageType; // loot vault
	
	private double duration; // in hours. 4.5
	
	@Lob
	private String comments;
	
	@Column(name = "emissary_level")
	private int emissaryLevel;
	
	private String ship;
	
	@Column(name = "reward_amount")
	private long rewardAmount; // in gold earned

	@PrePersist 
	private void createdAt() {
		date = new Date();
	}

}
