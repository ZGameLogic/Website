package dataStructures.json;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class Build {

	private int size;
	private int limit;
	private boolean isLastPage;
	private List<Value> values;
	private int start;
    
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @ToString
    public static class Value{
    	private String state;
    	private String key;
    	private String name;
    	private String url;
    	private String description;
    	private long dateAdded;
    }
}
