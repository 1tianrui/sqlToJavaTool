package createIbaits;

import java.util.List;

/**
 * Created by jnkmhbl on 16/5/10.
 */
public class ClassEntity {
    private String className ;
    private List<ColumnEntry> attributeMap ;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<ColumnEntry> getAttributeMap() {
        return attributeMap;
    }

    public void setAttributeMap(List<ColumnEntry> attributeMap) {
        this.attributeMap = attributeMap;
    }

}
