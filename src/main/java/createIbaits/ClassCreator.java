package createIbaits;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jnkmhbl on 16/5/10.
 */
public class ClassCreator {
    private String convertToTuofeng(String name,boolean firstKey){
        boolean bottomPre = firstKey ;
        StringBuilder className = new StringBuilder();
        for(char a:name.toCharArray()){
            if(a == '\'' || a == '‘'){
                continue;
            }
            if(a !='_'&&(!bottomPre)){
                className.append(String.valueOf(a));
            }
            if(a=='_'){
                bottomPre=true;
                continue;
            }
            if(a!='_'&&bottomPre){
                className.append(String.valueOf(Character.toUpperCase(a)));
                bottomPre=false;
            }
        }
        return className.toString();
    }
  public  ClassEntity createClassEntityFromTable(TableEntity entity){
      ClassEntity classEntity = new ClassEntity();
      classEntity.setClassName(this.convertToTuofeng(entity.getTableName(), true));
      List<ColumnEntry> columnEntryList = new ArrayList<ColumnEntry>();

      List<ColumnEntry> columnMap = entity.getColumn();
      for(ColumnEntry entry : columnMap){
         String attr = this.convertToTuofeng(entry.getName(),false);
         String type = this.typeJudge(entry.getType());
         ColumnEntry tmpEntry = new ColumnEntry();
          tmpEntry.setType(type);
          tmpEntry.setName(attr);
          columnEntryList.add(tmpEntry);
       }
      classEntity.setAttributeMap(columnEntryList);
      return classEntity;
  }

    private String typeJudge(String content){
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
