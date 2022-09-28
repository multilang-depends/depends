package depends.matrix.core;

import java.io.Serializable;

public class LocationInfo implements Serializable {
	String object;
	String file;
	String type;
	Integer lineNumber;
	public LocationInfo(String object, String type, String file, Integer lineNumber){
		if (lineNumber ==null) lineNumber = 0;
		this.object = object;
		this.file = file;
		this.lineNumber = lineNumber;
		this.type = type;
	}

	public String getObject() {
		return object;
	}

	public String getFile() {
		return file;
	}

	public String getType() {
		return type;
	}

	public Integer getLineNumber() {
		return lineNumber;
	}

	@Override
	public String toString(){
		return object + "<" + type +">" + "(" + file + ":"+ lineNumber +")";
	}
}