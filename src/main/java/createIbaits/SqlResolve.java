package createIbaits;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jnkmhbl on 16/5/5.
 *
 * 从表创建语句导出表名字，字段名字和字段类型
 */
public class SqlResolve {


    public TableEntity solve(String sql){
        List<Character> chars = new ArrayList<Character>();
        chars.add('\r'); chars.add('\n');
        String tmp = TrStringHelper.filterCharacter(sql, chars);
        return new TableEntity(resolveTableName(tmp),resolveColumn(tmp));
    }


    private   String resolveTableName(String sql){
      List<String> elements = new ArrayList<String>();
      boolean tableReaded = false ;
      StringBuilder builder =  new StringBuilder();
      int index =0 ;
      boolean tableNameReaded =false ;
        while(!tableNameReaded){
            char tmp =sql.charAt(index);
            index ++;
            if(tmp == ' '){
                if(builder.length() !=0 ){
                    String content =builder.toString();
                    if(tableReaded){
                        filtInvalidCharTwoSides(content.toString());
                        return filtInvalidCharTwoSides(content.toString());
                    }
                    builder = new StringBuilder();
                    if(content.equalsIgnoreCase("table")){
                        tableReaded = true;
                    }
                }
            }else{
                builder.append(tmp);
            }
        }
        return  filtInvalidCharTwoSides(toString());
    }



    //取出表名两侧可能存在的符号
    private String filtInvalidCharTwoSides(String tableName){
        if(StringUtils.isEmpty(tableName)){
            return  "";
        }
        int start =0;
        int end =tableName.length();
        while(true) {
            if (tableName.charAt(start) < 'A' || tableName.charAt(start) > 'z' ||( tableName.charAt(start)< 'a' &&  tableName.charAt(start) >'Z' ) ) {
                start = start + 1;
            }else{
                break;
            }
        }
        while(true) {
            if (tableName.charAt(end-1) < 'A' || tableName.charAt(end-1) > 'z' ||( tableName.charAt(end-1)< 'a' &&  tableName.charAt(end-1) >'Z' ) ) {
                end = end - 1;
            }else{
                break;
            }
        }
       return tableName.substring(start, end);
    }


    private List<ColumnEntry> resolveColumn(String sql){
       String content = sql.substring(sql.indexOf('(')+1,sql.lastIndexOf(')')-1);
        List<ColumnEntry> columnEntries = new ArrayList<ColumnEntry>();
       String[] columns = content.split(",");
       int newIndex = 0;
       //将可能被,号隔离开的行重新凑到一起
        String [] newColumns = new String[columns.length];
        for(int i =0 ;i<columns.length;i++){
            String tmp = columns[i];
            for(int tmpIndex = tmp.length()-1;tmpIndex >= 0 ; tmpIndex -- ){
                char code = tmp.charAt(tmpIndex);
                if(code == ')'){
                    newColumns[newIndex] = tmp;
                    newIndex ++;
                    break ;
                }
                if(code == '(' && i != columns.length -1){
                    newColumns[newIndex] = columns[i]+columns[i+1];
                    newIndex ++;
                    i++;
                    break;
                }
                if(tmpIndex == 0){
                    newColumns[newIndex] = columns[i];
                    newIndex ++;
                    break;
                }
            }
        }
       for(String column : newColumns){
         if(!isColumnContent(column)){
             continue;
         }
           //取出字段名称和类型
          String [] eles = TrStringHelper.splitBlank(column);

           //todo 已经支持自动生成column name  & type
          try {
              ColumnEntry entry = new ColumnEntry();
              entry.setName(filtInvalidCharTwoSides(eles[0]));
              entry.setType(eles[1]);
              columnEntries.add(entry);
          }catch (Exception e){
              System.out.println("hehe");
          }
       }
          return columnEntries;
    }

    private boolean isColumnContent(String column){
       return TableSolveHelper.isColumn(column);
    }


}
