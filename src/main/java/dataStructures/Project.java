package dataStructures;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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

}
