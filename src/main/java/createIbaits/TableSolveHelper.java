package createIbaits;

import org.springframework.util.StringUtils;

/**
 * Created by jnkmhbl on 16/5/17.
 */
public class TableSolveHelper {

    private static boolean containsSqlType(String column){
        if(StringUtils.isEmpty(column)){
            return false;
        }
        if((column.contains("text")||column.contains("decimal")||column.contains("int(") || column.contains("datetime")||column.contains("char"))){
            return true;
        }
        return false;
    }
    //content是split ,之后的一个子段落 ; 通过判断content内是否包含数据库内的类型来判断是否是一个column的定义
    public static boolean isColumn(String content){
       return containsSqlType(content);
    }

}
