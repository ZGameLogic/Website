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
    public List<Value> values;
    public int size;
    public boolean isLastPage;
    public int start;
    public int limit;
    public int nextPageStart;
    
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @ToString
    public static class Self{
        public String href;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @ToString
    public static class Links{
        public List<Self> self;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @ToString
    public static class Author{
        public String name;
        public String emailAddress;
        public int id;
        public String displayName;
        public boolean active;
        public String slug;
        public String type;
        public Links links;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @ToString
    public static class Committer{
        public String name;
        public String emailAddress;
        public int id;
        public String displayName;
        public boolean active;
        public String slug;
        public String type;
        public Links links;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @ToString
    public static class Parent{
        public String id;
        public String displayId;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @ToString
    public static class Properties{
        @JsonProperty("jira-key") 
        public List<String> jiraKey;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @ToString
    public static class Value{
        public String id;
        public String displayId;
        public Author author;
        public Object authorTimestamp;
        public Committer committer;
        public Object committerTimestamp;
        public String message;
        public List<Parent> parents;
        public Properties properties;
        
        @Transient
        @Setter
    	private int state;
    }
}


