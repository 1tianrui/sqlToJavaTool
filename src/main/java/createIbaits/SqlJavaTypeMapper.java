package createIbaits;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jnkmhbl on 16/7/30.
 */
public class SqlJavaTypeMapper {
    public static  String  javaTypeFromSqlType(String content){
        Pattern pattern =  Pattern.compile("int\\([1-9]\\)");
        Matcher intMatcher = pattern.matcher(content);
        if(intMatcher.find()){
            return "int";
        }
        pattern = Pattern.compile("int\\(10\\)}");
        intMatcher = pattern.matcher(content);
        if(intMatcher.find()){
            return "int";
        }

        if(content.contains("int")){
            return "long";
        }

        if(content.contains("varchar")){
            return "String";
        }
        if(content.contains("datetime")){
            return "Date";
        }
        if(content.contains("double")){
            return "double";
        }
        if(content.contains("decimal")){
            return "BigDecimal";
        }
        if(content.contains("text")){
            return "String";
        }
        return "";
    }
}
