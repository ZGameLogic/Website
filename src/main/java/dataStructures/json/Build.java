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

	public int size;
    public int limit;
    public boolean isLastPage;
    public List<Value> values;
    public int start;
    
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @ToString
    public static class Value{
        public String state;
        public String key;
        public String name;
        public String url;
        public String description;
        public long dateAdded;
    }
}
