package createIbaits;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jnkmhbl on 16/5/18.
 */
public class SqlParam {
    private int type ;
    private WhereCondition condition;
    private List<Integer> preIndex ;
    private String id ;

    public SqlParam(){
        type =-1;
        condition = new WhereCondition();
        preIndex = new ArrayList<Integer>();
        id ="";
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Integer> getPreIndex() {
        return preIndex;
    }

    public void setPreIndex(List<Integer> preIndex) {
        this.preIndex = preIndex;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public WhereCondition getCondition() {
        return condition;
    }

    public void setCondition(WhereCondition condition) {
        this.condition = condition;
    }
}
