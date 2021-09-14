package dataStructures.database.websiteInfo;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
public class WebsiteInfo {
	
	@Id
	private String name;
	
	@ElementCollection
	private List<String> information;

}
