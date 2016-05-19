package createIbaits;

import java.util.List;

/**
 * Created by jnkmhbl on 16/5/18.
 */
public class SqlClassMapping {
    private List<Integer> classIdList ;
    private String className ;
    private String resultID;

    public List<Integer> getClassIdList() {
        return classIdList;
    }

    public void setClassIdList(List<Integer> classIdList) {
        this.classIdList = classIdList;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getResultID() {
        return resultID;
    }

    public void setResultID(String resultID) {
        this.resultID = resultID;
    }
}
