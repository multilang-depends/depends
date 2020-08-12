package depends.entity;

import java.io.Serializable;

public class Location implements Serializable {
    Integer line = null;
    public Integer getLine(){
        return line;
    }
    public void setLine(int line){
        this.line = line;
    }
}
