package dataStructures.json;
import java.util.List;

import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class Commits{
	private List<Value> values;
	private int size;
	private boolean isLastPage;
	private int start;
	private int limit;
	private int nextPageStart;
    
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @ToString
    public static class Self{
    	private String href;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @ToString
    public static class Links{
    	private List<Self> self;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @ToString
    public static class Author{
    	private String name;
    	private String emailAddress;
    	private int id;
    	private String displayName;
    	private boolean active;
    	private String slug;
    	private String type;
    	private Links links;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @ToString
    public static class Committer{
    	private String name;
    	private String emailAddress;
        private int id;
        private String displayName;
        private boolean active;
        private String slug;
        private String type;
        private Links links;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @ToString
    public static class Parent{
    	private String id;
    	private String displayId;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @ToString
    public static class Properties{
        @JsonProperty("jira-key") 
        private List<String> jiraKey;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @ToString
    public static class Value{
    	private String id;
    	private String displayId;
    	private Author author;
    	private Object authorTimestamp;
    	private Committer committer;
    	private Object committerTimestamp;
    	private String message;
        private List<Parent> parents;
        private Properties properties;
        
        @Transient
        @Setter
    	private int state;
    }
}


