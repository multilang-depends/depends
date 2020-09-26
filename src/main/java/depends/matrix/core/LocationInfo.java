package depends.matrix.core;

import java.io.Serializable;

public class LocationInfo implements Serializable {
	String object;
	String file;
	Integer lineNumber;
	public LocationInfo(String object, String file, Integer lineNumber){
		if (lineNumber ==null) lineNumber = 0;
		this.object = object;
		this.file = file;
		this.lineNumber = lineNumber;
	}

	public String getObject() {
		return object;
	}

	public String getFile() {
		return file;
	}

	public Integer getLineNumber() {
		return lineNumber;
	}

	@Override
	public String toString(){
		return object + "(" + file + ":"+ lineNumber +")";
	}
}