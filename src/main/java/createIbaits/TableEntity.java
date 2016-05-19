package createIbaits;

import java.util.List;
import java.util.Map;

/**
 * Created by jnkmhbl on 16/5/9.
 */
public class TableEntity {
    private String tableName;
    private List<ColumnEntry> columns ;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<ColumnEntry> getColumn() {
        return columns;
    }

    public void setColumn(Map<String, String> column) {
        this.columns = columns;
    }

    public TableEntity(String tableName,List<ColumnEntry> columns){
        this.tableName = tableName;
        this.columns = columns ;
    }
}
