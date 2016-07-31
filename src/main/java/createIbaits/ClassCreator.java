package createIbaits;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jnkmhbl on 16/5/10.
 */
public class ClassCreator {

  public  ClassEntity create(TableEntity entity){
      ClassEntity classEntity = new ClassEntity();
      classEntity.setClassName(TrStringHelper.convertToTuofeng(entity.getTableName(), true));
      List<ColumnEntry> columnEntryList = new ArrayList<ColumnEntry>();

      List<ColumnEntry> columnMap = entity.getColumn();
      for(ColumnEntry entry : columnMap){
         String attr = TrStringHelper.convertToTuofeng(entry.getName(), false);
         String type = SqlJavaTypeMapper.javaTypeFromSqlType(entry.getType());
         ColumnEntry tmpEntry = new ColumnEntry();
          tmpEntry.setType(type);
          tmpEntry.setName(attr);
          columnEntryList.add(tmpEntry);
       }
      classEntity.setAttributeMap(columnEntryList);
      return classEntity;
  }

}
