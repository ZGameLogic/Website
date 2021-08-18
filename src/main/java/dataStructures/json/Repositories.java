package dataStructures.json;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class Repositories {
	private int size;
	private int limit;
	private boolean isLastPage;
	private List<Value> values;
	private int start;

	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	@ToString
	public static class Self {
		private String href;
	}

	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	@ToString
	public static class Links {
		private List<Self> self;
		private List<Clone> clone;
	}

	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	@ToString
	public static class Project {
		private String key;
		private int id;
		private String name;
		private String description;
		private String type;
		private Links links;
	}

	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	@ToString
	private static class Clone {
		private String href;
		private String name;
	}

	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	@ToString
	public static class Value {
		private String slug;
		private int id;
		private String name;
		private String description;
		private String hierarchyId;
		private String scmId;
		private String state;
		private String statusMessage;
		private boolean forkable;
		private Project project;
		private Links links;
	}
}
