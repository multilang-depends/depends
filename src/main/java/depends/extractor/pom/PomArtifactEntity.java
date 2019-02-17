package depends.extractor.pom;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import depends.entity.Entity;
import depends.entity.FileEntity;
import depends.entity.TypeEntity;

public class PomArtifactEntity extends TypeEntity {
	HashMap<String,String> properties;
	public PomArtifactEntity(String simpleName, Entity parent, Integer id) {
		super(simpleName, parent, id);
		properties = new HashMap<>();
	}

	public String getProperty(String key) {
		String v = properties.get(key);
		if (v!=null) return v;
		FileEntity file = (FileEntity)(this.getAncestorOfType(FileEntity.class));
		List<Entity> parents = file.getImportedRelationEntities();
		for(Entity parent:parents) {
			if (parent instanceof PomArtifactEntity) {
				return ((PomArtifactEntity)parent).getProperty(key);
			}
		}
		return null;
	}
	
	public void addProperty(String key, String value) {
		value = replaceProperty(value);
		properties.put(key, value);
	}

	public String replaceProperty(String content) {
	    Pattern pattern = Pattern.compile("\\$\\{[_A-Za-z0-9\\-\\.]*\\}");
	    Matcher matcher = pattern.matcher(content);
	    String s = content;
	    while (matcher.find()) {
	    	String keyPattern = matcher.group();
	    	String key = keyPattern.replace("${", "").replace("}","");
	    	String value = getProperty(key);
	    	if (value!=null)
	        s = s.replace(keyPattern, value);
	    }
	    return s;
	};

}
